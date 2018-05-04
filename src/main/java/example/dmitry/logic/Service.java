package example.dmitry.logic;

import example.dmitry.Account;

public interface Service {
    void createAccount(Account account);

    void replenishmentAccount(Account account);

    void writeOffAccount(Account account);

    int searchBalance(Account account);

    void deleteAccount(Account account);
}
