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
        if (veiculos == null || veiculos.size() == 0) {
            Veiculo v1 = new Veiculo();
            v1.setDescricao("Carro Esportivo");
            v1.setFabricante("Ferrari");
            v1.setCor("Vermelho");
            v1.setPlaca("FERR-1234");
            repository.save(v1);

            Veiculo v2 = new Veiculo();
            v2.setDescricao("Sedan Familiar");
            v2.setFabricante("Toyota");
            v2.setCor("Preto");
            v2.setPlaca("TOYO-5678");
            repository.save(v2);

            Veiculo v3 = new Veiculo();
            v3.setDescricao("SUV Compacto");
            v3.setFabricante("Honda");
            v3.setCor("Branco");
            v3.setPlaca("HOND-9012");
            repository.save(v3);

            veiculos = repository.findAll();
        }

        model.addAttribute("veiculos", veiculos);

        return "hello";
    }

}
