package com.agh.soa;

import com.agh.soa.lab4.counter.ITestBeanCounter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("pierwszy")
public class First extends HttpServlet {

    @EJB(lookup = "java:global/ejb_war/TestBeanCounter")
    ITestBeanCounter counter;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        counter.increment();

        response.setContentType("text/html");
        response.setCharacterEncoding("windows-1250");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>Pierwszy Servlet</title></head>");
        out.println("<body>");
        out.println("<p>Witaj, " + request.getParameter("imie") +
                ", masz " + counter.getNumber() + " lat</p>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}
