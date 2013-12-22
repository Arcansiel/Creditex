[#ftl]
[#-- @ftlvariable name="application" type="org.kofi.creditex.model.Application" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Просмотр заявки на кредит"]
    [@creditex.includeJQuery/]
    [@creditex.includeTableCloth/]
    <script type="text/javascript">
        $(function(){
            [@creditex.sorting table="creditTable" theme = "default" sortable=false class = "data-table"/]
        });
    </script>
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.account_manager/]
        <div class="data-table">
            <p class="name">Данные по кредиту</p>
            <table id="creditTable">
                [#if application??]
                    <tr>
                        <th>Параметр</th>
                        <th>Данные кредита</th>
                    </tr>
                    <tr>
                        <td>Дата подачи заявки</td>
                        <td>${application.applicationDate}</td>
                    </tr>
                    <tr>
                        <td>Требуемая сумма денег</td>
                        <td>${application.request}</td>
                    </tr>
                    <tr>
                        <td>Длительность кредита</td>
                        <td>${application.duration}</td>
                    </tr>
                    <tr>
                        <td>Кредитный продукт</td>
                        <td><a href="[@spring.url '/account/product/${application.product.id}/view'/]">${application.product.name}</a></td>
                    </tr>
                    <tr>
                        <td>Принята ли заявка</td>
                        <td>${application.acceptance}</td>
                    </tr>

                    [#if application.acceptance.name()="Rejected"]
                        [#if application.securityAcceptance.name()="Rejected"]
                            [#assign whoRejected="Специалист службы безопасности"/]
                            [#assign whyRejected=application.securityComment/]
                        [/#if]
                        [#if application.committeeAcceptance.name()="Rejected"]
                            [#assign whoRejected="Кредитный комитет"/]
                            [#assign whyRejected="${application.voteAcceptance}/${application.voteRejection}"/]
                        [/#if]
                        [#if application.headAcceptance.name()="Rejected"]
                            [#assign whoRejected="Глава кредитного комитета"/]
                            [#assign whyRejected=application.headComment/]
                        [/#if]
                        <tr>
                            <td>Кто отверг заявку</td>
                            <td>${whoRejected}</td>
                        </tr>
                        <tr>
                            <td>Причина отвержения</td>
                            <td>${whyRejected}</td>
                        </tr>
                    [/#if]
                    [#if application.acceptance.name()="Accepted"]
                        <tr>
                            <td>Кредит</td>
                            <td><a href="[@spring.url '/account/credit/${application.credit.id}/view'/]">Просмотреть</a></td>
                        </tr>
                    [/#if]
                [/#if]
            </table>
        </div>
        <div class="content">
            <ul class="nav-menu">
                <li><a href="[@spring.url '/account'/]" id="foo">Вернуться назад</a></li>
                [#if application??]<li><a href="[@spring.url '/account/credit/application/${application.id}/abort'/]">Отменить заявку</a></li>[/#if]
            </ul>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]