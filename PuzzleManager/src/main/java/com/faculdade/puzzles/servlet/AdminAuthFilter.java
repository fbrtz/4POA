package com.faculdade.puzzles.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/WEB-INF/admin-dashboard.jsp")
public class AdminAuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        if (session != null && session.getAttribute("adminUser") != null) {
            chain.doFilter(request, response); // usuário logado, continua
        } else {
            resp.sendRedirect(req.getContextPath() + "/admin"); // não logado, volta para login
        }
    }
}
