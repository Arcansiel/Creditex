[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]
[#import "creditex_data.ftl" as creditex_data]
[@creditex.root]
    [@creditex.head "Служба безопасности / заявка на кредит"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.security_manager /]
        [@creditex.goback /]
         <div class="data-table">
             <p class="name"><a href="[@spring.url '/security_manager/'/]">На главную страницу</a></p>
             <p class="name"><a href="[@spring.url '/security_manager/appliances/'/]">Список заявок</a></p>
        [#if application??]
            <p class="name"><a href="[@spring.url '/security_manager/client/check/${application.client.id?string("0")}'/]">Проверка клиента по внутренним базам</a></p>
            <p class="name"><a href="[@spring.url '/security_manager/client/check/outer/${application.client.id?string("0")}'/]">Проверка клиента по внешним базам</a></p>

            [@creditex_data.application_view_table application/]

            <p class="name"><a href="[@spring.url '/security_manager/appliance/cancel_assignment/${application.id?string("0")}'/]">Отказаться от рассмотрения заявки</a></p>

            [@creditex_data.confirmation_form
            "post"
            '/security_manager/appliance/confirm/${application.id?string("0")}'
            /]

            [#if (application.product)??]
                [@creditex_data.product_view_table application.product /]
            [/#if]

        [/#if]

         </div>
    </div>
    [/@creditex.body]
[/@creditex.root]