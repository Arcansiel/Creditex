[#ftl]
[#-- @ftlvariable name="notification" type="org.kofi.creditex.model.Notification" --]
[#-- @ftlvariable name="creditApplication" type="org.kofi.creditex.model.Application" --]
[#-- @ftlvariable name="credit" type="org.kofi.creditex.model.Credit" --]
[#-- @ftlvariable name="prolongationApplication" type="org.kofi.creditex.model.ProlongationApplication" --]
[#-- @ftlvariable name="priorRepaymentApplication" type="org.kofi.creditex.model.PriorRepaymentApplication" --]
[#-- @ftlvariable name="isError" type="String" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Специалист по работе с клиентами"]

    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.account_manager/]
        <div class="content">

            <ul class="nav-menu">
                <li>
                    <a>Кредиты и кредитные продукты</a>
                    <ul>
                        <li><a href="[@spring.url '/account/product/list?active=true'/]">Кредитные продукты</a></li>
                        <li><a href="[@spring.url '/account/credit/list?running=false'/]">Предыдущие кредиты клиента</a></li>
                        [#if credit??]
                            <li><a href="[@spring.url '/account/credit/${credit.id}/view/'/]">Текущий кредит клиента</a></li>
                        [/#if]

                    </ul>
                </li>
                <li><a>Заявки</a>
                    <ul>
                        [#if credit??]
                            [#if priorRepaymentApplication??]
                                [#switch priorRepaymentApplication.acceptance.name()]
                                    [#case "Accepted"]
                                        <li><a href="[@spring.url '/account/prior/${priorRepaymentApplication.id}/process'/]">Оформить предварительное погашение кредита</a> </li>
                                    [#break ]
                                    [#case "InProcess"]
                                        <li><a href="[@spring.url '/account/prior/${priorRepaymentApplication.id}/view'/]">Проверить заявку на предварительное погашение кредита</a> </li>
                                    [#break ]
                                [/#switch]
                            [#elseif credit.product.prior.name()!="NotAvailable" && !prolongationApplication?? && !credit.priorRepayment]
                                <li><a href="[@spring.url '/account/prior'/]">Создать заявку на предварительное погашение кредита</a></li>
                            [/#if]
                            [#if prolongationApplication??]
                                [#switch prolongationApplication.acceptance.name()]
                                    [#case "Accepted"]
                                        <li><a href="[@spring.url '/account/prolongation/${prolongationApplication.id}/process'/]">Оформить пролонгацию кредита</a> </li>
                                    [#break]
                                    [#case "InProcess"]
                                        <li><a href="[@spring.url '/account/prolongation/${prolongationApplication.id}/view'/]">Проверить заявку на пролонгацию кредита</a> </li>
                                    [#break]
                                [/#switch]
                            [#elseif !priorRepaymentApplication?? && !credit.priorRepayment]
                                <li><a href="[@spring.url '/account/prolongation'/]">Создать заявку на пролонгацию кредита кредита</a></li>
                            [/#if]
                        [#else ]
                            [#if creditApplication??]
                                [#switch creditApplication.acceptance.name()]
                                    [#case "Accepted"]
                                        <li><a href="[@spring.url '/account/credit/application/${creditApplication.id}/process'/]">Оформить кредит по заявке</a> </li>
                                    [#break]
                                    [#case "InProcess"]
                                        <li><a href="[@spring.url '/account/credit/application/${creditApplication.id}/view'/]">Проверить статус заявки на предоставление кредита</a> </li>
                                    [#break]
                                [/#switch]
                            [#else]
                                <li><a href="[@spring.url '/account/credit/application/'/]">Создать заявку на предоставление кредита</a></li>
                            [/#if]
                        [/#if]
                    </ul>
                </li>
                <li><a>Просмотр уведомлений</a>
                    <ul>
                        [#if notification??]<li><a href="[@spring.url '/account/notification/${notification.id}/view'/]">Крайнее непросмотренное уведомление</a></li>[/#if]
                        <li><a href="[@spring.url '/account/notification/list?viewed=false'/]">Непросомтренные уведомления</a></li>
                        <li><a href="[@spring.url '/account/notification/list?viewed=true'/]">История уведомлений</a></li>
                    </ul>
                </li>
                <li><a>Просмотр заявок</a>
                    <ul>
                        <li><a href="[@spring.url '/account/credit/application/list?processed=true'/]">На предоставление кредита</a></li>
                        <li><a href="[@spring.url '/account/prior/list?processed=true'/]">На предварительное погашение кредита</a></li>
                        <li><a href="[@spring.url '/account/prolongation/list?processed=true'/]">На пролонгацию кредита кредита</a></li>
                    </ul>
                </li>
                <li><a>Операции с клиентом</a>
                    <ul>
                        <li><a href="[@spring.url '/account/change'/]">Изменить личные данные</a></li>
                        <li><a href="[@spring.url '/account?change=true'/]">Выбрать другого клиента</a></li>
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