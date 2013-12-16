[#ftl]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Заявки клиента на пролонгацию клиента"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.goback /]
        <div class="form-action">
            <p class="name"><a href="[@spring.url '/department_head/'/]">На главную страницу</a></p>
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

            [#if priors??]
                <p class="name">Завки клиента на досрочное погашение</p>
                <table>
                    <tr>
                        <th class="start_date">Дата</th>
                        <th class="name">ID кредита</th>
                        <th class="name">Удовлетворена</th>
                        <th class="comment">Комментарий</th>
                        <th class="name">Проверить</th>
                    </tr>
                    [#list priors as prior]
                        <tr>
                            <td class="start_date">${prior.applicationDate}</td>
                            <td class="name">${prior.credit.id}</td>
                            <td class="name">[#if (prior.acceptance)??]${prior.acceptance?html}[/#if]</td>
                            <td class="comment">${prior.comment?html}</td>
                            <td class="name">
                                [#if prior.acceptance.name()=="InProcess"]
                                    <a href="[@spring.url '/department_head/prior/${prior.id?string("0")}'/]">Проверить</a>
                                [/#if]
                            </td>
                        </tr>
                    [/#list]
                </table>
            [/#if]

        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]