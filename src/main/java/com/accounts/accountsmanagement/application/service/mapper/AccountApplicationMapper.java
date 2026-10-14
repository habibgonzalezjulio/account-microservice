package com.accounts.accountsmanagement.application.service.mapper;

import com.accounts.accountsmanagement.application.service.dto.command.CreateAccountCommand;
import com.accounts.accountsmanagement.application.service.dto.query.GetAccountByIdQuery;
import com.accounts.accountsmanagement.domain.enums.AccountType;
import com.accounts.accountsmanagement.domain.model.AccountModel;
import com.accounts.accountsmanagement.domain.valueobject.AccountId;
import com.accounts.accountsmanagement.domain.valueobject.OwnerEmail;
import com.accounts.accountsmanagement.domain.valueobject.OwnerName;
import com.accounts.accountsmanagement.domain.valueobject.OwnerUserId;
import java.util.Objects;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountApplicationMapper {

  public AccountModel fromCreateCommandToModel(final CreateAccountCommand command) {
    return AccountModel.create(
        toAccountId(command.accountId()),
        new OwnerUserId(command.userId()),
        new OwnerName(command.ownerName()),
        new OwnerEmail(command.ownerEmail()),
        AccountType.fromString(command.type()));
  }

  private AccountId toAccountId(final String accountId) {
    return Objects.isNull(accountId) || accountId.isBlank()
        ? AccountId.generate()
        : new AccountId(accountId);
  }

  public AccountId fromGetAccountByIdQueryToAccountId(final GetAccountByIdQuery query) {
    return new AccountId(query.id());
  }
}