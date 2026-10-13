package com.accounts.accountsmanagement.application.port.out;

import com.accounts.accountsmanagement.domain.model.AccountModel;

public interface SaveAccountPort {
  AccountModel save(AccountModel account);
}