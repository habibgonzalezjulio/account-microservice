package com.accounts.accountsmanagement.infrastructure.adapter.messaging.mapper;

import com.accounts.accountsmanagement.domain.event.DomainEvent;
import com.accounts.accountsmanagement.infrastructure.adapter.messaging.dto.EventMessage;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EventMessageMapper {

  public EventMessage fromDomainEventToMessage(final DomainEvent event) {
    return new EventMessage(
        event.getEventName(),
        event.getOccurredOn().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        event.payload());
  }
}