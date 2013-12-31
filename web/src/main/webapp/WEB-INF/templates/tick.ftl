[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]
[@creditex.root]
    [@creditex.head "Ручной тик"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">

        <div class="data-table">
            <p class="page-link"><a href="[@spring.url '/'/]">На главную страницу</a></p>
            [#if date??]
                <p style="text-transform:uppercase; font-weight:bold; padding:10px 0" ><a href="[@spring.url '/tick/'/]">Текущая дата : ${date?html}</a></p>
            [/#if]
            <form method="post" action="[@spring.url '/tick/do/'/]">
                <label for="count_field">Количество дней (от 1 до 365)</label>
                <input id="count_field" type="text" name="count" value="1" style="width: 100px"/>
                <div class="page-link">
                    <button style="width: 309px; height: 50px" type="submit">Инкремент даты</button>
                </div>
            </form>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]