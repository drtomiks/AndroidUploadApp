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
	<h1>Developmental OCR Server</h1>

	<p>This is the server component of my mobile data capture testing
		framework. This server accepts image uploads from a data capture app
		running on an Android device emulator. It then performs OCR on the
		upload and returns the data to the device. This basic work flow is
		illustrated in the slide below.
	<p>
	<div align="center">
		<img src="resources/images/data_cap_flow.jpg" width="500" />
	</div>

	<ul>
		<li><a href="uploadfile">File Upload</a></li>
	</ul>


	<hr>
	<P>The time on the server is ${serverTime}.</P>

</body>
</html>
