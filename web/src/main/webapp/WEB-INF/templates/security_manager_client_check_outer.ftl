[#ftl]
[#import "creditex.ftl" as creditex]
[#import "creditex_data.ftl" as creditex_data]
[@creditex.root]
    [@creditex.head "Клиент банка (внешняя проверка)"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.security_manager /]
        [@creditex.goback /]
        <div class="data-table">
        <p class="name"><a href="[@spring.url '/security_manager/'/]">На главную страницу</a></p>
        [#if client??]
            <p class="name"><a href=[@spring.url '/security_manager/client/check/${client.id?string("0")}'/]>Проверка клиента по внутренним базам</a></p>

            [@creditex_data.client_view_table client "Клиент банка (внешняя проверка)"/]

         [/#if]

            <p class="name">Проверка по внешним базам данных</p>

        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]