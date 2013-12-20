[#ftl]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Открытые кредиты"]
        [@creditex.tableProcess "listtable" "list" 10 /]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.department_head /]
        [@creditex.goback /]
        <div class="data-table">
            <p class="name"><a href="[@spring.url '/department_head/'/]">На главную страницу</a></p>
            <p><a href="[@spring.url '/department_head/credits/returned/list/'/]">Погашеные кредиты</a></p>
            <p class="name">Открытые кредиты</p>
            <div class="holder"></div>
            <table id="listtable">
                <thead>
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
                    <th class="submit-button">Клиент</th>
                </tr>
                </thead>
                <tbody id="list">
                [#if credits??][#list credits as c]
                    <tr>
                        <td class="name">${c.id}</td>
                        <td class="name">${c.product.name?html}</td>
                        <td class="name">${c.user.userData.last?html} ${c.user.userData.first?html} ${c.user.userData.patronymic?html}</td>
                        <td class="passport">${c.user.userData.passportSeries?html} ${c.user.userData.passportNumber?string("0000000")}</td>
                        <td class="start_date">${c.creditStart}</td>
                        <td class="start_date">${c.creditEnd}</td>
                        <td class="amount">${c.originalMainDebt}</td>
                        <td class="duration">${c.duration}</td>
                        <td class="amount">${c.currentMainDebt}</td>
                        <td class="amount">${c.currentPercentDebt}</td>
                        <td class="amount">${c.mainFine}</td>
                        <td class="amount">${c.percentFine}</td>
                        <td class="submit-button"><a href="[@spring.url '/department_head/client/${c.user.id?string("0")}'/]">Клиент</a></td>
                    </tr>
                [/#list][/#if]
                </tbody>
            </table>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]