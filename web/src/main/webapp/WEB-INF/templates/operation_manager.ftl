[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]
[#import "creditex_data.ftl" as creditex_data]
[@creditex.root]
    [@creditex.head "Операционист"]
        [@creditex_data.user_search_form_validation /]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.operation_manager /]
        [@creditex.goback /]
        <div class="form-action">
            [#include "creditex_error_info.ftl" /]
            <p class="name">Введите данные клиента</p>
            [@creditex_data.user_search_form "post" "" "Подтвердить" /]
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]