[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Служба безопасности / просроченные кредиты"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        <div class="form-action">
            <p class="name"><a href=[@spring.url "/security_manager/"/]>Назад на главную страницу</a></p>
            <p class="name">Кредиты с просроченными платежами</p>
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
                    <th class="amount">Проценты</th>
                    <th class="amount">Долг по платежам</th>
                    <th class="amount">Пеня</th>
                    <th class="name">Активный кредит</th>
                </tr>

                [#list credits as c]
                    <tr>
                        <td class="name">${c.id}</td>
                        <td class="name">${c.product.name?html}</td>
                        <td class="name">${c.user.userData.last?html} ${c.user.userData.first?html} ${c.user.userData.patronymic?html}</td>
                        <td class="passport">${c.user.userData.passportSeries?html} ${c.user.userData.passportNumber}</td>
                        <td class="start_date">${c.start}</td>
                        <td class="start_date">${c.endDate}</td>
                        <td class="amount">${c.originalMainDebt}</td>
                        <td class="duration">${c.duration}</td>
                        <td class="amount">${c.currentMainDebt}</td>
                        <th class="amount">${c.currentPercentDebt}</th>
                        <th class="amount">${c.mainFine}</th>
                        <th class="amount">${c.percentFine}</th>
                        <th class="name">${c.running?c}</th>
                    </tr>
                [/#list]
            </table>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]