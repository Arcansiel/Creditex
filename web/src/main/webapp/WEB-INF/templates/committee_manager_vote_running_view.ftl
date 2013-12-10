[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Кредитный комитет / голосование по заявке"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        <div class="form-action">
            <p class="name"><a href=[@spring.url "/committee_manager/"/]>Назад на главную страницу</a></p>
            <p class="name"><a href=[@spring.url "/committee_manager/appliances/running/"/]>Список заявок для голосования</a></p>
            <p class="name"><a href=[@spring.url "/committee_manager/appliances/finished/"/]>Список заявок, голосование по которым завершено</a></p>
            [#if application??]

                <p class="name">Заявка на кредит (Голосование открыто)</p>
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
                <form method="post" action=[@spring.url '/committee_manager/appliance/vote/${application.id?string("0")}'/]>
                    <table>
                        <td class="comment"><textarea name="comment" ></textarea></td>
                        <td class="action">
                            <p>
                                <label>
                                    <input type="radio" name="acceptance" value="true" />
                                    ЗА</label>
                                <br />
                                <label>
                                    <input type="radio" name="acceptance" value="false" checked/>
                                    ПРОТИВ</label>
                                <br />
                            </p>
                        </td>
                        <td class="submit-button">
                            <button type="submit" class="button">Голосовать</button>
                        </td>
                    </table>
                </form>


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



            [#if credits??]
                <p class="name">Кредиты клиента</p>
                <table>
                    <tr>
                        <th class="name">ID</th>
                        <th class="name">Продукт</th>
                        <th class="start_date">Начало кредитования</th>
                        <th class="duration">Длительность</th>
                        <th class="start_date">Конец кредитования</th>
                        <th class="amount">Сумма кредита</th>
                        <th class="amount">Основной долг</th>
                        <th class="amount">Процентный долг</th>
                        <th class="amount">Долг по платежам</th>
                        <th class="amount">Пеня</th>
                        <th class="name">Активный кредит</th>
                    </tr>
                    [#list credits as credit]
                        <tr>
                            <td class="name">${credit.id}</td>
                            <td class="name">${credit.product.name?html}</td>
                            <td class="start_date">${credit.start}</td>
                            <td class="duration">${credit.duration}</td>
                            <td class="start_date">${credit.endDate}</td>
                            <td class="amount">${credit.originalMainDebt}</td>
                            <td class="amount">${credit.currentMainDebt}</td>
                            <td class="amount">${credit.currentPercentDebt}</td>
                            <td class="amount">${credit.mainFine}</td>
                            <td class="amount">${credit.percentFine}</td>
                            <td class="name">${credit.running?c}</td>
                        </tr>
                    [/#list]
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
                    </tr>
                    [#list priors as prior]
                        <tr>
                            <td class="start_date">${prior.applicationDate}</td>
                            <td class="name">${prior.credit.id}</td>
                            <td class="name">${prior.acceptance?c}</td>
                            <td class="comment">${prior.comment?html}</td>
                        </tr>
                    [/#list]
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
                            <td class="name">${prolongation.acceptance}</td>
                            <td class="comment">${prolongation.comment?html}</td>
                        </tr>
                    [/#list]
                </table>
            [/#if]

        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]