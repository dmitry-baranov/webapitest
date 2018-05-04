package example.dmitry.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import example.dmitry.Account;
import example.dmitry.Util;
import example.dmitry.errors.MyException;
import example.dmitry.logic.AccountService;
import example.dmitry.logic.Service;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyServletAdd extends HttpServlet {

    private Service accountService = new AccountService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            ObjectMapper mapper = new ObjectMapper();
            Account account = mapper.readValue(req.getReader(), Account.class);
            Util.validation(account);
            accountService.createAccount(account);
//        String accountResponse = mapper.writeValueAsString(account);
            resp.getWriter().append("OK");

        } catch (MyException e) {
            resp.getWriter().append(e.getResponse().toJson());
        }
    }
}
