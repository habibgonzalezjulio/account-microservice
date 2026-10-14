package com.accounts.accountsmanagement.application.service;

import com.accounts.accountsmanagement.application.port.in.CreateAccountUseCase;
import com.accounts.accountsmanagement.application.port.out.GetAccountByIdPort;
import com.accounts.accountsmanagement.application.port.out.GetAccountByOwnerAndTypePort;
import com.accounts.accountsmanagement.application.port.out.PublishDomainEventPort;
import com.accounts.accountsmanagement.application.port.out.SaveAccountPort;
import com.accounts.accountsmanagement.application.service.dto.command.CreateAccountCommand;
import com.accounts.accountsmanagement.application.service.mapper.AccountApplicationMapper;
import com.accounts.accountsmanagement.domain.enums.AccountType;
import com.accounts.accountsmanagement.domain.event.AccountCreatedDomainEvent;
import com.accounts.accountsmanagement.domain.exception.AccountAlreadyExistsException;
import com.accounts.accountsmanagement.domain.model.AccountModel;
import com.accounts.accountsmanagement.domain.valueobject.OwnerUserId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAccountService implements CreateAccountUseCase {

  private final SaveAccountPort saveAccountPort;
  private final GetAccountByIdPort getAccountByIdPort;
  private final GetAccountByOwnerAndTypePort getAccountByOwnerAndTypePort;
  private final PublishDomainEventPort publishDomainEventPort;
  private final Validator validator;

  @Override
  public AccountModel execute(final CreateAccountCommand command) {
    validateCommand(command);

    final OwnerUserId ownerUserId = new OwnerUserId(command.userId());
    final AccountType type = AccountType.fromString(command.type());
    ensureOwnerHasNoAccountOfType(ownerUserId, type);

    final AccountModel accountToSave = AccountApplicationMapper.fromCreateCommandToModel(command);
    ensureAccountIdIsFree(accountToSave);
    final AccountModel savedAccount = saveAccountPort.save(accountToSave);

    publishDomainEventPort.publish(new AccountCreatedDomainEvent(savedAccount));

    return savedAccount;
  }

  private void validateCommand(final CreateAccountCommand command) {
    final Set<ConstraintViolation<CreateAccountCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  private void ensureAccountIdIsFree(final AccountModel account) {
    getAccountByIdPort
        .getById(account.getId())
        .ifPresent(
            ignored -> {
              throw AccountAlreadyExistsException.becauseIdAlreadyExists(account.getId().value());
            });
  }

  private void ensureOwnerHasNoAccountOfType(
      final OwnerUserId ownerUserId, final AccountType type) {
    getAccountByOwnerAndTypePort
        .getByOwnerAndType(ownerUserId, type)
        .ifPresent(
            ignored -> {
              throw AccountAlreadyExistsException.becauseOwnerAlreadyHasAccountOfType(
                  ownerUserId.value(), type.name());
            });
  }
}