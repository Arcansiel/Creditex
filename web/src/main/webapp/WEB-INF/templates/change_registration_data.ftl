[#ftl]
[#-- @ftlvariable name="user" type="org.kofi.creditex.model.User" --]
[#-- @ftlvariable name="isError" type="java.lang.String" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Main page"]
        [@creditex.includeBootstrapCss/]
        [@creditex.addValidator/]
    <script type="text/javascript">
        $(function(){
           $("#change_form").validate({
               rules:{
                   changePassword:{
                       minlength:8,
                       maxlength:46,
                       nowhitespace:true
                   },
                   changeRepeatPassword:{
                       equalTo:"#password_field"
                   },
                   first:{
                       required: true,
                       nowhitespace:true
                   },
                   last:{
                       required:true,
                       nowhitespace:true
                   },
                   patronymic:{
                       required:true,
                       nowhitespace:true
                   },
                   series:{
                       required:true,
                       passportseries:true
                   },
                   number:{
                       required:true,
                       min:1,
                       max:9999999
                   },
                   workName:{
                       required:true,
                       nowhitespace:true
                   },
                   workPosition:{
                       required:true,
                       nowhitespace:true
                   },
                   workIncome:{
                       required:true,
                       min:0
                   }
               },
               messages:{
                   first:{
                       nowhitespace:"Пробелы и спецсимволы недопустимы"
                   },
                   last:{
                       nowhitespace:"Пробелы и спецсимволы недопустимы"
                   },
                   patronymic:{
                       nowhitespace:"Пробелы и спецсимволы недопустимы"
                   },
                   series:{
                       lettersonly:"Введите буквенную комбинацию"
                   }
               }
           });
        });
    </script>
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.account_manager/]
        <div class="form-action">
            <p class="name">Введите регистационные данные</p>
            <form action="" method="post" id="change_form" class="form">
                <input name="username" value="${user.username}" type="hidden">
                <p>
                    <label for="password_field">Пароль</label>
                    <input type="password" id="password_field" name="changePassword">
                </p>
                <p>
                    <label for="repeatPassword_field">Повторите пароль</label>
                    <input type="password" id="repeatPassword_field" name="changeRepeatPassword">
                </p>
                <p>
                    <label for="name_field">Имя</label>
                    <input type="text" id="name_field" name="first" value="${user.userData.first}">
                </p>
                <p>
                    <label for="last_field">Фамилия</label>
                    <input type="text" id="last_field" name="last" value="${user.userData.last}">
                </p>
                <p>
                    <label for="patronymic_field">Отчество</label>
                    <input type="text" id="patronymic_field" name="patronymic" value="${user.userData.patronymic}">
                </p>
                <p>
                    <label for="series_field">Серия паспорта</label>
                    <input type="text" id="series_field" name="series" value = "${user.userData.passportSeries}">
                </p>
                <p>
                    <label for="number_filed">Номер паспорта</label>
                    <input type="text" id="number_filed" name="number" value="${user.userData.passportNumber}">
                </p>
                <p>
                    <label for="workName_field">Место работы</label>
                    <input type="text" id="workName_field" name="workName" value="${user.userData.workName}">
                </p>
                <p>
                    <label for="workPosition_field">Занимаемая позиция</label>
                    <input type="text" id="workPosition_field" name="workPosition" value="${user.userData.workPosition}">
                </p>
                <p>
                    <label for="workIncome_field">Доход</label>
                    <input type="text" id="workIncome_field" name="workIncome" value="${user.userData.workIncome}">
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