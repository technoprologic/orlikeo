<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<c:url value="/password" var="passwordUrl" />

<div class="container" style="height: 100%;">
        <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 col-xs-offset-0 col-sm-offset-0 col-md-offset-3 col-lg-offset-3 toppad" >
          <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title">Lionel Messi</h3>
            </div>
            <div class="panel-body">
              <div class="row">
                <div class="col-md-3 col-lg-3 " align="center"> <img alt="User Pic" src="http://cdn.content.easports.com/fifa/fltOnlineAssets/2013/fut/items/images/players/web/158023.png" class="img-circle"> </div>
                <div class=" col-md-9 col-lg-9 "> 
                  <table class="table table-user-information">
                    <tbody>
                      <tr>
						<td>Imię:</td>
                        <td>Lionel</td>
                      </tr>
                      <tr>
                        <td>Nazwisko:</td>
                        <td>Messi</td>
                      </tr>
                      <tr>
                        <td>Data założenia konta: </td>
                        <td>11/21/2014</td>
                      </tr>
                      <tr>
                        <td>Data urodzenia: </td>
                        <td>01/24/1988</td>
                      </tr>
					<tr>
						<td>Płeć: </td>
						<td>mężczyzna</td>
					</tr>
                        <tr>
                        <td>Telefon: </td>
                        <td>691 536 255</td>
                      </tr>
                      <tr>
                        <td>Email</td>
                        <td><a href="mailto:info@support.com">messi@orlikeo.com</a></td>
                      </tr>                     
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
			<div class="panel-footer text-right">
				<a href="${passwordUrl}" class="btn btn-primary">Zmien haslo</a>
				<a href="${editUrl}" class="btn btn-primary">Edytuj profil</a>	
			</div>
          </div>
        </div>
</div>
