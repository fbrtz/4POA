package com.faculdade.puzzles.servlet;

import com.faculdade.puzzles.dao.UserDAO;
import com.faculdade.puzzles.dao.VoteDAO;
import com.faculdade.puzzles.dao.PuzzleDAO;
import com.faculdade.puzzles.model.User;
import com.faculdade.puzzles.model.Puzzle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet("/vote")
public class VoteServlet extends HttpServlet {

    private VoteDAO voteDAO = new VoteDAO();
    private UserDAO userDAO = new UserDAO();
    private PuzzleDAO puzzleDAO = new PuzzleDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Se for requisição "API", retorna JSON
        if ("true".equals(req.getParameter("api"))) {
            Integer sessionId = voteDAO.getActiveSessionId();
            resp.setContentType("application/json; charset=UTF-8");

            try (PrintWriter out = resp.getWriter()) {
                if (sessionId == null) {
                    out.print("{\"active\":false}");
                    return;
                }

                List<Integer> optionIds = voteDAO.getOptionsForSession(sessionId);
                List<Puzzle> puzzles = new ArrayList<>();
                for (Integer pid : optionIds) {
                    Puzzle p = puzzleDAO.getById(pid);
                    if (p != null) puzzles.add(p);
                }

                StringBuilder sb = new StringBuilder();
                sb.append("{\"active\":true, \"sessionId\":").append(sessionId).append(", \"options\":[");
                for (int i = 0; i < puzzles.size(); i++) {
                    Puzzle p = puzzles.get(i);
                    sb.append("{")
                      .append("\"id\":").append(p.getId()).append(",")
                      .append("\"title\":\"").append(escapeJson(p.getTitle())).append("\",")
                      .append("\"pieces\":").append(p.getPieces())
                      .append("}");
                    if (i < puzzles.size() - 1) sb.append(",");
                }
                sb.append("]}");
                out.print(sb.toString());
            }
            return;
        }

        // GET normal pode redirecionar para o HTML
        resp.sendRedirect(req.getContextPath() + "/vote.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String sSession = req.getParameter("sessionId");
        String sPuzzle = req.getParameter("puzzleId");

        try {
            if (username == null || username.isEmpty() || sSession == null || sPuzzle == null) {
                resp.sendRedirect(req.getContextPath() + "/vote.html?error=missing");
                return;
            }

            int sessionId = Integer.parseInt(sSession);
            int puzzleId = Integer.parseInt(sPuzzle);

            // garantir usuário existe
            User user = userDAO.getByUsername(username);
            if (user == null) {
                User u = new User();
                u.setUsername(username);
                u.setPassword("nao_pass");
                u.setRole("STUDENT");
                userDAO.create(u);
                user = userDAO.getByUsername(username);
            }

            boolean ok = voteDAO.castVote(sessionId, puzzleId, user.getId());
            if (ok) resp.sendRedirect(req.getContextPath() + "/puzzle-list.html?msg=voted");
            else resp.sendRedirect(req.getContextPath() + "/vote.html?error=already");

        } catch (Exception ex) {
            ex.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/vote.html?error=internal");
        }
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\"", "\\\"").replace("\n", "").replace("\r", "");
    }
}
