[#ftl]
[#-- @ftlvariable name="user" type="org.kofi.creditex.model.User" --]
[#-- @ftlvariable name="isError" type="java.lang.String" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Изменить регистрационные данные"]
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
                       equalTo:"#inputPassword"
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
                       required:true
                   },
                   workPosition:{
                       required:true
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

        <div class="form-action">
            <p class="name">Введите регистационные данные</p>
            <form class="form-horizontal" role="form" id="change_form" action="" method="post">
                <input name="username" value="${user.username}" type="hidden">
                <div class="form-group">
                    <label for="inputPassword" class="col-sm-4 control-label">Пароль</label>
                    <div class="col-xs-6">
                        <input type="password" class="form-control" id="inputPassword" placeholder="Пароль" name="changePassword">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputRepeatPassword" class="col-sm-4 control-label">Повторите пароль</label>
                    <div class="col-xs-6">
                        <input type="password" class="form-control" id="inputRepeatPassword" placeholder="Повторите пароль" name="changeRepeatPassword">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputFirst" class="col-sm-4 control-label">Имя</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="inputFirst" placeholder="Имя пользователя" name="first" value="${user.userData.first}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputLast" class="col-sm-4 control-label">Фамилия</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="inputLast" placeholder="Фамилия пользователя" name="last" value="${user.userData.last}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPatronymic" class="col-sm-4 control-label">Отчество</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="inputPatronymic" placeholder="Отчество пользователя" name="patronymic" value="${user.userData.patronymic}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputSeries" class="col-sm-4 control-label">Серия паспорта</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="inputSeries" placeholder="Серия паспорта" name="series" value = "${user.userData.passportSeries}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputNumber" class="col-sm-4 control-label">Номер паспорта</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="inputNumber" placeholder="Номер паспорта" name="number" value="${user.userData.passportNumber}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputWorkName" class="col-sm-4 control-label">Место работы</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="inputWorkName" placeholder="Место работы" name="workName" value="${user.userData.workName}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputWorkPosition" class="col-sm-4 control-label">Занимаемая должность</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="inputWorkPosition" placeholder="Занимаемая должность" name="workPosition" value="${user.userData.workPosition}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputWorkIncome" class="col-sm-4 control-label">Доход</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="inputWorkIncome" placeholder="Доход" name="workIncome" value="${user.userData.workIncome}">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-4 col-xs-6">
                        <button type="submit" class="button">Изменить регистрационные данные</button>
                    </div>
                </div>
            </form>
            [#if isError??]
                <p>${isError}</p>
            [/#if]
        </div>
        <div class="content">
            <ul class="nav-menu">
                <li><a onclick="history.go(-1)">Вернуться назад</a>
                </li>
            </ul>
        </div>
    </div>

    [/@creditex.body]
[/@creditex.root]