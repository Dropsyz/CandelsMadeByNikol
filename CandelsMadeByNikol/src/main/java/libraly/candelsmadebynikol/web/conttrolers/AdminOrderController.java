package libraly.candelsmadebynikol.web.conttrolers;

import libraly.candelsmadebynikol.models.dto.OrderDTO;
import libraly.candelsmadebynikol.services.interfaces.OrderServiceClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin/orders")
public class AdminOrderController {

    private final OrderServiceClient orderServiceClient;

    public AdminOrderController(OrderServiceClient orderServiceClient) {
        this.orderServiceClient = orderServiceClient;
    }

    // Показва всички поръчки
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String manageOrders(Model model) {
        List<OrderDTO> orders = orderServiceClient.getAllOrders();
        model.addAttribute("orders", orders);
        return "admin-orders";
    }

    // Сменя статуса
    @PostMapping("/status/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateStatus(@PathVariable("id") UUID id, @RequestParam("status") String status) {
        orderServiceClient.updateStatus(id, status);
        return "redirect:/admin/orders";
    }
}
