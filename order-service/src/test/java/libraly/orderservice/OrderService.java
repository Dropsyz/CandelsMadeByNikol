package libraly.orderservice.services;

import libraly.orderservice.model.dto.OrderDTO;
import libraly.orderservice.model.entity.OrderEntity;
import libraly.orderservice.model.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(orderRepository);
    }

    @Test
    void testCreateOrder_SavesEntity() {
        OrderDTO dto = new OrderDTO();
        dto.setUserId(UUID.randomUUID());
        dto.setCandleId(UUID.randomUUID());
        dto.setPrice(BigDecimal.valueOf(50));
        dto.setShippingAddress("Sofia, Bulgaria");


        orderService.createOrder(dto);


        ArgumentCaptor<OrderEntity> captor = ArgumentCaptor.forClass(OrderEntity.class);
        verify(orderRepository).save(captor.capture());

        OrderEntity savedOrder = captor.getValue();
        Assertions.assertEquals("NEW", savedOrder.getStatus());
        Assertions.assertEquals(dto.getPrice(), savedOrder.getTotalPrice());
        Assertions.assertEquals(dto.getShippingAddress(), savedOrder.getShippingAddress());
    }
}