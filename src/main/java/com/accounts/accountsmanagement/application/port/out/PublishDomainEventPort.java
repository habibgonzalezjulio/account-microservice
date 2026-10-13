package com.accounts.accountsmanagement.application.port.out;

import com.accounts.accountsmanagement.domain.event.DomainEvent;

public interface PublishDomainEventPort {
  void publish(DomainEvent event);
}