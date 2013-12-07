[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]
[@creditex.root]
    [@creditex.head "Main page"]

    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.account_manager/]
        <div class="form-action">
            <p class="name">Введите данные клиента</p>
            <form action="" method="post" class="form">
                <p>
                    <label for="name_field" class="col-sm-10">Имя</label>
                    <input type="text" id="name_field" name="first" value="${data.first}">
                </p>
                <p>
                    <label for="last_field" class="col-sm-10">Фамилия</label>
                    <input type="text" id="last_field" name="last" value="${data.last}">
                </p>
                <p>
                    <label for="patronymic_field" class="col-sm-10">Отчество</label>
                    <input type="text" id="patronymic_field" name="patronymic" value="${data.patronymic}">
                </p>
                <p>
                    <label for="series_field" class="col-sm-10">Серия паспорта</label>
                    <input type="text" id="series_field" name="series" value="${data.passportSeries}">
                </p>
                <p>
                    <label for="number_filed" class="col-sm-10">Номер паспорта</label>
                    <input type="text" id="number_filed" name="number" value="${data.passportNumber}">
                </p>
                <p>
                    <label for="work_name_filed" class="col-sm-10">Место работы</label>
                    <input type="text" id="work_name_filed" name="workName" value="${data.workName}">
                </p>
                <p>
                    <label for="position_filed" class="col-sm-10">Занимаемая должность</label>
                    <input type="text" id="position_filed" name="workPosition" value="${data.workPosition}">
                </p>
                <p>
                    <label for="income_filed" class="col-sm-10">Доход</label>
                    <input type="text" id="income_filed" name="workIncome" value="${data.workIncome}">
                </p>
                <p class="a-center"><button type="submit" class="button">Обработать</button></p>
            </form>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]