package br.com.estudos.tarefas_api.service;

import br.com.estudos.tarefas_api.dto.CadastroUsuarioDTO;
import br.com.estudos.tarefas_api.repository.UsuarioRepository;
import br.com.estudos.tarefas_api.entity.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public void cadastrarUsuario(CadastroUsuarioDTO dto) {
        usuarioRepository.save(
                new Usuario(dto.getNome(), dto.getEmail())
        );
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(int id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "Usuário id: " + id + " não encontrado."
                ));
    }

}
