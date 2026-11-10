package br.com.estudos.tarefas_api;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class TarefaController {
    private final TarefaService tarefaService;

    public TarefaController(TarefaService tarefaService) {
        this.tarefaService = tarefaService;
    }

    @GetMapping("/tarefas")
    public String saudacao() {
        return "Bem-vindo à API de tarefas!";
    }

    @GetMapping("/tarefa")
    public Tarefa buscarTarefa() {
        return new Tarefa(1, "Estudar Spring Boot", false);
    }

    @GetMapping("/tarefas/lista")
    public List<Tarefa> listarTarefas() {
        return tarefaService.listarTarefas();
    }

    @PostMapping("/tarefas")
    public ResponseEntity<String> cadastrarTarefa(@Valid @RequestBody CadastroTarefaDTO dto) {
        tarefaService.cadastrarTarefa(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Tarefa cadastrada com sucesso!");
    }

    @GetMapping("/tarefas/{id}")
    public Tarefa buscarPorId(@PathVariable int id) {
        return tarefaService.buscarPorId(id);
    }

    @PatchMapping("/tarefas/{id}/concluir")
    public ResponseEntity<String> concluirTarefa(@PathVariable int id) {
        tarefaService.concluirTarefa(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Tarefa concluida com sucesso.");
    }

    @DeleteMapping("/tarefas/{id}")
    public ResponseEntity<String> deletarTarefar(@PathVariable int id) {
        tarefaService.deletarTarefa(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Tarefa deletada com sucesso.");
    }
}
