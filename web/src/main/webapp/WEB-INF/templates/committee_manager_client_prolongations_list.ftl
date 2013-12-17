[#ftl]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Заявки клиента на пролонгацию клиента"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.committee_manager /]
        [@creditex.goback /]
        <div class="form-action">
            <p class="name"><a href="[@spring.url '/committee_manager/'/]">На главную страницу</a></p>
            [#if client??]
                <p class="name">Клиент банка</p>
                <table>
                    <tr>
                        <th class="name">ID клиента</th>
                        <th class="name">ФИО клиента</th>
                        <th class="passport">Серия и номер паспорта</th>
                        <th class="name">username</th>
                        <th class="name">Место работы</th>
                        <th class="name">Занимаемая должность</th>
                        <th class="name">Доход</th>
                        <th class="name">Активен</th>
                    </tr>
                    <tr>
                        <td class="name">${client.id}</td>
                        <td class="name">${client.userData.last?html} ${client.userData.first?html} ${client.userData.patronymic?html}</td>
                        <td class="passport">${client.userData.passportSeries?html} ${client.userData.passportNumber}</td>
                        <td class="name">${client.username?html}</td>
                        <td class="name">${client.userData.workName?html}</td>
                        <td class="name">${client.userData.workPosition?html}</td>
                        <td class="name">${client.userData.workIncome?html}</td>
                        <td class="name">${client.enabled?c}</td>
                    </tr>
                </table>
            [/#if]

            [#if prolongations??]
                <p class="name">Завки клиента на пролонгацию</p>
                <table>
                    <tr>
                        <th class="start_date">Дата</th>
                        <th class="name">ID кредита</th>
                        <th class="duration">Длительность пролонгации</th>
                        <th class="name">Удовлетворена</th>
                        <th class="comment">Комментарий</th>
                    </tr>
                    [#list prolongations as prolongation]
                        <tr>
                            <td class="start_date">${prolongation.applicationDate}</td>
                            <td class="name">${prolongation.credit.id}</td>
                            <td class="duration">${prolongation.duration}</td>
                            <td class="name">[#if (prolongation.acceptance)??]${prolongation.acceptance?html}[/#if]</td>
                            <td class="comment">${prolongation.comment?html}</td>
                        </tr>
                    [/#list]
                </table>
            [/#if]

        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]