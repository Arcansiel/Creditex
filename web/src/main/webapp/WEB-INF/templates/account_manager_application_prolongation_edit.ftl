[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]
[@creditex.root]
    [@creditex.head "Main page"]

    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        <div class="form-action">
            <p class="name">Введите данные о пролонгации кредита</p>
            <form action="[@spring.url '/account_manager/client/prolongation/add/process/'/]" method="post" class="form">
                [#--<input type="hidden" name="id" value="0"/>--]
                [#--<input type="hidden" name="date" value=""/>--]
                [#--<input type="hidden" name="acceptance" value=""/>--]
                [#--<input type="hidden" name="creditId" value="0"/>--]
                <p>
                    <label for="duration_field">Срок пролонгации(мес)</label>
                    <input type="text" id="duration_field" name="duration">
                </p>
                <p>
                    <label for="comment_field">Комментарий</label>
                    <input type="text" id="comment_field" name="comment">
                </p>
                <p class="a-center"><button type="submit" class="button">Обработать</button></p>
            </form>
        </div>
        <div class="content">
            <ul class="nav-menu">
                <li><a href="[@spring.url '/account_manager/client/'/]">Вырнуться назад</a>
                </li>
            </ul>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]