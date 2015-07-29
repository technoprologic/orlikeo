<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>



<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>Oficiall Orlikeo service</title>
    <!-- Bootstrap Core CSS -->
    <link href="<c:url value="/resources/mytheme/bootstrap/css/bootstrap.min.css" />" rel="stylesheet">
    <!-- Bootstrap Core CSS -->
    <link href="<c:url value="/resources/mytheme/bootstrap/css/bootstrap-table.css" />" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="<c:url value="/resources/mytheme/bootstrap/css/sb-admin.css" />"  rel="stylesheet">
    <!-- Morris Charts CSS -->
    <link href="<c:url value="/resources/mytheme/bootstrap/css/plugins/morris.css" />" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <!--  css for password changing -->
   <link  href="<c:url value="/resources/mytheme/css/style.css" />" rel="stylesheet" />
      <link  href="<c:url value="/resources/mytheme/css/calendar.css" />" rel="stylesheet" />
   <script src="<c:url value="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js" />"> </script>
   <script type="text/javascript" src="<c:url value="/resources/mytheme/js/register.js" />"></script>
</head>
<body>

  



    <div id="wrapper">
		<tiles:insertAttribute name="navbar" />	
		<div id="page-wrapper" >	
			 <div class="container-fluid" >
			 	<tiles:insertAttribute name="eventsMainWindow" />
				<tiles:insertAttribute name="content" />
	    	 </div>
        </div> <!-- page-wrapper -->
	</div> <!-- wrapper -->

    <!-- jQuery -->
    <script src="<c:url value="/resources/mytheme/bootstrap/js/jquery.js" />" ></script>
    <!-- Bootstrap Core JavaScript -->
    <script src="<c:url value="/resources/mytheme/bootstrap/js/bootstrap.js" />" ></script>

</body>

</html>



