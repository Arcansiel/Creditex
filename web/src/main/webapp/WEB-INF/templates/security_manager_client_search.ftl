[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]
[#import "creditex_data.ftl" as creditex_data]
[@creditex.root]
    [@creditex.head "Служба безопасности / поиск клиента"]
        [@creditex_data.user_search_form_validation /]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.security_manager /]
        [@creditex.goback /]
        <div class="form-action">
            <p class="name"><a href="[@spring.url '/security_manager/'/]">На главную страницу</a></p>
            [#include "creditex_error_info.ftl" /]
            <p class="name">Введите данные клиента</p>
            [@creditex_data.user_search_form "post" "" "Проверка клиента" /]
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]