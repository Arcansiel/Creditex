[#ftl]
[#-- @ftlvariable name="applications" type="java.util.List<org.kofi.creditex.model.ProlongationApplication>" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Main page"]
    [@creditex.includeBootstrap/]
    <script>
        $(function(){
            $("div.holder").jPages({
                containerID:"applications",
                perPage      : 10,
                startPage    : 1,
                startRange   : 1,
                midRange     : 5,
                endRange     : 1,
                delay: 0
            });
        });
    </script>
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        <div class="data-table">
            <p class="name">Заявки на пролонгацию кредита</p>
            <div class="holder"></div>
            <table>
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
                            <td><a href="[@spring.url '/account_manager/client/credit/view/'+'${application.credit.id}'+'/'/]">Кредит</a> </td>
                            <td>${application.acceptance}</td>
                        </tr>
                        [/#list]
                    [/#if]
                </tbody>
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