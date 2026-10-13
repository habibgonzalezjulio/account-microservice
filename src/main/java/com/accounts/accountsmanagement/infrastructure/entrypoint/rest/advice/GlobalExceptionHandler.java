package com.accounts.accountsmanagement.infrastructure.entrypoint.rest.advice;

import com.accounts.accountsmanagement.domain.exception.AccountAlreadyExistsException;
import com.accounts.accountsmanagement.domain.exception.AccountNotFoundException;
import com.accounts.accountsmanagement.domain.exception.DomainException;
import com.accounts.accountsmanagement.infrastructure.adapter.messaging.exception.MessagingException;
import com.accounts.accountsmanagement.infrastructure.adapter.persistence.exception.PersistenceException;
import com.accounts.accountsmanagement.infrastructure.entrypoint.rest.dto.response.ApiErrorResponse;
import jakarta.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final String LOG_PERSISTENCE_SQL_ERROR =
      "[GlobalExceptionHandler] Error de persistencia. sqlState={} errorCode={}";
  private static final String LOG_PERSISTENCE_ERROR =
      "[GlobalExceptionHandler] Error de persistencia sin SQLException. type={}";
  private static final String LOG_MESSAGING_ERROR =
      "[GlobalExceptionHandler] Error de mensajeria. type={}";

  @ExceptionHandler(AccountNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ApiErrorResponse handleAccountNotFound(final AccountNotFoundException exception) {
    return new ApiErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage());
  }

  @ExceptionHandler(AccountAlreadyExistsException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public ApiErrorResponse handleAccountAlreadyExists(
      final AccountAlreadyExistsException exception) {
    return new ApiErrorResponse(HttpStatus.CONFLICT.value(), exception.getMessage());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiErrorResponse handleConstraintViolation(final ConstraintViolationException exception) {
    final String message =
        exception.getConstraintViolations().stream()
            .map(v -> v.getPropertyPath() + ": " + v.getMessage())
            .collect(Collectors.joining(", "));
    return new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiErrorResponse handleMethodArgumentNotValid(
      final MethodArgumentNotValidException exception) {
    final String message =
        exception.getBindingResult().getFieldErrors().stream()
            .map(e -> e.getField() + ": " + e.getDefaultMessage())
            .collect(Collectors.joining(", "));
    return new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
  }

  @ExceptionHandler(DomainException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiErrorResponse handleDomain(final DomainException exception) {
    return new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
  }

  @ExceptionHandler(PersistenceException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ApiErrorResponse handlePersistence(final PersistenceException exception) {
    if (exception.getCause() instanceof final SQLException sqlException) {
      log.error(LOG_PERSISTENCE_SQL_ERROR, sqlException.getSQLState(), sqlException.getErrorCode());
    } else {
      log.error(LOG_PERSISTENCE_ERROR, exception.getClass().getSimpleName());
    }
    return new ApiErrorResponse(
        HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error de persistencia.");
  }

  @ExceptionHandler(MessagingException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ApiErrorResponse handleMessaging(final MessagingException exception) {
    log.error(LOG_MESSAGING_ERROR, exception.getClass().getSimpleName());
    return new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error de mensajería.");
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ApiErrorResponse handleGeneral(final Exception ignored) {
    return new ApiErrorResponse(
        HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error interno del servidor.");
  }
}