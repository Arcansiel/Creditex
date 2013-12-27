[#ftl]
[#import "creditex.ftl" as creditex]
[#import "l_data.ftl" as l_data]
[@creditex.root]
    [@creditex.head "Статистика по клиету"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.department_head /]
        [@creditex.goback/]
        <p class="page-link"><a href="[@spring.url '/department_head/'/]">На главную страницу</a></p>
        <div class="data-table">
            [#include "statistics_client.ftl" /]
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]