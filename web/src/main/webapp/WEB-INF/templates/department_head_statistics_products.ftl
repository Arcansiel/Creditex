[#ftl]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Статистика по кредитным продуктам"]
        [@creditex.tableProcess "listtable" "list" 10 /]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.department_head /]
        [@creditex.goback/]
        <p class="page-link"><a href="[@spring.url '/department_head/'/]">На главную страницу</a></p>
        <div class="data-table">
            <p class="name">Статистика по кредитным продуктам</p>
            <p class="name">Общая статистика</p>
            <table>
                <thead>
                    <th class="name">Всего кредитных продуктов</th>
                    <th class="name">Активных продуктов</th>
                    <th class="name">Неактивных продуктов</th>
                </thead>
                <tbody>
                    <td class="name">[#if countAll??]${countAll}[/#if]</td>
                    <td class="name">[#if countActive??]${countActive}[/#if]</td>
                    <td class="name">[#if countInActive??]${countInActive}[/#if]</td>
                </tbody>
            </table>
            <p class="name">Статистика по отдельным кредитным продуктам</p>
            <div class="holder"></div>
            <table id="listtable">
                <thead>
                <tr>
                    <th class="name">ID продукта</th>
                    <th class="name">Название</th>
                    <th class="name">Активный</th>
                    <th class="name">Всего кредитов</th>
                    <th class="name">Открытые кредиты</th>
                    <th class="name">Погашеные кредиты</th>
                    <th class="name">Всего заявок</th>
                    <th class="name">Отклонённых заявок</th>
                    <th class="name">Отменённых заявок</th>
                    <th class="name">Заявок в рассмотрении</th>
                </tr>
                </thead>
                <tbody id="list">
                    [#if statistics??][#list statistics as item]
                    <tr>
                        <td class="name">${item.product.id?string("0")}</td>
                        <td class="name">${item.product.name?html}</td>
                        <td class="name">${item.product.active?c}</td>
                        <td class="name">${item.countCredits}</td>
                        <td class="name">${item.countActiveCredits}</td>
                        <td class="name">${item.countClosedCredits}</td>
                        <td class="name">${item.countApplications}</td>
                        <td class="name">${item.countRejectedApplications}</td>
                        <td class="name">${item.countAbortedApplications}</td>
                        <td class="name">${item.countInProcessApplications}</td>
                    </tr>
                    [/#list][/#if]
                </tbody>
            </table>

        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]