<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<c:url value="/events/register" var="registerEventUrl" />
<c:url value="/events/create" var="createEventUrl" />
<c:url value="/events/graphic" var="graphicEventUrl" />

<c:set var="graphic" scope="session" value="${event.getGraphic()}"/>
<c:set var="orlik" scope="session" value="${graphic.orlik }"/>



<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">Utworzyłeś wydarzenie
			<a href="${createEventUrl}"><i class="glyphicon glyphicon-edit"></i></a>
			Orlik ${orlik.city} ul.${orlik.getAddress()} <small><a
				href="${graphicEventUrl}"><i class="glyphicon glyphicon-edit"></i></a>
				<fmt:formatDate value="${graphic.getStartTime()}" type="both" pattern="HH.mm" /> - <fmt:formatDate value="${graphic.getEndTime()}" type="both" pattern="HH.mm" /> (<fmt:formatDate value="${graphic.getStartTime()}" type="both" pattern="dd.MM.yyyy" />)</small>
		</h1>
		<ul>
			<li><i class="glyphicon glyphicon-info-sign"></i> Oświetlenie: <c:out value="${orlik.getLights() == true ? 'TAK': 'NIE'}"/></li>
			<li><i class="glyphicon glyphicon-info-sign"></i> Bieżąca woda: <c:out value="${orlik.getWater() == true ? 'TAK': 'NIE'}"/></li>
			<li><i class="glyphicon glyphicon-info-sign"></i> Prysznic: <c:out value="${orlik.getShower() == true ? 'TAK': 'NIE'}"/></li>
			<li><i class="glyphicon glyphicon-info-sign"></i> Obowiązujące obuwie: <c:out value="${orlik.getShoes()}"/></li>
			<li><i class="glyphicon glyphicon-user"></i> Animatorzy: Francesco Totti, Angela Merkel, Tusek Złodziejaszek</li>
		</ul>
	</div>
</div>

		<legend>Lista biorących udział w wydarzeniu</legend>
		<table data-toggle="table" style="background-color: white" >
			<thead>
				<tr>
					<th>Prawo zapraszania</th>
					<th>Status uzytkwnika</th>
					<th>Użytkownik</th>
					<th>Pozycja</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${ usersEvent }" var="userEvent" varStatus="i">   
			<c:set var="decision" scope="session" value="${userEvent.userDecision}"/>                 
                    <tr>
					<td>${ userEvent.userPermission ? "TAK" : "NIE" }</td>
					<td><c:choose>
						    <c:when test="${decision == 1}">
						    	<c:if test="${not empty pageContext.request.userPrincipal}">
    								Zaproszony
								</c:if>
						    </c:when>
						    <c:when test="${decision == 2}">
						        <c:if test="${not empty pageContext.request.userPrincipal}">
    								${pageContext.request.userPrincipal.name == userEvent.getUser().email ? "Organizator" : "Zaakceptował" }
								</c:if>
						    </c:when>
						    <c:when test="${decision == 3}">
						        Odrzucił
						    </c:when>
						    <c:otherwise>
						        Nie wiadomo co zrobił
						    </c:otherwise>
						</c:choose></td>
					<td>${ userEvent.getUser().email }</td>
					<td>${pageContext.request.userPrincipal.name}</td>
			    </tr>
			</c:forEach> 

			</tbody>
		</table>
		



<script src="<c:url value="/resources/mytheme/bootstrap/js/bootstrap-table.js" />" ></script>