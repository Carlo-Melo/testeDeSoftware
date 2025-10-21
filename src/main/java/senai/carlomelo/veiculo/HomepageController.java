package senai.carlomelo.veiculo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomepageController {
    @GetMapping("")
    public String hello(Model model) {
        model.addAttribute("message", "Hello World");
        return "hello";
    }

}
