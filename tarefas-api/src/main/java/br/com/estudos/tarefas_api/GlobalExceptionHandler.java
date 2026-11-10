package br.com.estudos.tarefas_api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TarefaDuplicadaException.class)
    public ResponseEntity<String> tratarIdDuplicado(TarefaDuplicadaException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> tratarDescricaoInvalida(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> tratarIdNaoEcontrado(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> tratarTarefaJaConcluida(IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> tratarValidacao(MethodArgumentNotValidException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getBindingResult().getFieldErrors().getFirst().getDefaultMessage());
    }
}
