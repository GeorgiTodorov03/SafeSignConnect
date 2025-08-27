package Controllers;

import Models.User;
import Services.Impl.UserServiceImpl;
import Services.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user/")
public class UserController extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.userService = new UserServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String path = request.getPathInfo();
        if ("/register".equals(path)) {
            handleRegister(request, out);
        } else if ("/login".equals(path)) {
            handleLogin(request, out);
        } else if ("/update".equals(path)) {
            handleUpdate(request, out);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.print("{\"error\": \"Unknown endpoint\"}");
        }
    }

    private void handleRegister(HttpServletRequest request, PrintWriter out) {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        boolean success = userService.registerUser(username, email, password);

        if(success) {
            out.print("{\"success\": true, \"message\": \"User registered successfully\"}");
        } else {
            out.print("{\"success\": false, \"message\": \"Registration failed\"}");
        }
    }

    private void handleLogin(HttpServletRequest req, PrintWriter out) {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User loggedInUser = userService.loginUser(email, password);

        if (loggedInUser != null) {
            out.print("{\"success\": true, \"message\": \"Login successful\"}");
            // later: create session and store loggedInUser
        } else {
            out.print("{\"success\": false, \"message\": \"Invalid credentials\"}");
        }
    }

    private void handleUpdate(HttpServletRequest req, PrintWriter out) {
        int userId = Integer.parseInt(req.getParameter("id"));
        String newUsername = req.getParameter("username");
        String newPassword = req.getParameter("password");

        boolean success = userService.updateUser(userId, newUsername, newPassword);
        if (success) {
            out.print("{\"success\": true, \"message\": \"User updated successfully\"}");
        } else {
            out.print("{\"success\": false, \"message\": \"Update failed\"}");
        }
    }
}
