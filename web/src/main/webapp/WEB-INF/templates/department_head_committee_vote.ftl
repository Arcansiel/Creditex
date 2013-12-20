[#ftl]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Рассматриваемые комитетом заявки на кредит"]
        [@creditex.tableProcess "listtable" "list" 10 /]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.department_head /]
        [@creditex.goback/]
        <div class="data-table">
            <p class="name"><a href="[@spring.url '/department_head/'/]">На главную страницу</a></p>
            <p class="name"><a href="[@spring.url '/department_head/appliances/committee_rejected/'/]">Список заявок, отклонённых комитетом</a></p>
            <p class="name"><a href="[@spring.url '/department_head/appliances/committee_approved/'/]">Список заявок, одобренных комитетом</a></p>
            <p class="name">Заявки с открытым голосованием</p>
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
                    <th class="amount">Голоса ЗА/ПРОТИВ</th>
                    <th class="name">Специалист СБ</th>
                    <th class="comment">Комментарий СБ</th>
                    <th class="submit-button">Подробнее</th>
                </tr>
                </thead>
                <tbody id="list">
                [#if applications??][#list applications as app]
                    <tr>
                        <td class="name">${app.id?string("0")}</td>
                        <td class="name">${app.client.userData.last?html} ${app.client.userData.first?html} ${app.client.userData.patronymic?html}</td>
                        <td class="passport">${app.client.userData.passportSeries?html} ${app.client.userData.passportNumber?string("0000000")}</td>
                        <td class="start_date">${app.applicationDate}</td>
                        <td class="name">${app.product.name?html}</td>
                        <td class="amount">${app.request}</td>
                        <td class="duration">${app.duration}</td>
                        <td class="amount">${app.voteAcceptance?string("0")} / ${app.voteRejection?string("0")}</td>
                        <td class="name">[#if app.security??]${app.security.username?html}[/#if]</td>
                        <td class="comment">[#if app.securityComment??]${app.securityComment?html}[/#if]</td>
                        <td><a href="[@spring.url '/department_head/appliance/${app.id?string("0")}'/]">Открыть заявку</a></td>
                    </tr>
                [/#list][/#if]
                </tbody>
            </table>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]