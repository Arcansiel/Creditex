[#ftl]
[#-- @ftlvariable name="applications" type="java.util.List<org.kofi.creditex.model.ProlongationApplication>" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Предыдущие заявки на пролонгацию"]
    [@creditex.tableProcess "prolongationApplicationTable" "applications"/]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.account_manager/]
        <div class="data-table">
            <p class="name">Заявки на пролонгацию кредита</p>
            <div class="holder"></div>
            <table id="prolongationApplicationTable">
                <thead>
                <tr>
                    <th>Дата подачи</th>
                    <th>Продолжительность простоя (мес)</th>
                    <th>Комментарий</th>
                    <th>Кредит</th>
                    <th>Принята ли заявка</th>
                </tr>
                </thead>
                <tbody id="applications">
                    [#if applications??]
                        [#list applications as application]
                        <tr>
                            <td>${application.applicationDate}</td>
                            <td>${application.duration}</td>
                            <td>${application.comment}</td>
                            <td><a href="[@spring.url '/account/credit/${application.credit.id}/view'/]">Кредит</a> </td>
                            <td>${application.acceptance}</td>
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