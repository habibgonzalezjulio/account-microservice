package com.accounts.accountsmanagement.application.port.out;

import com.accounts.accountsmanagement.domain.model.AccountModel;
import java.util.List;

public interface GetAllAccountsPort {
  List<AccountModel> getAll();
}