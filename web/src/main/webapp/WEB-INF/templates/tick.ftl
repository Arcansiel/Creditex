[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]
[@creditex.root]
    [@creditex.head "Ручной тик"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.goback /]
        <div class="data-table">
            <p class="name"><a href="[@spring.url '/'/]">На главную страницу</a></p>
            [#if date??]
                <p class="name">Текущая дата : ${date?html}</p>
            [/#if]
            <p class="name"><a href="[@spring.url '/tick/do/'/]">Инкремент даты на один день (тик)</a></p>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]