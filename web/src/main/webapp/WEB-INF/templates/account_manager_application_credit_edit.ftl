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
                    <label for="name_field" class="col-sm-10">Имя</label>
                    <input type="text" id="name_field" name="first">
                </p>
                <p>
                    <label for="last_field" class="col-sm-10">Фамилия</label>
                    <input type="text" id="last_field" name="last">
                </p>
                <p>
                    <label for="patronymic_field" class="col-sm-10">Отчество</label>
                    <input type="text" id="patronymic_field" name="patronymic">
                </p>
                <p>
                    <label for="series_field" class="col-sm-10">Серия паспорта</label>
                    <input type="text" id="series_field" name="series">
                </p>
                <p>
                    <label for="number_filed" class="col-sm-10">Номер паспорта</label>
                    <input type="text" id="number_filed" name="number">
                </p>
                <p class="a-center"><button type="submit" class="button">Обработать</button></p>
            </form>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]