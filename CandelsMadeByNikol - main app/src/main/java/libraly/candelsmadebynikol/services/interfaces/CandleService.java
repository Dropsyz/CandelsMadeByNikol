package libraly.candelsmadebynikol.services.interfaces;

import libraly.candelsmadebynikol.models.dto.AddCandleDTO;
import libraly.candelsmadebynikol.models.dto.CandleViewDTO;

import java.util.List;
import java.util.UUID;

public interface CandleService {

    void addCandle(AddCandleDTO addCandleDTO);
    List<CandleViewDTO> getAllCandles();
    void deleteCandle(UUID candleId);
    AddCandleDTO findCandleById(UUID id);
    void editCandle(UUID id, AddCandleDTO editCandleDTO);
}
