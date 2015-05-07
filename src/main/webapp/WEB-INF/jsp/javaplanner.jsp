

<%-- <div class="planner" id="planner">${body}</div>

 --%>
 


<html>
<body>
<div class="planner" id="planner"><%= getPlanner(request) %></div>
<%@ page import="com.dhtmlx.planner.data.*, com.dhtmlx.planner.*, umk.zychu.inzynierka.controller.util.*" %>
<%!
           String getPlanner(HttpServletRequest request) throws Exception {
           DHXPlanner s = new DHXPlanner("./resources/codebase/",DHXSkin.TERRACE);
           s.setWidth(900);
           s.setInitialDate(2015, 04, 06);
           s.load(request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath()) + "/events", DHXDataFormat.JSON);
           s.data.dataprocessor.setURL(request.getRequestURL().toString().replace(request.getRequestURI(), request.getContextPath()) +  "/events");
           return s.render();
}
%>
</body>
</html>