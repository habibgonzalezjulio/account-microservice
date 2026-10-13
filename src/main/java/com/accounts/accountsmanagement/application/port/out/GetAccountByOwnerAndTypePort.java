package com.accounts.accountsmanagement.application.port.out;

import com.accounts.accountsmanagement.domain.enums.AccountType;
import com.accounts.accountsmanagement.domain.model.AccountModel;
import com.accounts.accountsmanagement.domain.valueobject.OwnerUserId;
import java.util.Optional;

public interface GetAccountByOwnerAndTypePort {
  Optional<AccountModel> getByOwnerAndType(OwnerUserId ownerUserId, AccountType type);
}