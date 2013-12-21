[#ftl]
[#-- @ftlvariable name="notifications" type="java.util.List<org.kofi.creditex.model.Notification>" --]
[#-- @ftlvariable name="credit" type="org.kofi.creditex.model.Credit" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Main page"]
        [@creditex.tableProcess "notificationTable" "notifications"/]
    <script type="text/javascript">
        $(function(){
            $("#foo").click(function () {
                history.go(-1);
            });
        });
    </script>
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        <div class="data-table">
            <p class="name">Уведомления</p>
            <div class="holder"></div>
            <table id="notificationTable">
                <thead>
                <tr>
                    <th>Дата нотификации</th>
                    <th>Тип сообщения</th>
                    <th>Просмотрено ли сообщение</th>
                    <th>Просмотреть сообщение</th>
                    <th>Кредит</th>
                </tr>
                </thead>
                <tbody id="notifications">
                    [#if notifications??]
                        [#list notifications as notification]
                        <tr>
                            <td>${notification.notificationDate}</td>
                            <td>${notification.type}</td>
                            <td>${notification.viewed?string("Да", "Нет")}</td>
                            <td><a href="[@spring.url '/client/notification/${notification.id}/view'/]">Перейти</a></td>
                            <td><a href="[@spring.url '/client/credit/${notification.credit.id}/view'/]">Перейти</a></td>
                        </tr>
                        [/#list]
                    [/#if]
                </tbody>

            </table>
        </div>
        <div class="content">
            <ul class="nav-menu">
                <li><a id="foo">Вернуться назад</a>
                </li>
            </ul>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]