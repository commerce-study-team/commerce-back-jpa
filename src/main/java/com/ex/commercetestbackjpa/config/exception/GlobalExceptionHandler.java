package com.ex.commercetestbackjpa.config.exception;

import com.ex.commercetestbackjpa.config.exception.dto.ExceptionResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ExceptionResponseDTO> NullPointerExceptionHandler(NullPointerException e) {
        ExceptionResponseDTO exceptionDTO = ExceptionResponseDTO.builder()
                                            .code("Null Exception!!!")
                                            .massage(e.getMessage()).build();
        log.error(e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionDTO);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ExceptionResponseDTO> SQLExceptionHandler(SQLException e) {
        ExceptionResponseDTO exceptionDTO = ExceptionResponseDTO.builder()
                                            .code("SQL Exception!!!")
                                            .massage(e.getMessage()).build();
        log.error(e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionDTO);
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionResponseDTO> noSuchExceptionHandler(NoSuchElementException e) {
        ExceptionResponseDTO exceptionDTO = ExceptionResponseDTO.builder()
                                            .code("Item not found!!!")
                                            .massage(e.getMessage()).build();
        log.error(e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDTO> ExceptionHandler(Exception e) {
        ExceptionResponseDTO exceptionDTO = ExceptionResponseDTO.builder()
                                            .code("죄송합니다. 서비스에 장애가 발생하였습니다.")
                                            .massage(e.getMessage()).build();
        log.error(e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionDTO);
    }
}
