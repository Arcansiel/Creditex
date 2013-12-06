[#ftl]
[#import "spring.ftl" as spring]
[#assign sec=JspTaglibs["http://www.springframework.org/security/tags"]/]
[#macro root]
<!DOCTYPE html>
<html lang="en">
    [#nested ]
</html>
[/#macro]

[#macro head title]
<head>
    <meta charset="utf-8"/>
    <title>${title}</title>
    <link rel="stylesheet" type="text/css" href="[@spring.url '/css/creditex.css'/]"/>
    [#nested]
</head>
[/#macro]

[#macro body]
<body>
[#nested]
</body>
[/#macro]