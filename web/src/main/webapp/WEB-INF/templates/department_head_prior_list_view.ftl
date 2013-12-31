[#ftl]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Заявки на досрочное погашение кредита"]
        [@creditex.tableProcess "listtable" "list" 10 /]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.department_head /]

        <div class="data-table">

            <p class="name">Заявки на досрочное погашение, нуждающиеся в проверке</p>
            <div class="holder"></div>
            <table id="listtable">
                <thead>
                <tr>
                    <th class="name">ID заявки</th>
                    <th class="name">ФИО клиента</th>
                    <th class="passport">Серия и номер паспорта</th>
                    <th class="start_date">Поступление заявки</th>
                    <th class="comment">Комментарий клиента</th>
                    <th class="submit-button">Подробнее</th>
                </tr>
                </thead>
                <tbody id="list">
                [#if priors??][#list priors as app]
                    <tr>
                        <td class="name">${app.id?string("0")}</td>
                        <td class="name">${app.client.userData.last?html} ${app.client.userData.first?html} ${app.client.userData.patronymic?html}</td>
                        <td class="passport">${app.client.userData.passportSeries?html} ${app.client.userData.passportNumber?string("0000000")}</td>
                        <td class="start_date">${app.applicationDate}</td>
                        <td class="comment">${app.comment?html}</td>
                        <td><a href="[@spring.url '/department_head/prior/${app.id?string("0")}'/]">Открыть заявку</a></td>
                    </tr>
                [/#list][/#if]
                </tbody>
            </table>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]