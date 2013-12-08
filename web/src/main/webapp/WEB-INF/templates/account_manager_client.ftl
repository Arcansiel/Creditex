[#ftl]
[#-- @ftlvariable name="isError" type="String" --]
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
                    <a href="#">Кредиты и кредитные продукты</a>
                    <ul>
                        <li><a href="[@spring.url '/account_manager/client/credit/list/'/]">Кредитные продукты</a></li>
                        <li><a href="[@spring.url '/account_manager/product/list/'/]">Кредиты клиента</a></li>
                    </ul>
                </li>
                <li><a href="#">Создать заявку</a>
                    <ul>
                        <li><a href="[@spring.url '/account_manager/client/credit/add/'/]">На предоставление кредита</a></li>
                        <li><a href="[@spring.url '/account_manager/client/prior/add/'/]">На предварительное погашение кредита</a></li>
                        <li><a href="[@spring.url '/account_manager/client/prolongation/add/'/]">На пролонгацию кредита кредита</a></li>
                    </ul>
                </li>
                <li><a href="#">Просмотр заявок</a>
                    <ul>
                        <li><a href="[@spring.url '/account_manager/client/credit/list/'/]">На предоставление кредита</a></li>
                        <li><a href="[@spring.url '/account_manager/client/prior/list/'/]">На предварительное погашение кредита</a></li>
                        <li><a href="[@spring.url '/account_manager/client/prolongation/list/'/]">На пролонгацию кредита кредита</a></li>
                    </ul>
                </li>
                <li><a href="#">Операции с клиентом</a>
                    <ul>
                        <li><a href="[@spring.url '/account_manager/client/change_data/'/]">Изменить личные данные</a></li>
                        <li><a href="[@spring.url '/account_manager/'/]">Выбрать другого клиента</a></li>
                    </ul>
                </li>
            </ul>
        </div>
        [#if isError??]
            <p>${isError}</p>
        [/#if]
    </div>
    [/@creditex.body]
[/@creditex.root]