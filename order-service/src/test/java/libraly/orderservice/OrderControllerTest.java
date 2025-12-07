package libraly.orderservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import libraly.orderservice.model.dto.OrderDTO;
import libraly.orderservice.services.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;


    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateOrder_StatusOk() throws Exception {
        OrderDTO dto = new OrderDTO();
        dto.setUserId(UUID.randomUUID());
        dto.setPrice(BigDecimal.TEN);

        mockMvc.perform(post("/orders/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());

        verify(orderService).createOrder(any(OrderDTO.class));
    }
}