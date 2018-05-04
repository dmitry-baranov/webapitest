package example.dmitry;

import example.dmitry.errors.MyException;
import example.dmitry.errors.Response;

public class Util {

    public static void validation(Account account) {

        if (!(String.valueOf(account.getAccount()).length() == 5)) {
            throw new MyException(Response.ACCOUNT_IS_NOT_CORRECT);
        }
        if (account.getValue() < 0) {
            throw new MyException(Response.VALUE_IS_NOT_CORRECT);
        }

    }
}
