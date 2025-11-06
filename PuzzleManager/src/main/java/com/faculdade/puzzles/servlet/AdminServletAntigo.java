package com.faculdade.puzzles.servlet;

import com.faculdade.puzzles.dao.PuzzleDAO;
import com.faculdade.puzzles.dao.UserDAO;
import com.faculdade.puzzles.dao.VoteDAO;
import com.faculdade.puzzles.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controle administrativo: abrir/encerrar votacoes (secretaria).
 * Admin faz login via form em admin.html (simples).
 *
 * Actions:
 *  - login (username,password) -> cria session admin simples
 *  - openVoting (title, optionIds csv)
 *  - closeVoting (sessionId)
 */
public class AdminServletAntigo extends HttpServlet {

    private UserDAO userDAO = new UserDAO();
    private VoteDAO voteDAO = new VoteDAO();
    private PuzzleDAO puzzleDAO = new PuzzleDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "";

        try {
            if (action.equals("login")) {
                String username = req.getParameter("username");
                String password = req.getParameter("password");
                User u = userDAO.getByUsername(username);
                if (u != null && "ADMIN".equals(u.getRole()) && u.getPassword().equals(password)) {
                    req.getSession().setAttribute("adminUser", u.getUsername());
                    resp.sendRedirect(req.getContextPath() + "/admin.html?msg=welcome");
                    return;
                } else {
                    resp.sendRedirect(req.getContextPath() + "/admin.html?error=login");
                    return;
                }
            } else {
                // require admin session
                String admin = (String) req.getSession().getAttribute("adminUser");
                if (admin == null) {
                    resp.sendRedirect(req.getContextPath() + "/admin.html?error=auth");
                    return;
                }
                if (action.equals("openVoting")) {
                    String title = req.getParameter("title");
                    String optionCsv = req.getParameter("optionIds"); // "1,2,3"
                    List<Integer> ids = new ArrayList<>();
                    if (optionCsv != null && !optionCsv.isEmpty()) {
                        String[] parts = optionCsv.split(",");
                        for (String p : parts) {
                            try { ids.add(Integer.parseInt(p.trim())); } catch (NumberFormatException ignored) {}
                        }
                    }
                    int sessionId = voteDAO.createVotingSession(title != null ? title : "Votação", ids);
                    if (sessionId > 0) resp.sendRedirect(req.getContextPath() + "/admin.html?msg=session_created&id=" + sessionId);
                    else resp.sendRedirect(req.getContextPath() + "/admin.html?error=session_failed");
                    return;
                } else if (action.equals("closeVoting")) {
                    int sessionId = Integer.parseInt(req.getParameter("sessionId"));
                    boolean ok = voteDAO.closeVotingSession(sessionId);
                    if (ok) resp.sendRedirect(req.getContextPath() + "/admin.html?msg=session_closed");
                    else resp.sendRedirect(req.getContextPath() + "/admin.html?error=session_close");
                    return;
                } else if (action.equals("createPuzzle")) {
                    // delega para PuzzleDAO (ou via PuzzleServlet). Aqui simplificado:
                    String title = req.getParameter("title");
                    String imageUrl = req.getParameter("imageUrl");
                    int pieces = Integer.parseInt(req.getParameter("pieces"));
                    com.faculdade.puzzles.model.Puzzle p = new com.faculdade.puzzles.model.Puzzle();
                    p.setTitle(title);
                    p.setImageUrl(imageUrl);
                    p.setPieces(pieces);
                    p.setComplete(false);
                    puzzleDAO.create(p);
                    resp.sendRedirect(req.getContextPath() + "/admin.html?msg=puzzle_created");
                    return;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/admin.html?error=internal");
        }
    }
}
