<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<c:url value="/home" var="registerEventUrl" />


<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">
			Orlik Toruń Sp9 ul.Rzepakowa 9  <small><i class="glyphicon glyphicon-picture"></i> 17:30 - 19:00</small>
		</h1>
		<ul>
			<li><i class="glyphicon glyphicon-thumbs-up"></i> Oświetlenie: Tak</li>
			<li><i class="glyphicon glyphicon-thumbs-up"></i> Bieżąca woda: Tak</li>
			<li><i class="glyphicon glyphicon-thumbs-down"></i> Prysznic: Tak</li>
			<li><i class="glyphicon glyphicon-road"></i> Nawierzchnia: Turf</li>
			<li><i class="glyphicon glyphicon-hand-up"></i> Obowiązujące obuwie: Turf</li>
			<li><i class="glyphicon glyphicon-user"></i> Animator: Francesco Totti</li>
		</ul>
	</div>
</div>






<form class="form" action="${registerEventUrl}">
	<fieldset>
		<!-- Form Name -->
		<legend>Zaproś znajomych</legend>
		<table data-toggle="table" style="background-color: white" >
			<thead>
				<tr>
					<th>Zaproś </th>
					<th>Pozwól zapraszać</th>
					<th>Użytkownik</th>
					<th>Pozycja</th>
					<th>Poziom zaufania</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><input type="checkbox" class="invite" name="inviteUsrId" value="23"></td>
					<td><input type="checkbox" class="allow" name="allowUsrId" value="23"></td>
					<td>Mariusz Zych</td>
					<td>Obrońca</td>
					<td>83%</td>
				</tr>
				<tr>
					<td><input type="checkbox" class="invite" name="inviteUsrId" value="23"></td>
					<td><input type="checkbox" class="allow" name="allowUsrId" value="23"></td>
					<td>Jan Worek</td>
					<td>Napastnik</td>
					<td>80%</td>
				</tr>
				<tr>
					<td><input type="checkbox" class="invite" name="inviteUsrId" value="23"></td>
					<td><input type="checkbox" class="allow" name="allowUsrId" value="23"></td>
					<td>Emag Dnim</td>
					<td>Obrońca</td>
					<td>86%</td>
				</tr>
				<tr>
					<td><input type="checkbox" class="invite" name="inviteUsrId" value="23"></td>
					<td><input type="checkbox" class="allow" name="allowUsrId" value="23"></td>
					<td>Mateusz Tamborek</td>
					<td>Napastnik</td>
					<td>90%</td>
				</tr>
			</tbody>
		</table>
		
		<div class="control-group .">
		
			<div>
				<input type="checkbox"  id="inviteAllStates">
				<label for="inviteAllStates">Zaproś wszystkich</label>
			</div>
			<div>
				<input type="checkbox" id="allowAllStates">
				<label for="allowAllStates">Pozwól zapraszać wszystkim</label>
			</div>
		</div>
		<legend>Inne ustawienia</legend>
		<!-- Select Basic -->
		<div class="control-group">
		  <label class="control-label" for="maxPlayers">Limit graczy</label>
		  <div class="controls">
		    <select id="maxPlayers" name="maxPlayers" class="input-medium">
		      <option>12 (bez zmian)</option>
		      <option>14 (po 1 na zmianę)</option>
		      <option>16 (po 2 na zmianę)</option>
		    </select>
		  </div>
		</div>
		<!-- Button -->
		<div class="control-group .">
			<div class="controls">
				<!-- Invite all -->
				<button id="singlebutton" name="singlebutton"
					class="btn btn-primary  pull-right">Złóż wniosek</button>
			</div>
		</div>
	</fieldset>
</form>
<script>

$(document).ready(function() {
    $('#inviteAllStates').click(function(event) {  //on click
        if(this.checked) { // check select status
            $('.invite').each(function() { //loop through each checkbox
                this.checked = true;  //select all checkboxes with class "controls"              
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