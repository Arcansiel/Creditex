[#ftl]
[#import "spring.ftl" as spring]
[#import "l_data.ftl" as l_data]
[#assign sec=JspTaglibs["http://www.springframework.org/security/tags"]/]
[#macro root]
<!DOCTYPE html>
<html lang="en">
    [#nested ]
</html>
[/#macro]

[#macro includeJQuery]
<script src="[@spring.url '/js/jquery-2.0.3.js'/]"></script>
[/#macro]

[#macro includeJPages]
<link rel="stylesheet" type="text/css" href="[@spring.url '/css/jPages.css'/]">
<script src="[@spring.url '/js/jPages.js'/]"></script>
[/#macro]

[#macro includeTableCloth]
<link rel="stylesheet" type="text/css" href="[@spring.url '/css/tablecloth.css'/]"/>
<script src="[@spring.url '/js/jquery.tablecloth.js'/]"></script>
<script src="[@spring.url '/js/jquery.tablesorter.js'/]"></script>
<script src="[@spring.url '/js/jquery.metadata.js'/]"></script>
[/#macro]



[#macro includeBootstrapCss]
<link rel="stylesheet" type="text/css" href="[@spring.url '/css/bootstrap.css'/]"/>
<link rel="stylesheet" type="text/css" href="[@spring.url '/css/bootstrap-theme.css'/]"/>
[/#macro]

[#macro includeBootstrapJS]
<script src="[@spring.url '/js/bootstrap.js'/]"></script>
[/#macro]

[#macro includeBootstrap]
[@includeBootstrapCss/]
[@includeBootstrapJS/]
[/#macro]

[#macro addValidator]
[@includeJQuery/]
<script type="text/javascript" src="[@spring.url '/js/jquery.validate.js'/]"></script>
<script type="text/javascript" src="[@spring.url '/js/additional-methods.js'/]"></script>
<script type="text/javascript" src="[@spring.url '/js/messages_ru.js'/]"></script>
[/#macro]

[#macro pagination container pageLength]
$("div.holder").jPages({
    containerID:"${container}",
    perPage: ${pageLength},
    delay: 0
});
[/#macro]

[#macro sorting table theme sortable class]
$("#${table}").tablecloth(
        {
            theme:"${theme}",
            sortable:${sortable?c},
            clean:true,
            cleanElements: "td th",
            customClass:"${class}"
        }
);
[/#macro]

[#macro tableProcess table container pageLength=10 theme = "default" sortable = true class = "data-table"]
[@includeJQuery/]
[@includeBootstrap/]
[@includeTableCloth/]
[@includeJPages/]
<script>
    $(function(){
        [@pagination container=container pageLength=pageLength/]
        [@sorting table=table theme=theme sortable=sortable class=class/]
    });
</script>
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
        <li><a href="[@spring.url '/change_registration_data/'/]">Изменить регистрационные данные</a></li>
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

[#macro returnButton]
<div class="content">
    <ul class="nav-menu"><li><a onclick="history.go(-1)">Вернуться назад</a></li>
        [#nested]
    </ul>
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

[#macro operation_manager]
<div class="title">
    <p class="icon"><img src="[@spring.url '/img/oparator-logo.jpg'/]" /></p>
    <p class="name">Операционист</p>
</div>
[/#macro]

[#macro security_manager]
<div class="title">
    <p class="icon"><img src="[@spring.url '/img/security-logo.jpg'/]" /></p>
    <p class="name">Служба безопасности</p>
</div>
[/#macro]

[#macro committee_manager]
<div class="title">
    <p class="icon"><img src="[@spring.url '/img/credit-committee-logo.jpg'/]" /></p>
    <p class="name">Кредитный комитет</p>
</div>
[/#macro]

[#macro department_head]
<div class="title">
    <p class="icon"><img src="[@spring.url '/img/boss-logo.jpg'/]" /></p>
    <p class="name">Глава кредитного отдела</p>
</div>
[/#macro]