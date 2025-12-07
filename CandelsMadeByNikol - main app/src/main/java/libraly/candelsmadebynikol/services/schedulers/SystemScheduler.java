package libraly.candelsmadebynikol.services.schedulers;

import libraly.candelsmadebynikol.repository.CandleRepository;
import libraly.candelsmadebynikol.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SystemScheduler {

    private static final Logger log = LoggerFactory.getLogger(SystemScheduler.class);

    private final UserRepository userRepository;
    private final CandleRepository candleRepository;

    public SystemScheduler(UserRepository userRepository, CandleRepository candleRepository) {
        this.userRepository = userRepository;
        this.candleRepository = candleRepository;
    }


    @Scheduled(cron = "0 * * * * *")
    public void performSystemReport() {
        long userCount = userRepository.count();
        long candleCount = candleRepository.count();

        log.info("--- SYSTEM REPORT [{}] ---", LocalDateTime.now());
        log.info("Total Users: {}", userCount);
        log.info("Total Candles: {}", candleCount);
        log.info("System is operational.");
        log.info("-----------------------------");
    }


    @Scheduled(fixedRate = 300000)
    public void heartbeat() {
        log.info("HEARTBEAT: Application is alive and running... Beep.");
    }
}