package libraly.candelsmadebynikol.web.conttrolers;

import libraly.candelsmadebynikol.models.dto.OrderDTO;
import libraly.candelsmadebynikol.models.entity.UserEntity;
import libraly.candelsmadebynikol.repository.UserRepository;
import libraly.candelsmadebynikol.services.interfaces.OrderServiceClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/my-orders")
public class UserOrderController {

    private final OrderServiceClient orderServiceClient;
    private final UserRepository userRepository;

    public UserOrderController(OrderServiceClient orderServiceClient, UserRepository userRepository) {
        this.orderServiceClient = orderServiceClient;
        this.userRepository = userRepository;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public String getMyOrders(Model model, @AuthenticationPrincipal UserDetails userDetails) {

        UserEntity user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        List<OrderDTO> myOrders = orderServiceClient.getOrdersByUser(user.getId());

        model.addAttribute("orders", myOrders);

        return "my-orders";
    }
}
