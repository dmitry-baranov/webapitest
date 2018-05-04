package example.dmitry.dao;

import example.dmitry.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDao {

    private BdConnectionParams params = new BdConnectionParams();

    public void addAccount(Account account) {
        try (Connection connection = getConnection(params)) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO account (account_id, balance)  VALUES (?,?)"
            );
            preparedStatement.setInt(1, account.getAccount());
            preparedStatement.setInt(2, 0);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean searchAccount(Account account) {
        try (Connection connection = getConnection(params)) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT COUNT(account_id) AS Duplicate "
                            + "FROM account "
                            + "WHERE account_id = ?");
            preparedStatement.setInt(1, account.getAccount());
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() && resultSet.getInt(1) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public int searchBalance(Account account) {
        try (Connection connection = getConnection(params)) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT balance "
                            + "FROM account "
                            + "WHERE account_id = ?");
            preparedStatement.setInt(1, account.getAccount());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void updateAccount(Account account, int balance) {
        try (Connection connection = getConnection(params)) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE account SET balance = ? WHERE account_id = ?");
            preparedStatement.setInt(1, balance);
            preparedStatement.setInt(2, account.getAccount());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAccount(Account account) {
        try (Connection connection = getConnection(params)) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM account WHERE account_id = ?");
            preparedStatement.setInt(1, account.getAccount());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection(BdConnectionParams params) throws SQLException {
        return DriverManager.getConnection(params.getUrl(),
                params.getLogin(), params.getPass());
    }
}
