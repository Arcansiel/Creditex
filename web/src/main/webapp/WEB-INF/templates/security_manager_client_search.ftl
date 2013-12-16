[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Служба безопасности / поиск клиента"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">

        <div class="form-action">
            [#if error??]
                <p>${error?html}</p>
            [/#if]
            <p class="name"><a href=[@spring.url "/security_manager/"/]>Назад на главную страницу</a></p>
            <p class="name">Введите данные клиента</p>
            <form action="" method="post" class="form">
                <p>
                    <label for="name_field">Имя</label>
                    <input type="text" id="name_field" name="first">
                </p>
                <p>
                    <label for="last_field">Фамилия</label>
                    <input type="text" id="last_field" name="last">
                </p>
                <p>
                    <label for="patronymic_field">Отчество</label>
                    <input type="text" id="patronymic_field" name="patronymic">
                </p>
                <p>
                    <label for="series_field">Серия паспорта</label>
                    <input type="text" id="series_field" name="passportSeries">
                </p>
                <p>
                    <label for="number_filed">Номер паспорта</label>
                    <input type="text" id="number_filed" name="passportNumber">
                </p>
                <p class="a-center"><button type="submit" class="button">Проверка клиента</button></p>
            </form>
        </div>

    </div>
    [/@creditex.body]
[/@creditex.root]