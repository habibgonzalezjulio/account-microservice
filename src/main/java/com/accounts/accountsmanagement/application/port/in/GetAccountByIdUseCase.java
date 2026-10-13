package com.accounts.accountsmanagement.application.port.in;

import com.accounts.accountsmanagement.application.service.dto.query.GetAccountByIdQuery;
import com.accounts.accountsmanagement.domain.model.AccountModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface GetAccountByIdUseCase {
  AccountModel execute(@NotNull @Valid GetAccountByIdQuery query);
}