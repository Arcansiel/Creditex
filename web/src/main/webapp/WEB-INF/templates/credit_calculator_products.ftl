[#ftl]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Список доступных кредитных продуктов"]
        [@creditex.tableProcess "listtable" "list" 10 /]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.credit_calculator_title /]
        <div class="data-table">
        <p class="name">Доступные кредитные продукты</p>
            <div class="holder"></div>
            <table id="listtable">
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
                    <th class="submit-button">Действия</th>
                </tr>
                </thead>
                <tbody id="list">
                [#if products??][#list products as product]
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
                        <td><a href="[@spring.url '/credit_calculator/for_product/${product.id?string("0")}'/]">Выбрать</a></td>
                    </tr>
                [/#list][/#if]
                </tbody>
            </table>

        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]