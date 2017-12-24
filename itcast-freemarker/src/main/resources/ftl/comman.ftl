<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<#-- 命令 -->
自定义对象输出：${emp}；
<br> 名称：${emp.name}，工龄：${emp.workAge}，生日：${emp.birthday?string("yyyy年MM月dd日")}

<hr><br>
条件控制语句：if
<br>
${emp.name}可以休
<#if emp.workAge gte 10>
15
<#elseif emp.workAge gte 7>
10
<#elseif emp.workAge gte 5>
7
<#elseif emp.workAge gte 3>
5
<#elseif emp.workAge gte 1>
3
<#else>
0
</#if>
天年假。


<hr><br>
循环控制语句：list <br>
<#list empList as e>
	${e}；
	${e.name} -- ${e.workAge}<br>

</#list>
</body>
</html>