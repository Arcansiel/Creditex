[#ftl]
[#-- @ftlvariable name="Acceptance" type="org.kofi.creditex.model.Acceptance" --]
[#-- @ftlvariable name="applications" type="java.util.List<org.kofi.creditex.model.Application>" --]
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
                    <th>Дата подачи</th>
                    <th>Длительность кредита</th>
                    <th>Принята ли заявка</th>
                    <th>Кем отвергнута</th>
                    <th>Причина отвержения</th>
                    <th>Просомтреть</th>
                </tr>
                [#if applications??]
                    [#list applications as application]
                        <tr>
                            <td><a href="[@spring.url '/account_manager/product/view/'+'${application.product.id}'+'/'/]">${application.product.name}</a></td>
                            <td>${application.request}</td>
                            <td>${application.applicationDate}</td>
                            <td>${application.duration}</td>
                            [#assign whoRejected=""/]
                            [#assign whyRejected=""/]
                            [#switch application.acceptance]
                                [#case "Accepted"]
                                    [#assign acceptance="Принята"/]
                                [#break ]
                                [#case "Rejected"]
                                    [#assign acceptance="Отвергнута"/]
                                    [#if application.securityAcceptance= Acceptance.Rejected]
                                        [#assign whoRejected="Специалист службы безопасности"/]
                                        [#assign whyRejected=application.securityComment/]
                                    [/#if]
                                    [#if application.committeeAcceptance=Acceptance.Rejected]
                                        [#assign whoRejected="Кредитный комитет"/]
                                        [#assign whyRejected=application.voteAcceptance+"/"+application.voteRejection/]
                                    [/#if]
                                    [#if application.headAcceptance = Acceptance.Rejected]
                                        [#assign whoRejected="Глава кредитного отдела"/]
                                        [#assign whyRejected=application.headComment/]
                                    [/#if]
                                [#break]
                                [#case "InProcess"]
                                    [#assign acceptance="В обработке"/]
                                [#break]
                                [#default]
                                    [#assign acceptance=""/]
                                [#break]
                            [/#switch]
                            <td>${acceptance}</td>
                            <td>${whoRejected}</td>
                            <td>${whyRejected}</td>
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