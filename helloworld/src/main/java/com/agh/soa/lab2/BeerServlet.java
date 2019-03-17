package com.agh.soa.lab2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;

@WebServlet("lab2/beer")
public class BeerServlet extends HttpServlet {

    private static final BeerExpert DEFAULT_BEER = new BeerExpert("Kustosz Teqila");

    private static final Map<String, BeerExpert> BEERS =
            Map.of("red", new BeerExpert("Kustosz Malinowy"),
                    "black", new BeerExpert("Kustosz Mocny"),
                    "yellow", DEFAULT_BEER);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        var beerExpert = ofNullable(request.getParameter("color"))
                .map(BEERS::get).orElse(DEFAULT_BEER);

        request.setAttribute("beerExpert", beerExpert);

        var dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/beer-result.jsp");

        dispatcher.forward(request, response);
    }
}
