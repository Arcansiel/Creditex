[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Служба безопасности / невозвращённые кредиты"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.security_manager /]
        [@creditex.goback /]
        <div class="form-action">
            <p class="name"><a href="[@spring.url '/security_manager/'/]">На главную страницу</a></p>

            <p class="name">Невозвращённые кредиты</p>
            <table>
                <tr>
                    <th class="name">ID кредита</th>
                    <th class="name">Кредитный продукт</th>
                    <th class="name">ФИО клиента</th>
                    <th class="passport">Серия и номер паспорта</th>
                    <th class="start_date">Начало кредитования</th>
                    <th class="start_date">Конец кредитования</th>
                    <th class="amount">Сумма кредита</th>
                    <th class="duration">Длительность кредитования</th>
                    <th class="amount">Основной долг</th>
                    <th class="amount">Процентный долг</th>
                    <th class="amount">Долг по платежам</th>
                    <th class="amount">Пеня</th>
                    <th class="name">Активный кредит</th>
                    <th class="name" colspan="2">Проверка клиента</th>
                </tr>

                [#if credits??][#list credits as c]
                    <tr>
                        <td class="name">${c.id}</td>
                        <td class="name">${c.product.name?html}</td>
                        <td class="name">${c.user.userData.last?html} ${c.user.userData.first?html} ${c.user.userData.patronymic?html}</td>
                        <td class="passport">${c.user.userData.passportSeries?html} ${c.user.userData.passportNumber}</td>
                        <td class="start_date">${c.creditStart}</td>
                        <td class="start_date">${c.creditEnd}</td>
                        <td class="amount">${c.originalMainDebt}</td>
                        <td class="duration">${c.duration}</td>
                        <td class="amount">${c.currentMainDebt}</td>
                        <td class="amount">${c.currentPercentDebt}</td>
                        <td class="amount">${c.mainFine}</td>
                        <td class="amount">${c.percentFine}</td>
                        <td class="name">${c.running?c}</td>
                        <td class="name"><a href=[@spring.url '/security_manager/client/check/${c.user.id?string("0")}'/]>Внутренняя</a></td>
                        <td class="name"><a href=[@spring.url '/security_manager/client/check/outer/${c.user.id?string("0")}'/]>Внешняя</a></td>
                    </tr>
                [/#list][/#if]
            </table>

        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]