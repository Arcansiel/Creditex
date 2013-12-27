[#ftl]
[#import "creditex.ftl" as creditex]
[#import "l_data.ftl" as l_data]
[@creditex.root]
    [@creditex.head "Кредиты клиента"]
        [@creditex.tableProcess "listtable" "list" 10 /]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.security_manager /]
        [@creditex.goback /]
        <div class="data-table">
            <p class="page-link"><a href="[@spring.url '/security_manager/'/]">На главную страницу</a></p>

            [#if client??]
                [@l_data.client_view_table client /]
            [/#if]


                <p class="name">Кредиты клиента</p>
            <div class="holder"></div>
            <table id="listtable">
                    <thead>
                    <tr>
                        <th class="name">ID</th>
                        <th class="name">Продукт</th>
                        <th class="start_date">Начало кредитования</th>
                        <th class="duration">Длительность</th>
                        <th class="start_date">Конец кредитования</th>
                        <th class="amount">Сумма кредита</th>
                        <th class="amount">Основной долг</th>
                        <th class="amount">Процентный долг</th>
                        <th class="amount">Долг по платежам</th>
                        <th class="amount">Пеня</th>
                        <th class="name">Активный кредит</th>
                        <th class="name">Были просроченные платежи</th>
                        <th class="name">Возвращён не вовремя</th>
                    </tr>
                    </thead>
                    <tbody id="list">
                        [#if credits??][#list credits as credit]
                        <tr>
                            <td class="name">${credit.id}</td>
                            <td class="name">${credit.product.name?html}</td>
                            <td class="start_date">${credit.creditStart}</td>
                            <td class="duration">${credit.duration}</td>
                            <td class="start_date">${credit.creditEnd}</td>
                            <td class="amount">${credit.originalMainDebt}</td>
                            <td class="amount">${credit.currentMainDebt}</td>
                            <td class="amount">${credit.currentPercentDebt}</td>
                            <td class="amount">${credit.mainFine}</td>
                            <td class="amount">${credit.percentFine}</td>
                            <td class="name">${credit.running?c}</td>
                            <td class="name">${credit.expired?c}</td>
                            <td class="name">${credit.unreturned?c}</td>
                        </tr>
                    [/#list][/#if]
                    </tbody>
                </table>

        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]