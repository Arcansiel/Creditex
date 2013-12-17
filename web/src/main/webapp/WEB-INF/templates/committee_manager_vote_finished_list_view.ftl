[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Кредитный комитет / Заявки, голосование по которым окончено"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.committee_manager /]
        [@creditex.goback /]
        <div class="form-action">
            <p class="name"><a href="[@spring.url '/committee_manager/'/]">На главную страницу</a></p>
            <p class="name"><a href="[@spring.url '/committee_manager/appliances/running/'/]">Список заявок для голосования</a></p>
            <p class="name">Заявки, голосование по которым окончено</p>
            <table>
                <tr>
                    <th class="name">ID заявки</th>
                    <th class="name">ФИО клиента</th>
                    <th class="passport">Серия и номер паспорта</th>
                    <th class="start_date">Поступление заявки</th>
                    <th class="name">Кредитный продукт</th>
                    <th class="amount">Запрашиваемая сумма</th>
                    <th class="duration">Длительность кредитования</th>
                    <th class="amount">Голоса ЗА/ПРОТИВ</th>
                    <th class="name">Одобрена комитетом</th>
                    <th class="name">Одобрена ГКО</th>
                    <th class="comment">Комментарий ГКО</th>
                    <th class="submit-button">Подробнее</th>
                </tr>

                [#if applications??][#list applications as app]
                    <tr>
                        <td class="name">${app.id?string("0")}</td>
                        <td class="name">${app.client.userData.last?html} ${app.client.userData.first?html} ${app.client.userData.patronymic?html}</td>
                        <td class="passport">${app.client.userData.passportSeries?html} ${app.client.userData.passportNumber}</td>
                        <td class="start_date">${app.applicationDate}</td>
                        <td class="name">${app.product.name?html}</td>
                        <td class="amount">${app.request}</td>
                        <td class="duration">${app.duration}</td>
                        <td class="amount">${app.voteAcceptance?string("0")} / ${app.voteRejection?string("0")}</td>
                        <td class="name">[#if app.committeeAcceptance??]${app.committeeAcceptance?html}[/#if]</td>
                        <td class="name">[#if app.headAcceptance??]${app.headAcceptance?html}[/#if]</td>
                        <td class="comment">[#if app.headComment??]${app.headComment?html}[/#if]</td>
                        <td><a href="[@spring.url '/committee_manager/appliance/${app.id?string("0")}'/]">Открыть заявку</a></td>
                    </tr>
                [/#list][/#if]
            </table>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]