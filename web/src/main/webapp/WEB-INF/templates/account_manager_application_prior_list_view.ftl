[#ftl]
[#-- @ftlvariable name="applications" type="java.util.List<org.kofi.creditex.model.PriorRepaymentApplication>" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Main page"]
    [@creditex.tableProcess "priorApplicationsTable" "applications"/]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        <div class="data-table">
            <p class="name">Заявки на досрочное погашение кредита</p>
            <div class="holder"></div>
            <table id="priorApplicationsTable">
                <thead>
                <tr>
                    <th>Дата</th>
                    <th>Комментарий</th>
                    <th>Кредит</th>
                    <th>Принята ли заявка</th>
                </tr>
                </thead>
                <tbody id = "applications">
                    [#if applications??]

                        [#list applications as application]
                        <tr>
                            <td>${application.applicationDate}</td>
                            <td>${application.comment}</td>
                            <td><a href="[@spring.url '/account/credit/${application.credit.id}/view'/]">Посмотреть</a></td>
                            <td>${application.acceptance}</td>
                        </tr>
                        [/#list]

                    [/#if]
                </tbody>

            </table>
        </div>
        <div class="content">
            <ul class="nav-menu">
                <li><a href="[@spring.url '/account'/]">Вырнуться назад</a>
                </li>
            </ul>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]