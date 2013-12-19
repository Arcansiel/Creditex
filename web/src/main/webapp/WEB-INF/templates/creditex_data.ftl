[#ftl]

[#import "spring.ftl" as spring]

[#macro product_view_table product caption = "Кредитный продукт" show_active = true]
[#if product??]
<p class="name">${caption?html}</p>
<table>
    <thead>
    <tr>
        <th class="name">ID продукта</th>
        <th class="name">Название</th>
        <th class="amount">Процентная ставка за год (%)</th>
        <th class="name">Тип погашения</th>
        <th class="amount">Минимальная сумма</th>
        <th class="amount">Максимальная сумма</th>
        <th class="amount">Минимальная сумма для голосования комитета</th>
        <th class="duration">Минимальная длительность</th>
        <th class="duration">Максимальная длительность</th>
        <th class="amount">Пеня за день просрочки платежа (%)</th>
        <th class="name">Тип досрочного погашения</th>
        <th class="amount">Штраф за досрочное погашения (%)</th>
        [#if show_active]<th class="name">Активный</th>[/#if]
    </tr>
    </thead>
    <tbody>
    <tr>
        <td class="name">${product.id?string("0")}</td>
        <td class="name">${product.name?html}</td>
        <td class="amount">${product.percent?string("0.####")}</td>
        <td class="name">${product.type}</td>
        <td class="amount">${product.minMoney}</td>
        <td class="amount">${product.maxMoney}</td>
        <td class="amount">${product.minCommittee}</td>
        <td class="duration">${product.minDuration}</td>
        <td class="duration">${product.maxDuration}</td>
        <td class="amount">${product.debtPercent?string("0.####")}</td>
        <td class="name">${product.prior}</td>
        <td class="amount">${product.priorRepaymentPercent}</td>
        [#if show_active]<td class="name">${product.active?c}</td>[/#if]
    </tr>
    </tbody>
</table>
[/#if]
[/#macro]


[#macro credit_view_table credit caption = "Кредит" show_running = true show_product_name = true]
[#if credit??]
<p class="name">${caption?html}</p>
<table>
    <thead>
    <tr>
        <th class="name">ID</th>
        [#if show_product_name]<th class="name">Продукт</th>[/#if]
        <th class="start_date">Начало кредитования</th>
        <th class="duration">Длительность</th>
        <th class="start_date">Конец кредитования</th>
        <th class="amount">Сумма кредита</th>
        <th class="amount">Основной долг</th>
        <th class="amount">Процентный долг</th>
        <th class="amount">Долг по платежам</th>
        <th class="amount">Пеня</th>
        [#if show_running]<th class="name">Активный кредит</th>[/#if]
    </tr>
    </thead>
    <tbody>
    <tr>
        <td class="name">${credit.id}</td>
        [#if show_product_name]<td class="name">${credit.product.name?html}</td>[/#if]
        <td class="start_date">${credit.creditStart}</td>
        <td class="duration">${credit.duration}</td>
        <td class="start_date">${credit.creditEnd}</td>
        <td class="amount">${credit.originalMainDebt}</td>
        <td class="amount">${credit.currentMainDebt}</td>
        <td class="amount">${credit.currentPercentDebt}</td>
        <td class="amount">${credit.mainFine}</td>
        <td class="amount">${credit.percentFine}</td>
        [#if show_running]<td class="name">${credit.running?c}</td>[/#if]
    </tr>
    </tbody>
</table>
[/#if]
[/#macro]


[#macro client_view_table client caption = "Клиент банка" show_enabled = true]
[#if client??]
<p class="name">${caption?html}</p>
<table>
    <thead>
    <tr>
        <th class="name">ID клиента</th>
        <th class="name">ФИО клиента</th>
        <th class="passport">Серия и номер паспорта</th>
        <th class="name">username</th>
        <th class="name">Место работы</th>
        <th class="name">Занимаемая должность</th>
        <th class="name">Доход</th>
        [#if show_enabled]<th class="name">Активен</th>[/#if]
    </tr>
    </thead>
    <tbody>
    <tr>
        <td class="name">${client.id}</td>
        <td class="name">${client.userData.last?html} ${client.userData.first?html} ${client.userData.patronymic?html}</td>
        <td class="passport">${client.userData.passportSeries?html} ${client.userData.passportNumber}</td>
        <td class="name">${client.username?html}</td>
        <td class="name">${client.userData.workName?html}</td>
        <td class="name">${client.userData.workPosition?html}</td>
        <td class="name">${client.userData.workIncome?html}</td>
        [#if show_enabled]<td class="name">${client.enabled?c}</td>[/#if]
    </tr>
    </tbody>
</table>
[/#if]
[/#macro]


[#macro vote_list_table votes caption = "Голоса кредитного комитета"]
[#if votes??]
<p class="name">${caption?html}</p>
<table>
    <thead>
    <tr>
        <th class="name">Член комитета</th>
        <th class="name">Принятие</th>
        <th class="comment">Комментарий</th>
    </tr>
    </thead>
    <tbody>
    [#list votes as vote]
        <tr>
            <td class="name">${vote.manager.username?html}</td>
            <td class="name">${vote.acceptance?c}</td>
            <td class="comment">${vote.comment?html}</td>
        </tr>
    [/#list]
    </tbody>
</table>
[/#if]
[/#macro]

[#macro application_view_table application caption = "Заявка на кредит"]
[#if application??]
<p class="name">${caption?html}</p>
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
[/#if]
[/#macro]


[#macro confirmation_form
form_method
form_action
accept_label = "Утвердить"
reject_label = "Отклонить"
submit_label = "Принять"]
<form method="${form_method}" action="[@spring.url form_action/]">
    <table>
        <td class="comment"><textarea name="comment" ></textarea></td>
        <td class="action">
            <p>
                <label>
                    <input type="radio" name="acceptance" value="true" />
                    ${accept_label?html}</label>
                <br />
                <label>
                    <input type="radio" name="acceptance" value="false" checked/>
                    ${reject_label?html}</label>
                <br />
            </p>
        </td>
        <td class="submit-button">
            <button type="submit" class="button">${submit_label?html}</button>
        </td>
    </table>
</form>
[/#macro]

