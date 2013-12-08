[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Main page"]

    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        <div class="form-action">
            <p class="name">Введите данные о предварительном возврате кредита</p>
            <form action="[@spring.url '/account_manager/client/prior/add/process/'/]" method="post" class="form">
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