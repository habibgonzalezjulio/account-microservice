package com.accounts.accountsmanagement.infrastructure.adapter.persistence.mapper;

import com.accounts.accountsmanagement.domain.enums.AccountStatus;
import com.accounts.accountsmanagement.domain.enums.AccountType;
import com.accounts.accountsmanagement.domain.model.AccountModel;
import com.accounts.accountsmanagement.domain.valueobject.AccountId;
import com.accounts.accountsmanagement.domain.valueobject.OwnerEmail;
import com.accounts.accountsmanagement.domain.valueobject.OwnerName;
import com.accounts.accountsmanagement.domain.valueobject.OwnerUserId;
import com.accounts.accountsmanagement.infrastructure.adapter.persistence.dto.AccountPersistenceDto;
import com.accounts.accountsmanagement.infrastructure.adapter.persistence.entity.AccountEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountPersistenceMapper {

  public AccountPersistenceDto fromModelToDto(final AccountModel account) {
    return new AccountPersistenceDto(
        account.getId().value(),
        account.getOwnerUserId().value(),
        account.getOwnerName().value(),
        account.getOwnerEmail().value(),
        account.getType().name(),
        account.getStatus().name(),
        null,
        null);
  }

  public AccountEntity fromResultSetToEntity(final ResultSet resultSet) throws SQLException {
    return new AccountEntity(
        resultSet.getString("id"),
        resultSet.getString("user_id"),
        resultSet.getString("owner_name"),
        resultSet.getString("owner_email"),
        resultSet.getString("type"),
        resultSet.getString("status"),
        resultSet.getString("created_at"),
        resultSet.getString("updated_at"));
  }

  public AccountModel fromEntityToModel(final AccountEntity entity) {
    return new AccountModel(
        new AccountId(entity.id()),
        new OwnerUserId(entity.userId()),
        new OwnerName(entity.ownerName()),
        new OwnerEmail(entity.ownerEmail()),
        AccountType.fromString(entity.type()),
        AccountStatus.fromString(entity.status()));
  }

  public AccountModel fromResultSetToModel(final ResultSet resultSet) throws SQLException {
    return fromEntityToModel(fromResultSetToEntity(resultSet));
  }

  public List<AccountModel> fromResultSetToModelList(final ResultSet resultSet)
      throws SQLException {
    final List<AccountModel> accounts = new ArrayList<>();
    while (resultSet.next()) {
      accounts.add(fromResultSetToModel(resultSet));
    }
    return accounts;
  }
}