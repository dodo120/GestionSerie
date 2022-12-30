<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link type="text/css" rel="stylesheet" href="./inc/style.css" />
<script type="text/javascript" src="./inc/GestionSeries.js"></script>
<title>Mes séries</title>
</head>
<body>
	<h1>Mes épisodes</h1>
	<span class="erreur"><c:out value="${erreur }" /></span>
	<table style="padding: 20px; border: 1px;">
		<c:forEach var="Episode" items="${episode}">
			<tr class="${episode.titre}_saison">
				<td class="filler">&nbsp;</td>
				<td class="filler">&nbsp;</td>
				<td class="${episode.vu} Episode">${episode.titre}</td>
				<td class="${episode.vu}"></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>