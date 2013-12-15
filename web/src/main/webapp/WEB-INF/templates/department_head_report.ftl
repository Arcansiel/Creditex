[#ftl]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Отчёт"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        <div class="form-action">
            <p class="name"><a href=[@spring.url "/department_head/"/]>Назад на главную страницу</a></p>
            <p class="name">Отчёт</p>

        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]