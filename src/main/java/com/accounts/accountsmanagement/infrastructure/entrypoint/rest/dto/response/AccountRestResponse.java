package com.accounts.accountsmanagement.infrastructure.entrypoint.rest.dto.response;

public record AccountRestResponse(
    String id,
    String userId,
    String ownerName,
    String ownerEmail,
    String type,
    String status) {}