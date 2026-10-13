package com.accounts.accountsmanagement.infrastructure.adapter.messaging.mapper;

import com.accounts.accountsmanagement.domain.event.DomainEvent;
import com.accounts.accountsmanagement.infrastructure.adapter.messaging.dto.EventMessage;
import java.time.format.DateTimeFormatter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EventMessageMapper {

  public EventMessage fromDomainEventToMessage(final DomainEvent event) {
    return new EventMessage(
        event.getEventName(),
        event.getOccurredOn().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        event.payload());
  }
}