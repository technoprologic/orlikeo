<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<c:url value="/events/edit" var="editEventUrl" />
<c:url value="/events/create" var="createEventUrl" />
<c:url value="/events/graphic/${orlik.id }/${event.id}" var="graphicEventUrl" />


<c:choose>
	<c:when test="${ event.getState().getId() == 1 }"><c:set var="available" value="${false}"/></c:when>
	<c:otherwise><c:set var="available"  value="${true}"/></c:otherwise>
</c:choose>


<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">
		<c:if test="${ isOrganizer }" >
		<a href="${createEventUrl}/${event.id}"><i class="glyphicon glyphicon-edit"></i></a>
		</c:if>
		
		<c:choose>
			<c:when test="${ available }">
			Orlik ${orlik.city} ul.${orlik.getAddress()} <small>
			<c:if test="${ isOrganizer }">
				<a href="${graphicEventUrl}"><i class="glyphicon glyphicon-edit"></i></a>
			</c:if>
				<fmt:formatDate value="${graphic.getStartTime()}" type="both" pattern="HH.mm" /> - <fmt:formatDate value="${graphic.getEndTime()}" type="both" pattern="HH.mm" /> (<fmt:formatDate value="${graphic.getStartTime()}" type="both" pattern="dd.MM.yyyy" />)</small>
		
			</c:when>
			<c:otherwise>
				Brak Orlika
			</c:otherwise>
		</c:choose>
		</h1>
		<c:if test="${ available }">
		<ul>
			<li><i class="glyphicon glyphicon-info-sign"></i> Oświetlenie: <c:out value="${orlik.getLights() == true ? 'TAK': 'NIE'}"/></li>
			<li><i class="glyphicon glyphicon-info-sign"></i> Bieżąca woda: <c:out value="${orlik.getWater() == true ? 'TAK': 'NIE'}"/></li>
			<li><i class="glyphicon glyphicon-info-sign"></i> Prysznic: <c:out value="${orlik.getShower() == true ? 'TAK': 'NIE'}"/></li>
			<li><i class="glyphicon glyphicon-info-sign"></i> Obowiązujące obuwie: <c:out value="${orlik.getShoes()}"/></li>
			<li><i class="glyphicon glyphicon-user"></i> Animatorzy: <c:forEach items="${managers}" var="manager" varStatus="i">
																		<c:out value="${manager.email}"/><c:if test="${ i.index < managers.size()-1 }">, </c:if>
																	</c:forEach></li>
		</ul>
		</c:if>
	</div>
</div>



<form:form modelAttribute="editEventForm" class="form" action="${editEventUrl}" method="POST">
	<fieldset>
		<!-- Form Name -->
		<legend>Lista zaproszonych</legend>
		<table data-toggle="table" style="background-color: white; " >
			<thead>
				<tr>
					<th>Zaproszenie</th>
					<c:if test="${ isOrganizer }">
						<th>Prawo zapraszania</th>
					</c:if>
					<th>Zaprosił</th>
					<th>Data urodzenia</th>
					<th>Pozycja</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${ editEventForm.getEventFormMembers() }" var="friend" varStatus="i">                    
                    <tr>
                    <c:set var="friendUserEmail" value="${friend.getEmail()}" />
                    <c:set var="organizerEmail" value="${editEventForm.getOrganizerEmail()}" />
                    <c:choose>
                    	<c:when test="${ isOrganizer }">
                    		<td><form:checkbox path="eventFormMembers[${i.index}].invited"  class="invite" /><span style="margin-left:20px">${friend.getEmail()}</span> </td>
                    		<td><form:checkbox  path="eventFormMembers[${i.index}].allowed" class="allow" /></td>
                    	</c:when>
                    	<c:when test="${ friend.getInviter() eq organizerEmail }">
                    		<td><c:choose>
                    				<c:when test="${ friend.invited }">
                    					<form:hidden path="eventFormMembers[${i.index}].invited" value="true"/>
                    					<i style="color: gold" class="glyphicon glyphicon-star"></i>
                    				</c:when>
                    				<c:otherwise>
                    					<i style="color: silver" class="glyphicon glyphicon-random"></i>
                    				</c:otherwise>
                    			</c:choose>
                    			<form:hidden  path="eventFormMembers[${i.index}].allowed"  /><span style="margin-left:20px">${friend.getEmail()}</span> 
                    		</td>
                    	</c:when>
                    	<c:when test="${ friendUserEmail eq organizerEmail }">
                    		<td><form:hidden path="eventFormMembers[${i.index}].invited"  value="true"  />
                    			<i style="color: gold; margin-right:20px" class="glyphicon glyphicon-star"></i>
                    			<form:hidden  path="eventFormMembers[${i.index}].allowed"  value="true" />${organizerEmail}
                    		</td>
                    	</c:when>
                    	<c:otherwise>
                    		<td><form:checkbox path="eventFormMembers[${i.index}].invited" class="invite" />
                    			<form:hidden  path="eventFormMembers[${i.index}].allowed" />
                    			<span style="margin-left:20px">${friend.getEmail()}</span>
                    		</td>
                    	</c:otherwise>
                    </c:choose>
					<td><form:hidden path="eventFormMembers[${i.index}].email" value="${ friend.getEmail() }" /> 
					<c:choose >
						<c:when test="${ not empty friend.getInviter() }">
							<c:choose>
								<c:when test="${ friendUserEmail eq organizerEmail or organizerEmail eq friend.getInviter()}">
									<i style="color: gold;" class="glyphicon glyphicon-star"></i>
								</c:when>
								<c:otherwise>
									${ friend.getInviter() }
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							-
						</c:otherwise>
					</c:choose>
					</td>
					<td><fmt:formatDate value="${ friend.dateOfBirth }" type="both" pattern="dd.MM.yyyy" /></td>
					<td>${ friend.getPosition() }</td>
			    </tr>
			</c:forEach> 

			</tbody>
		</table>
		
		<div class="control-group">
		<form:hidden path="graphicId" value="${ editEventForm.getGraphicId() }" />
		<form:hidden path="eventId" value="${ event.id }" />
			<div>
				<input type="checkbox"  id="inviteAllStates">
				<label for="inviteAllStates">Zaproś wszystkich</label>
			</div>
			<c:if test="${ isOrganizer }" >
			<div>
				<input type="checkbox" id="allowAllStates">
				<label for="allowAllStates">Pozwól zapraszać wszystkim</label>
			</div>
			</c:if>
		</div>
		<c:choose>
			<c:when test="${ isOrganizer }" >
			<legend>Inne ustawienia</legend>
			<!-- Select Basic -->
			<div class="control-group">
			  <label class="control-label" for="maxPlayers">Limit graczy</label>
			  <div class="controls">
			    <form:select path="usersLimit" id="maxPlayers" name="maxPlayers" class="input-medium">
			    
			    <c:choose>
			    	<c:when test="${event.playersLimit == 12 }">
			    		<form:option value="12" selected="selected">12 (bez zmian)</form:option>
			    		<form:option value="14" >14 (po 1 na zmianę)</form:option>
			      		<form:option value="16" >16 (po 2 na zmianę)</form:option>
			    	</c:when>
			    		<c:when test="${event.playersLimit == 14 }">
			    		<form:option value="12">12 (bez zmian)</form:option>
			    		<form:option value="14" selected="selecet" >14 (po 1 na zmianę)</form:option>
			      		<form:option value="16" >16 (po 2 na zmianę)</form:option>
			    	</c:when>
			    		<c:when test="${event.playersLimit == 16 }">
			    		<form:option   value="12" >12 (bez zmian</form:option>
			    		<form:option value="14" >14 (po 1 na zmianę)</form:option>
			      		<form:option value="16" selected="selected">16 (po 2 na zmianę)</form:option>
			    	</c:when>
			    </c:choose>
			    </form:select>
			  </div>
			</div>
			</c:when>
			<c:otherwise>
				<form:hidden path="usersLimit" value="${event.playersLimit}"/>
			</c:otherwise>
		</c:choose>
		<!-- Buttons -->
		<div class="control-group .">
			<div class="controls">
				<button id="singlebutton" name="singlebutton"
					class="btn btn-primary  pull-right">Zapisz zmiany</button>
			</div>
		</div>
	</fieldset>
</form:form>




<script>


 


$(document).ready(function() {
    $('#inviteAllStates').click(function(event) {  //on click
        if(this.checked) { // check select status
            $('.invite').each(function() { //loop through each checkbox
                this.checked = true; //select all checkboxes with class "controls"              
            });
        }else{
            $('.invite').each(function() { //loop through each checkbox
                this.checked = false; //deselect all checkboxes with class "controls"                      
            });        
        }
    });
    $('#allowAllStates').click(function(event) {  //on click
        if(this.checked) { // check select status
            $('.allow').each(function() { //loop through each checkbox
                this.checked = true;  //select all checkboxes with class "controls"              
            });
        }else{
            $('.allow').each(function() { //loop through each checkbox
                this.checked = false; //deselect all checkboxes with class "controls"                      
            });        
        }
    });
   
});


</script>
		



<script src="<c:url value="/resources/mytheme/bootstrap/js/bootstrap-table.js" />" ></script>