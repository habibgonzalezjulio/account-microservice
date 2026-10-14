package com.accounts.accountsmanagement.infrastructure.entrypoint.rest.mapper;

import com.accounts.accountsmanagement.application.service.dto.command.CreateAccountCommand;
import com.accounts.accountsmanagement.application.service.dto.query.GetAccountByIdQuery;
import com.accounts.accountsmanagement.domain.model.AccountModel;
import com.accounts.accountsmanagement.infrastructure.entrypoint.rest.dto.request.CreateAccountRestRequest;
import com.accounts.accountsmanagement.infrastructure.entrypoint.rest.dto.response.AccountRestResponse;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountRestMapper {

  public CreateAccountCommand toCreateCommand(final CreateAccountRestRequest request) {
    return new CreateAccountCommand(
        request.accountId(),
        request.userId(),
        request.ownerName(),
        request.ownerEmail(),
        request.type());
  }

  public GetAccountByIdQuery toGetByIdQuery(final String id) {
    return new GetAccountByIdQuery(id);
  }

  public AccountRestResponse toResponse(final AccountModel account) {
    return new AccountRestResponse(
        account.getId().value(),
        account.getOwnerUserId().value(),
        account.getOwnerName().value(),
        account.getOwnerEmail().value(),
        account.getType().name(),
        account.getStatus().name());
  }

  public List<AccountRestResponse> toResponseList(final List<AccountModel> accounts) {
    return accounts.stream().map(AccountRestMapper::toResponse).toList();
  }
}