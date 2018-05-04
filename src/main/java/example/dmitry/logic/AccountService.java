package example.dmitry.logic;

import example.dmitry.Account;
import example.dmitry.errors.MyException;
import example.dmitry.dao.AccountDao;
import example.dmitry.errors.Response;

public class AccountService implements Service {

    private AccountDao accountDao = new AccountDao();

    public void createAccount(Account account) {
        if (!accountDao.searchAccount(account)) {
            accountDao.addAccount(account);
        } else {
            throw new MyException(Response.ACCOUNT_ALREADY_EXIST);
        }
    }

    public void replenishmentAccount(Account account) {
        if (accountDao.searchAccount(account)) {
            int balance = accountDao.searchBalance(account) + account.getValue();
            accountDao.updateAccount(account, balance);
        } else {
            throw new MyException(Response.ACCOUNT_NOT_FOUND);
        }
    }

    public void writeOffAccount(Account account) {
        if (accountDao.searchAccount(account)) {
            int balance = accountDao.searchBalance(account) - account.getValue();
            if (balance >= 0) {
                accountDao.updateAccount(account, balance);
            } else {
                throw new MyException(Response.WRITE_OFF_MORE_BALANCE);
            }
        } else {
            throw new MyException(Response.ACCOUNT_NOT_FOUND);
        }
    }

    public int searchBalance(Account account) {
        if (accountDao.searchAccount(account)) {
            return accountDao.searchBalance(account);
        } else {
            throw new MyException(Response.ACCOUNT_NOT_FOUND);
        }
    }

    public void deleteAccount(Account account) {
        if (accountDao.searchAccount(account)) {
            if (accountDao.searchBalance(account) == 0) {
                accountDao.deleteAccount(account);
            } else {
                throw new MyException(Response.BALANCE_IS_NOT_ZERO_FOR_REMOVAL);
            }
        } else {
            throw new MyException(Response.ACCOUNT_NOT_FOUND);
        }
    }
}

