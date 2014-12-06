<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>




<c:url value="/events/reserve" var="editEventUrl" />
<c:url value="/events/reserve" var="editEventUrl" />
<c:url value="/events/organized" var="removeEventUrl" />

<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">
			Orlik Toruń Sp9 ul.Rzepakowa 9  <small><i class="glyphicon glyphicon-picture"></i> 17:30 - 19:00 </small>
			<a href="${editEventUrl}" title="Edytuj"> <i class="glyphicon glyphicon-edit" style="margin-left:0.5em"></i></a>
    		<a href="${joinEventUrl}?join=true" title="Wezmę udział"> <i class="glyphicon glyphicon-plus" style="margin-left:0.5em"></i></a> /
    		<a href="${joinEventUrl}?join=false" title="Nie wezmę udziału"> <i class="glyphicon glyphicon-minus" style="margin-left:0.5em"></i></a>
    		<a href="${removeEventUrl}" title="Usuń"> <i class="glyphicon glyphicon-remove" style="margin-left:0.5em"></i></a>
		</h1>
		<ul>
			<li><i class="glyphicon glyphicon-thumbs-up"></i> Oświetlenie: Tak</li>
			<li><i class="glyphicon glyphicon-thumbs-up"></i> Bieżąca woda: Tak</li>
			<li><i class="glyphicon glyphicon-thumbs-down"></i> Prysznic: Tak</li>
			<li><i class="glyphicon glyphicon-road"></i> Nawierzchnia: Turf</li>
			<li><i class="glyphicon glyphicon-hand-up"></i> Obowiązujące obuwie: Turf</li>
			<li><i class="glyphicon glyphicon-user"></i> Animator: Francesco Totti</li>
		</ul>
		<ul>
			<li><i class="glyphicon glyphicon-thumbs-up"></i> Aktualny status: Zagrożony</li>
			<li><i class="glyphicon glyphicon-thumbs-up"></i> Liczba osób zaproszonych: 19</li>
			<li><i class="glyphicon glyphicon-thumbs-down"></i> Nadanych praw zapraszania: 2</li>
		</ul>
	</div>
</div>

<div class="row">
		<!-- Form Name -->
<legend>Wezmą udział (7)</legend>
<table data-toggle="table" id="table-pagination"    data-search="true" style="background-color:white">
    <thead style="background-color:#999999">
        <tr>
            <th data-field="orlikId" data-align="center" data-sortable="true"><i class="glyphicon glyphicon-user"></i> Użytkownik</th>
            <th data-field="eventDate" data-align="center" data-sortable="true">Prawo zapraszania</th>
            <th data-field="eventTime" data-align="center" data-sortable="true">Zaproszony przez</th>
            <th data-field="eventStatus" data-align="center" data-sortable="true" >Dołączył</th>
            <th data-field="eventPlayers" data-align="center" data-sortable="true" >Poziom zaufania</th>
        </tr>
    </thead>
    <tbody>
    	<tr>
    		<td>Mateusz Tamborek</td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td>Mariusz Zych</td>
    		<td>12.12.2014 22:58</td>
    		<td>95%</td>    		
    	</tr>
    	<tr>
    		<td>Mariusz Zych</td>
    		<td><i class="glyphicon glyphicon-ok"></i></td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td>12.12.2014 13:57</td>
    		<td>92%</td> 
    	</tr>  
    	<tr>
    		<td>Georgio Armani</td>
    		<td><i class="glyphicon glyphicon-ok"></i></td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td>13.12.2014 07:43</td>
    		<td>91%</td> 
    	</tr>  
    	<tr>
    		<td>Stefano Alfano</td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td>11.12.2014 03:58</td>
    		<td>89%</td> 
    	</tr>  
    	<tr>
    		<td>Miszcz Miszczów</td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td>Mariusz Zych</td>
    		<td>10.12.2014 13:05</td>
    		<td>90%</td> 
    	</tr>  
    	<tr>
    		<td>Kopacz Pospolity</td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td>09.12.2014 14:25</td>
    		<td>99%</td> 
    	</tr>
    	<tr>
    		<td>Ronaldo Messi</td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td>11.12.2014 12:51</td>
    		<td>100%</td> 
    	</tr>       	
    </tbody>
</table>
</div>
<div class="row" style="margin-top:40px">
		<!-- Form Name -->
<legend>Niezdecydowani (7)</legend>
<table data-toggle="table" id="table-pagination"   data-search="true" style="background-color:white">
    <thead style="background-color:#999999">
        <tr>
            <th data-field="orlikId" data-align="center" data-sortable="true"><i class="glyphicon glyphicon-user"></i> Użytkownik</th>
            <th data-field="eventDate" data-align="center" data-sortable="true">Prawo zapraszania</th>
            <th data-field="eventTime" data-align="center" data-sortable="true">Zaproszony przez</th>
            <th data-field="eventStatus" data-align="center" data-sortable="true" >Wysłano</th>
            <th data-field="eventPlayers" data-align="center" data-sortable="true" >Poziom zaufania</th>
        </tr>
    </thead>
    <tbody>
    	<tr>
    		<td>Mateusz Tamborek</td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td>Mariusz Zych</td>
    		<td>12.12.2014 22:58</td>
    		<td>95%</td>    		
    	</tr>
    	<tr>
    		<td>Mariusz Zych</td>
    		<td><i class="glyphicon glyphicon-ok"></i></td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td>12.12.2014 13:57</td>
    		<td>92%</td> 
    	</tr>  
    	<tr>
    		<td>Georgio Armani</td>
    		<td><i class="glyphicon glyphicon-ok"></i></td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td>13.12.2014 07:43</td>
    		<td>91%</td> 
    	</tr>  
    	<tr>
    		<td>Stefano Alfano</td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td>11.12.2014 03:58</td>
    		<td>89%</td> 
    	</tr>  
    	<tr>
    		<td>Miszcz Miszczów</td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td>Mariusz Zych</td>
    		<td>10.12.2014 13:05</td>
    		<td>90%</td> 
    	</tr>  
    	<tr>
    		<td>Kopacz Pospolity</td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td>09.12.2014 14:25</td>
    		<td>99%</td> 
    	</tr>
    	<tr>
    		<td>Ronaldo Messi</td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td>11.12.2014 12:51</td>
    		<td>100%</td> 
    	</tr> 
    </tbody>
</table>
</div>
<div class="row" style="margin-top:40px">
		<!-- Form Name -->
<legend>Odrzucili (7)</legend>
<table data-toggle="table" id="table-pagination"   data-search="true" style="background-color:white">
 <thead style="background-color:#999999">
        <tr>
            <th data-field="orlikId" data-align="center" data-sortable="true"><i class="glyphicon glyphicon-user"></i> Użytkownik</th>
            <th data-field="eventDate" data-align="center" data-sortable="true">Prawo zapraszania</th>
            <th data-field="eventTime" data-align="center" data-sortable="true">Zaproszony przez</th>
            <th data-field="eventStatus" data-align="center" data-sortable="true" >Odrzucił</th>
            <th data-field="eventPlayers" data-align="center" data-sortable="true" >Poziom zaufania</th>
        </tr>
    </thead>
    <tbody>
    	<tr>
    		<td>Mateusz Tamborek</td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td>Mariusz Zych</td>
    		<td>12.12.2014 22:58</td>
    		<td>95%</td>    		
    	</tr>
    	<tr>
    		<td>Mariusz Zych</td>
    		<td><i class="glyphicon glyphicon-ok"></i></td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td>12.12.2014 13:57</td>
    		<td>92%</td> 
    	</tr>  
    	<tr>
    		<td>Georgio Armani</td>
    		<td><i class="glyphicon glyphicon-ok"></i></td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td>13.12.2014 07:43</td>
    		<td>91%</td> 
    	</tr>  
    	<tr>
    		<td>Stefano Alfano</td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td>11.12.2014 03:58</td>
    		<td>89%</td> 
    	</tr>  
    	<tr>
    		<td>Miszcz Miszczów</td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td>Mariusz Zych</td>
    		<td>10.12.2014 13:05</td>
    		<td>90%</td> 
    	</tr>  
    </tbody>
</table>
</div>
<script src="<c:url value="/resources/mytheme/bootstrap/js/bootstrap-table.js" />"></script>