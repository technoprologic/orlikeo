<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<%-- 	<h1>Stock Ticker</h1>

  <table>
    <thead><tr><th>Code</th><th>Price</th><th>Time</th></tr></thead>
    <tbody id="price"></tbody>
  </table>

  <p class="new">
    Code: <input type="text" class="code"/>
    Price: <input type="text" class="price"/>
    <button class="add">Add</button>
    <button class="remove-all">Remove All</button>
  </p>

<p id="dedicated">name&number</p>

  <script src="http://cdn.sockjs.org/sockjs-0.3.min.js"></script>
  <script src="<c:url value="/resources/websocket/stomp.js" />" type="text/javascript" > </script>
  <script src="https://code.jquery.com/jquery-1.11.0.min.js"></script>
  <script>
    //Create stomp client over sockJS protocol
    var socket = new SockJS("/jbossews/hello");
    var stompClient = Stomp.over(socket);

    // Render price data from server into HTML, registered as callback
    // when subscribing to price topic
    function renderPrice(frame) {
      var prices = JSON.parse(frame.body);
      $('#price').empty();
      for(var i in prices) {
        var price = prices[i];
        $('#price').append(
          $('<tr>').append(
            $('<td>').html(price.code),
            $('<td>').html(price.price.toFixed(2)),
            $('<td>').html(price.timeStr)
          )
        );
      }
    }

    // Render user dedicated data from server into HTML, registered as callback
    // when subscribing to dedicated topic
    function renderName(frame) {
      var info = frame.body;
      $('#dedicated').empty();
      $('#dedicated').html(info);
      }
    
    // Callback function to be called when stomp client is connected to server
    var connectCallback = function() {
      stompClient.subscribe('/topic/price', renderPrice);
      stompClient.subscribe('/user/topic/dedicated', renderName);
    }; 

    // Callback function to be called when stomp client could not connect to server
    var errorCallback = function(error) {
      alert(error.headers.message);
    };

    // Connect to server via websocket
    stompClient.connect({}, connectCallback, errorCallback);
    
    // Register handler for add button
    $(document).ready(function() {
      $('.add').click(function(e){
        e.preventDefault();
        var code = $('.new .code').val();
        var price = Number($('.new .price').val());
        var jsonstr = JSON.stringify({ 'code': code, 'price': price });
        stompClient.send("/app/addStock", {}, jsonstr);
        return false;
      });
    });
    
    // Register handler for remove all button
    $(document).ready(function() {
      $('.remove-all').click(function(e) {
        e.preventDefault();
        stompClient.send("/app/removeAllStocks");
        return false;
      });
    });
  </script> --%>
  


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
					    <c:set var="willCome"  value="${ windowBlock.goingToCome }"/>
						<c:set var="limit"  value="${ windowBlock.playersLimit}"/>
						<c:set var="address"  value="${ windowBlock.address }"/>
						<c:set var="city"  value="${ windowBlock.city }"/>
						<c:set var="start"  value="${ windowBlock.startTime }"/>
						<c:set var="end"  value="${ windowBlock.endTime }"/>
						<c:set var="state"  value="${ windowBlock.stateId }"/>
						<c:set var="inSameState"  value="${ windowBlock.countedInSameState }"/>
						<c:set var="displayOrder"  value="${ windowBlock.displayOrder }"/>
						<c:set var="label"  value="${ windowBlock.label }"/>
						<c:set var="incoming"  value="${ windowBlock.incoming }"/>

							
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
										<c:when test="${ displayOrder == 3 }">
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
									<div class="large">${ label }</div>
									<div>${ address } </div>
									<c:if test="${ start != null }">
										<div><fmt:formatDate value="${start}" type="both" pattern="dd.MM.yyyy" /></div>
										<div><fmt:formatDate value="${start}" type="both" pattern="HH:mm" /> - <fmt:formatDate value="${end}" type="both" pattern="HH:mm" /></div>
									</c:if>
								</div>
							</div>
						</div>
					<c:choose>
						<c:when test="${inSameState != 0 }">
							<c:choose>
								<c:when test="${ displayOrder  == 5 && incoming }">
									<c:choose>
										<c:when test="${ page == 'fast' ||  page == 'all' }"><a href="<c:url value="/events/allInState/6" />"></a></c:when>
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