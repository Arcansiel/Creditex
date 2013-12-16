[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Операционист / ближайшие платежи по кредиту"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.goback /]
        <div class="form-action">
            <p class="name"><a href="[@spring.url '/operation_manager/'/]">Другой клиент</a></p>
            <p class="name"><a href="[@spring.url '/operation_manager/operation/list/'/]">История операций</a></p>
            <p class="name"><a href="[@spring.url '/operation_manager/operation/'/]">Новая операция</a></p>
            <p class="name">Ближайшие платежи по текущему кредиту</p>
            <table>
                <tr>
                    <th class="name">Номер платежа</th>
                    <th class="start_date">Крайняя дата</th>
                    <th class="amount">Сумма платежа</th>
                    <th class="amount">Сумма по основному долгу</th>
                    <th class="amount">Сумма по процентам</th>
                    <th class="name">Просрочен</th>
                </tr>

                [#if payments??]
                    [#list payments as payment]
                        <tr>
                            <td class="name">${payment.number?html}</td>
                            <td class="start_date">${payment.paymentEnd?html}</td>
                            <td class="amount">${payment.requiredPayment?html}</td>
                            <td class="amount">${(payment.requiredPayment - payment.percents)?html}</td>
                            <td class="amount">${payment.percents?html}</td>
                            <td class="name">${payment.paymentExpired?c}</td>
                        </tr>
                    [/#list]
                [#else]
                    <tr><td colspan="6">Данные отсутствуют</td></tr>
                [/#if]
            </table>

            [#if prior??]
                <p class="name">Досрочное погашение кредита</p>
                <table>
                    <tr>
                        <th class="name">ID заявки</th>
                        <th class="start_date">Дата подачи заявки</th>
                        <th class="name">Тип досрочного погашения</th>
                        <th class="amount">Процент штрафа</th>
                        <th class="amount">Штраф</th>
                        <th class="amount">Сумма к оплате</th>
                        <th class="amount">Основной долг</th>
                        <th class="amount">Текущий долг по процентам</th>
                    </tr>
                    <tr>
                        <td class="name">${prior.id?string("0")}</td>
                        <td class="start_date">${prior.applicationDate?html}</td>
                        <td class="name">${prior.credit.product.prior?html}</td>
                        <td class="amount">${prior.credit.product.priorRepaymentPercent?string("0.####")}</td>
                        <td class="amount">[#if priorFine??]${priorFine}[/#if]</td>
                        <td class="amount">[#if priorAmount??]${priorAmount}[/#if]</td>
                        <td class="amount">${prior.credit.currentMainDebt}</td>
                        <td class="amount">${prior.credit.currentPercentDebt}</td>
                    </tr>
                </table>
            [/#if]

        </div>

    </div>
    [/@creditex.body]
[/@creditex.root]