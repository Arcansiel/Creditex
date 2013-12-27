[#ftl]
[#import "creditex.ftl" as creditex]
[#import "l_data.ftl" as l_data]
[@creditex.root]
    [@creditex.head "Клиент банка (внешняя проверка)"]
        [@creditex.includeBootstrap /]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.security_manager /]
        [@creditex.goback /]
        <div class="data-table">
        <p class="name"><a href="[@spring.url '/security_manager/'/]">На главную страницу</a></p>
        [#if client??]
            <p class="name"><a href=[@spring.url '/security_manager/client/check/${client.id?string("0")}'/]>Проверка клиента по внутренним базам</a></p>

            [@l_data.client_view_table client "Клиент банка (внешняя проверка)"/]

         [/#if]

            <p class="name">Проверка по внешним базам данных</p>

            <p class="name">Провкрка паспортных данных клиента</p>
            [#if (info.passport)??]
                <p>${info.passport?html}</p>
            [/#if]

            <p class="name">Информация о работодателе клиента</p>
            [#if (info.employer)??]
                <p>${info.employer?html}</p>
            [/#if]

            <p class="name">Провкрка зарплаты и должности клиента</p>
            [#if (info.position)??]
                <p>${info.position?html}</p>
            [/#if]

            <p class="name">Информация из баз данных налоговых служб</p>
            [#if (info.taxes)??]
                <p>${info.taxes?html}</p>
            [/#if]

            <p class="name">Информация из баз данных МВД</p>
            [#if (info.mia)??]
                <p>${info.mia?html}</p>
            [/#if]

            <p class="name">Информация из баз данных других банков</p>
            [#if (info.banks)??]
                <p>${info.banks?html}</p>
            [/#if]

        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]