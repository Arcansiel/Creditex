[#ftl]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Клиент банка (внешняя проверка)"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.security_manager /]
        [@creditex.goback /]
        <div class="form-action">
        <p class="name"><a href="[@spring.url '/security_manager/'/]">На главную страницу</a></p>
        [#if client??]
            <p class="name"><a href=[@spring.url '/security_manager/client/check/${client.id?string("0")}'/]>Проверка клиента по внутренним базам</a></p>
            <p class="name">Клиент банка (внешняя проверка)</p>
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

            <p class="name">Проверка по внешним базам данных</p>

        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]