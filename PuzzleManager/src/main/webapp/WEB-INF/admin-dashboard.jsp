<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="pt-BR">
<head>
  <meta charset="utf-8" />
  <title>PuzzleManager - Painel Administrativo</title>
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css" />
</head>
<body>
<header class="header">
    <div class="container">
        <h1>Painel Administrativo</h1>
        <form method="post" action="${pageContext.request.contextPath}/admin" style="display:inline;">
            <input type="hidden" name="action" value="logout">
            <button type="submit" style="float:right;">Logout</button>
        </form>
    </div>
</header>

<main class="container">
    <div class="card">
        <h2>Cadastrar novo quebra-cabeça</h2>
        <form method="post" action="${pageContext.request.contextPath}/admin">
            <input type="hidden" name="action" value="createPuzzle">
            <div class="form-row"><label>Título</label><input name="title" type="text" required></div>
            <div class="form-row"><label>URL da Imagem</label><input name="imageUrl" type="text"></div>
            <div class="form-row"><label>Peças</label><input name="pieces" type="number" required></div>
            <button type="submit">Cadastrar</button>
        </form>

        <hr/>

        <h2>Abrir votação</h2>
        <form method="post" action="${pageContext.request.contextPath}/admin">
            <input type="hidden" name="action" value="openVoting">
            <div class="form-row"><label>Título da votação</label><input name="title" type="text" required></div>
            <div class="form-row"><label>IDs das opções (ex: 1,2,3)</label><input name="optionIds" type="text" required></div>
            <button type="submit">Abrir votação</button>
        </form>

        <hr/>

        <h2>Encerrar votação</h2>
        <form method="post" action="${pageContext.request.contextPath}/admin">
            <input type="hidden" name="action" value="closeVoting">
            <div class="form-row"><label>Session ID</label><input name="sessionId" type="number" required></div>
            <button type="submit">Encerrar</button>
        </form>
    </div>
</main>

<footer class="footer">© Faculdade — PuzzleManager</footer>
</body>
</html>
