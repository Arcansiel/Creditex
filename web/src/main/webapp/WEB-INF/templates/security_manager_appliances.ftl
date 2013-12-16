[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Служба безопасности / заявки на рассмотрение"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.goback /]
        <div class="form-action">
            <p class="name"><a href="[@spring.url '/security_manager/'/]">На главную страницу</a></p>
        <p class="name">Заявки, нуждающиеся в проверке</p>
        <table>
            <tr>
                <th class="name">ID заявки</th>
                <th class="name">ФИО клиента</th>
                <th class="passport">Серия и номер паспорта</th>
                <th class="start_date">Поступление заявки</th>
                <th class="name">Кредитный продукт</th>
                <th class="amount">Запрашиваемая сумма</th>
                <th class="duration">Длительность кредитования</th>
                <th class="submit-button">Проверка заявки</th>
            </tr>

            [#if applications??][#list applications as app]
            <tr>
                <td class="name">${app.id?string("0")}</td>
                <td class="name">${app.client.userData.last?html} ${app.client.userData.first?html} ${app.client.userData.patronymic?html}</td>
                <td class="passport">${app.client.userData.passportSeries?html} ${app.client.userData.passportNumber}</td>
                <td class="start_date">${app.applicationDate}</td>
                <td class="name">${app.product.name}</td>
                <td class="amount">${app.request}</td>
                <td class="duration">${app.duration}</td>
                <td><a href=[@spring.url '/security_manager/appliance/check/${app.id?string("0")}'/]>Проверка заявки</a></td>
            </tr>
            [/#list][/#if]
         </table>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]