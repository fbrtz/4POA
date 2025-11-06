package com.faculdade.puzzles.servlet;

import com.faculdade.puzzles.dao.PuzzleDAO;
import com.faculdade.puzzles.dao.UserDAO;
import com.faculdade.puzzles.dao.VoteDAO;
import com.faculdade.puzzles.model.Puzzle;
import com.faculdade.puzzles.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controle administrativo da secretaria.
 * Login via form.
 */
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();
    private VoteDAO voteDAO = new VoteDAO();
    private PuzzleDAO puzzleDAO = new PuzzleDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("adminUser") != null) {
            req.getRequestDispatcher("/WEB-INF/admin-dashboard.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/admin.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "";

        HttpSession session = req.getSession();

        try {
            if (action.equals("login")) {
                String username = req.getParameter("username");
                String password = req.getParameter("password");

                User u = userDAO.getByUsername(username);
                if (u != null && "ADMIN".equals(u.getRole()) && u.getPassword().equals(password)) {
                    session.setAttribute("adminUser", u.getUsername());
                    resp.sendRedirect(req.getContextPath() + "/admin");
                    return;
                } else {
                    req.setAttribute("error", "Usuário ou senha inválidos!");
                    req.getRequestDispatcher("/WEB-INF/admin.jsp").forward(req, resp);
                    return;
                }

            } else if (action.equals("logout")) {
                session.invalidate();
                resp.sendRedirect(req.getContextPath() + "/admin");
                return;
            } else {
                String admin = (String) session.getAttribute("adminUser");
                if (admin == null) {
                    resp.sendRedirect(req.getContextPath() + "/admin");
                    return;
                }

                switch (action) {
                    case "createPuzzle":
                        String title = req.getParameter("title");
                        String imageUrl = req.getParameter("imageUrl");
                        int pieces = Integer.parseInt(req.getParameter("pieces"));
                        Puzzle p = new Puzzle();
                        p.setTitle(title);
                        p.setImageUrl(imageUrl);
                        p.setPieces(pieces);
                        p.setComplete(false);
                        puzzleDAO.create(p);
                        resp.sendRedirect(req.getContextPath() + "/admin?msg=puzzle_created");
                        break;

                    case "openVoting":
                        String voteTitle = req.getParameter("title");
                        String optionCsv = req.getParameter("optionIds");
                        List<Integer> ids = new ArrayList<>();
                        if (optionCsv != null && !optionCsv.isEmpty()) {
                            String[] parts = optionCsv.split(",");
                            for (String part : parts) {
                                try {
                                    ids.add(Integer.parseInt(part.trim()));
                                } catch (NumberFormatException ignored) {}
                            }
                        }
                        int sessionId = voteDAO.createVotingSession(voteTitle != null ? voteTitle : "Votação", ids);
                        if (sessionId > 0)
                            resp.sendRedirect(req.getContextPath() + "/admin?msg=session_created&id=" + sessionId);
                        else
                            resp.sendRedirect(req.getContextPath() + "/admin?error=session_failed");
                        break;

                    case "closeVoting":
                        int sessionIdClose = Integer.parseInt(req.getParameter("sessionId"));
                        boolean ok = voteDAO.closeVotingSession(sessionIdClose);
                        if (ok)
                            resp.sendRedirect(req.getContextPath() + "/admin?msg=session_closed");
                        else
                            resp.sendRedirect(req.getContextPath() + "/admin?error=session_close");
                        break;

                    default:
                        resp.sendRedirect(req.getContextPath() + "/admin");
                        break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/admin?error=internal");
        }
    }
}
