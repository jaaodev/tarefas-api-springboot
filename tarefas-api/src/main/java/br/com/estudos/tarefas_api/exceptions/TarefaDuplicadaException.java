package br.com.estudos.tarefas_api.exceptions;

public class TarefaDuplicadaException extends RuntimeException {
    public TarefaDuplicadaException(String message) {
        super(message);
    }
}
