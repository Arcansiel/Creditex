[#ftl]
[#-- @ftlvariable name="creditApplication" type="org.kofi.creditex.model.Application" --]
[#-- @ftlvariable name="credit" type="org.kofi.creditex.model.Credit" --]
[#-- @ftlvariable name="prolongationApplication" type="org.kofi.creditex.model.ProlongationApplication" --]
[#-- @ftlvariable name="priorRepaymentApplication" type="org.kofi.creditex.model.PriorRepaymentApplication" --]
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
                    <a>Кредиты и кредитные продукты</a>
                    <ul>
                        <li><a href="[@spring.url '/account_manager/product/list/'/]">Кредитные продукты</a></li>
                        <li><a href="[@spring.url '/account_manager/client/credit/list/'/]">Предыдущие кредиты клиента</a></li>
                        [#if credit??]
                            <li><a href="[@spring.url '/account_manager/client/credit/view/'+'${credit.id}'+'/'/]">Терущий кредит клиента</a></li>
                        [/#if]

                    </ul>
                </li>
                <li><a>Заявки</a>
                    <ul>
                        [#if credit??]
                            [#if priorRepaymentApplication??]
                                [#switch priorRepaymentApplication.acceptance.name()]
                                    [#case "Accepted"]
                                        <li><a href="[@spring.url '/account_manager/client/prior/final/'/]">Оформить предварительное погашение кредита</a> </li>
                                    [#break ]
                                    [#case "InProcess"]
                                        <li><a href="[@spring.url '/account_manager/client/prior/view/'/]">Проверить заявку на предварительное погашение кредита</a> </li>
                                    [#break ]
                                [/#switch]
                            [#else]
                                <li><a href="[@spring.url '/account_manager/client/prior/'/]">Создать заявку на предварительное погашение кредита</a></li>
                            [/#if]
                            [#if prolongationApplication??]
                                [#switch prolongationApplication.acceptance.name()]
                                    [#case "Accepted"]
                                        <li><a href="[@spring.url '/account_manager/client/prolongation/final/'/]">Оформить пролонгацию кредита</a> </li>
                                    [#break]
                                    [#case "InProcess"]
                                        <li><a href="[@spring.url '/account_manager/client/prolongation/view/'/]">Проверить заявку на пролонгацию кредита</a> </li>
                                    [#break]
                                [/#switch]
                            [#else]
                                <li><a href="[@spring.url '/account_manager/client/prolongation/'/]">Создать заявку на пролонгацию кредита кредита</a></li>
                            [/#if]
                        [#else ]
                            [#if creditApplication??]
                                [#switch creditApplication.acceptance.name()]
                                    [#case "Accepted"]
                                        <li><a href="[@spring.url '/account_manager/client/credit/'/]">Оформить кредит по заявке</a> </li>
                                    [#break]
                                    [#case "InProcess"]
                                        <li><a href="[@spring.url '/account_manager/client/credit/application/view/'/]">Проверить статус заявки на предоставление кредита</a> </li>
                                    [#break]
                                [/#switch]
                            [#else]
                                <li><a href="[@spring.url '/account_manager/client/product/'/]">Создать заявку на предоставление кредита</a></li>
                            [/#if]
                        [/#if]



                    </ul>
                </li>
                <li><a>Просмотр заявок</a>
                    <ul>
                        <li><a href="[@spring.url '/account_manager/client/credit/application/list/'/]">На предоставление кредита</a></li>
                        <li><a href="[@spring.url '/account_manager/client/prior/list/'/]">На предварительное погашение кредита</a></li>
                        <li><a href="[@spring.url '/account_manager/client/prolongation/list/'/]">На пролонгацию кредита кредита</a></li>
                    </ul>
                </li>
                <li><a>Операции с клиентом</a>
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