[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Служба безопасности / просроченные кредиты"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        <div class="form-action">
            <p class="name">Кредиты с просроченными платежами</p>
            <table>
                <tr>
                    <th class="name">ID кредита</th>
                    <th class="name">Кредитный продукт</th>
                    <th class="name">ФИО клиента</th>
                    <th class="passport">Серия и номер паспорта</th>
                    <th class="start_date">Начало кредитования</th>
                    <th class="amount">Сумма кредита</th>
                    <th class="duration">Длительность кредитования</th>
                    <th class="amount">Основной долг</th>
                </tr>

                [#list credits as c]
                    <tr>
                        <form name="" method="post" action="">
                            <td class="name">${c.id}</td>
                            <td class="name">${c.product.name?html}</td>
                            <td class="name">${c.user.userData.last?html} ${c.user.userData.first?html} ${c.user.userData.patronymic?html}</td>
                            <td class="passport">${c.user.userData.passportSeries?html} ${c.user.userData.passportNumber}</td>
                            <td class="start_date">${c.start}</td>
                            <td class="amount">${c.originalMainDebt}</td>
                            <td class="duration">${c.duration}</td>
                            <td class="amount">${c.currentMainDebt}</td>
                        </form>
                    </tr>
                [/#list]
            </table>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]