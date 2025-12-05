package libraly.candelsmadebynikol.services.implementation;

import libraly.candelsmadebynikol.models.dto.AddCandleDTO;
import libraly.candelsmadebynikol.models.dto.CandleViewDTO;
import libraly.candelsmadebynikol.models.entity.CandleEntity;
import libraly.candelsmadebynikol.models.entity.CategoryEntity;
import libraly.candelsmadebynikol.repository.CandleRepository;
import libraly.candelsmadebynikol.repository.CategoryRepository;
import libraly.candelsmadebynikol.services.interfaces.CandleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CandleServiceImpl implements CandleService {

    private final CandleRepository candleRepository;
    private final CategoryRepository categoryRepository;
    private final String UPLOAD_DIR = "uploads";
    private static final String DEFAULT_IMAGE = "/images/default-candle.png";

    public CandleServiceImpl(CandleRepository candleRepository, CategoryRepository categoryRepository) {
        this.candleRepository = candleRepository;
        this.categoryRepository = categoryRepository;
    }
    @Override
    public void addCandle(AddCandleDTO addCandleDTO) {
        CategoryEntity category = categoryRepository.findById(addCandleDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found!"));


        String imagePath = saveFile(addCandleDTO.getImage());


        CandleEntity newCandle = new CandleEntity();
        newCandle.setName(addCandleDTO.getCandleName());
        newCandle.setDescription(addCandleDTO.getDescription());
        newCandle.setPrice(addCandleDTO.getPrice());
        newCandle.setSku(addCandleDTO.getSku());
        newCandle.setCategory(category);
        newCandle.setImageUrl(imagePath);

        candleRepository.save(newCandle);
        log.info("Successfully added candle: {}", newCandle.getName());
    }

    @Override
    public List<CandleViewDTO> getAllCandles() {
        return candleRepository.findAll().stream()
                .map(this::mapToViewDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AddCandleDTO findCandleById(UUID id) {
        CandleEntity candle = findEntityById(id);
        return mapToEditDTO(candle);
    }

    @Override
    public void editCandle(UUID id, AddCandleDTO addCandleDTO) {
        CandleEntity candle = findEntityById(id);

        CategoryEntity category = categoryRepository.findById(addCandleDTO.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found!"));

        // Логика за смяна на снимката
        if (addCandleDTO.getImage() != null && !addCandleDTO.getImage().isEmpty()) {
            String newImagePath = saveFile(addCandleDTO.getImage());
            candle.setImageUrl(newImagePath);
            log.info("Updated image for candle: {}", candle.getName());
        }

        candle.setName(addCandleDTO.getCandleName());
        candle.setDescription(addCandleDTO.getDescription());
        candle.setPrice(addCandleDTO.getPrice());
        candle.setSku(addCandleDTO.getSku());
        candle.setCategory(category);

        candleRepository.save(candle);
        log.info("Edited candle: {}", candle.getName());
    }

    @Override
    public void deleteCandle(UUID id) {
        if (!candleRepository.existsById(id)) {
            throw new IllegalArgumentException("Candle with ID " + id + " not found!");
        }
        candleRepository.deleteById(id);
        log.info("Deleted candle with ID: {}", id);
    }

    // --- Helper Methods ---

    private CandleEntity findEntityById(UUID id) {
        return candleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Candle with ID " + id + " not found!"));
    }

    private CandleViewDTO mapToViewDTO(CandleEntity candle) {
        CandleViewDTO dto = new CandleViewDTO();
        dto.setId(candle.getId());
        dto.setName(candle.getName());
        dto.setPrice(candle.getPrice());
        dto.setImageUrl(candle.getImageUrl());
        return dto;
    }

    private AddCandleDTO mapToEditDTO(CandleEntity candle) {
        AddCandleDTO dto = new AddCandleDTO();
        dto.setCandleName(candle.getName());
        dto.setDescription(candle.getDescription());
        dto.setPrice(candle.getPrice());
        dto.setImageUrl(candle.getImageUrl());
        dto.setSku(candle.getSku());

        if (candle.getCategory() != null) {
            dto.setCategoryId(candle.getCategory().getId());
        }
        return dto;
    }

    private String saveFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return DEFAULT_IMAGE;
        }

        try {

            String originalName = file.getOriginalFilename();
            String extension = "";
            if (originalName != null && originalName.contains(".")) {
                extension = originalName.substring(originalName.lastIndexOf("."));
            }
            String fileName = UUID.randomUUID().toString() + extension;


            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                log.info("Created upload directory: {}", uploadPath.toAbsolutePath());
            }

            // Запис
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/" + fileName;

        } catch (IOException e) {
            log.error("Error saving file", e);
            throw new RuntimeException("Could not save image file: " + e.getMessage());
        }
    }
}
