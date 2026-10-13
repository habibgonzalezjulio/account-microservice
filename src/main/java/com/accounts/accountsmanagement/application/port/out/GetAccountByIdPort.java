package com.accounts.accountsmanagement.application.port.out;

import com.accounts.accountsmanagement.domain.model.AccountModel;
import com.accounts.accountsmanagement.domain.valueobject.AccountId;
import java.util.Optional;

public interface GetAccountByIdPort {
  Optional<AccountModel> getById(AccountId accountId);
}