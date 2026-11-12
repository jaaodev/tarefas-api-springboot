package br.com.estudos.tarefas_api;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TarefaRepository
        extends JpaRepository<Tarefa, Integer> {

}
