package com.accounts.accountsmanagement.application.port.in;

import com.accounts.accountsmanagement.application.service.dto.command.CreateAccountCommand;
import com.accounts.accountsmanagement.domain.model.AccountModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface CreateAccountUseCase {
  AccountModel execute(@NotNull @Valid CreateAccountCommand command);
}
