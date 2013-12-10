[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Операционист / список операций пользователя"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">

        <div class="form-action">
            [#if error??]
                <p>${error?html}</p>
            [/#if]
            <p class="name"><a href=[@spring.url "/operation_manager/"/]>Другой клиент</a></p>
            <p class="name"><a href=[@spring.url "/operation_manager/operation/"/]>Новая операция</a></p>
            <p class="name">Ближайшие платежи по текущему кредиту</p>
            <table>
                <tr>
                    <th class="name">Номер платежа</th>
                    <th class="start_date">крайняя дата</th>
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
                            <td class="name">${payment.expired?c}</td>
                        </tr>
                    [/#list]
                [#else]
                    <tr><td colspan="6">Данные отсутствуют</td></tr>
                [/#if]
            </table>

            <p class="name">Все операции по текущему кредиту</p>
            <table>
                <tr>
                    <th class="start_date">Дата операции</th>
                    <th class="name">Тип операции</th>
                    <th class="amount">Сумма операции</th>
                    <th class="name">Операционист</th>
                </tr>

                [#if operations??]
                    [#list operations as operation]
                        <tr>
                            <td class="start_date">${operation.operationDate?html}</td>
                            <td class="name">${operation.type?html}</td>
                            <td class="amount">${operation.amount?html}</td>
                            <td class="name">${operation.operator.username?html}</td>
                        </tr>
                    [/#list]
                [#else]
                <tr><td colspan="4">Данные отсутствуют</td></tr>
                [/#if]
            </table>
        </div>

    </div>
    [/@creditex.body]
[/@creditex.root]