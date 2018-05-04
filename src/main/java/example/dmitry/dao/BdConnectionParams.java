package example.dmitry.dao;

import example.dmitry.errors.MyException;
import example.dmitry.errors.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Data
@Builder
@AllArgsConstructor
public class BdConnectionParams {

    private String url;
    private String login;
    private String pass;

    public BdConnectionParams() {

        Properties properties = new Properties();
        ClassLoader classLoader = BdConnectionParams.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("db-connection.properties");
        try {
            properties.load(inputStream);
            url = properties.getProperty("url");
            login = properties.getProperty("login");
            pass = properties.getProperty("pass");
        } catch (IOException e) {
            throw new MyException(Response.BD_CONNECTION_ERROR);
        }
    }
}
