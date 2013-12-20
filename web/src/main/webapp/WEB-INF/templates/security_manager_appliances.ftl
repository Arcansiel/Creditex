[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]
[#import "creditex_data.ftl" as creditex_data]
[@creditex.root]
    [@creditex.head "Служба безопасности / заявки на рассмотрение"]
        [@creditex.tableProcess "listtable" "list" 10 /]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.security_manager /]
        [@creditex.goback /]
        <div class="data-table">
            <p class="name"><a href="[@spring.url '/security_manager/'/]">На главную страницу</a></p>

        [#if current??]
            [@creditex_data.application_view_table current "Текущая заявка на кредит"/]
            <p class="name"><a href="[@spring.url '/security_manager/appliance/check/${current.id?string("0")}'/]">Проверка текущей заявки</a></p>
        [/#if]

        <p class="name">Заявки, нуждающиеся в проверке</p>
            <div class="holder"></div>
            <table id="listtable">
            <thead>
            <tr>
                <th class="name">ID заявки</th>
                <th class="name">ФИО клиента</th>
                <th class="passport">Серия и номер паспорта</th>
                <th class="start_date">Поступление заявки</th>
                <th class="name">Кредитный продукт</th>
                <th class="amount">Запрашиваемая сумма</th>
                <th class="duration">Длительность кредитования</th>
                [#if !current??]
                <th class="submit-button">Принять заявку для рассмотрения</th>
                [/#if]
            </tr>
            </thead>
            <tbody id="list">
            [#if applications??][#list applications as app]
            <tr>
                <td class="name">${app.id?string("0")}</td>
                <td class="name">${app.client.userData.last?html} ${app.client.userData.first?html} ${app.client.userData.patronymic?html}</td>
                <td class="passport">${app.client.userData.passportSeries?html} ${app.client.userData.passportNumber?string("0000000")}</td>
                <td class="start_date">${app.applicationDate}</td>
                <td class="name">${app.product.name}</td>
                <td class="amount">${app.request}</td>
                <td class="duration">${app.duration}</td>
                [#if !current??]
                <td><a href="[@spring.url '/security_manager/appliance/check/${app.id?string("0")}'/]">Проверка заявки</a></td>
                [/#if]
            </tr>
            [/#list][/#if]
            </tbody>
         </table>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]