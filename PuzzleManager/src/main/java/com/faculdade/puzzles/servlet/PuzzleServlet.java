package com.faculdade.puzzles.servlet;

import com.faculdade.puzzles.dao.PuzzleDAO;
import com.faculdade.puzzles.model.Puzzle;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

/**
 * CRUD de puzzles + endpoint REST /api/puzzles
 */
@WebServlet({"/puzzles", "/api/puzzles"})
public class PuzzleServlet extends HttpServlet {

    private PuzzleDAO puzzleDAO = new PuzzleDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uri = req.getRequestURI();
        if (uri.endsWith("/api/puzzles") || uri.contains("/api/puzzles")) {
            List<Puzzle> list = puzzleDAO.getAll();
            resp.setContentType("application/json; charset=UTF-8");
            try (PrintWriter out = resp.getWriter()) {
                out.print("[");
                for (int i = 0; i < list.size(); i++) {
                    out.print(list.get(i).toJson());
                    if (i < list.size() - 1) out.print(",");
                }
                out.print("]");
            }
            return;
        }
        resp.sendRedirect(req.getContextPath() + "/puzzle-list.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");
        if (action == null) action = "";

        try {
            if (action.equals("create")) {
                String title = req.getParameter("title");
                String imageUrl = req.getParameter("imageUrl");
                int pieces = Integer.parseInt(req.getParameter("pieces"));
                Date startDate = null;
                if (req.getParameter("startDate") != null && !req.getParameter("startDate").isEmpty())
                    startDate = Date.valueOf(req.getParameter("startDate"));

                Puzzle p = new Puzzle();
                p.setTitle(title);
                p.setImageUrl(imageUrl);
                p.setPieces(pieces);
                p.setStartDate(startDate);
                p.setComplete(false);
                puzzleDAO.create(p);
                resp.sendRedirect(req.getContextPath() + "/admin.html?msg=created");
                return;

            } else if (action.equals("update")) {
                int id = Integer.parseInt(req.getParameter("id"));
                Puzzle p = puzzleDAO.getById(id);
                if (p != null) {
                    p.setTitle(req.getParameter("title"));
                    p.setImageUrl(req.getParameter("imageUrl"));
                    p.setPieces(Integer.parseInt(req.getParameter("pieces")));
                    String start = req.getParameter("startDate");
                    p.setStartDate(start != null && !start.isEmpty() ? Date.valueOf(start) : null);
                    String end = req.getParameter("endDate");
                    p.setEndDate(end != null && !end.isEmpty() ? Date.valueOf(end) : null);
                    p.setComplete("on".equals(req.getParameter("complete")) || "true".equals(req.getParameter("complete")));
                    puzzleDAO.update(p);
                }

            } else if (action.equals("delete")) {
                int id = Integer.parseInt(req.getParameter("id"));
                puzzleDAO.delete(id);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        resp.sendRedirect(req.getContextPath() + "/puzzle-list.html");
    }
}
