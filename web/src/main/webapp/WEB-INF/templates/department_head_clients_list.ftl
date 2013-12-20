[#ftl]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Клиенты банка"]
        [@creditex.tableProcess "listtable" "list" 10 /]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.department_head /]
        [@creditex.goback/]
        <div class="data-table">
            <p class="name"><a href="[@spring.url '/department_head/'/]">На главную страницу</a></p>
            <p class="name">Клиенты банка</p>
            <div class="holder"></div>
            <table id="listtable">
                <thead>
                <tr>
                    <th class="name">ID клиента</th>
                    <th class="name">ФИО клиента</th>
                    <th class="passport">Серия и номер паспорта</th>
                    <th class="name">username</th>
                    <th class="name">Активен</th>
                    <th class="name">Место работы</th>
                    <th class="name">Занимаемая должность</th>
                    <th class="name">Доход</th>
                    <th class="submit-button">Клиент</th>
                </tr>
                </thead>
                <tbody id="list">
                [#if clients??][#list clients as c]
                    <tr>
                        <td class="name">${c.id}</td>
                        <td class="name">${c.userData.last?html} ${c.userData.first?html} ${c.userData.patronymic?html}</td>
                        <td class="passport">${c.userData.passportSeries?html} ${c.userData.passportNumber?string("0000000")}</td>
                        <td class="name">${c.username?html}</td>
                        <td class="name">${c.enabled?c}</td>
                        <td class="name">${c.userData.workName?html}</td>
                        <td class="name">${c.userData.workPosition?html}</td>
                        <td class="name">${c.userData.workIncome?html}</td>
                        <td class="submit-button"><a href="[@spring.url '/department_head/client/${c.id?string("0")}'/]">Клиент</a></td>
                    </tr>
                [/#list][/#if]
                </tbody>
            </table>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]