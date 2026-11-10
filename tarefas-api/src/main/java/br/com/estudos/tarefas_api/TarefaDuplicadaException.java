package br.com.estudos.tarefas_api;

public class TarefaDuplicadaException extends RuntimeException {
    public TarefaDuplicadaException(String message) {
        super(message);
    }
}
