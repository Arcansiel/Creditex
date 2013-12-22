[#ftl]
[#-- @ftlvariable name="products" type="java.util.List<org.kofi.creditex.model.Product>" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Выбор кредитного продукта для заявки"]
        [@creditex.tableProcess "productTable" "products"/]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.account_manager/]
        <div class="data-table">
            <p class="name">Список кредитных продуктов для заявки на кредит</p>
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
                    <th>Минмимальная сумма рассмотрения комитетом</th>
                    <th>Процент пени</th>
                    <th>Предварительный возврат</th>
                    <th>Процент штрафа за предварительный возврат</th>
                    <th>Заявка</th>
                </tr>
                </thead>
                <tbody id="products">
                    [#if products??]
                        [#list products as product]
                        <tr>
                            <td>${product.name}</td>
                            <td>${product.type}</td>
                            <td>${product.percent}</td>
                            <td>${product.minMoney}</td>
                            <td>${product.maxMoney}</td>
                            <td>${product.minDuration}</td>
                            <td>${product.maxDuration}</td>
                            <td>${product.minCommittee}</td>
                            <td>${product.debtPercent}</td>
                            <td>${product.prior}</td>
                            <td>${product.priorRepaymentPercent}</td>
                            <td><a href="[@spring.url '/account/credit/application?productId=${product.id}'/]">Оформить заявку</a> </td>
                        </tr>
                        [/#list]
                    [/#if]
                </tbody>

            </table>
        </div>
        <div class="content">
            <ul class="nav-menu">
                <li><a href="[@spring.url '/account'/]">Вернуться назад</a>
                </li>
            </ul>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]