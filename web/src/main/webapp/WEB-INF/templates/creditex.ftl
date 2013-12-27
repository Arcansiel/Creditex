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

[#macro addValidator include_JQuery=true]
[#if include_JQuery][@includeJQuery/][/#if]
<script type="text/javascript" src="[@spring.url '/js/jquery.validate.js'/]"></script>
<script type="text/javascript" src="[@spring.url '/js/additional-methods.js'/]"></script>
<script type="text/javascript" src="[@spring.url '/js/messages_ru.js'/]"></script>
[/#macro]

[#macro includeCharts]
<script src="[@spring.url '/js/knockout-3.0.0.js'/]"></script>
<script src="[@spring.url '/js/globalize.min.js'/]"></script>
<script src="[@spring.url '/js/dx.chartjs.js'/]"></script>
[/#macro]

[#macro overallChart containerId]
$("#${containerId}").dxChart({
    dataSource: dataSource,
    commonSeriesSettings:{
        argumentField: "dayDateString",
        type:"spline"
    },
    commonAxisSettings:{
        grid:{
            visible:true
        }
    },
    series:[
        {valueField:"overallCredits",name:"Общее количество кредитов"},
        {valueField:"overallRunningCredits",name:"Количество текущих кредитов"},
        {valueField:"overallClosedCredits",name:"Количество закрытых кредитов"},
        {valueField:"overallExpiredCredits",name:"Количество кредитов с просроченными платежами"},
        {valueField:"overallUnreturnedCredits",name:"Количество невозвращённых кредитов"},
        {valueField:"overallCreditApplications",name:"Общее количество заявок на кредиты"},
        {valueField:"overallPriorRepaymentApplications",name:"Общее количество заявок на предварительное погашение"},
        {valueField:"overallProlongationApplications",name:"Общее количество заявок на пролонгацию"}
    ],
    tooltip:{
        enabled:true
    },
    legend:{
        verticalAlignment: "bottom",
        horizontalAlignment: "center"
    },
    title:"Общая статистика",
    commonPaneSettings: {
        border:{
            visible: true,
            bottom: false
        }
    }
});
[/#macro]

[#macro bankMoneyChart containerId]
$("#${containerId}").dxChart({
    dataSource: dataSource,
    commonSeriesSettings:{
        argumentField: "dayDateString",
        type:"spline"
    },
    commonAxisSettings:{
        grid:{
            visible:true
        }
    },
    series:[
        {valueField:"currentBankMoney",name:"Деньги на счету банка"}
    ],
    tooltip:{
        enabled:true
    },
    legend:{
        verticalAlignment: "bottom",
        horizontalAlignment: "center"
    },
    title:"Деньги банка",
    commonPaneSettings: {
        border:{
            visible: true,
            bottom: false
        }
    }
});
[/#macro]

[#macro ioChart containerId]
$("#${containerId}").dxChart({
    dataSource: dataSource,
    commonSeriesSettings:{
        argumentField: "dayDateString",
        type:"spline"
    },
    commonAxisSettings:{
        grid:{
            visible:true
        }
    },
    series:[
        {valueField:"income",name:"Доход"},
        {valueField:"outcome",name:"Расход"}
    ],
    tooltip:{
        enabled:true
    },
    legend:{
        verticalAlignment: "bottom",
        horizontalAlignment: "center"
    },
    title:"Доход/расход",
    commonPaneSettings: {
        border:{
            visible: true,
            bottom: false
        }
    }
});
[/#macro]

[#macro registeredCurrent containerId]
$("#${containerId}").dxChart({
    dataSource: dataSource,
    commonSeriesSettings:{
        argumentField: "dayDateString",
        type:"spline"
    },
    commonAxisSettings:{
        grid:{
            visible:true
        }
    },
    series:[
        {valueField:"clients",name:"Клиенты"},
        {valueField:"accountManagers",name:"Специалисты по работе с клиентами"},
        {valueField:"securityManagers",name:"Специалисты по безопасности"},
        {valueField:"operationManagers",name:"Операционисты"},
        {valueField:"committeeManagers",name:"Члены кредитного комитета"}
    ],
    tooltip:{
        enabled:true
    },
    legend:{
        verticalAlignment: "bottom",
        horizontalAlignment: "center"
    },
    title:"Зарегистрировано пользователей",
    commonPaneSettings: {
        border:{
            visible: true,
            bottom: false
        }
    }
});
[/#macro]

[#macro currentChart containerId]
$("#${containerId}").dxChart({
    dataSource: dataSource,
    commonSeriesSettings:{
        argumentField: "dayDateString",
        type:"spline"
    },
    commonAxisSettings:{
        grid:{
            visible:true
        }
    },
    series:[
        {valueField:"operations",name:"Совершено операций"},
        {valueField:"credits",name:"Количество зарегистрированных кредитов"},
        {valueField:"expiredCredits",name:"Количество просроченных кредитов"},
        {valueField:"unreturnedCredits",name:"Количество невозвращённых кредитов"},
        {valueField:"creditApplications",name:"Количество заявок на кредиты"},
        {valueField:"priorRepaymentApplications",name:"Количество заявок на предварительное погашение"},
        {valueField:"prolongationApplications",name:"Количество заявок на пролонгацию"},
        {valueField:"products",name:"Создано кредитных продуктов"}
    ],
    tooltip:{
        enabled:true
    },
    legend:{
        verticalAlignment: "bottom",
        horizontalAlignment: "center"
    },
    title:"Статистика по дням",
    commonPaneSettings: {
        border:{
            visible: true,
            bottom: false
        }
    }
});
[/#macro]

[#macro charts data, containerOverall="", containerRegistered="", containerCurrent="", containerBankMoney="", containerIO="", includeOverall=true, includeRegistered=true,includeCurrent=true, includeBankMoney=true, includeIO=true]
<script type="text/javascript">
    $(function(){
        var dataSource = ${data};
        [#if includeBankMoney]
            [@bankMoneyChart containerId=containerBankMoney/]
        [/#if]
        [#if includeOverall]
            [@overallChart containerId=containerOverall/]
        [/#if]
        [#if includeIO]
            [@ioChart containerId=containerIO/]
        [/#if]
        [#if includeCurrent]
            [@currentChart containerId=containerCurrent/]
        [/#if]
        [#if includeRegistered]
            [@registeredCurrent containerId=containerRegistered/]
        [/#if]

    });
</script>
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

[#macro bank_client]
<div class="title">
    <p class="icon"><img src="[@spring.url '/img/client-bank-logo.jpg'/]" /></p>
    <p class="name">Клиент банка</p>
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
        [#nested ]
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