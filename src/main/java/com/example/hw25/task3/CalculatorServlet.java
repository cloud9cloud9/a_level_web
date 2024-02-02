package com.example.hw25.task3;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/calculate")
public class CalculatorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String operation = req.getParameter("op");
        int number1 = Integer.parseInt(req.getParameter("n1"));
        int number2 = Integer.parseInt(req.getParameter("n2"));

        double result = 0;

        switch (operation) {
            case "add":
                result = number1 + number2;
                break;
            case "minus":
                result = number1 - number2;
                break;
            case "multiplication":
                result = number1 * number2;
                break;
            case "division":
                result = number1 / number2;
                break;
            default:
                resp.getWriter().println("Unsupported operation");
                return;
        }
        resp.getWriter().println(result);
    }
}
