package com.accounts.accountsmanagement.domain.model;

import com.accounts.accountsmanagement.domain.enums.AccountStatus;
import com.accounts.accountsmanagement.domain.enums.AccountType;
import com.accounts.accountsmanagement.domain.valueobject.AccountId;
import com.accounts.accountsmanagement.domain.valueobject.OwnerEmail;
import com.accounts.accountsmanagement.domain.valueobject.OwnerName;
import com.accounts.accountsmanagement.domain.valueobject.OwnerUserId;
import lombok.Value;

@Value
public class AccountModel {

  AccountId id;
  OwnerUserId ownerUserId;
  OwnerName ownerName;
  OwnerEmail ownerEmail;
  AccountType type;
  AccountStatus status;

  public static AccountModel create(
      final AccountId id,
      final OwnerUserId ownerUserId,
      final OwnerName ownerName,
      final OwnerEmail ownerEmail,
      final AccountType type) {
    return new AccountModel(id, ownerUserId, ownerName, ownerEmail, type, AccountStatus.ACTIVE);
  }

  public AccountModel activate() {
    return new AccountModel(id, ownerUserId, ownerName, ownerEmail, type, AccountStatus.ACTIVE);
  }

  public AccountModel deactivate() {
    return new AccountModel(id, ownerUserId, ownerName, ownerEmail, type, AccountStatus.INACTIVE);
  }
}