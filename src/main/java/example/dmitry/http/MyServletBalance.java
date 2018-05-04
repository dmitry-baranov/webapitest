package example.dmitry.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.dmitry.Account;
import example.dmitry.Util;
import example.dmitry.errors.MyException;
import example.dmitry.logic.AccountService;
import example.dmitry.logic.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyServletBalance extends HttpServlet {

    private Service accountService = new AccountService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Account account = new Account(Integer.parseInt(req.getParameter("id")), 0);
            Util.validation(account);
            int balance = accountService.searchBalance(account);
            account.setValue(balance);
            String accountResponse = mapper.writeValueAsString(account);
            resp.getWriter().append(accountResponse);

        } catch (MyException e) {
            resp.getWriter().append(e.getResponse().toJson());
        }
    }
}
