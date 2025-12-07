package libraly.candelsmadebynikol.web.conttrolers;

import jakarta.validation.Valid;
import libraly.candelsmadebynikol.models.dto.AddCandleDTO;
import libraly.candelsmadebynikol.repository.CandleRepository;
import libraly.candelsmadebynikol.repository.CategoryRepository;
import libraly.candelsmadebynikol.services.implementation.ReviewServiceImpl;
import libraly.candelsmadebynikol.services.interfaces.CandleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/candles")
public class CandleController {

    private final CandleService candleService;
    private final CategoryRepository categoryRepository;
    private final ReviewServiceImpl reviewService;
    private final CandleRepository candleRepository;

    @Autowired
    public CandleController(CandleService candleService, CategoryRepository categoryRepository, ReviewServiceImpl reviewService, CandleRepository candleRepository) {
        this.candleService = candleService;
        this.categoryRepository = categoryRepository;
        this.reviewService = reviewService;
        this.candleRepository = candleRepository;
    }

    @ModelAttribute("addCandleDTO")
    public AddCandleDTO initAddCandleDTO() {
        return new AddCandleDTO();
    }


    @GetMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addCandle(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());
        return "add-candle";
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addCandleConfirm(@Valid AddCandleDTO addCandleDTO,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll());

            return "add-candle";
        }

        try {
            candleService.addCandle(addCandleDTO);
        } catch (Exception e) {

            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("errorMessage", "Error saving candle: " + e.getMessage());
            return "add-candle";
        }

        return "redirect:/";
    }


    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editCandle(@PathVariable("id") UUID id, Model model) {
        AddCandleDTO dto = candleService.findCandleById(id);

        model.addAttribute("addCandleDTO", dto);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("candleId", id);

        return "edit-candle";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editCandleConfirm(@PathVariable("id") UUID id,
                                    @Valid AddCandleDTO addCandleDTO,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addCandleDTO", addCandleDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addCandleDTO", bindingResult);
            return "redirect:/candles/edit/" + id;
        }

        candleService.editCandle(id, addCandleDTO);

        return "redirect:/";
    }


    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteCandle(@PathVariable UUID id) {
        candleService.deleteCandle(id);
        return "redirect:/";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") UUID id, Model model) {
        var candle = candleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Candle not found"));

        model.addAttribute("candle", candle);
        model.addAttribute("addReviewDTO", new libraly.candelsmadebynikol.models.dto.AddReviewDTO()); // За формата

        return "candle-details";
    }

    // 2. Обработва добавянето на ревю
    @PostMapping("/details/{id}/add-review")
    @PreAuthorize("isAuthenticated()")
    public String addReview(@PathVariable("id") UUID id,
                            @Valid libraly.candelsmadebynikol.models.dto.AddReviewDTO addReviewDTO,
                            BindingResult bindingResult,
                            java.security.Principal principal,
                            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addReviewDTO", addReviewDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addReviewDTO", bindingResult);
            return "redirect:/candles/details/" + id;
        }

        reviewService.addReview(addReviewDTO, id, principal.getName());
        return "redirect:/candles/details/" + id;
    }
}


