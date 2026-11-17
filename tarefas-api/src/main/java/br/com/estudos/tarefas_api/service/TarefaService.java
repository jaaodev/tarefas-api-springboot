package br.com.estudos.tarefas_api.service;

import br.com.estudos.tarefas_api.dto.CadastroTarefaDTO;
import br.com.estudos.tarefas_api.dto.TarefaRespostaDTO;
import br.com.estudos.tarefas_api.entity.Tarefa;
import br.com.estudos.tarefas_api.entity.Usuario;
import br.com.estudos.tarefas_api.repository.TarefaRepository;
import br.com.estudos.tarefas_api.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
public class TarefaService {
    private final TarefaRepository tarefaRepository;
    private final UsuarioRepository usuarioRepository;

    public TarefaService(TarefaRepository tarefaRepository, UsuarioRepository usuarioRepository, UsuarioService usuarioService) {
        this.tarefaRepository = tarefaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public void cadastrarTarefa(CadastroTarefaDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new NoSuchElementException(
                        "Id de usuário não encontrado."
                ));
        if (dto.getDescricao() == null || dto.getDescricao().isBlank()) {
            throw new IllegalArgumentException("Descrição inválida");
        }
        Tarefa tarefa = new Tarefa(dto.getDescricao(), dto.isConcluida());
        tarefa.setUsuario(usuario);
        tarefaRepository.save(tarefa);

    }

    @Transactional(readOnly = true)
    public List<Tarefa> listarTarefas() {
        return tarefaRepository.findAll(
                Sort.by(DESC, "id")
        );
    }

    @Transactional(readOnly = true)
    public Tarefa buscarPorId(int id) {
        return tarefaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "Tarefa " + id + " não encontrada."
                ));
    }

    @Transactional
    public void concluirTarefa(int id) {
        Tarefa tarefa = buscarPorId(id);

        if (tarefa.isConcluida()) {
            throw new IllegalStateException("Tarefa já concluida.");
        }

        tarefa.setConcluida(true);
    }

    @Transactional
    public void deletarTarefa(int id) {
        Tarefa tarefa = buscarPorId(id);
        tarefaRepository.delete(tarefa);
    }

    @Transactional(readOnly = true)
    public List<Tarefa> listarPendentes() {
        return tarefaRepository.findByConcluidaFalse();
    }

    @Transactional(readOnly = true)
    public List<Tarefa> buscarPorDescricao(String descricao) {
        return tarefaRepository.findByDescricaoContainingIgnoreCase(descricao);
    }

    @Transactional(readOnly = true)
    public List<Tarefa> filtrarTarefas(String descricao, boolean concluida) {
        return tarefaRepository.findByDescricaoContainingIgnoreCaseAndConcluida(descricao, concluida);
    }

    @Transactional(readOnly = true)
    public List<Tarefa> listarPorStatus(boolean concluida) {
        return tarefaRepository.buscarPorStatus(concluida);
    }

    @Transactional(readOnly = true)
    public List<Tarefa> pesquisarTarefa(String descricao, boolean concluida) {
        return tarefaRepository.pesquisarTarefas(descricao, concluida);
    }

    @Transactional(readOnly = true)
    public Page<Tarefa> listarPaginas(int pagina, int tamanho) {
        if (pagina < 0) {
            throw new IllegalArgumentException("Número de páginas inválido.");
        }
        if (tamanho < 1 || tamanho > 100) {
            throw new IllegalArgumentException("O número de tarefas por página deve ser entre 1 e 100.");
        }
        Sort ordenacao = Sort.by(DESC, "id");
        Pageable pageable = PageRequest.of(pagina, tamanho, ordenacao);
        return tarefaRepository.findAll(pageable);
    }

    public TarefaRespostaDTO converterParaDTO(Tarefa tarefa) {
        Usuario usuario = tarefa.getUsuario();
        Integer usuarioId = null;
        String usuarioNome = null;
        if (usuario != null) {
            usuarioId = usuario.getId();
            usuarioNome = usuario.getNome();
        }
        return new TarefaRespostaDTO(
                tarefa.getId(),
                tarefa.getDescricao(),
                tarefa.isConcluida(),
                usuarioId,
                usuarioNome
        );
    }

    @Transactional(readOnly = true)
    public List<TarefaRespostaDTO> listarTarefasDTO() {
        return tarefaRepository.findAll(
                Sort.by(DESC, "id")
                )
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public TarefaRespostaDTO buscarPorIdDTO(int id) {
        Tarefa tarefa = buscarPorId(id);
        return converterParaDTO(tarefa);
    }

    @Transactional(readOnly = true)
    public List<TarefaRespostaDTO> listarComUsuarios() {
        return tarefaRepository.buscarComUsuarios().stream()
                .map(this::converterParaDTO)
                .toList();
    }
}
