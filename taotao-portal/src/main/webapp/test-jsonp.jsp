<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery-1.6.4.js"></script>
<script type="text/javascript">

	/* $.ajax({
		url:"http://manage.taotao.com/jsonp.json",
		dataType:"json",
		success:function(msg){
			alert(msg.test);
		}
	}); */
	
	
	//{"test":"itcast"}
	
	function fun(data){
		alert(data.test);
	}
	function itcast(data){
		alert(data.test);
	}
	
	$.ajax({
		url:"http://manage.taotao.com/jsonp.jsp",
		dataType:"jsonp",
		success:function(msg){
			alert(msg.test);
		}
	});
</script>
<!-- <script type="text/javascript" src="http://manage.taotao.com/jsonp.jsp?callback=itcast"></script> -->
</head>
<body>

</body>
</html>