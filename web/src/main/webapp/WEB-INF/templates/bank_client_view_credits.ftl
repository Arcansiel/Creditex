[#ftl]
[#-- @ftlvariable name="credits" type="java.util.List<org.kofi.creditex.model.Credit>" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Кредиты"]
    [@creditex.tableProcess "creditTable" "credits"/]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.bank_client/]
        <div class="data-table">
            <p class="name">Список кредитов</p>
            <div class="holder"></div>
            <table id="creditTable">
                <thead>
                <tr>
                    <th>Дата начала</th>
                    <th>Длительность</th>
                    <th>Текущий основной долг</th>
                    <th>Пеня</th>
                    <th>Текущие деньги на счету</th>
                    <th>Начальный основной долг</th>
                    <th>Кредитный продукт</th>
                    <th>Просмотреть</th>
                </tr>
                </thead>
                <tbody id="credits">
                    [#if credits??]
                        [#list credits as credit]
                        <tr>
                            <td>${credit.creditStart}</td>
                            <td>${credit.duration}</td>
                            <td>${credit.currentMainDebt}</td>
                            <td>${credit.mainFine+credit.percentFine}</td>
                            <td>${credit.currentMoney}</td>
                            <td>${credit.originalMainDebt}</td>
                            <td><a href="[@spring.url '/client/product/${credit.product.id}/view/'/]">${credit.product.name}</a></td>
                            <td><a href="[@spring.url '/client/credit/${credit.id}/view/'/]">Просмотреть</a></td>
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