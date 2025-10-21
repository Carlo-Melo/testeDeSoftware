package senai.carlomelo.veiculo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import senai.carlomelo.veiculo.model.Veiculo;
import senai.carlomelo.veiculo.repository.VeiculoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    public Veiculo salvar(Veiculo veiculo) {
        return veiculoRepository.save(veiculo);
    }

    
    public List<Veiculo> listarTodos() {
        return veiculoRepository.findAll();
    }

    
    public Optional<Veiculo> buscarPorId(Long id) {
        return veiculoRepository.findById(id);
    }

    
    public Optional<Veiculo> atualizar(Long id, Veiculo novoVeiculo) {
        return veiculoRepository.findById(id)
                .map(veiculo -> {
                    veiculo.setDescricao(novoVeiculo.getDescricao());
                    veiculo.setFabricante(novoVeiculo.getFabricante());
                    veiculo.setCor(novoVeiculo.getCor());
                    veiculo.setPlaca(novoVeiculo.getPlaca());
                    return veiculoRepository.save(veiculo);
                });
    }

    
    public boolean deletar(Long id) {
        if (veiculoRepository.existsById(id)) {
            veiculoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
