[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]
[#import "l_data.ftl" as l_data]
[@creditex.root]
    [@creditex.head "Служба безопасности / заявка на кредит"]
        [@creditex.includeBootstrap /]
        [@l_data.confirmation_form_validation /]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.security_manager /]

         <div class="data-table">
             <p class="page-link"><a href="[@spring.url '/security_manager/appliances/'/]">Список заявок</a></p>
        [#if application??]
            <p class="page-link"><a href="[@spring.url '/security_manager/client/check/${application.client.id?string("0")}'/]">Проверка клиента по внутренним базам</a></p>
            <p class="page-link"><a href="[@spring.url '/security_manager/client/check/outer/${application.client.id?string("0")}'/]">Проверка клиента по внешним базам</a></p>

            [@l_data.application_view_table application/]

            <p class="page-link"><a href="[@spring.url '/security_manager/appliance/cancel_assignment/${application.id?string("0")}'/]">Отказаться от рассмотрения заявки</a></p>

            [@l_data.confirmation_form
            "post"
            '/security_manager/appliance/confirm/${application.id?string("0")}'
            /]

            [#if (application.product)??]
                [@l_data.product_view_table application.product /]
            [/#if]

        [/#if]

         </div>
    </div>
    [/@creditex.body]
[/@creditex.root]