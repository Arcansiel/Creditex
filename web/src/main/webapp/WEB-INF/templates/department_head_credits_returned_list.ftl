[#ftl]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Погашеные кредиты"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.department_head /]
        [@creditex.goback /]
        <div class="form-action">
            <p class="name"><a href="[@spring.url '/department_head/'/]">На главную страницу</a></p>
            <p><a href="[@spring.url '/department_head/credits/active/list/'/]">Открытые кредиты</a></p>
            <p class="name">Погашеные кредиты</p>
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
                    <th class="amount">Процентный долг</th>
                    <th class="submit-button">Клиент</th>
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
                        <td class="amount">${c.currentPercentDebt}</td>
                        <td class="submit-button"><a href="[@spring.url '/department_head/client/${c.user.id?string("0")}'/]">Клиент</a></td>
                    </tr>
                [/#list][/#if]
            </table>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]