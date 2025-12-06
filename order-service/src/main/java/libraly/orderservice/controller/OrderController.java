package libraly.orderservice.controller;


import libraly.orderservice.model.dto.OrderDTO;
import libraly.orderservice.services.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public void createOrder(@RequestBody OrderDTO orderDTO) {
        orderService.createOrder(orderDTO);
    }
    @GetMapping("/user/{userId}")
    public List<OrderDTO> getOrdersByUser(@PathVariable UUID userId) {
        return orderService.getUserOrders(userId);
    }

    @GetMapping("/all")
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PostMapping("/status/{id}")
    public void updateStatus(@PathVariable UUID id, @RequestParam String status) {
        orderService.updateStatus(id, status);
    }
}
