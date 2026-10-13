package com.accounts.accountsmanagement.infrastructure.adapter.messaging.dto;

import java.util.Map;

public record EventMessage(String eventName, String occurredOn, Map<String, String> payload) {}