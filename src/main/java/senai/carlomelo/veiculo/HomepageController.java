package senai.carlomelo.veiculo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import senai.carlomelo.veiculo.model.Veiculo;
import senai.carlomelo.veiculo.repository.VeiculoRepository;

@Controller
public class HomepageController {
    
    @Autowired
    private VeiculoRepository repository;

    @GetMapping("")
    public String hello(Model model) {
        List<Veiculo> veiculos = repository.findAll();
        model.addAttribute("veiculos", veiculos);
        return "hello";
    }

}
