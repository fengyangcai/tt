<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
assgin命令:<br>
字符串：<#assign str="黑马">
${str}

<br><hr>
数值：<#assign num=123>
${num}

<br><hr>
布尔值：不能直接输出，必须将boolean类型转换为字符串输出，bool?string表示直接将布尔值输出（true 或者 false）；如果需要将输出值根据
具体的布尔值的真假显示不同的内容的话也可以如此指定：bool?string("YES","NO")表示真值的时候显示YES,假的时候显示NO
<#assign bool=false> <br>
默认值显示：${bool?string}；指定显示值：${bool?string("YES","NO")}

<br><hr>
日期处理：.now表示当前日期；可以使用string函数将日期转换为格式化的字符串并输出<br>
${.now} --- 格式化显示日期：${.now?string("yyyy-MM-dd HH:mm:ss SSSS")}
<br>
<#assign date="2012-12-20"?date("yyyy-MM-dd")>

${date?string("yyyy年MM月dd日")}

<br>

<a href="http://www.itheima.com" title="我和黑马程序员的故事">
${"我和黑马程序员的故事。"[2..6]}...
</a>



</body>
</html>