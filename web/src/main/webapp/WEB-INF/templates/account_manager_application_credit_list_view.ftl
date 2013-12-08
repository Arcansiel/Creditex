[#ftl]
[#-- @ftlvariable name="applications" type="java.util.List<org.kofi.creditex.web.model.CreditApplicationForm>" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Main page"]

    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        <div class="data-table">
            <p class="name">Заявки на кредит</p>
            <table>
                <tr>
                    <th>Кредитный продукт</th>
                    <th>Запрашиваемая сумма</th>
                    <th>Длительность кредита</th>
                    <th>Принята ли заявка</th>
                    <th>Кем отвергнута</th>
                    <th>Причина отвержения</th>
                    <th>Просомтреть</th>
                </tr>
                [#if applications??]
                    [#list applications as application]
                        <tr>
                            <td><a href="[@spring.url '/account_manager/product/view/'+'${application.productId}'+'/'/]">${application.productName}</a></td>
                            <td>${application.requestedMoney}</td>
                            <td>${application.duration}</td>
                            <td>${application.acceptance}</td>
                            <td>${application.whoRejected}</td>
                            <td>${application.whyRejected}</td>
                            <td><a href="[@spring.url '/account_manager/client/credit/view/'+'${application.id}'+'/'/]">Просомотреть</a> </td>
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