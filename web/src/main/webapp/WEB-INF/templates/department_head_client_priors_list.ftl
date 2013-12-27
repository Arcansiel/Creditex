[#ftl]
[#import "creditex.ftl" as creditex]
[#import "l_data.ftl" as l_data]
[@creditex.root]
    [@creditex.head "Заявки клиента на пролонгацию клиента"]
        [@creditex.tableProcess "listtable" "list" 10 /]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.department_head /]
        [@creditex.goback /]
        <p class="page-link"><a href="[@spring.url '/department_head/'/]">На главную страницу</a></p>
        <div class="data-table">

            [#if client??]
                [@l_data.client_view_table client /]
            [/#if]


                <p class="name">Завки клиента на досрочное погашение</p>
            <div class="holder"></div>
            <table id="listtable">
                    <thead>
                    <tr>
                        <th class="start_date">Дата</th>
                        <th class="name">ID кредита</th>
                        <th class="name">Удовлетворена</th>
                        <th class="comment">Комментарий</th>
                        <th class="name">Проверить</th>
                    </tr>
                    </thead>
                    <tbody id="list">
                        [#if priors??][#list priors as prior]
                        <tr>
                            <td class="start_date">${prior.applicationDate}</td>
                            <td class="name">${prior.credit.id}</td>
                            <td class="name">[#if (prior.acceptance)??]${prior.acceptance?html}[/#if]</td>
                            <td class="comment">${prior.comment?html}</td>
                            <td class="name">
                                [#if prior.acceptance.name()=="InProcess"]
                                    <a href="[@spring.url '/department_head/prior/${prior.id?string("0")}'/]">Проверить</a>
                                [/#if]
                            </td>
                        </tr>
                    [/#list][/#if]
                    </tbody>
                </table>


        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]