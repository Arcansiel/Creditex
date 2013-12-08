[#ftl]
[#-- @ftlvariable name="isError" type="java.lang.String" --]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Main page"]

    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.account_manager/]
        <div class="form-action">
            <p class="name">Введите регистационные данные</p>
            <form action="" method="post" class="form">
                <p>
                    <label for="username_field">Логин пользователя</label>
                    <input type="text" id="username_field" name="username">
                </p>
                <p>
                    <label for="password_field">Пароль</label>
                    <input type="password" id="password_field" name="password">
                </p>
                <p>
                    <label for="repeatPassword_field">Повторите пароль</label>
                    <input type="password" id="repeatPassword_field" name="repeatPassword">
                </p>
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
                <p>
                    <label for="workName_field">Место работы</label>
                    <input type="text" id="workName_field" name="workName">
                </p>
                <p>
                    <label for="workPosition_field">Занимаемая позиция</label>
                    <input type="text" id="workPosition_field" name="workPosition">
                </p>
                <p>
                    <label for="workIncome_field">Доход</label>
                    <input type="text" id="workIncome_field" name="workIncome">
                </p>
                <p>
                    <label for="role_selector">Выполняемая в системе роль</label>
                    <select name="role" id ="role_selector">
                        <option value="ROLE_CLIENT">Клиент</option>
                        <option value="ROLE_ACCOUNT_MANAGER">Специалист по работе с клиентами</option>
                        <option value="ROLE_SECURITY_MANAGER">Служба безопасности</option>
                        <option value="ROLE_COMMITTEE_MANAGER">Член комитета</option>
                        <option value="ROLE_DEPARTMENT_HEAD">Глава отдела</option>
                        <option value="ROLE_OPERATION_MANAGER">Операционист</option>
                    </select>
                </p>

                <p class="a-center"><button type="submit" class="button">Зарегистрировать</button></p>
            </form>
            [#if isError??]
                <p>${isError}</p>
            [/#if]
        </div>
    </div>

    [/@creditex.body]
[/@creditex.root]