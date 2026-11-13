package br.com.estudos.tarefas_api;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarefaRepository
        extends JpaRepository<Tarefa, Integer> {
    List<Tarefa> findByConcluidaFalse();
    List<Tarefa> findByDescricaoContainingIgnoreCase(String descricao);
    List<Tarefa> findByDescricaoContainingIgnoreCaseAndConcluida(String descricao, boolean concluida);
}
