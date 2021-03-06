[#ftl]
[#-- @ftlvariable name="notifications" type="java.util.List<org.kofi.creditex.model.Notification>" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Уведомления"]
    [@creditex.tableProcess "notificationTable" "notifications"/]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.bank_client/]
        <div class="data-table">
            <p class="name">Уведомления</p>
            <div class="holder"></div>
            <table id="notificationTable">
                <thead>
                <tr>
                    <th>Дата уведомления</th>
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
        [@creditex.returnButton/]
    </div>
    [/@creditex.body]
[/@creditex.root]