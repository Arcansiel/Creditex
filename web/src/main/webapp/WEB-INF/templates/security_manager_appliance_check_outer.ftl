[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Служба безопасности / отчёты по заявке"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        <div class="form-action">
            <p class="name"><a href=[@spring.url "/security_manager/"/]>Назад на главную страницу</a></p>
            <p class="name"><a href=[@spring.url "/security_manager/appliances/"/]>Назад к списку заявок</a></p>
            [#if application??]
                <p class="name"><a href=[@spring.url '/security_manager/appliance/check/${application.id?string("0")}'/]>Проверка по внутренним базам</a></p>

                <p class="name">Заявка на кредит</p>
                <table>
                    <tr>
                        <th class="name">ID заявки</th>
                        <th class="name">ФИО клиента</th>
                        <th class="passport">Серия и номер паспорта</th>
                        <th class="start_date">Поступление заявки</th>
                        <th class="name">Кредитный продукт</th>
                        <th class="amount">Запрашиваемая сумма</th>
                        <th class="duration">Длительность кредитования</th>
                    </tr>
                    <tr>
                        <td class="name">${application.id?string("0")}</td>
                        <td class="name">${application.client.userData.last?html} ${application.client.userData.first?html} ${application.client.userData.patronymic?html}</td>
                        <td class="passport">${application.client.userData.passportSeries?html} ${application.client.userData.passportNumber}</td>
                        <td class="start_date">${application.applicationDate}</td>
                        <td class="name">${application.product.name}</td>
                        <td class="amount">${application.request}</td>
                        <td class="duration">${application.duration}</td>
                    </tr>
                </table>
                <form method="post" action=[@spring.url '/security_manager/appliance/confirm/${application.id?string("0")}'/]>
                    <table>
                        <td class="comment"><textarea name="comment" ></textarea></td>
                        <td class="action">
                            <p>
                                <label>
                                    <input type="radio" name="acceptance" value="true" />
                                    Утвердить</label>
                                <br />
                                <label>
                                    <input type="radio" name="acceptance" value="false" checked/>
                                    Отклонить</label>
                                <br />
                            </p>
                        </td>
                        <td class="submit-button">
                            <button type="submit" class="button">Принять</button>
                        </td>
                    </table>
                </form>
            [/#if]

            [#if result??]
                <p>${result?html}</p>
            [/#if]

        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]