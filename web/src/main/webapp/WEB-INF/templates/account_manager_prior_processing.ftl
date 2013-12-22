[#ftl]
[#-- @ftlvariable name="id" type="java.lang.Number" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Регистрация погашения кредита"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.account_manager/]
        <div class="content">
            <ul class="nav-menu">
                <li><a href="#">Операции с заявкой</a>
                    <ul>
                        <li><a href="[@spring.url '/account/prior/${id}/register'/]">Зарегистрировать предварительное погашение</a></li>
                        <li><a href="[@spring.url '/account/prior/${id}/abort'/]">Отменить заявку</a></li>
                    </ul>
                </li>
                <li><a href="[@spring.url '/account'/]">Назад</a>
                </li>
            </ul>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]