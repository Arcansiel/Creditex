[#ftl]
[#-- @ftlvariable name="id" type="java.lang.Number" --]
[#-- @ftlvariable name="payments" type="java.util.List<org.kofi.creditex.model.Payment>" --]
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
                <li><a href="#">Операции с заявкой</a>
                    <ul>
                        <li><a href="[@spring.url '/account_manager/client/prior/finalize/'/]">Зарегистрировать предварительное погашение</a></li>
                        <li><a href="[@spring.url '/account_manager/client/prior/abort/${id}/'/]">Отменить заявку</a></li>
                    </ul>
                </li>
                <li><a href="[@spring.url '/account_manager/client/'/]">Назад</a>
                </li>
            </ul>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]