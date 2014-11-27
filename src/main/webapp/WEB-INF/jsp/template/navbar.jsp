<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<c:url value="/home" var="homeUrl" />
<c:url value="/profile" var="profileUrl" />
<c:url value="/" var="logoutUrl" />
 

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
                <a class="navbar-brand" href="${homeUrl}">Orlikeo!</a>
            </div>
            
            
            <!-- Top Menu Items -->
            <ul class="nav navbar-right top-nav">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="glyphicon glyphicon-envelope"></i> <b class="caret"></b></a>
                    <ul class="dropdown-menu message-dropdown">
                        <li class="message-preview">
                            <a href="#">
                                <div class="media">
                                    <span class="pull-left">
                                        <img class="media-object" src="http://epilka.pl/public/upload/player/robert_lewandowski.jpg" alt="">
                                    </span>
                                    <div class="media-body">
                                        <h5 class="media-heading"><strong>Robert Lewandowski</strong>
                                        </h5>
                                        <p class="small text-muted"><i class="fa fa-clock-o"></i> Dzisiaj o 18:01 </p>
                                        <p>Finał jest nasz...</p>
                                    </div>
                                </div>
                            </a>
                        </li>
                        <li class="message-preview">
                            <a href="#">
                                <div class="media">
                                    <span class="pull-left">
                                        <img class="media-object" src="https://lh5.googleusercontent.com/-abmIrPFO_4o/AAAAAAAAAAI/AAAAAAAAAT4/WRtI40jdXms/photo.jpg?sz=50" alt="">
                                    </span>
                                    <div class="media-body">
                                        <h5 class="media-heading"><strong>Ronaldo</strong>
                                        </h5>
                                        <p class="small text-muted"><i class="fa fa-clock-o"></i> Dzisiaj o 17:04 </p>
                                        <p>Szczeliłem gola...</p>
                                    </div>
                                </div>
                            </a>
                        </li>
                        <li class="message-preview">
                            <a href="#">
                                <div class="media">
                                    <span class="pull-left">
                                        <img class="media-object" src="http://cs9403.vk.me/v9403819/12e0/knAMr1aDwqs.jpg" alt="">
                                    </span>
                                    <div class="media-body">
                                        <h5 class="media-heading"><strong>Zinedine Zidane</strong>
                                        </h5>
                                        <p class="small text-muted"><i class="glyphicon glyphicon-user"></i> Wczoraj o 16:32 </p>
                                        <p>Skończyłem karierę, ale co z tego...</p>
                                    </div>
                                </div>
                            </a>
                        </li>
                        <li class="message-footer">
                            <a href="#">Read All New Messages</a>
                        </li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="glyphicon glyphicon-bell"></i> <b class="caret"></b></a>
                    <ul class="dropdown-menu alert-dropdown">
                        <li>
                            <a href="#">Alert Name <span class="label label-default">Alert Badge</span></a>
                        </li>
                        <li>
                            <a href="#">Alert Name <span class="label label-primary">Alert Badge</span></a>
                        </li>
                        <li>
                            <a href="#">Alert Name <span class="label label-success">Alert Badge</span></a>
                        </li>
                        <li>
                            <a href="#">Alert Name <span class="label label-info">Alert Badge</span></a>
                        </li>
                        <li>
                            <a href="#">Alert Name <span class="label label-warning">Alert Badge</span></a>
                        </li>
                        <li>
                            <a href="#">Alert Name <span class="label label-danger">Alert Badge</span></a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="#">View All</a>
                        </li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="glyphicon glyphicon-user"></i> Leo Messi <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="${profileUrl}"><i class="glyphicon glyphicon-cog"></i> Profil</a>
                        </li>
                        <li>
                            <a href="#"><i class="glyphicon glyphicon-envelope"></i> Skrzynka</a>
                        </li>
                        <li>
                            <a href="#"><i class="glyphicon glyphicon-wrench"></i> Ustawienia</a>
                        </li>
                        <li class="divider"></li>
                        <li>
                            <a href="${logoutUrl}"><i class="glyphicon glyphicon-off"></i> Wyloguj</a>
                        </li>
                    </ul>
                </li>
            </ul>
            
            
            
<!--  LEFT MENU  -->
            <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <ul class="nav navbar-nav side-nav">
                <li class="active">
                        <a href="javascript:;" data-toggle="collapse" data-target="#main"><i class="glyphicon glyphicon-eye-open"></i> SZYBKI PRZEGLĄD </a>
                    </li>
                    <li class="active">
                        <a href="javascript:;" data-toggle="collapse" data-target="#events"><i class="glyphicon glyphicon-list"></i> WYDARZENIA </a>
                        <ul id="events" class="collapse">
                            <li>
                                <a href="#"><i class="glyphicon glyphicon-plus"></i> Utwórz nowe </a>
                            </li>
                            <li>
                                <a href="#"><i class="glyphicon glyphicon-pushpin"></i> Organizowane </a> 
                            </li>
                            <li>
                                <a href="#"><i class="glyphicon glyphicon-list-alt"></i> Zaproszenia </a>
                            </li>
                        </ul>
                    </li>
                    <li class="active">
                        <a href="javascript:;" data-toggle="collapse" data-target="#friends"><i class="glyphicon glyphicon-user"></i><i class="glyphicon glyphicon-user"></i> ZNAJOMI </a>
                        <ul id="friends" class="collapse">
                            <li>
                                <a href="#"><i class="glyphicon glyphicon-plus"></i> Dodaj znajomego </a>
                            </li>
                            <li>
                                <a href="#"><i class="glyphicon glyphicon-pushpin"></i> Moi znajomi </a> 
                            </li>
                            <li>
                                <a href="#"><i class="glyphicon glyphicon-list-alt"></i> Szukaj znajomych </a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </nav>