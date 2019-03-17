package com.agh.soa.lab2;

import one.util.streamex.StreamEx;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;

@WebServlet("lab2/numbers")
public class NumbersServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String numbers = StreamEx.of(request.getParameterNames())
                    .map(request::getParameter)
                    .mapToInt(Integer::parseInt)
                    .sorted()
                    .boxed()
                    .map(Object::toString)
                    .collect(joining(","));

            response.setHeader("numbers", numbers);
        } catch (NumberFormatException e) {
            response.sendError(SC_BAD_REQUEST, e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DoubleConsumer setResponseHeader = avg -> response.setHeader("average", String.valueOf(avg));

        Stream.of(request.getParameter("numbers").split(","))
                .mapToInt(Integer::parseInt)
                .average()
                .ifPresent(setResponseHeader);
    }
}
