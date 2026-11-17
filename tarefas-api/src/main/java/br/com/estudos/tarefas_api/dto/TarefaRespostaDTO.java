package br.com.estudos.tarefas_api.dto;

public record TarefaRespostaDTO(
        int id,
        String descricao,
        boolean concluida,
        Integer usuarioId,
        String usuarioNome
) {
}
