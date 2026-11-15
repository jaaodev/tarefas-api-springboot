package br.com.estudos.tarefas_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CadastroUsuarioDTO {
    @NotBlank(message = "O nome é obrigatório!")
    private String nome;
    @NotBlank(message = "O email é obrigatório!")
    @Email(message = "Formato de email inválido!")
    private String email;

    public CadastroUsuarioDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
