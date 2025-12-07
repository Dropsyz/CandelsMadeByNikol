package libraly.orderservice.services;


import libraly.orderservice.model.dto.OrderDTO;
import libraly.orderservice.model.entity.OrderEntity;
import libraly.orderservice.model.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void createOrder(OrderDTO orderDTO) {
        OrderEntity order = new OrderEntity();

        order.setUserId(orderDTO.getUserId());
        order.setCandleId(orderDTO.getCandleId());
        order.setShippingAddress(orderDTO.getShippingAddress());
        order.setTotalPrice(orderDTO.getPrice());

        order.setOrderDate(LocalDateTime.now());
        order.setStatus("NEW");

        orderRepository.save(order);
        System.out.println("ORDER SAVED SUCCESSFULY: " + orderDTO);
    }

    public List<OrderDTO> getUserOrders(UUID userId) {
        return orderRepository.findAllByUserId(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public void updateStatus(UUID orderId, String newStatus) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setStatus(newStatus);
        orderRepository.save(order);
    }

    private OrderDTO mapToDTO(OrderEntity entity) {
        OrderDTO dto = new OrderDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setCandleId(entity.getCandleId());
        dto.setShippingAddress(entity.getShippingAddress());
        dto.setPrice(entity.getTotalPrice());
        dto.setStatus(entity.getStatus());
        dto.setOrderDate(entity.getOrderDate());
        return dto;
    }
}