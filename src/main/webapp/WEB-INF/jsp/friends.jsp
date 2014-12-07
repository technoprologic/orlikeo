<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<c:url value="/friends/user" var="detailsUrl" />


Page Heading
<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">
			Znajomi <small><i class="glyphicon glyphicon-user"></i> Moi znajomi</small>
		</h1>
	</div>
</div>
/.row




<table data-toggle="table" id="table-pagination"  data-pagination="true" data-search="true" style="background-color:white">
    <thead style="background-color:#999999">
        <tr>
            <th data-field="uId" data-align="left" data-sortable="true">Użytkownik</th>
            <th data-field="email" data-align="center" data-sortable="true">Email</th>
            <th data-field="phone" data-align="center" data-sortable="true">Telefona</th>
            <th data-field="responsibility" data-align="center" data-sortable="true" >Stopień zaufania</th>
            <th data-align="center">Szczegóły </th>
        </tr>
    </thead>
    <tbody>
    	<tr>
    		<td>Mariusz Zych</td>
    		<td>jshdjshd@jds.pl</td>
    		<td>645 263 456</td>
    		<td>90%</td> 		
    		<td><a href="${detailsUrl}?id=33" title="Szczegóły"> <i class="glyphicon glyphicon-eye-open"></i></a>
    		</td>    		
    	</tr>  
    	<tr>
    		<td>Mariusz Zych</td>
    		<td>jshdjshd@jds.pl</td>
    		<td>645 263 456</td>
    		<td>90%</td> 		
    		<td><a href="${detailsUrl}?id=33" title="Szczegóły"> <i class="glyphicon glyphicon-eye-open"></i></a>
    		</td>    		
    	</tr>  
    	<tr>
    		<td>Mariusz Zych</td>
    		<td>jshdjshd@jds.pl</td>
    		<td>645 263 456</td>
    		<td>90%</td> 		
    		<td><a href="${detailsUrl}?id=33" title="Szczegóły"> <i class="glyphicon glyphicon-eye-open"></i></a>
    		</td>    		
    	</tr>  
    	<tr>
    		<td>Mariusz Zych</td>
    		<td>jshdjshd@jds.pl</td>
    		<td>645 263 456</td>
    		<td>90%</td> 		
    		<td><a href="${detailsUrl}?id=33" title="Szczegóły"> <i class="glyphicon glyphicon-eye-open"></i></a>
    		</td>    		
    	</tr>   
    	<tr>
    		<td>Mariusz Zych</td>
    		<td>jshdjshd@jds.pl</td>
    		<td>645 263 456</td>
    		<td>90%</td> 		
    		<td><a href="${detailsUrl}?id=33" title="Szczegóły"> <i class="glyphicon glyphicon-eye-open"></i></a>
    		</td>    		
    	</tr>  
    	<tr>
    		<td>Mariusz Zych</td>
    		<td>jshdjshd@jds.pl</td>
    		<td>645 263 456</td>
    		<td>90%</td> 		
    		<td><a href="${detailsUrl}?id=33" title="Szczegóły"> <i class="glyphicon glyphicon-eye-open"></i></a>
    		</td>    		
    	</tr>  
    	<tr>
    		<td>Mariusz Zych</td>
    		<td>jshdjshd@jds.pl</td>
    		<td>645 263 456</td>
    		<td>90%</td> 		
    		<td><a href="${detailsUrl}?id=33" title="Szczegóły"> <i class="glyphicon glyphicon-eye-open"></i></a>
    		</td>    		
    	</tr>   
    	<tr>
    		<td>Mariusz Zych</td>
    		<td>jshdjshd@jds.pl</td>
    		<td>645 263 456</td>
    		<td>90%</td> 		
    		<td><a href="${detailsUrl}?id=33" title="Szczegóły"> <i class="glyphicon glyphicon-eye-open"></i></a>
    		</td>    		
    	</tr>       	
    </tbody>
</table>

<script src="<c:url value="/resources/mytheme/bootstrap/js/bootstrap-table.js" />"></script>



