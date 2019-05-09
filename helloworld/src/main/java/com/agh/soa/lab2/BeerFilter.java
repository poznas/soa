package com.agh.soa.lab2;


import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;

import java.io.IOException;
import java.util.function.IntConsumer;
import java.util.stream.Stream;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;

@Log
@WebFilter("/BeerFilter")
public class BeerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        var response = (HttpServletResponse) servletResponse;

        IntConsumer sendError = age -> {
            try {
                response.sendError(SC_FORBIDDEN);
            } catch (IOException e) {
                log.warning(e.getMessage());
            }
        };

        Stream.of(servletRequest)
                .map(HttpServletRequest.class::cast)
                .map(rq -> rq.getHeader("x-age"))
                .mapToInt(Integer::parseInt)
                .filter(age -> age < 18)
                .findAny()
                .ifPresent(sendError);

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
