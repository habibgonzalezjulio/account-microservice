package com.accounts.accountsmanagement.infrastructure.adapter.persistence.entity;

public record AccountEntity(
    String id,
    String userId,
    String ownerName,
    String ownerEmail,
    String type,
    String status,
    String createdAt,
    String updatedAt) {}