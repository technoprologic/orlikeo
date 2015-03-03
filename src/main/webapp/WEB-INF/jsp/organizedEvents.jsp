<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<c:url value="/events/edit" var="editEventUrl" />
<c:url value="/events/remove" var="removeEventUrl" />
<c:url value="/events/details" var="detailsEventUrl" />



 <c:set var="userEvent" scope="session" value="${ event.usersEvent }"/>

		<!-- Form Name -->
<legend>Wydarzenia - wszystkie / organizowane / zaproszenia (Zagrożone)</legend>
<table data-toggle="table" id="table-pagination"  data-pagination="true" data-search="true" style="background-color:white">
    <thead style="background-color:#999999">
        <tr>
            <th data-field="orlikId" data-align="right" data-sortable="true">Orlik</th>
            <th data-field="eventDate" data-align="right" data-sortable="true">Data</th>
            <th data-field="eventTime" data-align="center" data-sortable="true">Godzina</th>
            <th data-field="eventStatus" data-align="center" data-sortable="true" >Status</th>
            <th data-field="eventDecision" data-align="center" data-sortable="true" >Decyzja</th>
            <th data-field="eventPlayers" data-align="center" data-sortable="true" >Graczy</th>
            <th data-align="center">Edytuj / Usuń / Szczegóły / Dołącz</th>
        </tr>
    </thead>
    <tbody>
    	<c:forEach items="${ eventsInBuildState }" var="event" varStatus="i">
    	<tr>
    		<td>${ event.graphic.orlik.address } </td>
    		<td><fmt:formatDate value="${event.graphic.startTime}" type="both" 
      pattern="dd.MM.yyyy" /></td>
    		<td><fmt:formatDate value="${event.graphic.startTime}" type="both" 
      pattern="HH:mm" /> - <fmt:formatDate value="${event.graphic.startTime}" type="both" 
      pattern="HH:mm" /></td>
      		
    		<td>${event.state.getState()}</td>
    		

    		
    		
    		<td><c:forEach items="${ userEvent }" var="event" varStatus="j">
    			<c:out value="${ event }"/> 
    		</c:forEach><i class="glyphicon glyphicon-minus"></i></td>
    		<td>12/14</td>    		
    		<td><a href="${editEventUrl}/${event.id}" title="Edytuj"> <i class="glyphicon glyphicon-edit"></i></a>
    			<a href="${removeEventUrl}/${event.id}" title="Usuń"> <i class="glyphicon glyphicon-remove"></i></a>
    			<a href="${detailsEventUrl}/${event.id}" title="Szczegóły"> <i class="glyphicon glyphicon-eye-open"></i></a>
    		</td>    		
    	</tr>
    	</c:forEach>  
    </tbody>
</table>

<c:if test="${not empty pageContext.request.userPrincipal}">
    User: <c:out value="${pageContext.request.userPrincipal.name}" />
</c:if>

<script src="<c:url value="/resources/mytheme/bootstrap/js/bootstrap-table.js" />"></script>



