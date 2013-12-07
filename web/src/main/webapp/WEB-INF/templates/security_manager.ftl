[#ftl]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Main page"]

    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.account_manager/]
        <div class="content">

            <ul class="nav-menu">

                <li><a href="#">Заявки</a>
                    <ul>
                        <li><a href="[@spring.url '/account_manager/client/credit/add/'/]">На предоставление кредита</a></li>
                    </ul>
                </li>
                <li><a href="#">Кредиты</a>
                    <ul>
                        <li><a href="[@spring.url '/account_manager/client/credit/list/'/]">Просроченные</a></li>
                        <li><a href="[@spring.url '/account_manager/client/prior/list/'/]">Невозвращённые</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]