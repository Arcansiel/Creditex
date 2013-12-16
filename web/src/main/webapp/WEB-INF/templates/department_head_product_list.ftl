[#ftl]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Списки кредитных продуктов"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.goback/]
        <div class="form-action">
            <p class="name"><a href="[@spring.url '/department_head/'/]">На главную страницу</a></p>
            <p class="name">Активные кредитные продукты</p>
            <table>
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
                    <th class="submit-button">Действия</th>
                </tr>

                [#if activated??][#list activated as product]
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
                        <td><a href="[@spring.url '/department_head/product/${product.id?string("0")}/set_active/false'/]">Деактивировать</a></td>
                    </tr>
                [/#list][/#if]
            </table>

            <p class="name">Неактивные кредитные продукты</p>
            <table>
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
                    <th class="submit-button">Действия</th>
                </tr>

                [#if deactivated??][#list deactivated as product]
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
                        <td><a href="[@spring.url '/department_head/product/${product.id?string("0")}/set_active/true'/]">Активировать</a></td>
                    </tr>
                [/#list][/#if]
            </table>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]