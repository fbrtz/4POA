<%@ page import="java.util.List, com.example.model.Cliente" %>
<%
    List<Cliente> list = (List<Cliente>) request.getAttribute("listClientes");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lista de Clientes</title>
</head>
<body>
<h1>Clientes</h1>
<p><a href="cliente?action=new">Adicionar novo cliente</a></p>
<table border="1" cellpadding="6" cellspacing="0">
    <tr><th>ID</th><th>Nome</th><th>Email</th><th>Telefone</th><th>Ações</th></tr>
    <%
        if (list != null) {
            for (Cliente c : list) {
    %>
    <tr>
        <td><%= c.getId() %></td>
        <td><%= c.getNome() %></td>
        <td><%= c.getEmail() %></td>
        <td><%= c.getTelefone() %></td>
        <td>
            <a href="cliente?action=edit&id=<%= c.getId() %>">Editar</a>
            |
            <a href="cliente?action=delete&id=<%= c.getId() %>" onclick="return confirm('Excluir este cliente?');">Excluir</a>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>
</body>
</html>
