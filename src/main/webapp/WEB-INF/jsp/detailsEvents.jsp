<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<c:url value="/events/register" var="registerEventUrl" />
<c:url value="/events/create" var="createEventUrl" />
<c:url value="/events/graphic" var="graphicEventUrl" />


<c:url value="/events/join" var="joinEventUrl" />
<c:url value="/events/reject" var="rejectEventUrl" />
<c:url value="/events/remove" var="removeEventUrl" />
<c:url value="/events/edit" var="editEventUrl" />

<c:set var="graphic"  value="${eventDetails.graphic}"/>
<c:set var="orlik"    value="${eventDetails.orlik }"/>
<c:set var="event"    value="${eventDetails.event }"/>


<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">
			<%-- <c:if test="${ organizerEmail}">
				<a href="${createEventUrl}" title="Zmień boisko"><i class="glyphicon glyphicon-edit"></i></a>
			</c:if> --%>
			${orlik.city} ul.${orlik.getAddress()} <small>
			<%-- <c:if test="${ organizerEmail}">
				<a href="${graphicEventUrl}" title="Zmień godzinę"><i class="glyphicon glyphicon-edit"></i></a>
			</c:if> --%>
				<fmt:formatDate value="${graphic.getStartTime()}" type="both" pattern="HH.mm" /> - <fmt:formatDate value="${graphic.getEndTime()}" type="both" pattern="HH.mm" /> (<fmt:formatDate value="${graphic.getStartTime()}" type="both" pattern="dd.MM.yyyy" />)</small>
				<c:choose >
					<c:when test="${ organizerEmail}" >
						<a href="${editEventUrl}/${event.id}" title="Edytuj"> <i class="glyphicon glyphicon-edit" style="margin-left:0.5em"></i></a>
						<%-- <a href="${removeEventUrl}/${event.id}" title="Usuń"> <i class="glyphicon glyphicon-remove" style="margin-left:0.5em"></i></a> --%>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${ decision == 1 }">
								<a href="${joinEventUrl}/${event.id}" title="Wezmę udział"> <i class="glyphicon glyphicon-plus" style="margin-left:0.5em; color:green;"></i></a> /
	    						<a href="${rejectEventUrl}/${event.id}" title="Nie wezmę udziału"> <i class="glyphicon glyphicon-minus" style="margin-left:0.5em; color:red;"></i></a>	
							</c:when>
							<c:when test="${ decision == 2}">
								<i class="glyphicon glyphicon-plus" style="margin-left:0.5em; color:green;"></i> /
	    						<a href="${rejectEventUrl}/${event.id}" title="Nie wezmę udziału"> <i class="glyphicon glyphicon-minus" style="margin-left:0.5em; color:grey;"></i></a>
							</c:when>
							<c:when test="${ decision == 3}">
								<a href="${joinEventUrl}/${event.id}" title="Wezmę udział"> <i class="glyphicon glyphicon-plus" style="margin-left:0.5em; color: grey;"></i></a> /
	    						<i class="glyphicon glyphicon-minus" style="margin-left:0.5em; color:red;"></i>
							</c:when>
						</c:choose>
					</c:otherwise>
	    		</c:choose>
		</h1>
		<ul>
			<li><i class="glyphicon glyphicon-info-sign"></i> Oświetlenie: <c:out value="${orlik.getLights() == true ? 'TAK': 'NIE'}"/></li>
			<li><i class="glyphicon glyphicon-info-sign"></i> Bieżąca woda: <c:out value="${orlik.getWater() == true ? 'TAK': 'NIE'}"/></li>
			<li><i class="glyphicon glyphicon-info-sign"></i> Prysznic: <c:out value="${orlik.getShower() == true ? 'TAK': 'NIE'}"/></li>
			<li><i class="glyphicon glyphicon-info-sign"></i> Obowiązujące obuwie: <c:out value="${orlik.getShoes()}"/></li>
			<li><i class="glyphicon glyphicon-user"></i> Animatorzy:<c:forEach items="${managers}" var="manager" varStatus="i">
																		<c:out value="${manager.email}"/><c:if test="${ i.index < managers.size()-1 }">, </c:if>
																	</c:forEach></li>
		</ul>
		<ul>
			<li> Aktualny status:<c:choose>
									<c:when test="${ event.stateId == 1}"> <i class="glyphicon glyphicon-trash"></i> W koszu</c:when>
									<c:when test="${ event.stateId == 2}"> <i class="glyphicon glyphicon-thumbs-down"></i> W budowie</c:when>
									<c:when test="${ event.stateId == 3}"> <i class="glyphicon glyphicon-send"></i> Do akceptacji</c:when>
									<c:when test="${ event.stateId == 4}"> <i class="glyphicon glyphicon-warning-sign"></i> Zagrożony</c:when>
									<c:when test="${ event.stateId == 5}"> <i class="glyphicon glyphicon-thumbs-up"></i> Przyjęty</c:when>
								</c:choose></li>
			<li> Liczba osób zaproszonych: ${eventDetails.invitedPlayers}</li>
		</ul>
	</div>
</div>


</br>
</br>
</br>
<div class="row">
		<!-- Form Name -->
<legend>Wezmą udział (${usersJoinedDecision.size()})</legend>
<table data-toggle="table" id="table-pagination"    data-search="true" style="background-color:white">
    <thead style="background-color:#999999">
        <tr>
            <th data-field="orlikId" data-align="center" data-sortable="true"><i class="glyphicon glyphicon-user"></i> Użytkownik</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${usersJoinedDecision }" var="user">
    	<tr>
    		<td>${user.email }</td>		
    	</tr> 
    </c:forEach>      	
    </tbody>
</table>
</div>






</br>
</br>
</br>
<div class="row">
		<!-- Form Name -->
<legend>Niezdecydowani (${usersWithoutDecision.size()})</legend>
<table data-toggle="table" id="table-pagination"    data-search="true" style="background-color:white">
    <thead style="background-color:#999999">
        <tr>
            <th data-field="orlikId" data-align="center" data-sortable="true"><i class="glyphicon glyphicon-user"></i> Użytkownik</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${usersWithoutDecision }" var="user">
    	<tr>
    		<td>${user.email }</td>
    	</tr> 
    </c:forEach>       	
    </tbody>
</table>
</div>

</br>
</br>
</br>
<div class="row">
		<!-- Form Name -->
<legend>Odrzucili (${usersRejectedDecision.size()})</legend>
<table data-toggle="table" id="table-pagination"    data-search="true" style="background-color:white">
    <thead style="background-color:#999999">
        <tr>
            <th data-field="orlikId" data-align="center" data-sortable="true"><i class="glyphicon glyphicon-user"></i> Użytkownik</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${usersRejectedDecision }" var="user">
    	<tr>
    		<td>${user.email }</td>
    	</tr> 
    </c:forEach>  	
    </tbody>
</table>
</div>

</br>
</br>
</br>
<div class="row">
		<!-- Form Name -->
<legend>Mogących tylko zapraszać (${usersPermittedOnly.size()})</legend>
<table data-toggle="table" id="table-pagination"    data-search="true" style="background-color:white">
    <thead style="background-color:#999999">
        <tr>
            <th data-field="orlikId" data-align="center" data-sortable="true"><i class="glyphicon glyphicon-user"></i> Użytkownik</th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${usersPermittedOnly }" var="user">
    	<tr>
    		<td>${user.email }</td>
    	</tr> 
    </c:forEach>  	
    </tbody>
</table>
</div>
</br>
</br>
</br>
<script src="<c:url value="/resources/mytheme/bootstrap/js/bootstrap-table.js" />"></script>