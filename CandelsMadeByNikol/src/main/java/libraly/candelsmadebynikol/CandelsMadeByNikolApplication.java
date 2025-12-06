package libraly.candelsmadebynikol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CandelsMadeByNikolApplication {

    public static void main(String[] args) {
        SpringApplication.run(CandelsMadeByNikolApplication.class, args);
    }

}
