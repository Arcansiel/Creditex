[#ftl]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Заявка на пролонгацию"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
    <div class="form-action">
        <p class="name"><a href=[@spring.url "/department_head/"/]>Назад на главную страницу</a></p>
        <p class="name"><a href=[@spring.url "/department_head/prolongation/list/"/]>Назад к списку заявок на пролонгацию</a></p>
        [#if prolongation??]

            <p class="name">Заявка на пролонгацию</p>
            <table>
                <tr>
                    <th class="name">ID заявки</th>
                    <th class="name">ФИО клиента</th>
                    <th class="passport">Серия и номер паспорта</th>
                    <th class="start_date">Поступление заявки</th>
                    <th class="duration">Запрашиваемая длительность пролонгации</th>
                    <th class="comment">Комментарий клиента</th>
                </tr>

                <tr>
                    <td class="name">${prolongation.id?string("0")}</td>
                    <td class="name">${prolongation.client.userData.last?html} ${prolongation.client.userData.first?html} ${prolongation.client.userData.patronymic?html}</td>
                    <td class="passport">${prolongation.client.userData.passportSeries?html} ${prolongation.client.userData.passportNumber}</td>
                    <td class="start_date">${prolongation.applicationDate}</td>
                    <td class="duration">${prolongation.duration}</td>
                    <td class="comment">${prolongation.comment?html}</td>
                </tr>
            </table>
            <form method="post" action=[@spring.url '/department_head/prolongation/${application.id?string("0")}/set_head_approved/'/]>
                <table>
                    <td class="comment"><textarea name="comment" ></textarea></td>
                    <td class="action">
                        <p>
                            <label>
                                <input type="radio" name="confirmation" value="true" />
                                Утвердить</label>
                            <br />
                            <label>
                                <input type="radio" name="confirmation" value="false" checked/>
                                Отклонить</label>
                            <br />
                        </p>
                    </td>
                    <td class="submit-button">
                        <button type="submit" class="button">Принять</button>
                    </td>
                </table>
            </form>

            [#if prolongation.credit??]
                <p class="name">Состояние кредита</p>
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
                    <tr>
                        <td class="name">${prolongation.credit.id}</td>
                        <td class="name">${prolongation.credit.product.name?html}</td>
                        <td class="start_date">${prolongation.credit.creditStart}</td>
                        <td class="duration">${prolongation.credit.duration}</td>
                        <td class="start_date">${prolongation.credit.creditEnd}</td>
                        <td class="amount">${prolongation.credit.originalMainDebt}</td>
                        <td class="amount">${prolongation.credit.currentMainDebt}</td>
                        <td class="amount">${prolongation.credit.currentPercentDebt}</td>
                        <td class="amount">${prolongation.credit.mainFine}</td>
                        <td class="amount">${prolongation.credit.percentFine}</td>
                        <td class="name">${prolongation.credit.running?c}</td>
                    </tr>
                </table>
            [/#if]
        [/#if]
    [/@creditex.body]
[/@creditex.root]