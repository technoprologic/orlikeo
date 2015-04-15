<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<c:url value="/account/password" var="passwordUrl" />
<c:url value="/account/edit" var="editAccountUrl" />
<c:url value="/register" var="removeAccountUrl" />




<!-- INVITE USER-->
<c:url value="/friends/invite" var="inviteUserUrl" />
<!-- ACCEPT INVITATION-->
<c:url value="/friends/accept" var="acceptInvitationUrl" />
<!-- REJECT INVITATION-->
<c:url value="/friends/reject" var="rejectInvitationUrl" />
<!-- REMOVE FROM FRIENDS-->
<c:url value="/friends/remove" var="removeFriendshipUrl" />
<!-- CANCEL INVITATION-->
<c:url value="/friends/cancelInvitation" var="cancelInvitationUrl" />
<!-- BLOCK USER-->
<c:url value="/friends/block" var="blockUserUrl" />
<!-- UNBLOCK USER-->
<c:url value="/friends/unblockUser" var="unblockUserUrl" />


<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">
			Profil zawodnika <small><i class="glyphicon glyphicon-info-sign"></i> Informacje</small>
		</h1>
		
<%-- 		<br>
		INVITE <a href="${inviteUserUrl}/${targetEmail}" title="Zaproś do znajomych" class="btn btn-default"><i class="glyphicon glyphicon-plus-sign"></i></a> <br>
		ACCEPT <a href="${acceptInvitationUrl}/${targetEmail}" title="Akceptuj zaproszenie" class="btn btn-default"><i class="glyphicon glyphicon-ok-sign"></i></a> <br>
		REJECT <a href="${rejectInvitationUrl}/${targetEmail}" title="Odrzuć zaproszenie" class="btn btn-default"><i class="glyphicon glyphicon-remove-sign"></i></a> <br>
		REMOVE <a href="${removeFriendshipUrl}/${targetEmail}" title="Usuń ze znajomych" class="btn btn-default"><i class="glyphicon glyphicon-minus-sign"></i></a> <br>
		CANCEL <a href="${cancelInvitationUrl}/${targetEmail}" title="Cofnij zaproszenie" class="btn btn-default"><i class="glyphicon glyphicon-stop"></i></a><br>
		BLOCK  <a href="${blockUserUrl}/${targetEmail}" title="Zablokuj" class="btn btn-default"><i class="glyphicon glyphicon-ban-circle"></i></a> <br>		
		UNBLOCK <a href="${unblockUserUrl}/${targetEmail}" title="Odblokuj" class="btn btn-default"><i class="glyphicon glyphicon-refresh"></i></a><br>
		<br> --%>
		
  

	</div>
</div>

<c:if test="${not empty user}">
<div class="container" >
        <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xs-offset-0 col-sm-offset-0 col-md-offset-3 col-lg-offset-3 toppad" >
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title">${ user.name } ${ user.surname }</h3>
            </div>
            <div class="panel-body">
              <div class="row">
                <div class="col-md-3 col-lg-3 " align="center"> 
                <img alt="User Pic" src="http://afcchiropracticphoenix.com/wp-content/plugins/google-places-reviews-pro/assets/images/mystery-man.png" class="img-circle"></div>
                <div class=" col-md-9 col-lg-9 "> 
                  <table class="table table-user-information">
                    <tbody>
                      <tr>
						<td>Email:</td>
                        <td><a href="mailto:${user.email}">${user.email}</a></td>
                      </tr>
                      <tr>
						<td>Data urodzenia:</td>
                        <td><fmt:formatDate value="${user.dateOfBirth}" type="both" pattern="dd.MM.yyyy" /></td>
                      </tr>
                      <tr>
						<td>Pozycja:</td>
                        <td>${user.position}</td>
                      </tr>
                      <tr>
						<td>Waga:</td>
                        <td>${user.weight}</td>
                      </tr>
                      <tr>
						<td>Wzrost:</td>
                        <td>${user.height}</td>
                      </tr>
                      <tr>
						<td>Noga:</td>
                        <td>${user.foot}</td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
			<div class="panel-footer text-right">
				<c:if test="${ self }">
					<a href="${passwordUrl}" class="btn btn-primary">Zmien hasło</a>
					<a href="${editAccountUrl}" class="btn btn-primary">Edytuj profil</a>
					<a href="${removeAccountUrl}" class="btn btn-danger" title="Usuń konto"><i class="glyphicon glyphicon-remove"> </i></a>
				</c:if>
		<c:choose>
					<c:when test="${ contact == 'pendingRequester'}">Wysłano zaproszenie do użytkownika 
						<a href="${cancelInvitationUrl}/${targetEmail}" title="Cofnij zaproszenie" class="btn btn-default"><i class="glyphicon glyphicon-stop"></i></a>
						<a href="${blockUserUrl}/${targetEmail}" title="Zablokuj" class="btn btn-default"><i class="glyphicon glyphicon-ban-circle"></i></a>
					</c:when>
					<c:when test="${ contact == 'pendingReceiver'}">Otrzymałeś zaproszenie od użytkownika 
						<a href="${acceptInvitationUrl}/${targetEmail}" title="Akceptuj zaproszenie" class="btn btn-default"><i class="glyphicon glyphicon-ok-sign"></i></a> 
						<a href="${rejectInvitationUrl}/${targetEmail}" title="Odrzuć zaproszenie" class="btn btn-default"><i class="glyphicon glyphicon-remove-sign"></i></a>
						<a href="${blockUserUrl}/${targetEmail}" title="Zablokuj" class="btn btn-default"><i class="glyphicon glyphicon-ban-circle"></i></a>
					</c:when>
					<c:when test="${ contact == 'friends'}">Jesteście znajomymi 
						<a href="${removeFriendshipUrl}/${targetEmail}" title="Usuń ze znajomych" class="btn btn-default"><i class="glyphicon glyphicon-minus-sign"></i></a>
						<a href="${blockUserUrl}/${targetEmail}" title="Zablokuj" class="btn btn-default"><i class="glyphicon glyphicon-ban-circle"></i></a>
					</c:when>
					<c:when test="${ contact == 'decliner'}">Odrzuciłeś zaproszenie użytkownika 
						<a href="${inviteUserUrl}/${targetEmail}" title="Zaproś do znajomych" class="btn btn-default"><i class="glyphicon glyphicon-plus-sign"></i></a>
						<a href="${blockUserUrl}/${targetEmail}" title="Zablokuj" class="btn btn-default"><i class="glyphicon glyphicon-ban-circle"></i></a>
					</c:when>
					<c:when test="${ contact == 'declined'}">Użytkownik odrzucił Twoje zaproszenie 
						<a href="${inviteUserUrl}/${targetEmail}" title="Zaproś do znajomych" class="btn btn-default"><i class="glyphicon glyphicon-plus-sign"></i></a>
						<a href="${blockUserUrl}/${targetEmail}" title="Zablokuj" class="btn btn-default"><i class="glyphicon glyphicon-ban-circle"></i></a>
					</c:when>
					<c:when test="${ contact == 'blocker'}">Zablokowałeś użytkownika 
						<a href="${unblockUserUrl}/${targetEmail}" title="Odblokuj" class="btn btn-default"><i class="glyphicon glyphicon-refresh"></i></a>
					</c:when>
					<c:when test="${ contact == 'blocked'}">Zostałeś zablokowany przez użytkownika</c:when>
		</c:choose>
			</div>
          </div>
        </div>
</div>
</c:if>