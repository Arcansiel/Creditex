[#ftl]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Заявка на кредит, результаты голосования"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        <div class="form-action">
            <p class="name"><a href=[@spring.url "/department_head/"/]>Назад на главную страницу</a></p>
            <p class="name"><a href=[@spring.url "/department_head/appliances/committee_rejected/"/]>Список заявок, отклонённых комитетом</a></p>
            <p class="name"><a href=[@spring.url "/department_head/appliances/committee_approved/"/]>Список заявок, одобренных комитетом</a></p>
            <p class="name"><a href=[@spring.url "/department_head/appliances/committee_vote/"/]>Список заявок с открытым голосованием</a></p>
            [#if application??]
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
                        <td class="passport">${application.client.userData.passportSeries?html} ${application.client.userData.passportNumber}</td>
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
                <form method="post" action=[@spring.url '/department_head/appliance/${application.id?string("0")}/set_head_approved/'/]>
                    <table>
                        <td class="comment"><textarea name="comment" ></textarea></td>
                        <td class="action">
                            <p>
                                <label>
                                    <input type="radio" name="acceptance" value="true" />
                                    ПРИНЯТЬ</label>
                                <br />
                                <label>
                                    <input type="radio" name="acceptance" value="false" checked/>
                                    ОТКЛОНИТЬ</label>
                                <br />
                            </p>
                        </td>
                        <td class="submit-button">
                            <button type="submit" class="button">Выполнить</button>
                        </td>
                    </table>
                </form>
                [/#if]

                [#if votes??]
                    <p class="name">Голоса членов комитета</p>
                    <table>
                        <tr>
                            <th class="name">Член комитета</th>
                            <th class="name">Принятие</th>
                            <th class="comment">Комментарий</th>
                        </tr>
                        [#list votes as vote]
                            <tr>
                                <td class="name">${vote.manager.username?html}</td>
                                <td class="name">${vote.acceptance?c}</td>
                                <td class="comment">${vote.comment?html}</td>
                            </tr>
                        [/#list]
                    </table>
                [/#if]

            [/#if]


        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]