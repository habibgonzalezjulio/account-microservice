package com.accounts.accountsmanagement.application.port.in;

import com.accounts.accountsmanagement.domain.model.AccountModel;
import java.util.List;

public interface GetAllAccountsUseCase {
  List<AccountModel> execute();
}