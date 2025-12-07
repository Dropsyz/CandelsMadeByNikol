package libraly.candelsmadebynikol.web.conttrolers;

import libraly.candelsmadebynikol.services.implementation.ShopServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.UUID;

@Controller
@RequestMapping("/shop")
public class ShopController {

    private final ShopServiceImpl shopService;

    @Autowired
    public ShopController(ShopServiceImpl shopService) {
        this.shopService = shopService;
    }

    @PostMapping("/buy/{id}")
    @PreAuthorize("isAuthenticated()")
    public String buyCandle(@PathVariable("id") UUID candleId,
                            Principal principal,
                            RedirectAttributes redirectAttributes) {

        try {
            shopService.buyCandle(candleId, principal.getName());
            redirectAttributes.addFlashAttribute("successMessage", "Order placed successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Order failed: " + e.getMessage());
        }

        return "redirect:/";
    }
}