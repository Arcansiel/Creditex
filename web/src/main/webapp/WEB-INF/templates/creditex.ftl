[#ftl]
[#import "spring.ftl" as spring]
[#assign sec=JspTaglibs["http://www.springframework.org/security/tags"]/]
[#macro root]
<!DOCTYPE html>
<html lang="en">
    [#nested ]
</html>
[/#macro]

[#macro includeBootstrap]
<link rel="stylesheet" type="text/css" href="[@spring.url '/css/bootstrap.css'/]"/>
<link rel="stylesheet" type="text/css" href="[@spring.url '/css/bootstrap-theme.css'/]"/>
<script src="[@spring.url '/js/bootstrap.js'/]"></script>
<script src="[@spring.url '/js/jquery-2.0.3.js'/]"></script>
[/#macro]

[#macro head title]
<head>
    <meta charset="utf-8"/>
    <title>${title}</title>
    <link rel="stylesheet" type="text/css" href="[@spring.url '/css/index.css'/]"/>
    [#nested]
</head>
[/#macro]

[#macro body]
<body>
<div class="inner-box">
    <div id="header">
        <p><a href="#"><img src="[@spring.url '/img/logo.jpg'/]" /></a></p>
    </div>
</div>
[#nested]
<div class="description">
    <ul class="about">
        <li><a href="#">О системе</a></li>
        <li><a href="#">Разработчики</a></li>
        [@sec.authorize access='isAnonymous()']
            <li><a href="[@spring.url '/register/'/]">Зарегистрироваться</a></li>
        [/@sec.authorize]
        [@sec.authorize access='isAuthenticated()']
            <li><a href="[@spring.url '/j_spring_security_logout'/]">Выйти</a></li>
        [/@sec.authorize]
</ul>
</div>
</body>
[/#macro]

[#macro account_manager]
<div class="title">
    <p class="icon"><img src="[@spring.url '/img/consultant-logo.jpg'/]" /></p>
    <p class="name">Специалист по работе с клиентами</p>
</div>
[/#macro]


[#macro goback]
<div class="content">
    <ul class="nav-menu">
        <li><a href="javascript: history.go(-1)">Вернуться назад</a>
        </li>
    </ul>
</div>
[/#macro]