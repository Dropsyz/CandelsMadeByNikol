package libraly.candelsmadebynikol.web.conttrolers;

import jakarta.validation.Valid;
import libraly.candelsmadebynikol.models.dto.AddCategoryDTO;
import libraly.candelsmadebynikol.repository.CategoryRepository;
import libraly.candelsmadebynikol.services.interfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @ModelAttribute("addCategoryDTO")
    public AddCategoryDTO addCategoryDTO() {
        return new AddCategoryDTO();
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addCategory(Model model) {
        return "add-category";
    }
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addCategoryConfirm(@Valid AddCategoryDTO addCategoryDTO,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addCategoryDTO", addCategoryDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.category", bindingResult);
            return "redirect:/categories/add";
        }

        try{
            categoryService.addCategory(addCategoryDTO);
        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute("addCategoryDTO", addCategoryDTO);
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/categories/add";
        }

        return "redirect:/";
    }
}
