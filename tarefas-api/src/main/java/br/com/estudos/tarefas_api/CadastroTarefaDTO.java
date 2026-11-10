package br.com.estudos.tarefas_api;

import jakarta.validation.constraints.NotBlank;

public class CadastroTarefaDTO { //DTO representa somente os dados que o cliente precisa enviar para cadastrar uma tarefa.
    @NotBlank(message = "A descrição é obrigatória!")
    private String descricao;
    private boolean concluida;

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
}
