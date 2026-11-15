package br.com.estudos.tarefas_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CadastroTarefaDTO { //DTO representa somente os dados que o cliente precisa enviar para cadastrar uma tarefa.
    @NotBlank(message = "A descrição é obrigatória!")
    private String descricao;
    private boolean concluida;
    @NotNull(message = "O usuário é obrigatório!")
    @Positive(message = "O id deve ser maior que zero.")
    private Integer usuarioId;

    public CadastroTarefaDTO() {

    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
}
