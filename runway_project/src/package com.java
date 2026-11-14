package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "HelloServlet", urlPatterns = {"/hello"})
public class HelloServlet extends HttpServlet {

    private int hitCount;

    @Override
    public void init() throws ServletException {
        hitCount = 0;
        System.out.println("HelloServlet initialized");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        if (name == null || name.isBlank()) {
            name = "World";
        }
        respond(response, name, "GET");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        if (name == null || name.isBlank()) {
            name = "Guest";
        }
        respond(response, name, "POST");
    }

    private synchronized void respond(HttpServletResponse response, String name, String method)
            throws IOException {
        hitCount++;
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Hello Servlet</title></head><body>");
            out.println("<h1>Hello, " + escape(name) + "!</h1>");
            out.println("<p>Request method: " + method + "</p>");
            out.println("<p>Total hits since server start: " + hitCount + "</p>");
            out.println("<a href=\"index.html\">Back</a>");
            out.println("</body></html>");
        }
    }

    private String escape(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;");
    }

    @Override
    public void destroy() {
        System.out.println("HelloServlet destroyed");
    }
}