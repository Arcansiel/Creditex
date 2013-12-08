[#ftl]
[#-- @ftlvariable name="credits" type="java.util.List<org.kofi.creditex.web.model.CreditForm>" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Main page"]

    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        <div class="data-table">
            <p class="name">Заявки на досрочное погашение кредита</p>
            <table>
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
                [#if credits??]
                    [#list credits as credit]
                        <tr>
                            <td>${credit.start}</td>
                            <td>${credit.duration}</td>
                            <td>${credit.currentMainDebt}</td>
                            <td>${credit.fine}</td>
                            <td>${credit.currentMoney}</td>
                            <td>${credit.originalMainDebt}</td>
                            <td><a href="[@spring.url '/account_manager/product/view/'+'${credit.productId}'+'/'/]">${credit.productName}</a></td>
                            <td><a href="[@spring.url '/account_manager/client/credit/view/'+'${credit.id}'+'/'/]">Просмотреть</a></td>
                        </tr>
                    [/#list]
                [/#if]
            </table>
        </div>
        <div class="content">
            <ul class="nav-menu">
                <li><a href="[@spring.url '/account_manager/client/'/]">Вырнуться назад</a>
                </li>
            </ul>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]