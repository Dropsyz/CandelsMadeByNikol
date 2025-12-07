package libraly.candelsmadebynikol.services.interfaces;

import libraly.candelsmadebynikol.models.dto.AddReviewDTO;


import java.util.UUID;

public interface ReviewService {

    public void addReview(AddReviewDTO addReviewDTO, UUID candleId, String username);
}
