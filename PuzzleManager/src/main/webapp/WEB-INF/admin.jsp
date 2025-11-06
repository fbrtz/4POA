<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="pt-BR">
<head>
  <meta charset="utf-8" />
  <title>PuzzleManager - Login Administrativo</title>
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />
</head>
<body>
<header class="header">
    <div class="container">
        <h1>Área da Secretaria</h1>
    </div>
</header>

<main class="container">
    <div class="card">
        <h2>Login Administrativo</h2>

        <c:if test="${not empty error}">
            <div style="color:red; margin-bottom:10px;">${error}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/admin">
            <input type="hidden" name="action" value="login">
            <div class="form-row"><label>Usuário</label><input name="username" type="text" required></div>
            <div class="form-row"><label>Senha</label><input name="password" type="password" required></div>
            <button type="submit">Entrar</button>
        </form>
        
        <a href="${pageContext.request.contextPath}/index.html" class="btn" style="margin-top:10px; display:inline-block;">Página Inicial</a>
    </div>
</main>

<footer class="footer">© Faculdade — PuzzleManager</footer>
</body>
</html>
