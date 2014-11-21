<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">
	<div class="col-sm-6 vcenter">
		<img src='<c:url value="/resources/images/content/error/ghost.png" />' style="width:75%" />
	</div><!--
  --><div class="col-sm-6 vcenter">
		<p class="h1">[404]</p>
		<p class="h2"><spring:message code="web.errors.error404.message" /></p>
	</div>
</div>