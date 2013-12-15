[#ftl]
[#-- @ftlvariable name="application" type="org.kofi.creditex.model.PriorRepaymentApplication" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Main page"]

    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        <div class="data-table">
            <p class="name">Данные по кредиту</p>
            <table>
                <tr>
                    <th>Параметр</th>
                    <th>Данные</th>
                </tr>
                <tr>
                    <td>Дата подачи заявки</td>
                    <td>${application.applicationDate}</td>
                </tr>
                <tr>
                    <td>Комментарий</td>
                    <td>${application.comment}</td>
                </tr>
                <tr>
                    <td>Принята ли заявка</td>
                    <td>${application.acceptance}</td>
                </tr>
                <tr>
                    <td>Кредит</td>
                    <td><a href="[@spring.url '/account_manager/client/credit/view/${application.credit.id}/'/]">Просмотреть</a></td>
                </tr>
            </table>
        </div>
        <div class="content">
            <ul class="nav-menu">
                <li><a href="[@spring.url '/account_manager/client/'/]" id="foo">Вернуться назад</a></li>
                <li><a href="[@spring.url '/account_manager/client/prior/abort/${application.id}/'/]">Отменить заявку</a></li>
            </ul>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]