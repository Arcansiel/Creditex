[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]
[#import "l_data.ftl" as l_data]
[@creditex.root]
    [@creditex.head "ГКО / поиск клиента"]
        [@creditex.includeBootstrap /]
        [@l_data.user_search_form_validation /]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.department_head /]
        [@creditex.goback /]
        <div class="form-action">
            <p class="name"><a href="[@spring.url '/department_head/'/]">На главную страницу</a></p>
            [#include "l_error_info.ftl" /]
            <p class="name">Введите данные клиента</p>
            [@l_data.user_search_form "post" "" "Поиск клиента" /]
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]