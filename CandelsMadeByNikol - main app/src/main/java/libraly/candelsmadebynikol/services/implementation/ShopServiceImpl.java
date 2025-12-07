package libraly.candelsmadebynikol.services.implementation;

import libraly.candelsmadebynikol.common.exceptions.ObjectNotFoundException;
import libraly.candelsmadebynikol.models.dto.OrderDTO;
import libraly.candelsmadebynikol.models.entity.CandleEntity;
import libraly.candelsmadebynikol.models.entity.UserEntity;
import libraly.candelsmadebynikol.repository.CandleRepository;
import libraly.candelsmadebynikol.repository.UserRepository;
import libraly.candelsmadebynikol.services.interfaces.OrderServiceClient;
import libraly.candelsmadebynikol.services.interfaces.ShopService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ShopServiceImpl implements ShopService {

    private final CandleRepository candleRepository;
    private final UserRepository userRepository;
    private final OrderServiceClient orderServiceClient;

    public ShopServiceImpl(CandleRepository candleRepository, UserRepository userRepository, OrderServiceClient orderServiceClient) {
        this.candleRepository = candleRepository;
        this.userRepository = userRepository;
        this.orderServiceClient = orderServiceClient;
    }

    @Override
    public void buyCandle(UUID candleId, String username) {
        CandleEntity candle = candleRepository.findById(candleId)
                .orElseThrow(() -> new ObjectNotFoundException("Candle not found!"));

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ObjectNotFoundException("User not found!"));

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCandleId(candle.getId());
        orderDTO.setUserId(user.getId());
        orderDTO.setPrice(candle.getPrice());
        orderDTO.setShippingAddress("Default Address");


        orderServiceClient.createOrder(orderDTO);

        System.out.println("Sent order request for candle: " + candle.getName());
    }
}
