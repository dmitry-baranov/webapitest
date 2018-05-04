package example.dmitry;

import example.dmitry.http.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;


public class Main {
    public static void main(String[] args) throws Exception {

        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(
                ServletContextHandler.SESSIONS);
        context.setContextPath("/bankaccount");
        server.setHandler(context);

        context.addServlet(MyServletAdd.class, "/add");
        context.addServlet(MyServletDeposit.class, "/deposit");
        context.addServlet(MyServletWithDraw.class, "/withdraw");
        context.addServlet(MyServletBalance.class, "/balance");
        context.addServlet(MyServletDelete.class, "/delete");


        server.start();
        System.out.println("started!");
        server.join();


    }
}
