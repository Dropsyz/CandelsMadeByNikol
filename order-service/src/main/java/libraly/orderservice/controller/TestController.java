package libraly.orderservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/orders/test")
    public String test() {
        return "Order Service is RUNNING on port 8081!";
    }
}