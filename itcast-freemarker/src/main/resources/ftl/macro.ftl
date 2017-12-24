<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
macro自定义命令：<br>

<#-- 定义命令 -->
<#macro sayHello name>
	hello ${name}！
</#macro>

<#-- 使用命令 -->
<@sayHello name="itcast"/>

<br>
<hr>
<br>

<#macro mySelect name options=10>
	<select name="${name}">
		<option>请选择</option>
		<#list 1..options as num>
			<option value="${num}">---${num}</option>
		</#list>
	</select>
</#macro>

<@mySelect name="city"/> -- <@mySelect name="province" options=18/>

</body>
</html>