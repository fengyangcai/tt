<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<#-- 列表与map的处理 -->
列表数据处理：<br>
<#assign nums=[1,2,3,4,5]>

<#assign nums=6..10>

<#list nums[2..4] as num>

	${num}<br>
</#list>

<br>
<hr>
<br>
map的处理:<br>

<#assign map={"name":"黑马","age":12}>

<#list map?keys as key>
  key=${key}；value=${map["${key}"]}<br>
</#list>

</body>
</html>