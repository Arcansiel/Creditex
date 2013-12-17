[#ftl]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Клиент банка (внутренняя проверка)"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.security_manager /]
        [@creditex.goback /]
        <div class="form-action">
            <p class="name"><a href="[@spring.url '/security_manager/'/]">На главную страницу</a></p>
            [#if client??]
                <p class="name"><a href="[@spring.url '/security_manager/client/check/outer/${client.id?string("0")}'/]">Проверка клиента по внешним базам</a></p>
                <p class="name">Клиент банка (внутренняя проверка)</p>
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
            [/#if]

            <p class="name">Проверка по внутренним базам данных</p>

            <p class="name"><a href="[@spring.url '/security_manager/client/${client.id?string("0")}/credits/all/'/]">Все кредиты клиента</a></p>
            <p class="name"><a href="[@spring.url '/security_manager/client/${client.id?string("0")}/credits/expired/'/]">Просроченные кредиты клиента</a></p>
            <p class="name"><a href="[@spring.url '/security_manager/client/${client.id?string("0")}/prolongations/'/]">Заявки на пролонгацию</a></p>
            <p class="name"><a href="[@spring.url '/security_manager/client/${client.id?string("0")}/priors/'/]">Заявки на досрочное погашение</a></p>


            [#if credit??]
                <p class="name">Текущий кредит клиента</p>
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
                </table>
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
                        <th class="name">Активный кредит</th>
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
                            <td class="amount">${credit.running?c}</td>
                        </tr>
                    [/#list]
                </table>
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