package br.com.estudos.tarefas_api;

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

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    @Transactional
    public void cadastrarTarefa(CadastroTarefaDTO dto) {
        if (dto.getDescricao() == null || dto.getDescricao().isBlank()) {
            throw new IllegalArgumentException("Descrição inválida");
        }
        tarefaRepository.save(new Tarefa(
                dto.getDescricao(), dto.isConcluida()
        ));
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
}
