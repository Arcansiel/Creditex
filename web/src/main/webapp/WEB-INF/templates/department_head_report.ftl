[#ftl]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Отчёт"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.department_head /]
        [@creditex.goback /]
        <div class="data-table">
            <p class="name"><a href="[@spring.url '/department_head/'/]">На главную страницу</a></p>
            <p class="name">Отчёт</p>

        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]