<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<c:url value="/friends/user" var="detailsUrl" />



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
		<c:set var="name" scope="session" value="${ friend.name }"/>
		<c:set var="surname" scope="session" value="${ friend.surname }"/>
		<c:set var="email" scope="session" value="${ friend.email }"/>
		<c:set var="userId" scope="session" value="${ friend.id }"/>
    	<tr>
    		<td>${name} ${surname}</td>
    		<td>${email}</td>		
    		<td><a href="${detailsUrl}?id=${userId}" title="Szczegóły"> <i class="glyphicon glyphicon-eye-open"></i></a>
    		</td>    		
    	</tr>  
   </c:forEach>     	
    </tbody>
</table>

<script src="<c:url value="/resources/mytheme/bootstrap/js/bootstrap-table.js" />"></script>



