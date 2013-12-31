[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]
[#import "l_data.ftl" as l_data]
[@creditex.root]
    [@creditex.head "Кредитный комитет / заявка, голосование закрыто"]
    [@creditex.includeBootstrap /]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.committee_manager /]

        <div class="data-table">

            <p class="page-link"><a href="[@spring.url '/committee_manager/appliances/running/'/]">Список заявок для голосования</a></p>
            <p class="page-link"><a href="[@spring.url '/committee_manager/appliances/finished/'/]">Список заявок, голосование по которым завершено</a></p>
            [#if application??]

                <p class="page-link"><a href="[@spring.url '/committee_manager/client/${application.client.id?string("0")}'/]">Проверить клиента</a></p>

                <p class="name">Заявка на кредит (Голосование завершено)</p>
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
                    </tr>

                        <tr>
                            <td class="name">${application.id?string("0")}</td>
                            <td class="name">${application.client.userData.last?html} ${application.client.userData.first?html} ${application.client.userData.patronymic?html}</td>
                            <td class="passport">${application.client.userData.passportSeries?html} ${application.client.userData.passportNumber?string("0000000")}</td>
                            <td class="start_date">${application.applicationDate}</td>
                            <td class="name">${application.product.name?html}</td>
                            <td class="amount">${application.request}</td>
                            <td class="duration">${application.duration}</td>
                            <td class="amount">${application.voteAcceptance?string("0")} / ${application.voteRejection?string("0")}</td>
                            <td class="name">[#if application.committeeAcceptance??]${application.committeeAcceptance?html}[/#if]</td>
                            <td class="name">[#if application.headAcceptance??]${application.headAcceptance?html}[/#if]</td>
                            <td class="comment">[#if application.headComment??]${application.headComment?html}[/#if]</td>
                        </tr>
                </table>

                [#if (application.product)??]
                    [@l_data.product_view_table application.product /]
                [/#if]

            [/#if]

            [#if votes??]
                [@l_data.vote_list_table votes /]
            [/#if]

        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]