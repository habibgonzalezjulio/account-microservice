package com.accounts.accountsmanagement.infrastructure.entrypoint.rest.controller;

import com.accounts.accountsmanagement.infrastructure.entrypoint.rest.dto.request.CreateAccountRestRequest;
import com.accounts.accountsmanagement.infrastructure.entrypoint.rest.dto.response.AccountRestResponse;
import com.accounts.accountsmanagement.infrastructure.entrypoint.rest.dto.response.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Accounts", description = "Gestión de cuentas: crear y consultar.")
public interface AccountRestControllerDocs {

  @Operation(
      summary = "Crear cuenta",
      description =
          "Registra una cuenta para un usuario. Un usuario solo puede tener una cuenta por tipo. "
              + "Al crearla se publica el evento account.created en Kafka para que el "
              + "microservicio notifiers envíe el correo.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "201",
        description = "Cuenta creada exitosamente.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AccountRestResponse.class))),
    @ApiResponse(
        responseCode = "400",
        description = "Datos de entrada inválidos.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class))),
    @ApiResponse(
        responseCode = "409",
        description = "El usuario ya tiene una cuenta de ese tipo.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class))),
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  AccountRestResponse create(@Valid @RequestBody CreateAccountRestRequest request);

  @Operation(
      summary = "Listar todas las cuentas",
      description = "Retorna todas las cuentas, de la más reciente a la más antigua.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Lista obtenida exitosamente. Puede ser vacía.",
        content =
            @Content(
                mediaType = "application/json",
                array = @ArraySchema(schema = @Schema(implementation = AccountRestResponse.class)))),
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  List<AccountRestResponse> getAll();

  @Operation(
      summary = "Obtener cuenta por ID",
      description = "Retorna los datos de una cuenta identificada por su ID único.")
  @ApiResponses({
    @ApiResponse(
        responseCode = "200",
        description = "Cuenta encontrada.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AccountRestResponse.class))),
    @ApiResponse(
        responseCode = "404",
        description = "No existe ninguna cuenta con el ID proporcionado.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class))),
    @ApiResponse(
        responseCode = "500",
        description = "Error interno del servidor.",
        content =
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class)))
  })
  AccountRestResponse getById(
      @Parameter(description = "ID único de la cuenta.", example = "acc-001", required = true)
          @PathVariable
          String id);
}