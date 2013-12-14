[#ftl]
[#-- @ftlvariable name="applications" type="java.util.List<org.kofi.creditex.model.PriorRepaymentApplication>" --]
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
                    <th>Дата</th>
                    <th>Комментарий</th>
                    <th>Кредит</th>
                    <th>Принята ли заявка</th>
                </tr>
                [#if applications??]
                    [#list applications as application]
                        <tr>
                            <td>${application.applicationDate?string("dd/MM/yyyy")}</td>
                            <td>${application.comment}</td>
                            <td><a href="[@spring.url '/account_manager/client/credit/view/'+'${application.credit.id}'+'/'/]">Посмотреть</a></td>
                            [#assign acceptance=""/]
                            [#switch application.acceptance]
                                [#case "Accepted"]
                                    [#assign acceptance="Принята"/]
                                [#break ]
                                [#case "Rejected"]
                                    [#assign acceptance="Отвергнута"/]
                                [#break ]
                                [#case "InProcess"]
                                    [#assign acceptance="В рассмотрении"/]
                                [#break ]
                            [/#switch]
                            <td>${acceptance}</td>
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