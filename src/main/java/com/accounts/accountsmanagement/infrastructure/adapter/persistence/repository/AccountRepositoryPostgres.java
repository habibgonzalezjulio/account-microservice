package com.accounts.accountsmanagement.infrastructure.adapter.persistence.repository;

import com.accounts.accountsmanagement.application.port.out.GetAccountByIdPort;
import com.accounts.accountsmanagement.application.port.out.GetAccountByOwnerAndTypePort;
import com.accounts.accountsmanagement.application.port.out.GetAllAccountsPort;
import com.accounts.accountsmanagement.application.port.out.SaveAccountPort;
import com.accounts.accountsmanagement.domain.enums.AccountType;
import com.accounts.accountsmanagement.domain.exception.AccountNotFoundException;
import com.accounts.accountsmanagement.domain.model.AccountModel;
import com.accounts.accountsmanagement.domain.valueobject.AccountId;
import com.accounts.accountsmanagement.domain.valueobject.OwnerUserId;
import com.accounts.accountsmanagement.infrastructure.adapter.persistence.dto.AccountPersistenceDto;
import com.accounts.accountsmanagement.infrastructure.adapter.persistence.exception.PersistenceException;
import com.accounts.accountsmanagement.infrastructure.adapter.persistence.mapper.AccountPersistenceMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryPostgres
    implements SaveAccountPort,
        GetAccountByIdPort,
        GetAccountByOwnerAndTypePort,
        GetAllAccountsPort {

  private static final String SQL_INSERT =
      "INSERT INTO accounts "
          + "(id, user_id, owner_name, owner_email, type, status, created_at, updated_at) "
          + "VALUES (?, ?, ?, ?, ?, ?, NOW(), NOW())";

  private static final String SQL_SELECT_BY_ID =
      "SELECT id, user_id, owner_name, owner_email, type, status, created_at, updated_at "
          + "FROM accounts "
          + "WHERE id = ? LIMIT 1";

  private static final String SQL_SELECT_BY_OWNER_AND_TYPE =
      "SELECT id, user_id, owner_name, owner_email, type, status, created_at, updated_at "
          + "FROM accounts "
          + "WHERE user_id = ? AND type = ? LIMIT 1";

  private static final String SQL_SELECT_ALL =
      "SELECT id, user_id, owner_name, owner_email, type, status, created_at, updated_at "
          + "FROM accounts "
          + "ORDER BY created_at DESC";

  private final DataSource dataSource;

  @Override
  public AccountModel save(final AccountModel account) {
    final AccountPersistenceDto dto = AccountPersistenceMapper.fromModelToDto(account);
    executeSave(dto);
    return findByIdOrFail(account.getId());
  }

  @Override
  public Optional<AccountModel> getById(final AccountId accountId) {
    try (final Connection connection = dataSource.getConnection();
        final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
      statement.setString(1, accountId.value());
      final ResultSet resultSet = statement.executeQuery();
      if (!resultSet.next()) {
        return Optional.empty();
      }
      return Optional.of(AccountPersistenceMapper.fromResultSetToModel(resultSet));
    } catch (final SQLException exception) {
      throw PersistenceException.becauseFindByIdFailed(accountId.value(), exception);
    }
  }

  @Override
  public Optional<AccountModel> getByOwnerAndType(
      final OwnerUserId ownerUserId, final AccountType type) {
    try (final Connection connection = dataSource.getConnection();
        final PreparedStatement statement =
            connection.prepareStatement(SQL_SELECT_BY_OWNER_AND_TYPE)) {
      statement.setString(1, ownerUserId.value());
      statement.setString(2, type.name());
      final ResultSet resultSet = statement.executeQuery();
      if (!resultSet.next()) {
        return Optional.empty();
      }
      return Optional.of(AccountPersistenceMapper.fromResultSetToModel(resultSet));
    } catch (final SQLException exception) {
      throw PersistenceException.becauseFindByOwnerFailed(ownerUserId.value(), exception);
    }
  }

  @Override
  public List<AccountModel> getAll() {
    try (final Connection connection = dataSource.getConnection();
        final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL)) {
      final ResultSet resultSet = statement.executeQuery();
      return AccountPersistenceMapper.fromResultSetToModelList(resultSet);
    } catch (final SQLException exception) {
      throw PersistenceException.becauseFindAllFailed(exception);
    }
  }

  private void executeSave(final AccountPersistenceDto dto) {
    try (final Connection connection = dataSource.getConnection();
        final PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {
      statement.setString(1, dto.id());
      statement.setString(2, dto.userId());
      statement.setString(3, dto.ownerName());
      statement.setString(4, dto.ownerEmail());
      statement.setString(5, dto.type());
      statement.setString(6, dto.status());
      statement.executeUpdate();
    } catch (final SQLException exception) {
      throw PersistenceException.becauseSaveFailed(dto.id(), exception);
    }
  }

  private AccountModel findByIdOrFail(final AccountId accountId) {
    return getById(accountId)
        .orElseThrow(() -> AccountNotFoundException.becauseIdWasNotFound(accountId.value()));
  }
}