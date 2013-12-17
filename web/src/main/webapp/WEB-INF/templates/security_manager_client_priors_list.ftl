[#ftl]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Заявки клиента на пролонгацию клиента"]
        [@creditex.tableProcess "listtable" "list" 10 /]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.security_manager /]
        [@creditex.goback /]
        <div class="data-table">
            <p class="name"><a href="[@spring.url '/security_manager/'/]">На главную страницу</a></p>
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


                <p class="name">Завки клиента на досрочное погашение</p>
            <div class="holder"></div>
            <table id="listtable">
                    <thead>
                    <tr>
                        <th class="start_date">Дата</th>
                        <th class="name">ID кредита</th>
                        <th class="name">Удовлетворена</th>
                        <th class="comment">Комментарий</th>
                    </tr>
                    </thead>
                    <tbody id="list">
                        [#if priors??][#list priors as prior]
                        <tr>
                            <td class="start_date">${prior.applicationDate}</td>
                            <td class="name">${prior.credit.id}</td>
                            <td class="name">[#if (prior.acceptance)??]${prior.acceptance?html}[/#if]</td>
                            <td class="comment">${prior.comment?html}</td>
                        </tr>
                        [/#list][/#if]
                    </tbody>
                </table>


        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]