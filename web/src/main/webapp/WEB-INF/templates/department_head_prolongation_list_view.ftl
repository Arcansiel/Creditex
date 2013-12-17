[#ftl]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Заявки на пролонгацию"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.department_head /]
        [@creditex.goback/]
        <div class="form-action">
            <p class="name"><a href="[@spring.url '/department_head/'/]">На главную страницу</a></p>
            <p class="name">Заявки на пролонгацию, нуждающиеся в проверке</p>
            <table>
                <tr>
                    <th class="name">ID заявки</th>
                    <th class="name">ФИО клиента</th>
                    <th class="passport">Серия и номер паспорта</th>
                    <th class="start_date">Поступление заявки</th>
                    <th class="duration">Запрашиваемая длительность пролонгации</th>
                    <th class="comment">Комментарий клиента</th>
                    <th class="submit-button">Подробнее</th>
                </tr>

                [#if prolongations??][#list prolongations as app]
                    <tr>
                        <td class="name">${app.id?string("0")}</td>
                        <td class="name">${app.client.userData.last?html} ${app.client.userData.first?html} ${app.client.userData.patronymic?html}</td>
                        <td class="passport">${app.client.userData.passportSeries?html} ${app.client.userData.passportNumber}</td>
                        <td class="start_date">${app.applicationDate}</td>
                        <td class="duration">${app.duration}</td>
                        <td class="comment">${app.comment?html}</td>
                        <td><a href="[@spring.url '/department_head/prolongation/${app.id?string("0")}'/]">Открыть заявку</a></td>
                    </tr>
                [/#list][/#if]
            </table>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]