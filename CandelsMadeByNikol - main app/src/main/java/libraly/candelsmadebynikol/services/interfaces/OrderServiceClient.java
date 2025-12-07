package libraly.candelsmadebynikol.services.interfaces;

import libraly.candelsmadebynikol.models.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "order-service", url = "http://localhost:8081")
public interface OrderServiceClient {

    @PostMapping("/orders/create")
    void createOrder(@RequestBody OrderDTO orderDTO);
    @GetMapping("/orders/all")
    List<OrderDTO> getAllOrders();

    @PostMapping("/orders/status/{id}")

    void updateStatus(@PathVariable("id") UUID id, @RequestParam("status") String status);

    @GetMapping("/orders/user/{userId}")

    List<OrderDTO> getOrdersByUser(@PathVariable("userId") UUID userId);
}