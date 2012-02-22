<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>UploadServerExample</title>
<link type="text/css" href="resources/css/main.css" rel="stylesheet" />
<link type="text/css"
	href="resources/css/ui-darkness/jquery-ui-1.8.17.custom.css"
	rel="stylesheet" />
<script type="text/javascript" src="resources/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript"
	src="resources/js/jquery-ui-1.8.17.custom.min.js"></script>
</head>
<body background="resources/images/dapaper.jpg">
	<h1>Upload Server Example</h1>

	<p>This server accepts image uploads from the upload example app
		running on an Android device emulator. It then returns some
		JSON formatted data to the Android app.
	<p>
	<div align="center">
		<img src="resources/images/nyan-cat.gif" />
	</div>

	<ul>
		<li><a href="uploadfile">File Upload</a></li>
	</ul>


	<hr>
	<P>The time on the server is ${serverTime}.</P>

</body>
</html>
