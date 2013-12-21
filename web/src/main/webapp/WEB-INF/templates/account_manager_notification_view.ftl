[#ftl]
[#-- @ftlvariable name="notification" type="org.kofi.creditex.model.Notification" --]
[#-- @ftlvariable name="product" type="org.kofi.creditex.model.Product" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Main page"]
    [@creditex.includeJQuery/]
    [@creditex.includeTableCloth/]
    <script type="text/javascript">
        $(function(){
            $("#foo").click(function(){
                history.go(-1);
            });
            [@creditex.sorting table="notificationTable" theme = "default" sortable=false class = "data-table"/]
        });
    </script>
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        <div class="data-table">
            <p class="name">Данные по кредиту</p>
            <table id="notificationTable">
                <thead>
                <tr>
                    <th>Параметр</th>
                    <th>Данные кредита</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>Дата нотификации</td>
                    <td>${notification.notificationDate}</td>
                </tr>
                <tr>
                    <td>Тип сообщения</td>
                    <td>${notification.type}</td>
                </tr>
                <tr>
                    <td>Просмотрено ли сообщение</td>
                    <td>${notification.viewed?string("Да", "Нет")}</td>
                </tr>
                <tr>
                    <td>Сообщение</td>
                    <td>${notification.message}</td>
                </tr>
                <tr>
                    <td>Кредит</td>
                    <td><a href="[@spring.url '/account/credit/${notification.credit.id}/view'/]">Перейти</a></td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="content">
            <ul class="nav-menu">
                <li><a id="foo">Вернуться назад</a></li>
            </ul>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]