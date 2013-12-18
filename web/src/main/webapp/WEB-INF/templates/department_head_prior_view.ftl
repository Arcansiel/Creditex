[#ftl]
[#import "creditex.ftl" as creditex]
[#import "creditex_data.ftl" as creditex_data]
[@creditex.root]
    [@creditex.head "Заявка на досрочное погашение кредита"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.department_head /]
    [@creditex.goback/]
    <div class="data-table">
        <p class="name"><a href="[@spring.url '/department_head/'/]">На главную страницу</a></p>
        <p class="name"><a href="[@spring.url '/department_head/prior/list/'/]">Список заявок на досрочное погашение</a></p>
        [#if prior??]

            <p class="name"><a href="[@spring.url '/department_head/client/${prior.client.id?string("0")}'/]">Информация о клиенте</a></p>

            <p class="name">Заявка на досрочное погашение кредита</p>
            <table>
                <tr>
                    <th class="name">ID заявки</th>
                    <th class="name">ФИО клиента</th>
                    <th class="passport">Серия и номер паспорта</th>
                    <th class="start_date">Поступление заявки</th>
                    <th class="comment">Комментарий клиента</th>
                </tr>

                <tr>
                    <td class="name">${prior.id?string("0")}</td>
                    <td class="name">${prior.client.userData.last?html} ${prior.client.userData.first?html} ${prior.client.userData.patronymic?html}</td>
                    <td class="passport">${prior.client.userData.passportSeries?html} ${prior.client.userData.passportNumber}</td>
                    <td class="start_date">${prior.applicationDate}</td>
                    <td class="comment">${prior.comment?html}</td>
                </tr>
            </table>

            [#if prior.acceptance?? && prior.acceptance.name() == "InProcess"]
            <form method="post" action="[@spring.url '/department_head/prior/${prior.id?string("0")}/set_head_approved/'/]">
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

            [#if (prior.credit)??]
                [@creditex_data.credit_view_table prior.credit "Состояние кредита" /]
            [/#if]

            [#if (prior.credit.product)??]
                [@creditex_data.product_view_table prior.credit.product /]
            [/#if]

        [/#if]
    [/@creditex.body]
[/@creditex.root]