<%@ page import="com.example.model.Cliente" %>
<%
    Cliente cliente = (Cliente) request.getAttribute("cliente");
    boolean edit = (cliente != null);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%= edit ? "Editar" : "Novo" %> Cliente</title>
</head>
<body>
<h1><%= edit ? "Editar" : "Novo" %> Cliente</h1>
<form method="post" action="cliente?action=<%= edit ? "update" : "insert" %>">
    <% if (edit) { %>
        <input type="hidden" name="id" value="<%= cliente.getId() %>">
    <% } %>
    <label>Nome:</label><br>
    <input type="text" name="nome" value="<%= edit ? cliente.getNome() : "" %>" required><br><br>

    <label>Email:</label><br>
    <input type="email" name="email" value="<%= edit ? cliente.getEmail() : "" %>" required><br><br>

    <label>Telefone:</label><br>
    <input type="text" name="telefone" value="<%= edit ? cliente.getTelefone() : "" %>"><br><br>

    <button type="submit"><%= edit ? "Atualizar" : "Inserir" %></button>
    <a href="cliente?action=list">Voltar</a>
</form>
</body>
</html>
