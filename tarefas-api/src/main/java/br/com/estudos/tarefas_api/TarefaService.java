package br.com.estudos.tarefas_api;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TarefaService {

    private int proximoId = 1;

    private final List<Tarefa> tarefas = new ArrayList<>();

    public TarefaService() {
        tarefas.add(new Tarefa(1, "Estudar Spring Boot 123", false));
        tarefas.add(new Tarefa(2, "Estudar Java Core 4567", true));
        proximoId = 3;
    }

    public void cadastrarTarefa(CadastroTarefaDTO dto) {
        if (dto.getDescricao() == null || dto.getDescricao().isBlank()) {
            throw new IllegalArgumentException("Descrição inválida");
        }

        tarefas.add(new Tarefa(
                proximoId++,
                dto.getDescricao(),
                dto.isConcluida())
        );
    }

    public List<Tarefa> listarTarefas() {
        return tarefas;
    }

    public Tarefa buscarPorId(int id) {
        for (Tarefa t : tarefas) {
            if (t.getId() == id) {
                return t;
            }
        }
        throw new NoSuchElementException("A tarefa " + id + " não foi encontrada.");
    }

    public void concluirTarefa(int id) {
        Tarefa tarefa = buscarPorId(id);
        if (tarefa.isConcluida()) {
            throw new IllegalStateException("Essa tarefa já está concluida.");
        }
        tarefa.setConcluida(true);
    }

    public void deletarTarefa(int id) {
        Tarefa tarefa = buscarPorId(id);
        tarefas.remove(tarefa);
    }
}
