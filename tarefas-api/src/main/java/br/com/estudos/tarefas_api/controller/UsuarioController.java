package br.com.estudos.tarefas_api.controller;

import br.com.estudos.tarefas_api.dto.CadastroUsuarioDTO;
import br.com.estudos.tarefas_api.entity.Usuario;
import br.com.estudos.tarefas_api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsuarioController {
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/usuarios")
    public ResponseEntity<String> cadastrarUsuario(@Valid @RequestBody CadastroUsuarioDTO dto) {
        usuarioService.cadastrarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Usuário cadastrado com sucesso!");
    }

    @GetMapping("/usuarios/{id}")
    public Usuario buscarPorId(
            @PathVariable int id) {
        return usuarioService.buscarPorId(id);
    }
}
