<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:url value="/events/edit" var="editEventUrl" />
<c:url value="/events/remove" var="removeEventUrl" />
<c:url value="/events/details" var="detailsEventUrl" />
<c:url value="/events/decision/" var="eventDecisionUrl" />

<legend><c:choose>
				<c:when test="${page == 'all' || page == 'fast' }"><i class="glyphicon glyphicon-info-sign"></i> Lista wszystkich </c:when> 
				<c:when test="${page == 'organized'}"><i class="glyphicon glyphicon-info-sign"></i> Lista organizowanych </c:when>
				<c:when test="${page == 'invitations'}"><i class="glyphicon glyphicon-info-sign"></i> Lista na które Cię zaproszono </c:when> 
			</c:choose> <c:if test="${not empty stateId }" >
				<c:choose>
					<c:when test="${ stateId == 1 }">- w koszu</c:when>
					<c:when test="${ stateId == 2 }">- w budowie</c:when>
					<c:when test="${ stateId == 3 }">- do akceptacji</c:when>
					<c:when test="${ stateId == 4 }">- zagrożonych</c:when>
					<c:when test="${ stateId == 5 }">- przyjetych</c:when>
					<c:when test="${ stateId == 6 }">- nadchodzących</c:when>
				</c:choose>
				</c:if></legend>
<table data-toggle="table" id="table-pagination"  data-pagination="true" data-search="true" style="background-color:white">
    <thead style="background-color:#999999">
        <tr>
            <th data-field="orlikId" data-align="left" data-sortable="true">Orlik</th>
            <th data-field="eventDate" data-align="left" data-sortable="true">Data</th>
            <th data-field="eventTime" data-align="center" data-sortable="true">Godzina</th>
            <th data-field="eventStatus" data-align="center" data-sortable="true" >Status</th>
            <th data-field="eventDecision" data-align="center" data-sortable="true" >Decyzja</th>
            <th data-field="eventPlayers" data-align="center" data-sortable="true" >Graczy</th>
            <th data-align="center">Akcja</th>
        </tr>
    </thead>
    <tbody>
		<c:forEach items="${ userGamesDetailsList }"  var="details" varStatus="i" >		
			<c:set var="eventId"  value="${ details.getEventId() }"/>
			<c:set var="stateId"  value="${ details.getStateId() }"/>
			<c:set var="decisionId"  value="${ details.getDecisionId() }"/>
			<c:set var="roleId"  value="${ details.getRoleId() }"/>
			<c:set var="userPermission"  value="${ details.getPermission() }"/>
			<c:set var="players"  value="${ details.getWillCome() }"/>
			<c:set var="address"  value="${ details.getAddress() }"/>
			<c:set var="startDate" value="${ details.getStartDate() }" />
			<c:set var="endDate" value="${ details.getEndDate() }" />
			<c:set var="limit" value="${ details.getPlayersLimit()}"/>
			<c:set var="invited" value="${ details.getInvited()}"/>

			<c:set var="available"  value="${true}"/>
			<c:if test="${ stateId == 1 }">
				<c:set var="available"  value="${false}" />
			</c:if>

<fmt:formatDate value="${startDate}" type="both" pattern="dd.MM.yyyy" />
<fmt:formatDate value="${endDate}" type="both" pattern="HH:mm" /> - <fmt:formatDate value="${endDate}" type="both" pattern="HH:mm" /> 
					
    	<tr>
    		<td>${ address } </td>
    	<c:choose>
    		<c:when test="${ available }">
    		<td><fmt:formatDate value="${startDate}" type="both" pattern="dd.MM.yyyy" /></td>
    		<td><fmt:formatDate value="${startDate}" type="both" pattern="HH:mm" /> - <fmt:formatDate value="${endDate}" type="both" pattern="HH:mm" /></td>    				
    		</c:when>
    		<c:otherwise>
    		<td></td>
    		<td></td>  
    		</c:otherwise>
    	</c:choose>
    		<td><c:choose>
    			<c:when test="${ stateId == 1 }">
    				Kosz
    			</c:when>
    			<c:when test="${ stateId == 2 }">
    				W budowie
    			</c:when>
    			<c:when test="${ stateId == 3 }">
    				Do akceptacji
    			</c:when>
    			<c:when test="${ stateId == 4 }">
    				Zagrożony
    			</c:when>
    			<c:when test="${ stateId == 5 }">
    				Przyjęty
    			</c:when>
    			</c:choose></td>
    		<td>
    		<c:choose>
    			<c:when test="${ roleId == 1 }"><i style="color: gold" class="glyphicon glyphicon-star"></i></c:when>		
	    			<c:when test="${ roleId == 2 &&  stateId != 1}">
						<c:choose>
	    					<c:when test="${ decisionId == 1 }">
	    						<i style="color: blue" class="glyphicon glyphicon glyphicon-star-empty"></i>
	    						<a href="${eventDecisionUrl}/${eventId}/accept" title="Dołącz"> <i style="color: grey" class="glyphicon glyphicon-plus"></i></a>
	    						<a href="${eventDecisionUrl}/${eventId}/reject" title="Odrzuć"> <i style="color: grey" class="glyphicon glyphicon-minus"></i></a>
	    					</c:when>
	    					<c:when test="${ decisionId == 2 }">
	    						<i style="color: green" class="glyphicon glyphicon-plus"></i>
	    						<a href="${eventDecisionUrl}/${eventId}/reject" title="Odrzuć"> <i style="color: grey" class="glyphicon glyphicon-minus"></i></a>
	    					</c:when>
	    					<c:when test="${ decisionId == 3 }">
	    						<a href="${eventDecisionUrl}/${eventId}/accept" title="Dołącz"> <i style="color: grey" class="glyphicon glyphicon-plus"></i></a>
	    						<i style="color: red" class="glyphicon glyphicon-minus"></i>
	    					</c:when>
	    					<c:when test="${ decisionId == 4 }">
	    					<c:choose>
	    						<c:when test="${ userPermission == false }"><i class=" glyphicon glyphicon-question-sign"></i></c:when>
	    						<c:when test="${ userPermission == true }"><i class="glyphicon glyphicon-random"></i></c:when>
	    					</c:choose>
	    					</c:when>
	    				</c:choose>
					</c:when> 
    		</c:choose></td>
    		<td>${ players }/${ limit }/${ invited }</td>    		
    		<td>
    			<c:if  test="${ roleId == 1  }">
    				<a href="${editEventUrl}/${eventId}" title="Edytuj"> <i class="glyphicon glyphicon-edit"></i></a>
    				<a href="${removeEventUrl}/${eventId}" title="Usuń"> <i class="glyphicon glyphicon-remove"></i></a>
    			</c:if>
    			<a href="${detailsEventUrl}/${eventId}" title="Szczegóły"> <i class="glyphicon glyphicon-eye-open"></i></a>
    		</td>    		
    	</tr>
    	</c:forEach>  
    </tbody>
</table>


<legend>Legenda</legend>
<table data-toggle="table" id="table-pagination" style="background-color:white">
    <thead style="background-color:#999999">
        <tr>
            <th data-align="center" >Znak</th>
            <th data-align="left" >Opis</th>
        </tr>
    </thead>
    <tbody>
       	<tr>
    		<td><i style="color: gold" class="glyphicon glyphicon-star"></i></td>
    		<td>Jesteś organizatorem</td>
    	</tr>
    	<tr>
    		<td><i style="color: blue" class="glyphicon glyphicon glyphicon-star-empty"></i></td>
    		<td>Nie dokonałeś wyboru</td>
    	</tr>
    	<tr>
    		<td><i style="color:green" class="glyphicon glyphicon-plus"></i></td>
    		<td>Weźmiesz udział w wydarzeniu</td>
    	</tr> 
     	<tr>
    		<td><i style="color: red" class="glyphicon glyphicon-minus"></i></td>
    		<td>Nie bierzesz udziału w wydarzeniu</td>
    	</tr>
     	<tr>
    		<td><i style="color: silver" class="glyphicon glyphicon-random"></i></td>
    		<td>Otrzymałeś tylko prawa zapraszania swoich znajomych</td>
    	</tr>
    	<tr>
    		<td><i class=" glyphicon glyphicon-question-sign"></i></td>
    		<td>Nie powinieneś tego znaku nigdy zobaczyć</td>
    	</tr>
    </tbody>
</table>


<script src="<c:url value="/resources/mytheme/bootstrap/js/bootstrap-table.js" />"></script>