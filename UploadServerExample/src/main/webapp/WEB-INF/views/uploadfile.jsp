<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<META http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title>Image Upload Form</title>
<link type="text/css" href="resources/css/main.css" rel="stylesheet" />
<link type="text/css"
	href="resources/css/ui-darkness/jquery-ui-1.8.17.custom.css"
	rel="stylesheet" />
<script type="text/javascript" src="resources/js/jquery-1.7.1.min.js"></script>
<script type="text/javascript"
	src="resources/js/jquery-ui-1.8.17.custom.min.js"></script>

<script language="JavaScript">
				function Validate()
				  {
					 var image =document.getElementById("image").value;
					 if(image!=''){
						  var checkimg = image.toLowerCase();
						  if (!checkimg.match(/(\.jpg|\.png|\.JPG|\.PNG|\.jpeg|\.JPEG)$/)){
							  alert("Please enter  Image  File Extensions .jpg,.png,.jpeg");
							  document.getElementById("image").focus();
							  return false;
						    }
						 }
					 return true;
				 }			
</script>
</head>
<body>
<form:form modelAttribute="uploadItem" name="frm" method="post"
	enctype="multipart/form-data" onSubmit="return Validate();">
	<fieldset><legend>Upload Image File</legend>
	<table>
		<tr>
			<td><form:label for="fileData" path="fileData">File</form:label><br />
			</td>
			<td><form:input path="fileData" id="image" type="file" /></td>
		</tr>
		<tr>
			<td><br />
			</td>
			<td><input type="submit" value="Upload" /></td>
		</tr>
	</table>
	</fieldset>
</form:form>
<br>
<ul>
<li><a href="/uploadserverexample">Home</a>
</ul>
</body>
</html>