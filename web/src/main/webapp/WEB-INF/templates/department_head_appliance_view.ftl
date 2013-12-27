[#ftl]
[#import "creditex.ftl" as creditex]
[#import "l_data.ftl" as l_data]
[@creditex.root]
    [@creditex.head "Заявка на кредит, результаты голосования"]
        [@creditex.includeBootstrap /]
        [@l_data.confirmation_form_validation /]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.department_head /]
        [@creditex.goback /]
        <div class="data-table">
            <p class="name"><a href="[@spring.url '/department_head/'/]">На главную страницу</a></p>
            <p class="name"><a href="[@spring.url '/department_head/appliances/committee_rejected/'/]">Список заявок, отклонённых комитетом</a></p>
            <p class="name"><a href="[@spring.url '/department_head/appliances/committee_approved/'/]">Список заявок, одобренных комитетом</a></p>
            <p class="name"><a href="[@spring.url '/department_head/appliances/committee_vote/'/]">Список заявок с открытым голосованием</a></p>
            [#if application??]

                <p class="name"><a href="[@spring.url '/department_head/client/${application.client.id?string("0")}'/]">Информация о клиенте</a></p>

                [#if application.committeeAcceptance??]
                    [#if application.committeeAcceptance.name() == "Accepted"]
                        <p class="name">Заявка на кредит, принятая комитетом</p>
                    [#elseif application.committeeAcceptance.name() == "Rejected"]
                        <p class="name">Заявка на кредит, отклонённая комитетом</p>
                    [#elseif application.committeeAcceptance.name() == "InProcess"]
                        <p class="name">Заявка на кредит, голосование комитета открыто</p>
                    [#else]
                        <p class="name">Заявка на кредит</p>
                    [/#if]
                [#else]
                    <p class="name">Заявка на кредит</p>
                [/#if]
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
                        <th class="name">Специалист СБ</th>
                        <th class="comment">Комментарий СБ</th>
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
                        <td class="name">[#if application.security??]${application.security.username?html}[/#if]</td>
                        <td class="comment">[#if application.securityComment??]${application.securityComment?html}[/#if]</td>
                    </tr>
                </table>

                [#if application.committeeAcceptance?? && application.committeeAcceptance.name() == "Accepted"]
                [#-- show form only for committee accepted applications --]
                    [@l_data.confirmation_form
                    "post"
                    '/department_head/appliance/${application.id?string("0")}/set_head_approved/'
                    /]
                [/#if]

                [#if (application.product)??]
                    [@l_data.product_view_table application.product /]
                [/#if]

                [#if votes??]
                    [@l_data.vote_list_table votes /]
                [/#if]

            [/#if]


        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]