package com.agh.soa.lab2;

import one.util.streamex.StreamEx;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Vector;
import java.util.function.DoubleConsumer;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;

@WebServlet("lab2/guest")
public class GuestServlet extends HttpServlet {

    private List<GuestPost> posts = new Vector<>();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            ofNullable(request.getParameter("password"))
                    .filter(pass -> pass.equals("123456"))
                    .orElseThrow();
        } catch (RuntimeException e) {
            response.sendError(SC_FORBIDDEN);
        }

        var user = new Guest();
        user.setLogin(request.getParameter("login"));
        user.setEmail(request.getParameter("email"));

        request.getSession(true).setAttribute("guest", user);
        redirectToGuestPostPage(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        var user = ofNullable(request.getSession().getAttribute("guest"))
                .map(Guest.class::cast);

        if (user.isEmpty()) {
            response.sendRedirect("guestLogin.html");
        } else {
            ofNullable(request.getParameter("comment"))
                    .map(comment -> GuestPost.of(user.get(), comment))
                    .ifPresent(posts::add);

            redirectToGuestPostPage(request, response);
        }
    }

    private void redirectToGuestPostPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("posts", posts);

        var dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/guests.jsp");
        dispatcher.forward(request, response);
    }
}
