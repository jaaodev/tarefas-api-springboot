package br.com.estudos.tarefas_api.controller;

import br.com.estudos.tarefas_api.dto.TarefaRespostaDTO;
import br.com.estudos.tarefas_api.entity.Usuario;
import br.com.estudos.tarefas_api.service.TarefaService;
import br.com.estudos.tarefas_api.dto.CadastroTarefaDTO;
import br.com.estudos.tarefas_api.entity.Tarefa;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/tarefas/buscar")
    public List<Tarefa> buscarDescricao(
            @RequestParam String descricao) {
        return tarefaService.buscarPorDescricao(descricao);
    }

    @GetMapping("/tarefas/filtrar")
    public List<Tarefa> filtrarTarefas(
            @RequestParam String descricao, boolean concluida) {
        return tarefaService.filtrarTarefas(descricao, concluida);
    }

    @GetMapping("/tarefas/status")
    public List<Tarefa> listarPorStatus (
            @RequestParam boolean concluida) {
        return tarefaService.listarPorStatus(concluida);
    }

    @GetMapping("/tarefas/pesquisar")
    public List<Tarefa> pesquisarTarefas (
            @RequestParam String descricao,
            @RequestParam boolean concluida) {
        return tarefaService.pesquisarTarefa(descricao, concluida);
    }

    @GetMapping("/tarefas/paginas")
    public Page<Tarefa> listasPaginas(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "3") int tamanho) {
        return tarefaService.listarPaginas(pagina, tamanho);
    }

    @GetMapping("/tarefas/lista")
    public List<Tarefa> listarTarefas() {
        return tarefaService.listarTarefas();
    }

    @GetMapping("/tarefas/lista/dto")
    public List<TarefaRespostaDTO> listarTarefasDTO() {
        return tarefaService.listarTarefasDTO();
    }

    @GetMapping("/tarefas/com-usuarios")
    public List<TarefaRespostaDTO> listarComUsuarios() {
        return tarefaService.listarComUsuarios();
    }

    @GetMapping("/tarefas/pendentes")
    public List<Tarefa> listarPendentes() {
        return tarefaService.listarPendentes();
    }

    @PostMapping("/tarefas")
    public ResponseEntity<String> cadastrarTarefa(
            @Valid @RequestBody CadastroTarefaDTO dto) {
        tarefaService.cadastrarTarefa(dto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Tarefa cadastrada com sucesso!");
    }

    @GetMapping("/tarefas/{id}")
    public Tarefa buscarPorId(
            @PathVariable int id) {
        return tarefaService.buscarPorId(id);
    }

    @GetMapping("/tarefas/dto/{id}")
    public TarefaRespostaDTO buscarPorIdDTO(
            @PathVariable int id) {
        return tarefaService.buscarPorIdDTO(id);
    }

    @PatchMapping("/tarefas/{id}/concluir")
    public ResponseEntity<String> concluirTarefa(
            @PathVariable int id) {
        tarefaService.concluirTarefa(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Tarefa concluida com sucesso.");
    }

    @DeleteMapping("/tarefas/{id}")
    public ResponseEntity<String> deletarTarefar(
            @PathVariable int id) {
        tarefaService.deletarTarefa(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Tarefa deletada com sucesso.");
    }
}
