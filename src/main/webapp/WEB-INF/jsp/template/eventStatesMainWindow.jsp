<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<div class="row">
	<div class="col-lg-12">
		<h1 class="page-header">
			<c:choose>
				<c:when test="${page == 'fast'}">Szybki przegląd</c:when>
				<c:when test="${page == 'all'}">Wydarzenia <small> <i class="glyphicon glyphicon-picture"></i> Wszystkie 
				</c:when> 
				<c:when test="${page == 'organized'}">Wydarzenia <small> <i class="glyphicon glyphicon-picture"></i> Organizowane</c:when>
				<c:when test="${page == 'invitations'}">Wydarzenia <small> <i class="glyphicon glyphicon-picture"></i> Zaproszenia</c:when> 
			</c:choose></small>
		</h1>
	</div>
</div>




<div class="row">
	<div class="col-lg-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">
			<c:choose>
				<c:when test="${page == 'fast'}"><i class="glyphicon glyphicon-info-sign"></i> Informacje o wydarzeniach w których uczestniczysz</c:when> 
				<c:when test="${page == 'all'}"><i class="glyphicon glyphicon-info-sign"></i> Informacje o wydarzeniach w których uczestniczysz</c:when> 
				<c:when test="${page == 'organized'}"><i class="glyphicon glyphicon-info-sign"></i> Informacje o wydarzeniach, które organizujesz</c:when>
				<c:when test="${page == 'invitations'}"><i class="glyphicon glyphicon-info-sign"></i> Informacje o wydarzeniach na które Cię zaproszono</c:when> 
			</c:choose>
				</h3>
			</div>
			<div class="panel-body">
			
			
			<c:forEach items="${eventWindowsList}" var="windowBlock" varStatus="i">
				<c:choose>
			    	<c:when test="${not empty windowBlock }">
					    <c:set var="willCome" scope="session" value="${ windowBlock.goingToCome }"/>
						<c:set var="limit" scope="session" value="${ windowBlock.playersLimit }"/>
						<c:set var="address" scope="session" value="${ windowBlock.address }"/>
						<c:set var="city" scope="session" value="${ windowBlock.city }"/>
						<c:set var="start" scope="session" value="${ windowBlock.startTime }"/>
						<c:set var="end" scope="session" value="${ windowBlock.endTime }"/>
						<c:set var="state" scope="session" value="${ windowBlock.stateId }"/>
						<c:set var="inSameState" scope="session" value="${ windowBlock.countedInSameState }"/>
			    	</c:when>
			    	<c:otherwise>
			        	<c:set var="willCome" scope="session" value="0"/>
						<c:set var="limit" scope="session" value="0"/>
						<c:set var="address" scope="session" value="brak"/>
						<c:set var="city" scope="session" value="brak"/>
						<c:set var="start" scope="session" value="${ null }"/>
						<c:set var="end" scope="session" value="${ null }"/>
						<c:set var="state" scope="session" value="${ null }"/>
						<c:set var="inSameState" scope="session" value="0"/>
			    	</c:otherwise>
				</c:choose>
				
				<c:set var="displayOrder" scope="session" value="${ eventWindowsList.size() - i.index - 1 }"/>
				
			
				<div class="col-md-2">
						<c:choose>
							<c:when test="${ displayOrder  == 5 }">
								<div class="panel panel-primary">
							</c:when>
							<c:when test="${ displayOrder  == 4 }">
								<div class="panel panel-green">
							</c:when>
							<c:when test="${ displayOrder  == 3 }">
								<div class="panel panel-red">
							</c:when>
							<c:when test="${ displayOrder  == 2 }">
								<div class="panel panel-yellow">
							</c:when>
							<c:when test="${ displayOrder  == 1 }">
								<div class="panel panel-silver">
							</c:when>
							<c:when test="${ displayOrder  == 0 }">
								<div class="panel panel-silver-dark">
							</c:when>
						</c:choose>
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-3">		
									<c:choose>
										<c:when test="${ displayOrder  == 5 }">
											<i style="font-size: 2.5em;" class="glyphicon glyphicon-plane"></i>
										</c:when>
										<c:when test="${ displayOrder  == 4 }">
											<i style="font-size: 2.5em;" class="glyphicon glyphicon-thumbs-up"></i>
										</c:when>
										<c:when test="${ displayOrder  == 3 }">
											<i style="font-size: 2.5em;" class="glyphicon glyphicon-warning-sign"></i>
										</c:when>
										<c:when test="${ displayOrder  == 2 }">
											<i style="font-size: 2.5em;" class="glyphicon glyphicon-send"></i>
										</c:when>
										<c:when test="${ displayOrder  == 1 }">
											<i style="font-size: 2.5em;" class="glyphicon glyphicon-thumbs-down"></i>
										</c:when>
										<c:when test="${ displayOrder  == 0 }">
											<i style="font-size: 2.5em;" class="glyphicon glyphicon-trash"></i>
										</c:when>
									</c:choose> ${willCome}/${limit}
								</div>
								<div class="col-xs-12 text-right">
									<div class="large"><c:choose>
															<c:when test="${ displayOrder  == 5 }">
																Nadchodzący
															</c:when>
															<c:when test="${ displayOrder  == 4 }">
																Przyjęty
															</c:when>
															<c:when test="${ displayOrder  == 3 }">
																Zagrożony
															</c:when>
															<c:when test="${ displayOrder  == 2 }">
																Do akceptacji
															</c:when>
															<c:when test="${ displayOrder  == 1 }">
																W budowie
															</c:when>
															<c:when test="${ displayOrder  == 0 }">
																Kosz
															</c:when>
														</c:choose></div>
									<div>${ address } </div>
									<c:if test="${ start != null }">
										<div><fmt:formatDate value="${start}" type="both" pattern="dd.MM.yyyy" /></div>
										<div><fmt:formatDate value="${start}" type="both" pattern="HH:mm" /> - <fmt:formatDate value="${end}" type="both" pattern="HH:mm" /></div>
									</c:if>
								</div>
							</div>
						</div>
					<c:choose>
						<c:when test="${not empty state }">
							<c:choose>
								<c:when test="${ displayOrder  == 5 }">
									<c:choose>
										<c:when test="${ page == 'fast' ||  page == 'all' }"><a href="<c:url value="/events/allInState/6" />"></c:when>
										<c:when test="${ page == 'organized' }"><a href="<c:url value="/events/listDetails/6/1" />"></c:when>
										<c:when test="${ page == 'invitations' }"><a href="<c:url value="/events/listDetails/6/2" />"></c:when>
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${ page == 'fast' ||  page == 'all' }"><a href="<c:url value="/events/allInState/${state}" />"></c:when>
										<c:when test="${ page == 'organized' }"><a href="<c:url value="/events/listDetails/${state}/1" />"></c:when>
										<c:when test="${ page == 'invitations' }"><a href="<c:url value="/events/listDetails/${state}/2" />"></c:when>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<a href="#">
						</c:otherwise>
					</c:choose>	
							<div class="panel-footer">
								<span class="pull-left">Sprawdź wszystkie (${ inSameState })</span> <span class="pull-right"><i class="glyphicon glyphicon-eye-open"></i></span>
								<div class="clearfix"></div>
							</div>
							</a>
						</div>
					</div>
			</c:forEach>
			
			</div>
		</div>
	</div>
</div>





