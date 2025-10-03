package com.example.servlet;

import com.example.dao.ClienteDAO;
import com.example.model.Cliente;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/cliente")
public class ClienteServlet extends HttpServlet {
    private ClienteDAO clienteDAO;

    @Override
    public void init() throws ServletException {
        clienteDAO = new ClienteDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // POST para insert e update
        String action = request.getParameter("action");
        try {
            if ("insert".equals(action)) {
                insertCliente(request, response);
            } else if ("update".equals(action)) {
                updateCliente(request, response);
            } else {
                doGet(request, response);
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // GET para list, new, edit, delete
        String action = request.getParameter("action");
        if (action == null) action = "list";

        try {
            switch (action) {
                case "new":
                    showNewForm(request, response);
                    break;
                case "edit":
                    showEditForm(request, response);
                    break;
                case "delete":
                    deleteCliente(request, response);
                    break;
                case "list":
                default:
                    listClientes(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listClientes(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Cliente> list = clienteDAO.listAll();
        request.setAttribute("listClientes", list);
        RequestDispatcher dispatcher = request.getRequestDispatcher("cliente-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("cliente-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Cliente existing = clienteDAO.getById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("cliente-form.jsp");
        request.setAttribute("cliente", existing);
        dispatcher.forward(request, response);
    }

    private void insertCliente(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String telefone = request.getParameter("telefone");
        Cliente novo = new Cliente(nome, email, telefone);
        clienteDAO.insert(novo);
        response.sendRedirect("cliente?action=list");
    }

    private void updateCliente(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String telefone = request.getParameter("telefone");
        Cliente cliente = new Cliente(id, nome, email, telefone);
        clienteDAO.update(cliente);
        response.sendRedirect("cliente?action=list");
    }

    private void deleteCliente(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        clienteDAO.delete(id);
        response.sendRedirect("cliente?action=list");
    }
}
