<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<c:url value="/friends/userDetail" var="detailsUrl" />
<c:url value="/friends/acceptUser" var="acceptUserUrl" />





<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">
			Znajomi <small><i class="glyphicon glyphicon-user"></i> Moi znajomi</small>
		</h1>
	</div>
</div>

<table data-toggle="table" id="table-pagination"  data-pagination="true" data-search="true" style="background-color:white">
    <thead style="background-color:#999999">
        <tr>
            <th data-field="uId" data-align="left" data-sortable="true">Użytkownik</th>
            <th data-field="email" data-align="center" data-sortable="true">Email</th>
            <th data-align="center">Szczegóły </th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${friends}" var="friend" varStatus="i">
		<c:set var="name"  value="${ friend.name }"/>
		<c:set var="surname"  value="${ friend.surname }"/>
		<c:set var="email"  value="${ friend.email }"/>
		<c:set var="userId"  value="${ friend.id }"/>
    	<tr>
    		<td>${name} ${surname}</td>
    		<td>${email}</td>		
    		<td><a href="${detailsUrl}/${email}" title="Szczegóły"> <i class="glyphicon glyphicon-eye-open"></i></a>
    		</td>    		
    	</tr>  
   </c:forEach>     	
    </tbody>
</table>












<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header"><small><i class="glyphicon glyphicon-user"></i> Otrzymane zaproszenia</small>
		</h1>
	</div>
</div>

<table data-toggle="table" id="table-pagination"  data-pagination="true" data-search="true" style="background-color:white">
    <thead style="background-color:#999999">
        <tr>
            <th data-field="uId" data-align="left" data-sortable="true">Użytkownik</th>
            <th data-field="email" data-align="center" data-sortable="true">Email</th>
            <th data-align="center">Szczegóły </th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${friendsReceivedRequests}" var="friend" varStatus="i">
		<c:set var="name"  value="${ friend.name }"/>
		<c:set var="surname"  value="${ friend.surname }"/>
		<c:set var="email"  value="${ friend.email }"/>
		<c:set var="userId"  value="${ friend.id }"/>
    	<tr>
    		<td>${name} ${surname}</td>
    		<td>${ email } </td>		
    		<td>
    		<a href="${detailsUrl}/${email}" title="Szczegóły"> <i class="glyphicon glyphicon-eye-open"></i></a>	    
		    <div class="modal fade" id="confirm-add" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		        <div class="modal-dialog">
		            <div class="modal-content">
		            
		                <div class="modal-header">
		                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		                    <h4 class="modal-title" id="myModalLabel">Jesteś pewien, że chcesz dodać użytkownika do znajomych ?</h4>
		                </div>
		
		                <div class="modal-footer">
		                    <button type="button" class="btn btn-default" data-dismiss="modal">Rezygnuj</button>
		                    <a class="btn btn-danger btn-ok">Dodaj</a>
		                </div>
		            </div>
		        </div>
		  </div>
  		  <a href="#" data-href="${acceptUserUrl}?email=${email}" data-toggle="modal" data-target="#confirm-add" title="Akceptuj" ><i class="glyphicon glyphicon-plus"></i></a><br>
  		  
	    <script>
	        $('#confirm-add').on('show.bs.modal', function(e) {
	            $(this).find('.btn-ok').attr('href', $(e.relatedTarget).data('href'));
	        });
	    </script>
    				
    		</td>    		
    	</tr>  
   </c:forEach>     	
    </tbody>
</table>







<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header"><small><i class="glyphicon glyphicon-user"></i> Wysłane zaproszenia</small>
		</h1>
	</div>
</div>

<table data-toggle="table" id="table-pagination"  data-pagination="true" data-search="true" style="background-color:white">
    <thead style="background-color:#999999">
        <tr>
            <th data-field="uId" data-align="left" data-sortable="true">Użytkownik</th>
            <th data-field="email" data-align="center" data-sortable="true">Email</th>
            <th data-align="center">Szczegóły </th>
        </tr>
    </thead>
    <tbody>
    <c:forEach items="${friendsPendedRequests}" var="friend" varStatus="i">
		<c:set var="name"  value="${ friend.name }"/>
		<c:set var="surname"  value="${ friend.surname }"/>
		<c:set var="email"  value="${ friend.email }"/>
		<c:set var="userId"  value="${ friend.id }"/>
    	<tr>
    		<td>${name} ${surname}</td>
    		<td>${email}</td>		
    		<td><a href="${detailsUrl}/${email}" title="Szczegóły"> <i class="glyphicon glyphicon-eye-open"></i></a>
    		</td>    		
    	</tr>  
   </c:forEach>     	
    </tbody>
</table>



<script src="<c:url value="/resources/mytheme/bootstrap/js/bootstrap-table.js" />"></script>



