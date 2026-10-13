package com.accounts.accountsmanagement.infrastructure.adapter.persistence.dto;

public record AccountPersistenceDto(
    String id,
    String userId,
    String ownerName,
    String ownerEmail,
    String type,
    String status,
    String createdAt,
    String updatedAt) {}