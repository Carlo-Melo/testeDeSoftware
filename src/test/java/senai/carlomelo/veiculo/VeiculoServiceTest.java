package senai.carlomelo.veiculo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import senai.carlomelo.veiculo.model.Veiculo;
import senai.carlomelo.veiculo.repository.VeiculoRepository;
import senai.carlomelo.veiculo.service.VeiculoService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VeiculoServiceTest {

    @Mock
    private VeiculoRepository veiculoRepository;

    @InjectMocks
    private VeiculoService veiculoService;

    private Veiculo veiculo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setDescricao("SUV");
        veiculo.setFabricante("Toyota");
        veiculo.setCor("Prata");
        veiculo.setPlaca("ABC-1234");
    }

    @Test
    void deveSalvarVeiculo() {
        when(veiculoRepository.save(any(Veiculo.class))).thenReturn(veiculo);
        Veiculo salvo = veiculoService.salvar(veiculo);
        assertNotNull(salvo);
        assertEquals("Toyota", salvo.getFabricante());
        verify(veiculoRepository, times(1)).save(veiculo);
    }

    @Test
    void deveListarTodos() {
        when(veiculoRepository.findAll()).thenReturn(List.of(veiculo));
        List<Veiculo> lista = veiculoService.listarTodos();
        assertEquals(1, lista.size());
        verify(veiculoRepository, times(1)).findAll();
    }

    @Test
    void deveBuscarPorId() {
        when(veiculoRepository.findById(1L)).thenReturn(Optional.of(veiculo));
        Optional<Veiculo> encontrado = veiculoService.buscarPorId(1L);
        assertTrue(encontrado.isPresent());
        assertEquals("ABC-1234", encontrado.get().getPlaca());
    }

    @Test
    void deveDeletarVeiculo() {
        when(veiculoRepository.existsById(1L)).thenReturn(true);
        boolean deletado = veiculoService.deletar(1L);
        assertTrue(deletado);
        verify(veiculoRepository, times(1)).deleteById(1L);
    }
}
