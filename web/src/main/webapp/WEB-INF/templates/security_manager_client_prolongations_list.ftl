[#ftl]
[#import "creditex.ftl" as creditex]
[#import "l_data.ftl" as l_data]
[@creditex.root]
    [@creditex.head "Заявки клиента на пролонгацию клиента"]
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


                <p class="name">Завки клиента на пролонгацию</p>
            <div class="holder"></div>
            <table id="listtable">
                    <thead>
                    <tr>
                        <th class="start_date">Дата</th>
                        <th class="name">ID кредита</th>
                        <th class="duration">Длительность пролонгации</th>
                        <th class="name">Удовлетворена</th>
                        <th class="comment">Комментарий</th>
                    </tr>
                    </thead>
                    <tbody id="list">
                        [#if prolongations??][#list prolongations as prolongation]
                        <tr>
                            <td class="start_date">${prolongation.applicationDate}</td>
                            <td class="name">${prolongation.credit.id}</td>
                            <td class="duration">${prolongation.duration}</td>
                            <td class="name">[#if (prolongation.acceptance)??]${prolongation.acceptance?html}[/#if]</td>
                            <td class="comment">${prolongation.comment?html}</td>
                        </tr>
                    [/#list][/#if]
                    </tbody>
                </table>


        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]