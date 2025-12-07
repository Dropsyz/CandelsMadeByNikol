package libraly.candelsmadebynikol.services;

import libraly.candelsmadebynikol.common.exceptions.ObjectNotFoundException;
import libraly.candelsmadebynikol.models.dto.AddCandleDTO;
import libraly.candelsmadebynikol.models.dto.CandleViewDTO;
import libraly.candelsmadebynikol.models.entity.CandleEntity;
import libraly.candelsmadebynikol.models.entity.CategoryEntity;
import libraly.candelsmadebynikol.repository.CandleRepository;
import libraly.candelsmadebynikol.repository.CategoryRepository;
import libraly.candelsmadebynikol.services.implementation.CandleServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CandleServiceImplTest {

    @Mock
    private CandleRepository candleRepository;

    @Mock
    private CategoryRepository categoryRepository;

    private CandleServiceImpl candleService;

    @BeforeEach
    void setUp() {
        candleService = new CandleServiceImpl(candleRepository, categoryRepository);
    }

    @Test
    void testGetAllCandles_ReturnsMappedDTOs() {
        CandleEntity candle = new CandleEntity();
        candle.setId(UUID.randomUUID());
        candle.setName("Vanilla Dream");
        candle.setPrice(BigDecimal.TEN);
        candle.setImageUrl("image.jpg");

        when(candleRepository.findAll()).thenReturn(List.of(candle));

        List<CandleViewDTO> result = candleService.getAllCandles();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("Vanilla Dream", result.get(0).getName());
    }

    @Test
    void testAddCandle_Success() {
        // Arrange
        UUID categoryId = UUID.randomUUID();
        CategoryEntity category = new CategoryEntity();
        category.setId(categoryId);

        AddCandleDTO dto = new AddCandleDTO();
        dto.setCandleName("New Candle");
        dto.setPrice(BigDecimal.valueOf(20));
        dto.setDescription("Test Desc");
        dto.setSku("SKU-123");
        dto.setCategoryId(categoryId);


        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));


        candleService.addCandle(dto);


        ArgumentCaptor<CandleEntity> candleCaptor = ArgumentCaptor.forClass(CandleEntity.class);
        verify(candleRepository).save(candleCaptor.capture());

        CandleEntity savedCandle = candleCaptor.getValue();
        Assertions.assertEquals("New Candle", savedCandle.getName());
        Assertions.assertEquals(category, savedCandle.getCategory());
    }

    @Test
    void testDeleteCandle_ThrowsWhenNotFound() {
        UUID id = UUID.randomUUID();
        when(candleRepository.existsById(id)).thenReturn(false);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            candleService.deleteCandle(id);
        });
    }
}