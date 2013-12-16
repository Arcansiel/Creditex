[#ftl]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Клиент банка"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        <div class="form-action">
            <p class="name"><a href=[@spring.url "/committee_manager/"/]>Назад на главную страницу</a></p>

            [#if client??]

                [#if application_id??]
                    <p class="name"><a href="[@spring.url '/committee_manager/appliance/${application_id?string("0")}'/]">Назад к заявке на кредит</a></p>
                [/#if]

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

            [/#if]

            <p class="name">Кредиты клиента</p>
            <table>
                <tr>
                    <th class="name">ID кредита</th>
                    <th class="name">Кредитный продукт</th>
                    <th class="start_date">Начало кредитования</th>
                    <th class="start_date">Конец кредитования</th>
                    <th class="amount">Сумма кредита</th>
                    <th class="duration">Длительность кредитования</th>
                    <th class="amount">Основной долг</th>
                    <th class="amount">Процентный долг</th>
                    <th class="amount">Долг по платежам</th>
                    <th class="amount">Пеня</th>
                    <th class="name">Активный кредит</th>
                </tr>

                [#if credits??][#list credits as c]
                    <tr>
                        <td class="name">${c.id}</td>
                        <td class="name">${c.product.name?html}</td>
                        <td class="start_date">${c.creditStart}</td>
                        <td class="start_date">${c.creditEnd}</td>
                        <td class="amount">${c.originalMainDebt}</td>
                        <td class="duration">${c.duration}</td>
                        <td class="amount">${c.currentMainDebt}</td>
                        <td class="amount">${c.currentPercentDebt}</td>
                        <td class="amount">${c.mainFine}</td>
                        <td class="amount">${c.percentFine}</td>
                        <td class="name">${c.running?c}</td>
                    </tr>
                [/#list][/#if]
            </table>

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

            [#if priors??]
                <p class="name">Завки клиента на досрочное погашение</p>
                <table>
                    <tr>
                        <th class="start_date">Дата</th>
                        <th class="name">ID кредита</th>
                        <th class="name">Удовлетворена</th>
                        <th class="comment">Комментарий</th>
                    </tr>
                    [#list priors as prior]
                        <tr>
                            <td class="start_date">${prior.applicationDate}</td>
                            <td class="name">${prior.credit.id}</td>
                            <td class="name">[#if (prior.acceptance)??]${prior.acceptance?html}[/#if]</td>
                            <td class="comment">${prior.comment?html}</td>
                        </tr>
                    [/#list]
                </table>
            [/#if]
            [#if prolongations??]
                <p class="name">Завки клиента на пролонгацию</p>
                <table>
                    <tr>
                        <th class="start_date">Дата</th>
                        <th class="name">ID кредита</th>
                        <th class="duration">Длительность пролонгации</th>
                        <th class="name">Удовлетворена</th>
                        <th class="comment">Комментарий</th>
                    </tr>
                    [#list prolongations as prolongation]
                        <tr>
                            <td class="start_date">${prolongation.applicationDate}</td>
                            <td class="name">${prolongation.credit.id}</td>
                            <td class="duration">${prolongation.duration}</td>
                            <td class="name">[#if (prolongation.acceptance)??]${prolongation.acceptance?html}[/#if]</td>
                            <td class="comment">${prolongation.comment?html}</td>
                        </tr>
                    [/#list]
                </table>
            [/#if]

        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]