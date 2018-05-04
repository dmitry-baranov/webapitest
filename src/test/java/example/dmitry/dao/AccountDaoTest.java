package example.dmitry.dao;

import example.dmitry.Account;
import org.junit.Test;
import java.sql.*;
import static org.junit.Assert.*;

public class AccountDaoTest {

    AccountDao accountDao = new AccountDao();
    private BdConnectionParams params = new BdConnectionParams();


    @Test
    public void addAccount() {
        Account account = new Account(12345, 0);
        accountDao.addAccount(account);
        try (Connection connection = getConnection(params)) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT COUNT(account_id) AS Duplicate "
                            + "FROM account "
                            + "WHERE account_id = ?");
            preparedStatement.setInt(1, account.getAccount());
            ResultSet resultSet = preparedStatement.executeQuery();
            assertTrue("Add account", resultSet.next() && resultSet.getInt(1) == 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try (Connection connection = getConnection(params)){
                PreparedStatement preparedStatementDelete = connection.prepareStatement(
                        "DELETE FROM account ");
                preparedStatementDelete.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void searchAccount() {
        Account account = new Account(12345, 0);
        try (Connection connection = getConnection(params)) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO account (account_id, balance)  VALUES (?,?)"
            );
            preparedStatement.setInt(1, account.getAccount());
            preparedStatement.setInt(2, 0);
            preparedStatement.executeUpdate();
            assertTrue("Search account", accountDao.searchAccount(account));
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try (Connection connection = getConnection(params)){
                PreparedStatement preparedStatementDelete = connection.prepareStatement(
                        "DELETE FROM account ");
                preparedStatementDelete.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void searchBalance() {
        Account account = new Account(23456, 859);
        try (Connection connection = getConnection(params)) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO account (account_id, balance)  VALUES (?,?)"
            );
            preparedStatement.setInt(1, account.getAccount());
            preparedStatement.setInt(2, account.getValue());
            preparedStatement.executeUpdate();
            assertTrue("Search balance", accountDao.searchBalance(account) == 859);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try (Connection connection = getConnection(params)){
                PreparedStatement preparedStatementDelete = connection.prepareStatement(
                        "DELETE FROM account ");
                preparedStatementDelete.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void updateAccount() {
        Account account = new Account(23456, 200);
        try (Connection connection = getConnection(params)) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO account (account_id, balance)  VALUES (?,?)"
            );
            preparedStatement.setInt(1, account.getAccount());
            preparedStatement.setInt(2, account.getValue());
            preparedStatement.executeUpdate();
            accountDao.updateAccount(account, 500);
            PreparedStatement preparedStatementSearch = connection.prepareStatement(
                    "SELECT balance "
                            + "FROM account "
                            + "WHERE account_id = ?");
            preparedStatementSearch.setInt(1, account.getAccount());
            ResultSet resultSet = preparedStatementSearch.executeQuery();
            if (resultSet.next()) {
                int balance = resultSet.getInt(1);
                assertTrue("Update balance", balance == 500);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try (Connection connection = getConnection(params)){
                PreparedStatement preparedStatementDelete = connection.prepareStatement(
                        "DELETE FROM account ");
                preparedStatementDelete.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void deleteAccount() {
        Account account = new Account(23456, 859);
        try (Connection connection = getConnection(params)) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO account (account_id, balance)  VALUES (?,?)"
            );
            preparedStatement.setInt(1, account.getAccount());
            preparedStatement.setInt(2, account.getValue());
            preparedStatement.executeUpdate();
            accountDao.deleteAccount(account);
            PreparedStatement preparedStatementSearch = connection.prepareStatement(
                    "SELECT COUNT(account_id) AS Duplicate "
                            + "FROM account "
                            + "WHERE account_id = ?");
            preparedStatementSearch.setInt(1, account.getAccount());
            ResultSet resultSet = preparedStatementSearch.executeQuery();
            assertTrue("Add account", !(resultSet.next() && resultSet.getInt(1) == 1));
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try (Connection connection = getConnection(params)){
                PreparedStatement preparedStatementDelete = connection.prepareStatement(
                        "DELETE FROM account ");
                preparedStatementDelete.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Connection getConnection(BdConnectionParams params) throws SQLException {
        return DriverManager.getConnection(params.getUrl(),
                params.getLogin(), params.getPass());
    }
}
