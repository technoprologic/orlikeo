<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<c:set var="username"   scope="session"  value="${username}"/>

<c:url value="/home" var="homeUrl" />
<c:url value="/account/profile" var="profileUrl" />
<c:url value="/events/create" var="createEventUrl" />

<c:url value="/events/list" var="EventsUrl" />
<c:url value="/friends" var="friendsUrl" />
<c:url value="/friends/search" var="searchFriends" />
<c:url value="/planner" var="plannerUrl" />
<c:url value="/pane" var="paneUrl" />
<c:url value="/websocket" var="socket" />
<c:url value="/websocket2" var="socket2" />

<c:url value="/events/show" var="showEvents" />



<!-- Navigation -->
        <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
                      
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${homeUrl}"><i class="glyphicon glyphicon-globe"></i>rlikeo!</a>
            </div>
            
            
            <!-- Top Menu Items -->
            <ul class="nav navbar-right top-nav">
    			<li class="nav nav-pills">
        			<li class="active"><a href="#">Wiadomości <span class="badge" style="background-color:#66CCFF">24</span></a></li>
        			<li><a href="${paneUrl}" id="notifications" >Animator</a></li>
    			</li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="glyphicon glyphicon-user"></i> <sec:authentication property="principal.username"/> <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="${profileUrl}" ><i class="glyphicon glyphicon-cog"></i> Profil</a>
                        </li>
<!--                         <li>
                            <a href="#"><i class="glyphicon glyphicon-envelope"></i> Skrzynka</a>
                        </li>
                        <li>
                            <a href="#"><i class="glyphicon glyphicon-wrench"></i> Ustawienia</a>
                        </li> -->
                        <li class="divider"></li>
                        <li>
                            <a href="<c:url value="/j_spring_security_logout" />"><i class="glyphicon glyphicon-off"></i> Wyloguj</a>
                        </li>
                    </ul>
                </li>
            </ul>
            
            
<!--  LEFT MENU  -->
            <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <ul class="nav navbar-nav side-nav">
<%--                 <li class="active">
                        <a href="${homeUrl}" data-toggle="collapse" data-target="#main"><i class="glyphicon glyphicon-eye-open"></i> SZYBKI PRZEGLĄD </a>
                    </li> --%>
                    <li class="active" >
                        <a href="javascript:;" data-toggle="collapse" data-target="#events"><i class="glyphicon glyphicon-list"></i> WYDARZENIA </a>
                        <ul id="events" class="collapse" >
                            <li>
                                <a href="${createEventUrl}"><i class="glyphicon glyphicon-plus"></i> Utwórz nowe </a>
                            </li>
                            <li>
                                <a href="${ showEvents }"><i class="glyphicon glyphicon-pushpin"></i> Wszystkie </a> 
                            </li>
                            <li>
                                <a href="${showEvents}?page=organized"><i class="glyphicon glyphicon-pushpin"></i> Organizowane </a> 
                            </li>
                            <li>
                                <a href="${showEvents}?page=invitations"><i class="glyphicon glyphicon-list-alt"></i> Zaproszenia </a>
                            </li>
                        </ul>
                    </li>
                    <li class="active">
                        <a href="javascript:;" data-toggle="collapse" data-target="#friends"><i class="glyphicon glyphicon-user"></i><i class="glyphicon glyphicon-user"></i> ZNAJOMI </a>
                        <ul id="friends" class="collapse" >
                            <li>
                                <a href="${friendsUrl}"><i class="glyphicon glyphicon-pushpin"></i> Moi znajomi </a> 
                            </li>
                            <li>
                                <a href="${searchFriends}"><i class="glyphicon glyphicon-list-alt"></i> Szukaj znajomych </a>
                            </li>
                        </ul>
                    </li>
                    <li class="active">
                        <a href="javascript:;" data-toggle="collapse" data-target="#animator"><i class="glyphicon glyphicon-user"></i> ANIMATOR </a>
                        <ul id="animator" class="collapse" >
                            <li>
                                <a href="${plannerUrl}?orlik=1"><i class="glyphicon glyphicon-plus"></i> Grafik </a>
                            </li>
                            <li>
                                <a href="${paneUrl}"><i class="glyphicon glyphicon-pushpin"></i> Panel </a> 
                            </li>
                        </ul>
                    </li>
<%--                    <li class="active">
                        <a href="javascript:;" data-toggle="collapse" data-target="#socket"><i class="glyphicon glyphicon-user"></i> socket </a>
                        <ul id="socket" class="collapse" >
                            <li>
                                <a href="${socket}"><i class="glyphicon glyphicon-plus"></i> websocket </a>
                            </li>
                            <li>
                                <a href="${socket2}"><i class="glyphicon glyphicon-pushpin"></i> websocket2 </a> 
                            </li>
                        </ul>
                    </li>--%>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </nav>
        <!--  
        
        
        -->

        
        
