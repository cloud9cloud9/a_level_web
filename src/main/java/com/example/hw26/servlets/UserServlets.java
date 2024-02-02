package com.example.hw26.servlets;

import com.example.hw26.entity.User;
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
        response.setContentType("application/json");
        try {
            if (request.getPathInfo() != null) {
                String stringId = request.getPathInfo().substring(1);
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
        PrintWriter out = response.getWriter();
        StringBuffer sf = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while((line = reader.readLine()) != null){
                sf.append(line);
            }
            User user = mapper.readValue(sf.toString(), User.class);
            userService.saveUser(user);
            out.write(mapper.writeValueAsString(user));

        } catch (Exception e){
            out.write("{\"error\" : \"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            if (request.getPathInfo() != null) {
                String stringId = request.getPathInfo().substring(1);
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
