[#ftl]
[#-- @ftlvariable name="notification" type="org.kofi.creditex.model.Notification" --]
[#-- @ftlvariable name="credit" type="org.kofi.creditex.model.Credit" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Main page"]

    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.account_manager/]
        <div class="content">

            <ul class="nav-menu">
                <li>
                    <a href="#">Кредиты</a>
                    <ul>
                        <li><a href="[@spring.url '/bank_client/credit/list'/]">Предыдущие кредиты клиента</a></li>
                        [#if credit??]
                            <li><a href="[@spring.url '/bank_client/credit/${credit.id}/view/'/]">Терущий кредит клиента</a></li>
                        [/#if]
                    </ul>
                </li>
                <li><a href="#">Уведомления</a>
                    <ul>
                        <li><a href="[@spring.url '/bank_client/notification/list/'/]">Просмотреть уведомления</a></li>
                        [#if notification??]
                            <li><a href="[@spring.url '/bank_client/notification/${notification.id}/view/'/]">Просмотреть последнее уведомление</a></li>
                        [/#if]
                    </ul>
                </li>
                <li><a href="">Кредитный калькулятор</a>
                    <ul>
                        <li><a href="[@spring.url '/bank_client/calculator/'/]">Выбрать ограничения поиска</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]