[#ftl]
[#import "creditex.ftl" as creditex]
[#import "l_data.ftl" as l_data]
[@creditex.root]
    [@creditex.head "Клиент банка (внутренняя проверка)"]
        [@creditex.includeBootstrap /]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.security_manager /]

        <div class="data-table">

            [#if client??]
                <p class="page-link"><a href="[@spring.url '/security_manager/client/check/outer/${client.id?string("0")}'/]">Проверка клиента по внешним базам</a></p>

            [@l_data.client_view_table client "Клиент банка (внутренняя проверка)"/]

            <p class="name">Проверка по внутренним базам данных</p>

            <p class="page-link"><a href="[@spring.url '/security_manager/client/${client.id?string("0")}/credits/all/'/]">Все кредиты клиента</a></p>
            <p class="page-link"><a href="[@spring.url '/security_manager/client/${client.id?string("0")}/credits/expired/'/]">Просроченные кредиты клиента</a></p>
            <p class="page-link"><a href="[@spring.url '/security_manager/client/${client.id?string("0")}/prolongations/'/]">Заявки на пролонгацию</a></p>
            <p class="page-link"><a href="[@spring.url '/security_manager/client/${client.id?string("0")}/priors/'/]">Заявки на досрочное погашение</a></p>
            <p class="page-link"><a href="[@spring.url '/security_manager/client/${client.id?string("0")}/statistics/'/]">Статистика по клиенту</a></p>

            [/#if]

            [#if credit??]
                [@l_data.credit_view_table credit "Текущий кредит клиента" false /]
                [#if (credit.product)??]
                    [@l_data.product_view_table credit.product /]
                [/#if]
            [#else]
                <p class="name">Текущих кредитов нет</p>
            [/#if]

            [#if unreturned??]
                <p class="name">Невозвращённые кредиты клиента</p>
                <table>
                    <tr>
                        <th class="name">ID</th>
                        <th class="name">Продукт</th>
                        <th class="start_date">Начало кредитования</th>
                        <th class="duration">Длительность</th>
                        <th class="start_date">Конец кредитования</th>
                        <th class="amount">Сумма кредита</th>
                        <th class="amount">Основной долг</th>
                        <th class="amount">Процентный долг</th>
                        <th class="amount">Долг по платежам</th>
                        <th class="amount">Пеня</th>
                    </tr>
                    [#list unreturned as credit]
                        <tr>
                            <td class="name">${credit.id}</td>
                            <td class="name">${credit.product.name?html}</td>
                            <td class="start_date">${credit.creditStart}</td>
                            <td class="duration">${credit.duration}</td>
                            <td class="start_date">${credit.creditEnd}</td>
                            <td class="amount">${credit.originalMainDebt}</td>
                            <td class="amount">${credit.currentMainDebt}</td>
                            <td class="amount">${credit.currentPercentDebt}</td>
                            <td class="amount">${credit.mainFine}</td>
                            <td class="amount">${credit.percentFine}</td>
                        </tr>
                    [/#list]
                </table>
            [/#if]

        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]