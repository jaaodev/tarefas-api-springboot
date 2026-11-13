package br.com.estudos.tarefas_api;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

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
        return tarefaRepository.findAll();
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
}
