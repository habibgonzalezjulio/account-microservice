package com.accounts.accountsmanagement.application.service.dto.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateAccountCommand(
    // Opcional: si no se envia, se genera un UUID
    String accountId,
    @NotBlank(message = "userId must not be blank") String userId,
    @NotBlank(message = "ownerName must not be blank")
        @Size(min = 3, message = "ownerName must have at least 3 characters")
        String ownerName,
    @NotBlank(message = "ownerEmail must not be blank")
        @Email(message = "ownerEmail must be a valid email address")
        String ownerEmail,
    @NotBlank(message = "type must not be blank") String type) {}