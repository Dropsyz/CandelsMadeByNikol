package libraly.candelsmadebynikol.services.implementation;

import libraly.candelsmadebynikol.models.dto.AddReviewDTO;
import libraly.candelsmadebynikol.models.entity.CandleEntity;
import libraly.candelsmadebynikol.models.entity.ReviewEntity;
import libraly.candelsmadebynikol.models.entity.UserEntity;
import libraly.candelsmadebynikol.repository.CandleRepository;
import libraly.candelsmadebynikol.repository.ReviewRepository;
import libraly.candelsmadebynikol.repository.UserRepository;
import libraly.candelsmadebynikol.services.interfaces.ReviewService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CandleRepository candleRepository;
    private final UserRepository userRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, CandleRepository candleRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.candleRepository = candleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addReview(AddReviewDTO addReviewDTO, UUID candleId, String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));

        CandleEntity candle = candleRepository.findById(candleId)
                .orElseThrow(() -> new IllegalArgumentException("Candle not found!"));

        ReviewEntity review = new ReviewEntity();
        review.setText(addReviewDTO.getText());
        review.setRating(addReviewDTO.getRating());
        review.setAuthor(user);
        review.setCandle(candle);

        reviewRepository.save(review);
    }
}