[#ftl]
[#import "creditex.ftl" as creditex]
[#import "l_data.ftl" as l_data]
[@creditex.root]
    [@creditex.head "Статистика по клиету"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.committee_manager /]
        <div class="data-table">
            [#include "statistics_client.ftl" /]
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]