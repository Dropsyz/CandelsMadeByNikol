package libraly.candelsmadebynikol.web.conttrolers;
import libraly.candelsmadebynikol.services.interfaces.CandleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    private final CandleService candleService;

    public HomeController(CandleService candleService) {
        this.candleService = candleService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("candles", candleService.getAllCandles());
        return "home";
    }
}