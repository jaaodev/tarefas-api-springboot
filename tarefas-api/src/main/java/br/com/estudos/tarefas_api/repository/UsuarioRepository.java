package br.com.estudos.tarefas_api.repository;

import br.com.estudos.tarefas_api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository
        extends JpaRepository<Usuario, Integer> {

}
