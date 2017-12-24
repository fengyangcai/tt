<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
空值处理：<br>
如果为空则什么都不显示可以使用在变量后加!：${emp!}<br>
如果为空则显示自定义的显示值在变量后面加!"显示值" ：${emp!"emp的值为空。。"}

<br><hr>
??? 前面两个??表示判断一个变量是否存在，如果存在则返回true，如果不存在则返回false;第3个?表示对应前面的值调用某个函数
${bool???string}<br>


<#assign str="">
<#if str??>
str 存在
<#else>
str 不存在
</#if>

</body>
</html>