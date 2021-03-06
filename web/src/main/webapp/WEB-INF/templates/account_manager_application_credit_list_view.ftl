[#ftl]
[#-- @ftlvariable name="Acceptance" type="org.kofi.creditex.model.Acceptance" --]
[#-- @ftlvariable name="applications" type="java.util.List<org.kofi.creditex.model.Application>" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Список предыдущих заявок на кредит"]
        [@creditex.tableProcess "applicationTable" "applications"/]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.account_manager/]
        <div class="data-table">
            <p class="name">Заявки на кредит</p>
            <div class="holder"></div>
            <table id="applicationTable">
                <thead>
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
                </thead>
                <tbody id="applications">
                    [#if applications??]
                        [#list applications as application]
                        <tr>
                            <td><a href="[@spring.url '/account/product/${application.product.id}/view/'/]">${application.product.name}</a></td>
                            <td>${application.request}</td>
                            <td>${application.applicationDate}</td>
                            <td>${application.duration}</td>
                            [#assign whoRejected=""/]
                            [#assign whyRejected=""/]
                            [#if application.securityAcceptance.name()= "Rejected"]
                                [#assign whoRejected="Специалист службы безопасности"/]
                                [#assign whyRejected=application.securityComment/]
                            [/#if]
                            [#if application.committeeAcceptance.name()="Rejected"]
                                [#assign whoRejected="Кредитный комитет"/]
                                [#assign whyRejected=application.voteAcceptance+"/"+application.voteRejection/]
                            [/#if]
                            [#if application.headAcceptance.name()= "Rejected"]
                                [#assign whoRejected="Глава кредитного отдела"/]
                                [#assign whyRejected=application.headComment/]
                            [/#if]
                            <td>${application.acceptance}</td>
                            <td>${whoRejected}</td>
                            <td>${whyRejected}</td>
                            <td><a href="[@spring.url '/account/credit/application/${application.id}/view/'/]">Просомотреть</a></td>
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