package servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class InitialServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Object thisIsThreadSafe;

        //thisIsThreadSafe = request.getParameter("foo"); // OK, this is thread safe.
        RequestDispatcher view = request.getRequestDispatcher("/index.xhtml");
        // don't add your web-app name to the path
        view.forward(request, response);
    }
}