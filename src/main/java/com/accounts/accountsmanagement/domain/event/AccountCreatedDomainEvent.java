package com.accounts.accountsmanagement.domain.event;

import com.accounts.accountsmanagement.domain.model.AccountModel;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public final class AccountCreatedDomainEvent extends DomainEvent {

  private static final String EVENT_NAME = "account.created";
  public static final String PAYLOAD_KEY_ACCOUNT_ID = "accountId";

  private final AccountModel account;

  public AccountCreatedDomainEvent(final AccountModel account) {
    super(EVENT_NAME);
    this.account = account;
  }

  @Override
  public Map<String, String> payload() {
    final Map<String, String> payload = new LinkedHashMap<>();
    payload.put(PAYLOAD_KEY_ACCOUNT_ID, account.getId().value());
    payload.put("userId", account.getOwnerUserId().value());
    payload.put("ownerName", account.getOwnerName().value());
    payload.put("ownerEmail", account.getOwnerEmail().value());
    return payload;
  }
}