package br.com.estudos.tarefas_api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TarefaRepository
        extends JpaRepository<Tarefa, Integer> {
    //Consultas derivadas...
    List<Tarefa> findByConcluidaFalse();

    List<Tarefa> findByDescricaoContainingIgnoreCase(
            String descricao
    );

    List<Tarefa> findByDescricaoContainingIgnoreCaseAndConcluida(
            String descricao,
            boolean concluida
    );

    //Consultas JPQL
    @Query("SELECT t FROM Tarefa t WHERE t.concluida = :status")
    List<Tarefa> buscarPorStatus(@Param("status") boolean status);

    @Query("SELECT t FROM Tarefa t " +
            "WHERE LOWER(t.descricao) LIKE LOWER(CONCAT('%', :descricao, '%')) " +
            "AND t.concluida = :status")
    List<Tarefa> pesquisarTarefas(@Param("descricao") String descricao,
                                  @Param("status") boolean status);

}