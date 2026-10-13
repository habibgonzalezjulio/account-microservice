package com.accounts.accountsmanagement.infrastructure.entrypoint.rest.dto.response;

public record ApiErrorResponse(int status, String message) {}