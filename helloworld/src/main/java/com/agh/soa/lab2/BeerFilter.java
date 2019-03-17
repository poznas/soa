package com.agh.soa.lab2;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

import static java.util.Optional.of;
import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;

@WebFilter("/BeerFilter")
public class BeerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        var response = (HttpServletResponse) servletResponse;

        Consumer<Integer> sendError = age -> {
            try {
                response.sendError(SC_FORBIDDEN);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        of(servletRequest)
                .map(HttpServletRequest.class::cast)
                .map(rq -> rq.getHeader("x-age"))
                .map(Integer::parseInt)
                .filter(age -> age < 18)
                .ifPresent(sendError);

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
