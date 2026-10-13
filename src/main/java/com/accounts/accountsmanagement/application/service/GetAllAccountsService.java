package com.accounts.accountsmanagement.application.service;

import com.accounts.accountsmanagement.application.port.in.GetAllAccountsUseCase;
import com.accounts.accountsmanagement.application.port.out.GetAllAccountsPort;
import com.accounts.accountsmanagement.domain.model.AccountModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAllAccountsService implements GetAllAccountsUseCase {

  private final GetAllAccountsPort getAllAccountsPort;

  @Override
  public List<AccountModel> execute() {
    return getAllAccountsPort.getAll();
  }
}