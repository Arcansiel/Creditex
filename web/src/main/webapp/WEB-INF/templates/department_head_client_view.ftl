[#ftl]
[#import "creditex.ftl" as creditex]
[#import "creditex_data.ftl" as creditex_data]
[@creditex.root]
    [@creditex.head "Клиент банка"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.department_head /]
        [@creditex.goback /]
        <div class="data-table">
            <p class="name"><a href="[@spring.url '/department_head/'/]">На главную страницу</a></p>

        [#if client??]
            [@creditex_data.client_view_table client /]

            <p class="name"><a href="[@spring.url '/department_head/client/${client.id?string("0")}/credits/all/'/]">Все кредиты клиента</a></p>
            <p class="name"><a href="[@spring.url '/department_head/client/${client.id?string("0")}/credits/expired/'/]">Просроченные кредиты клиента</a></p>
            <p class="name"><a href="[@spring.url '/department_head/client/${client.id?string("0")}/prolongations/'/]">Заявки на пролонгацию</a></p>
            <p class="name"><a href="[@spring.url '/department_head/client/${client.id?string("0")}/priors/'/]">Заявки на досрочное погашение</a></p>

        [/#if]

            [#if payments_count?? && expired_payments_count??]
                <p class="name">Просроченные платежи клиента</p>
                <table>
                    <tr>
                        <th class="amount">Количество просроченных платежей</th>
                        <th class="amount">Общее количество платежей</th>
                    </tr>
                    <tr>
                        <td class="amount">${expired_payments_count}</td>
                        <td class="amount">${payments_count}</td>
                    </tr>
                </table>
            [/#if]

        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]