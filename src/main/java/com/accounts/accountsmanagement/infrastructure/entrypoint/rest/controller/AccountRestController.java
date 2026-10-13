package com.accounts.accountsmanagement.infrastructure.entrypoint.rest.controller;

import com.accounts.accountsmanagement.application.port.in.CreateAccountUseCase;
import com.accounts.accountsmanagement.application.port.in.GetAccountByIdUseCase;
import com.accounts.accountsmanagement.application.port.in.GetAllAccountsUseCase;
import com.accounts.accountsmanagement.application.service.dto.command.CreateAccountCommand;
import com.accounts.accountsmanagement.application.service.dto.query.GetAccountByIdQuery;
import com.accounts.accountsmanagement.domain.model.AccountModel;
import com.accounts.accountsmanagement.infrastructure.entrypoint.rest.dto.request.CreateAccountRestRequest;
import com.accounts.accountsmanagement.infrastructure.entrypoint.rest.dto.response.AccountRestResponse;
import com.accounts.accountsmanagement.infrastructure.entrypoint.rest.mapper.AccountRestMapper;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountRestController implements AccountRestControllerDocs {

  private final CreateAccountUseCase createAccountUseCase;
  private final GetAccountByIdUseCase getAccountByIdUseCase;
  private final GetAllAccountsUseCase getAllAccountsUseCase;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public AccountRestResponse create(@Valid @RequestBody final CreateAccountRestRequest request) {
    final CreateAccountCommand command = AccountRestMapper.toCreateCommand(request);
    final AccountModel account = createAccountUseCase.execute(command);
    return AccountRestMapper.toResponse(account);
  }

  @GetMapping
  public List<AccountRestResponse> getAll() {
    return AccountRestMapper.toResponseList(getAllAccountsUseCase.execute());
  }

  @GetMapping("/{id}")
  public AccountRestResponse getById(@PathVariable final String id) {
    final GetAccountByIdQuery query = AccountRestMapper.toGetByIdQuery(id);
    final AccountModel account = getAccountByIdUseCase.execute(query);
    return AccountRestMapper.toResponse(account);
  }
}