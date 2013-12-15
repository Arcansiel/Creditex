[#ftl]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Клиент банка"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        <div class="form-action">
            <p class="name"><a href=[@spring.url "/department_head/"/]>Назад на главную страницу</a></p>
            <p class="name"><a href=[@spring.url "/department_head/clients/list/"/]>Назад к списку клиентов</a></p>
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

                [#if client??]
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
                [/#if]
            </table>

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

        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]