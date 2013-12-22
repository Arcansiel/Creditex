[#ftl]
[#-- @ftlvariable name="application" type="org.kofi.creditex.model.PriorRepaymentApplication" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Просмотр заявки на предварительное погашение"]
    [@creditex.includeJQuery/]
    [@creditex.includeTableCloth/]
    <script type="text/javascript">
        $(function(){
            [@creditex.sorting table="priorTable" theme = "default" sortable=false class = "data-table"/]
        });
    </script>
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.account_manager/]
        <div class="data-table">
            <p class="name">Данные по кредиту</p>
            <table id="priorTable">
                <tr>
                    <th>Параметр</th>
                    <th>Данные</th>
                </tr>
                <tr>
                    <td>Дата подачи заявки</td>
                    <td>${application.applicationDate}</td>
                </tr>
                <tr>
                    <td>Комментарий</td>
                    <td>${application.comment}</td>
                </tr>
                <tr>
                    <td>Принята ли заявка</td>
                    <td>${application.acceptance}</td>
                </tr>
                <tr>
                    <td>Кредит</td>
                    <td><a href="[@spring.url '/account/credit/${application.credit.id}/view'/]">Просмотреть</a></td>
                </tr>
            </table>
        </div>
        <div class="content">
            <ul class="nav-menu">
                <li><a href="[@spring.url '/account'/]" id="foo">Вернуться назад</a></li>
                <li><a href="[@spring.url '/account/prior/${application.id}/abort'/]">Отменить заявку</a></li>
            </ul>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]