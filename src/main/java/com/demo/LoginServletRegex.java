package com.demo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

@WebServlet(
        description = "Login Servlet Testing",
        urlPatterns = { "/LoginServlet" },
        initParams = {
                @WebInitParam(name = "user", value = "Sachin"),
                @WebInitParam(name = "password", value = "Sachin@2910")
        }
)
public class LoginServletRegex extends HttpServlet {

    private static final String NAME_REGEX = "^[A-Z][a-zA-Z]{2,}$";
    private static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9])(?!.*[^A-Za-z0-9].*[^A-Za-z0-9]).{8,}$";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get request parameters for user and password
        String user = request.getParameter("user");
        String pwd = request.getParameter("pwd");

        // Get servlet config init parameters
        String userID = getServletConfig().getInitParameter("user");
        String password = getServletConfig().getInitParameter("password");

        // Validate username
        if (!Pattern.matches(NAME_REGEX, user)) {
            out.println("<font color=red>Invalid Name: Name must start with a capital letter and have at least 3 characters.</font>");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.html");
            rd.include(request, response);
            return;
        }

        // Validate password
        if (!Pattern.matches(PASSWORD_REGEX, pwd)) {
            out.println("<font color=red>Invalid Password: Password must have at least 8 characters, one uppercase letter, one number, and exactly one special character.</font>");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.html");
            rd.include(request, response);
            return;
        }

        // Check if credentials match
        if (userID.equals(user) && password.equals(pwd)) {
            request.setAttribute("user", user);
            request.getRequestDispatcher("/LoginSuccess.jsp").forward(request, response);
        } else {
            out.println("<font color=red>Either username or password is incorrect.</font>");
            RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.html");
            rd.include(request, response);
        }
    }
}
