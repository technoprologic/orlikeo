<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<c:url value="/events/edit" var="editEventUrl" />
<c:url value="/events/remove" var="removeEventUrl" />
<c:url value="/events/details" var="detailsEventUrl" />


Page Heading
<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">
			Wydarzenia <small><i class="glyphicon glyphicon-picture"></i> Wszystkie / Organizowane / Zaproszenia </small>
		</h1>
	</div>
</div>
/.row


<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">
					<i class="glyphicon glyphicon-info-sign"></i> Informacje o wydarzeniach które organizujesz
				</h3>
			</div>
			<div class="panel-body">

				<div class="col-md-2">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<i style="font-size: 2.5em;" class="glyphicon glyphicon-plane"></i> 12/14
								</div>
								<div class="col-xs-12 text-right">
									<div class="large">Nadchodzące</div>
									<div>Orlik Sp9</div>
									<div>13.11.2014</div>
									<div>18:00 - 20:00</div>
								</div>
							</div>
						</div>
						<a href="#">
							<div class="panel-footer">
								<span class="pull-left">Sprawdź wszystkie (2)</span> <span
									class="pull-right"><i
									class="glyphicon glyphicon-eye-open"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
					</div>
				</div>
				<div class="col-md-2">
					<div class="panel panel-green">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<i style="font-size: 2.5em;"
										class="glyphicon glyphicon-thumbs-up"></i>11/12
								</div>
								<div class="col-xs-12 text-right">
									<div class="large">Przyjęty</div>
									<div>Orlik Sp9</div>
									<div>13.11.2014</div>
									<div>18:00 - 20:00</div>
								</div>
							</div>
						</div>
						<a href="#">
							<div class="panel-footer">
								<span class="pull-left">Sprawdź wszystkie (3)</span> <span
									class="pull-right"><i
									class="glyphicon glyphicon-eye-open"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
					</div>
				</div>
				<div class="col-md-2">
					<div class="panel panel-red">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<i style="font-size: 2.5em;" class="glyphicon glyphicon-warning-sign"></i> 7/12
								</div>
								<div class="col-xs-12 text-right">
									<div class="large">Zagrożony</div>
									<div>Orlik Sp9</div>
									<div>13.11.2014</div>
									<div>18:00 - 20:00</div>
								</div>
							</div>
						</div>
						<a href="#">
							<div class="panel-footer">
								<span class="pull-left">Sprawdź wszystkie (2)</span> <span
									class="pull-right"><i
									class="glyphicon glyphicon-eye-open"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
					</div>
				</div>
				<div class="col-md-2">
					<div class="panel panel-yellow">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<i style="font-size: 2.5em;" class="glyphicon glyphicon-send"></i> 10/14
								</div>
								<div class="col-xs-12 text-right">
									<div class="large">Do akceptacji</div>
									<div>Orlik Sp9</div>
									<div>13.11.2014</div>
									<div>18:00 - 20:00</div>
								</div>
							</div>
						</div>
						<a href="#">
							<div class="panel-footer">
								<span class="pull-left">Sprawdź wszystkie (1)</span> <span
									class="pull-right"><i
									class="glyphicon glyphicon-eye-open"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
					</div>
				</div>
				
				<div class="col-md-2">
					<div class="panel panel-silver">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<i style="font-size: 2.5em;" class="glyphicon glyphicon-thumbs-down"></i> 3/12
								</div>
								<div class="col-xs-12 text-right">
									<div class="large">W budowie</div>
									<div>Orlik Sp9</div>
									<div>13.11.2014</div>
									<div>18:00 - 20:00</div>
								</div>
							</div>
						</div>
						<a href="#">
							<div class="panel-footer">
								<span class="pull-left">Sprawdź wszystkie (1)</span> <span
									class="pull-right"><i
									class="glyphicon glyphicon-eye-open"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
					</div>
				</div>
				<div class="col-md-2">
					<div class="panel panel-silver-dark">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">
									<i style="font-size: 2.5em;" class="glyphicon glyphicon-trash"></i> 0/16
								</div>
								<div class="col-xs-12 text-right">
									<div class="large">Kosz</div>
									<div>Brak</div>
									<div>Brak</div>
									<div>Brak</div>
								</div>
							</div>
						</div>
						<a href="#">
							<div class="panel-footer">
								<span class="pull-left">Sprawdź wszystkie (0)</span> <span
									class="pull-right"><i
									class="glyphicon glyphicon-eye-open"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
/.row





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
    	<tr>
    		<td>Sp9</td>
    		<td>04.11.2014</td>
    		<td>17:30 - 19:00</td>
    		<td>Kosz</td>
    		<td><i class="glyphicon glyphicon-plus"></i></td>
    		<td>12/14</td>    		
    		<td><a href="${editEventUrl}?evId=33" title="Edytuj"> <i class="glyphicon glyphicon-edit"></i></a>
    			<a href="${removeEventUrl}?evId=33" title="Usuń"> <i class="glyphicon glyphicon-remove"></i></a>
    			<a href="${detailsEventUrl}?evId=33" title="Szczegóły"> <i class="glyphicon glyphicon-eye-open"></i></a>
    		</td>    		
    	</tr>  
    	<tr>
    		<td>Ugory</td>
    		<td>06.12.2014</td>
    		<td>13:30 - 15:00</td>
    		<td>W budowie</td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td>12/14</td>
    		<td><a href="${editEvent}" title="Edytuj"> <i class="glyphicon glyphicon-edit"></i></a>
    			<a href="${detailsEvent}" title="Szczegóły"> <i class="glyphicon glyphicon-eye-open"></i></a>
    		</td>
    	</tr>
    	    	<tr>
    		<td>Sp9</td>
    		<td>04.11.2014</td>
    		<td>17:30 - 19:00</td>
    		<td>Kosz</td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td>12/14</td>
    		<td><a href="${detailsEvent}" title="Szczegóły"> <i class="glyphicon glyphicon-eye-open"></i></a>
    		</td>
    	</tr>
    	<tr>
    		<td>Polna</td>
    		<td>14.12.2014</td>
    		<td>20:30 - 22:00</td>
    		<td>Do akceptacji</td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td>12/14</td>
    		<td><a href="${editEvent}" title="Edytuj"> <i class="glyphicon glyphicon-edit"></i></a>
    			<a href="${detailsEvent}" title="Szczegóły"> <i class="glyphicon glyphicon-eye-open"></i></a>
    		</td>
    	</tr>  
    	<tr>
    		<td>Legionów</td>
    		<td>03.12.2014</td>
    		<td>18:00 - 19:30</td>
    		<td>Przyjęty</td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td>12/14</td>
    		<td><a href="${detailsEvent}" title="Szczegóły"> <i class="glyphicon glyphicon-eye-open"></i></a>
    		</td>
    	</tr>  
    	<tr>
    		<td>Gagarina</td>
    		<td>09.12.2014</td>
    		<td>17:00 - 18:30</td>
    		<td>Zagrożony</td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td>12/14</td>
    		<td><a href="${detailsEvent}" title="Szczegóły"> <i class="glyphicon glyphicon-eye-open"></i></a>
    		</td>
    	</tr>  
    	<tr>
    		<td>Morcinka</td>
    		<td>12.12.2014</td>
    		<td>12:00 - 13:30</td>
    		<td>Do akceptacji</td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td>13/14</td>
    		<td><a href="${editEvent}" title="Edytuj"> <i class="glyphicon glyphicon-edit"></i></a>
    			<a href="${detailsEvent}" title="Szczegóły"> <i class="glyphicon glyphicon-eye-open"></i></a>
    		</td>
    	</tr>  
    	<tr>
    		<td>Ugory</td>
    		<td>06.12.2014</td>
    		<td>15:00 - 16:30</td>
    		<td>W budowie</td>
    		<td><i class="glyphicon glyphicon-minus"></i></td>
    		<td>14/14</td>
    		<td><a href="${detailsEvent}" title="Szczegóły"> <i class="glyphicon glyphicon-eye-open"></i></a>
    		</td>
    	</tr>       	
    </tbody>
</table>

<script src="<c:url value="/resources/mytheme/bootstrap/js/bootstrap-table.js" />"></script>



