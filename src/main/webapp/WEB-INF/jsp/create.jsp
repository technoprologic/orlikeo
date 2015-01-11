<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<c:url value="/events/graphic" var="terminarzUrl" />


<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">
			Nowe wydarzenie / Zmień miejsce <small><i
				class="glyphicon glyphicon-picture"></i> Wybór orlika / Orlik Toruń
				Sp9 ul.Rzepakowa 9 </small>
		</h1>
		<ol class="breadcrumb">
			<li class="active"><i class="fa fa-dashboard"></i> Dashboard</li>
			 <a href="<%=request.getContextPath()%>/events/graphic/1">Grafik</a>
		</ol>
	</div>
</div>
<div class="container">
	<div class="row">

		<div class="well well-sx">
			<div class="row">
				<div class="col-md-12">
					<form:form  modelAttribute="choosenOrlikBean" action="${terminarzUrl}" method="POST" >
						<div class="form-group">
							<form:errors path="Id" cssClass="error" />
							<spring:message code="web.register.email.placeholder" var="Placeholder"/>
							<form:select path="Id">
							    <form:options items="${orliks}" placeholder="${Placeholder}"/>
							</form:select>
						</div>
						<form:button type="submit" class="btn btn-primary pull-right"
							id="btnContactUs">Dalej</form:button>
					</form:form>
				</div>
			</div>
		</div>

	</div>
</div>