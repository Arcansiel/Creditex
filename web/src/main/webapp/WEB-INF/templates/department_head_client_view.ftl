[#ftl]
[#import "creditex.ftl" as creditex]

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

                <p class="name">Клиент банка</p>
                <table>
                    <tr>
                        <th class="name">ID клиента</th>
                        <th class="name">ФИО клиента</th>
                        <th class="passport">Серия и номер паспорта</th>
                        <th class="name">username</th>
                        <th class="name">Место работы</th>
                        <th class="name">Занимаемая должность</th>
                        <th class="name">Доход</th>
                        <th class="name">Активен</th>
                    </tr>
                    <tr>
                        <td class="name">${client.id}</td>
                        <td class="name">${client.userData.last?html} ${client.userData.first?html} ${client.userData.patronymic?html}</td>
                        <td class="passport">${client.userData.passportSeries?html} ${client.userData.passportNumber}</td>
                        <td class="name">${client.username?html}</td>
                        <td class="name">${client.userData.workName?html}</td>
                        <td class="name">${client.userData.workPosition?html}</td>
                        <td class="name">${client.userData.workIncome?html}</td>
                        <td class="name">${client.enabled?c}</td>
                    </tr>
                </table>

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