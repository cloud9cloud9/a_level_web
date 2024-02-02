package com.example.hw26.servlets;

import com.example.hw26.entity.User;
import com.example.hw26.entity.UserDao;
import com.example.hw26.entity.UserService;
import com.example.real.HelloServlet;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/users/*")
public class UserServlets extends HelloServlet {
    private final UserService userService = new UserService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String contextPath = request.getPathInfo();
        response.setContentType("application/json");
        try {
            if (contextPath != null) {
                String stringId = contextPath.substring(1);
                User user = userService.getUserById(Integer.parseInt(stringId));
                response.getWriter().println(mapper.writeValueAsString(user));
            } else {
                List<User> allUser = userService.getAllUser();
                response.getWriter().println(mapper.writeValueAsString(allUser));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal Server Error: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("userName");
        int age = Integer.parseInt(request.getParameter("age"));
        String email = request.getParameter("email");
        User newUser = User.builder().userName(name).age(age).email(email).build();
        userService.saveUser(newUser);
        response.getWriter().append("Person added: " + mapper.writeValueAsString(newUser));
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String contextPath = request.getPathInfo();
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            if (contextPath != null) {
                String stringId = contextPath.substring(1);
                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                User user = mapper.readValue(sb.toString(), User.class);
                userService.update(Integer.parseInt(stringId), user);
                response.getWriter().append("Person is updated");
            }
        } catch (Exception e) {
            out.write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        String contextPath = request.getPathInfo();
        try {
            if (contextPath != null) {
                userService.deleteUser(Integer.parseInt(contextPath.substring(1)));
                out.write("{\"report\": \"deleted\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}
