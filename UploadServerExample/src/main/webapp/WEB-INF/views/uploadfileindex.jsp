<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="true"%>
<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<META http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title>Image Upload Outcome</title>
<link type="text/css" href="resources/css/main.css" rel="stylesheet" />
<link type="text/css"
	href="resources/css/ui-darkness/jquery-ui-1.8.17.custom.css"
	rel="stylesheet" />
<script type="text/javascript" src="resources/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript"
	src="resources/js/jquery-ui-1.8.17.custom.min.js"></script>
</head>
<body>
<h2>Image File Upload Outcome</h2>
<p>The successfully uploaded image will be displayed below followed by its OCR 
results.</p>
<br>
<ul><li><a href="uploadfile">Click here to upload another image file</a></li>
<li><a href="/uploadserverexample">Home</a>
</ul>
<br>
<br>
<br>
<%
	if (session.getAttribute("uploadFile") != null
			&& !(session.getAttribute("uploadFile")).equals("")) {
%>
<h3>Uploaded File</h3>
<br>
<img
	src="<%= "resources/images/"
						+ session.getAttribute("uploadFile")%>"
	alt="Upload Image" />
	
<h3>OCR Text Returned</h3>
<p><%= session.getAttribute("ocrResultText") %></p>	
<%
	session.removeAttribute("uploadFile");
    session.removeAttribute("ocrResultText");
	}
%>


</body>
</html>