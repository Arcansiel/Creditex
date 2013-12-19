[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Служба безопасности"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.security_manager /]
        <div class="content">

            <ul class="nav-menu">

                <li><a href="#">Заявки</a>
                    <ul>
                        <li><a href="[@spring.url '/security_manager/appliances/'/]">Заявки на рассмотрение службой безопасности</a></li>
                        [#if application??]
                            <li><a href="[@spring.url '/security_manager/appliance/check/${application.id?string("0")}'/]">Открыть текущую заявку</a></li>
                        [/#if]
                    </ul>
                </li>
                <li><a href="#">Кредиты</a>
                    <ul>
                        <li><a href="[@spring.url '/security_manager/credits/expired/'/]">Кредиты с задержкой платежей</a></li>
                        <li><a href="[@spring.url '/security_manager/credits/unreturned/'/]">Невозвращённые кредиты</a></li>
                    </ul>
                </li>
                <li><a href="#">Клиенты</a>
                    <ul>
                        <li><a href="[@spring.url '/security_manager/clients/list/'/]">Список клиентов</a></li>
                        <li><a href="[@spring.url '/security_manager/client/search/'/]">Поиск клиента</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]