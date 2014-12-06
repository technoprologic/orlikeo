<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<c:url value="/events/graphic" var="terminarzUrl" />


<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">
			Nowe wydarzenie / Zmień miejsce <small><i class="glyphicon glyphicon-picture"></i> Wybór orlika / Orlik Toruń Sp9 ul.Rzepakowa 9 </small>
		</h1>
		<ol class="breadcrumb">
			<li class="active"><i class="fa fa-dashboard"></i> Dashboard</li>
		</ol>
	</div>
</div>
<div class="container">
    <div class="row">
        
            <div class="well well-sx">
                <div class="row">
                    <div class="col-md-12">
                    <form action="${terminarzUrl}">
                        <div class="form-group">
                            <label for="subject">
                                Subject</label>
                            <select id="subject" name="subject" class="form-control" required="required">
                                <option value="na" >Wybierz boisko:</option>
                                <option value="service" selected="">Orlik Toruń Sp9 ul.Rzepakowa </option>
                                <option value="suggestions">Orlik Legionów</option>
                                <option value="product">Orlik Gagarina</option>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary pull-right" id="btnContactUs">Dalej</button>
                </form>
                    </div>
                </div>
            </div>
        
    </div>
</div>

</div>
