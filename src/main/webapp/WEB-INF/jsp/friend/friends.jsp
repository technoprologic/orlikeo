<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<c:url value="/friends/userDetail" var="detailsUrl" />
<c:url value="/friends/acceptUser" var="acceptUserUrl" />

<!-- INVITE USER-->
<c:url value="/friends/friendRequest" var="inviteUserUrl" />
<!-- ACCEPT INVITATION-->
<c:url value="/friends/acceptUser" var="acceptInvitationUrl" />
<!-- REJECT INVITATION-->
<c:url value="/friends/reject" var="rejectInvitationUrl" />
<!-- REMOVE FROM FRIENDS-->
<c:url value="/friends/remove" var="removeFriendshipUrl" />
<!-- CANCEL INVITATION-->
<c:url value="/friends/cancel" var="cancelInvitationUrl" />
<!-- BLOCK USER-->
<c:url value="/friends/block" var="blockUserUrl" />
<!-- UNBLOCK USER-->
<c:url value="/friends/unblock" var="unblockUserUrl" />



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
    		<td><div style="display: inline-block; margin: auto">
				<a href="${detailsUrl}/${email}" ><button title="Szczegóły" class="btn btn-info"> <i class="glyphicon glyphicon-eye-open"></i> </button> </a>
				<button type="button" title="Usuń" class="btn btn-danger" data-toggle="modal" data-target="#removeFriendshipModal" data-whatever="${ email }"><i class="glyphicon glyphicon-minus-sign"></i></button>
				<button type="button" title="Zablokuj" class="btn btn-danger" data-toggle="modal" data-target="#blockInvitationModal" data-whatever="${ email }"><i class="glyphicon glyphicon-ban-circle"></i></button>
				</div>
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

<table data-toggle="table" id="table-pagination" data-pagination="true"
	data-search="true" style="background-color: white">
	<thead style="background-color: #999999">
		<tr>
			<th data-field="uId" data-align="left" data-sortable="true">Użytkownik</th>
			<th data-field="email" data-align="center" data-sortable="true">Email</th>
			<th data-align="center">Akcja</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${friendsReceivedRequests}" var="requester"
			varStatus="i">
			<c:set var="name" value="${ requester.name }" />
			<c:set var="surname" value="${ requester.surname }" />
			<c:set var="email" value="${ requester.email }" />
			<c:set var="userId" value="${ requester.id }" />
			<tr>
				<td>${name}${surname}</td>
				<td>${ email }</td>
				<td>
					<div style="display: inline-block; margin: auto">
						<a href="${detailsUrl}/${email}" ><button title="Szczegóły" class="btn btn-info"> <i class="glyphicon glyphicon-eye-open"></i> </button> </a>
						<button type="button" title="Akceptuj" class="btn btn-success" data-toggle="modal" data-target="#acceptInvitationModal" data-whatever="${ email }"><i class="glyphicon glyphicon-ok-sign"></i></button>
						<button type="button" title="Odrzuć" class="btn btn-warning" data-toggle="modal" data-target="#declineInvitationModal" data-whatever="${ email }"><i class="glyphicon glyphicon-remove-sign"></i></button>
						<button type="button" title="Zablokuj" class="btn btn-danger" data-toggle="modal" data-target="#blockInvitationModal" data-whatever="${ email }"><i class="glyphicon glyphicon-ban-circle"></i></button>
					</div>		
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>



<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header"><small><i class="glyphicon glyphicon-user"></i> Wysłane zaproszenia</small></h1>

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
    		<td>
				<div style="display: inline-block; margin: auto">
					<a href="${detailsUrl}/${email}" ><button title="Szczegóły" class="btn btn-info"> <i class="glyphicon glyphicon-eye-open"></i> </button> </a>
					<button type="button" title="Cofnij" class="btn btn-warning" data-toggle="modal" data-target="#cancelInvitationModal" data-whatever="${ email }"><i class="glyphicon glyphicon-stop"></i></button>
					<button type="button" title="Zablokuj" class="btn btn-danger" data-toggle="modal" data-target="#blockInvitationModal" data-whatever="${ email }"><i class="glyphicon glyphicon-ban-circle"></i></button>
				</div>	
    		</td>    		
    	</tr>  
   </c:forEach>     	
    </tbody>
</table>






<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header"><small><i class="glyphicon glyphicon-user"></i> Zablokowani użytkownicy</small></h1>

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
    <c:forEach items="${blockedUsers}" var="friend" varStatus="i">
		<c:set var="name"  value="${ friend.name }"/>
		<c:set var="surname"  value="${ friend.surname }"/>
		<c:set var="email"  value="${ friend.email }"/>
		<c:set var="userId"  value="${ friend.id }"/>
    	<tr>
    		<td>${name} ${surname}</td>
    		<td>${email}</td>		
    		<td>
				<div style="display: inline-block; margin: auto">
					<a href="${detailsUrl}/${email}" ><button title="Szczegóły" class="btn btn-info"> <i class="glyphicon glyphicon-eye-open"></i> </button> </a>
					<button type="button" title="Odblokuj" class="btn btn-success" data-toggle="modal" data-target="#unblockUser" data-whatever="${ email }"><i class="glyphicon glyphicon-refresh"></i></button>
				</div>	
    		</td>    		
    	</tr>  
   </c:forEach>     	
    </tbody>
</table>






<!-- Accept invitation -->
<div class="modal fade" id="acceptInvitationModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" title="Zamknij" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="exampleModalLabel" style="text-align:center" ></h4>
      </div>
      <div class="modal-body">
        <form:form action="${acceptInvitationUrl}" method="POST">
        <input type="hidden" class="form-control" name="email" id="email" ></input>
        <div class="modal-footer">
     	<button type="button" class="btn btn-default" data-dismiss="modal">Anuluj</button>
        <button type="submit" class="btn btn-success">Akceptuj</button>
      	</div>
        </form:form>
      </div>
    </div>
  </div>
</div>


<!-- Decline invitation -->
<div class="modal fade" id="declineInvitationModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" title="Zamknij" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="exampleModalLabel" style="text-align:center" ></h4>
      </div>
      <div class="modal-body">
        <form:form action="${rejectInvitationUrl}" method="POST">
        <input type="hidden" class="form-control" name="email" id="email" ></input>
        <div class="modal-footer">
     	<button type="button" class="btn btn-default" data-dismiss="modal">Anuluj</button>
        <button type="submit" class="btn btn-warning">Odrzuć</button>
      	</div>
        </form:form>
      </div>
    </div>
  </div>
</div>


<!-- Block invitation -->
<div class="modal fade" id="blockInvitationModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" title="Zamknij" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="exampleModalLabel" style="text-align:center" ></h4>
      </div>
      <div class="modal-body">
        <form:form action="${blockUserUrl}" method="POST">
        <input type="hidden" class="form-control" name="email" id="email" ></input>
        <div class="modal-footer">
     	<button type="button" class="btn btn-default" data-dismiss="modal">Anuluj</button>
        <button type="submit" class="btn btn-danger">Zablokuj</button>
      	</div>
        </form:form>
      </div>
    </div>
  </div>
</div>


<!-- Remove friendship -->
<div class="modal fade" id="removeFriendshipModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" title="Zamknij" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="exampleModalLabel" style="text-align:center" ></h4>
      </div>
      <div class="modal-body">
        <form:form action="${removeFriendshipUrl}" method="POST">
        <input type="hidden" class="form-control" name="email" id="email" ></input>
        <div class="modal-footer">
     	<button type="button" class="btn btn-default" data-dismiss="modal">Anuluj</button>
        <button type="submit" class="btn btn-danger">Usuń</button>
      	</div>
        </form:form>
      </div>
    </div>
  </div>
</div>


<!-- Cancel friendship invitation -->
<div class="modal fade" id="cancelInvitationModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" title="Zamknij" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="exampleModalLabel" style="text-align:center" ></h4>
      </div>
      <div class="modal-body">
        <form:form action="${cancelInvitationUrl}" method="POST">
        <input type="hidden" class="form-control" name="email" id="email" ></input>
        <div class="modal-footer">
     	<button type="button" class="btn btn-default" data-dismiss="modal">Anuluj</button>
        <button type="submit" class="btn btn-warning">Cofnij</button>
      	</div>
        </form:form>
      </div>
    </div>
  </div>
</div>


<!-- Unblock user -->
<div class="modal fade" id="unblockUser" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" title="Zamknij" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="exampleModalLabel" style="text-align:center" ></h4>
      </div>
      <div class="modal-body">
        <form:form action="${unblockUserUrl}" method="POST">
        <input type="hidden" class="form-control" name="email" id="email" ></input>
        <div class="modal-footer">
     	<button type="button" class="btn btn-default" data-dismiss="modal">Anuluj</button>
        <button type="submit" class="btn btn-success">Odblokuj</button>
      	</div>
        </form:form>
      </div>
    </div>
  </div>
</div>


<script>
$('#acceptInvitationModal').on('show.bs.modal', function (event) {
	  var button = $(event.relatedTarget) // Button that triggered the modal
	  var recipient = button.data('whatever') // Extract info from data-* attributes
	  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
	  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
	  var modal = $(this)
	  modal.find('.modal-title').text('Zaakceptować zaproszenie od ' + recipient + ' ?')
	  modal.find('.modal-body #email').val(recipient)
}); 

$('#declineInvitationModal').on('show.bs.modal', function (event) {
	  var button = $(event.relatedTarget) // Button that triggered the modal
	  var recipient = button.data('whatever') // Extract info from data-* attributes
	  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
	  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
	  var modal = $(this)
	  modal.find('.modal-title').text('Odrzucić zaproszenie od ' + recipient + ' ?')
	  modal.find('.modal-body #email').val(recipient)
}); 


$('#blockInvitationModal').on('show.bs.modal', function (event) {
	  var button = $(event.relatedTarget) // Button that triggered the modal
	  var recipient = button.data('whatever') // Extract info from data-* attributes
	  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
	  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
	  var modal = $(this)
	  modal.find('.modal-title').text('Zablokować użytkownika ' + recipient + ' ?')
	  modal.find('.modal-body #email').val(recipient)
}); 



$('#removeFriendshipModal').on('show.bs.modal', function (event) {
	  var button = $(event.relatedTarget) // Button that triggered the modal
	  var recipient = button.data('whatever') // Extract info from data-* attributes
	  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
	  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
	  var modal = $(this)
	  modal.find('.modal-title').text('Usunąć użytkownika ' + recipient + ' ze znajomych ?')
	  modal.find('.modal-body #email').val(recipient)
}); 


$('#cancelInvitationModal').on('show.bs.modal', function (event) {
	  var button = $(event.relatedTarget) // Button that triggered the modal
	  var recipient = button.data('whatever') // Extract info from data-* attributes
	  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
	  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
	  var modal = $(this)
	  modal.find('.modal-title').text('Cofnąć zaproszenie dla użytkownika ' + recipient + ' ?')
	  modal.find('.modal-body #email').val(recipient)
}); 


$('#unblockUser').on('show.bs.modal', function (event) {
	  var button = $(event.relatedTarget) // Button that triggered the modal
	  var recipient = button.data('whatever') // Extract info from data-* attributes
	  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
	  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
	  var modal = $(this)
	  modal.find('.modal-title').text('Odblokować użytkownika ' + recipient + ' ?')
	  modal.find('.modal-body #email').val(recipient)
}); 

</script>

<script src="<c:url value="/resources/mytheme/bootstrap/js/bootstrap-table.js" />" />
<script src="<c:url value="/resources/mytheme/bootstrap/js/bootstrap.js" />" />
<script src="<c:url value="/resources/mytheme/bootstrap/js/jquery.js" />" />

