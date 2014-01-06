[#ftl]
[#-- @ftlvariable name="products" type="java.util.List<org.kofi.creditex.model.Product>" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Кредитные продукты"]
    [@creditex.tableProcess "productTable" "products"/]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.account_manager/]
        <div class="data-table">
            <p class="name">Список кредитных продуктов</p>
            <div class="holder"></div>
            <table id="productTable">
                <thead>
                <tr>
                    <th>Название</th>
                    <th>Тип кредита</th>
                    <th>Процентная ставка</th>
                    <th>Минимальная сумма кредита</th>
                    <th>Максимальная сумма кредита</th>
                    <th>Минимальная длительность кредита</th>
                    <th>Максимальная длительность кредита</th>
                    <th>Минмимальная сумма для рассмотрения комитетом</th>
                    <th>Процент пени</th>
                    <th>Досрочное погашение</th>
                    <th>Процент штрафа за досрочное погашение</th>
                    <th>Просмотреть</th>
                </tr>
                </thead>
                <tbody id="products">
                    [#if products??]
                        [#list products as product]
                        <tr>
                            <td>${product.name}</td>
                            <td>${product.type}</td>
                            <td>${product.percent?string("0.####")}</td>
                            <td>${product.minMoney}</td>
                            <td>${product.maxMoney}</td>
                            <td>${product.minDuration}</td>
                            <td>${product.maxDuration}</td>
                            <td>${product.minCommittee}</td>
                            <td>${product.debtPercent?string("0.####")}</td>
                            <td>${product.prior}</td>
                            <td>${product.priorRepaymentPercent?string("0.####")}</td>
                            <td><a href="[@spring.url '/account/product/${product.id}/view'/]">Просмотреть</a> </td>
                        </tr>
                        [/#list]
                    [/#if]
                </tbody>

            </table>
        </div>
        [@creditex.returnButton/]
    </div>
    [/@creditex.body]
[/@creditex.root]