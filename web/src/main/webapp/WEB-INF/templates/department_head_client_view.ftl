[#ftl]
[#import "creditex.ftl" as creditex]
[#import "l_data.ftl" as l_data]
[@creditex.root]
    [@creditex.head "Клиент банка"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.department_head /]

        <div class="data-table">


        [#if client??]
            [@l_data.client_view_table client /]

            <p class="page-link"><a href="[@spring.url '/department_head/client/${client.id?string("0")}/credits/all/'/]">Все кредиты клиента</a></p>
            <p class="page-link"><a href="[@spring.url '/department_head/client/${client.id?string("0")}/credits/expired/'/]">Просроченные кредиты клиента</a></p>
            <p class="page-link"><a href="[@spring.url '/department_head/client/${client.id?string("0")}/prolongations/'/]">Заявки на пролонгацию</a></p>
            <p class="page-link"><a href="[@spring.url '/department_head/client/${client.id?string("0")}/priors/'/]">Заявки на досрочное погашение</a></p>
            <p class="page-link"><a href="[@spring.url '/department_head/client/${client.id?string("0")}/statistics/'/]">Статистика по клиенту</a></p>

        [/#if]

        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]