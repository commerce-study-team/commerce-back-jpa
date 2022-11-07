package com.ex.commercetestbackjpa.config;

import com.ex.commercetestbackjpa.config.dto.ExceptionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ExceptionResponseDTO> SQLExceptionHandler(SQLException e) {
        ExceptionResponseDTO exceptionDTO = ExceptionResponseDTO.builder()
                                            .code("SQL Exception")
                                            .massage(e.getMessage()).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionDTO);
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionResponseDTO> noSuchExceptionHandler(NoSuchFieldException e) {
        ExceptionResponseDTO exceptionDTO = ExceptionResponseDTO.builder()
                                            .code("Item not found")
                                            .massage(e.getMessage()).build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDTO> ExceptionHandler(Exception e) {
        ExceptionResponseDTO exceptionDTO = ExceptionResponseDTO.builder()
                                            .code("Exception!!!")
                                            .massage(e.getMessage()).build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionDTO);
    }
}
