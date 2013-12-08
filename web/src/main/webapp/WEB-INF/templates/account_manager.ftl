[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Специалист по работе с клиентами"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.account_manager/]
        <div class="form-action">
            <p class="name">Введите данные клиента</p>
            <form action="[@spring.url '/account_manager/process/'/]" method="post" class="form">
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
                    <input type="text" id="series_field" name="series">
                </p>
                <p>
                    <label for="number_filed">Номер паспорта</label>
                    <input type="text" id="number_filed" name="number">
                </p>
                <p class="a-center"><button type="submit" class="button">Обработать</button></p>
            </form>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]