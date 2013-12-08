[#ftl]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Main page"]

    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        <div class="form-action">
            <p class="name">Введите данные клиента</p>
            <form action="" method="post" class="form">
                <p>
                    <label for="month_field">Имя</label>
                    <input type="text" id="month_field" name="month">
                </p>
                <p>
                    <label for="reason_field">Фамилия</label>
                    <input type="text" id="reason_field" name="reason">
                </p>
                <p>
                    <label for="comment_field">Отчество</label>
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