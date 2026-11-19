package br.com.estudos.tarefas_api.service;

import br.com.estudos.tarefas_api.dto.CadastroTarefaDTO;
import br.com.estudos.tarefas_api.entity.Tarefa;
import br.com.estudos.tarefas_api.entity.Usuario;
import br.com.estudos.tarefas_api.repository.TarefaRepository;
import br.com.estudos.tarefas_api.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TarefaServiceTest {
    @Mock
    public TarefaRepository tarefaRepository;

    @Mock
    public UsuarioRepository usuarioRepository;

    @InjectMocks
    public TarefaService tarefaService;

    @Test
    public void deveLancarExcecaoQuandoTarefaNaoExiste() {
        when(tarefaRepository.findById(999))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> tarefaService.buscarPorId(999));
    }

    @Test
    public void deveImpedirConclusaoDeTarefaJaConcluida() {
        Tarefa tarefa = new Tarefa("Estudar Spring Boot", true);

        assertTrue(tarefa.isConcluida());

        when(tarefaRepository.findById(1))
                .thenReturn(Optional.of(tarefa));

        assertThrows(IllegalStateException.class,
                () -> tarefaService.concluirTarefa(1));

        assertTrue(tarefa.isConcluida());

        verify(tarefaRepository).findById(1);
    }

    @Test
    void deveConcluirTarefaPendente() {
        Tarefa tarefa = new Tarefa("Estudar MongoDB", false);

        assertFalse(tarefa.isConcluida());

        when(tarefaRepository.findById(1))
                .thenReturn(Optional.of(tarefa));

        tarefaService.concluirTarefa(1);

        assertTrue(tarefa.isConcluida());

        verify(tarefaRepository).findById(1);
    }

    @Test
    void deveImpedirCadastroComUsuarioInexistente() {
        CadastroTarefaDTO dto = new CadastroTarefaDTO();
        dto.setDescricao("Estudar Angular");
        dto.setUsuarioId(999);

        when(usuarioRepository.findById(999))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> tarefaService.cadastrarTarefa(dto));

        verify(usuarioRepository).findById(999);

        verify(tarefaRepository, never()).save(any(Tarefa.class));
    }

    @Test
    void deveCadastrarTarefaComUsuarioExistente() {
        Usuario usuario = new Usuario("Carlos" , "carlos@email.com");
        CadastroTarefaDTO dto = new CadastroTarefaDTO();
        ArgumentCaptor<Tarefa> captor = ArgumentCaptor.forClass(Tarefa.class);

        dto.setDescricao("Estudar testes com Mockito");
        dto.setUsuarioId(1);

        when(usuarioRepository.findById(1))
                .thenReturn(Optional.of(usuario));

        tarefaService.cadastrarTarefa(dto);

        verify(usuarioRepository).findById(1);
        verify(tarefaRepository).save(captor.capture());

        Tarefa tarefaCapturada = captor.getValue();

        assertEquals("Estudar testes com Mockito",
                tarefaCapturada.getDescricao());

        assertFalse(tarefaCapturada.isConcluida());

        assertSame(usuario, tarefaCapturada.getUsuario());
    }
}
