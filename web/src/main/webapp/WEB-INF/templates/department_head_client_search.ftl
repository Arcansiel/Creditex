[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]
[#import "creditex_data.ftl" as creditex_data]
[@creditex.root]
    [@creditex.head "ГКО / поиск клиента"]
        [@creditex_data.user_search_form_validation /]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.department_head /]
        [@creditex.goback /]
        <div class="form-action">
            <p class="name"><a href="[@spring.url '/department_head/'/]">На главную страницу</a></p>
            <p class="name">Введите данные клиента</p>
            [@creditex_data.user_search_form "post" "" "Поиск клиента" /]
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]