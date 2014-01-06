[#ftl]
[#-- @ftlvariable name="isError" type="java.lang.String" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Регистрация сотрудника"]
        [@creditex.includeBootstrapCss/]
        [@creditex.addValidator/]
    <script type="text/javascript">
        $(function(){
            $("#registrationForm").validate(
                    {
                        rules:{
                            username: {
                                required: true,
                                nowhitespace:true,
                                minlength: 8,
                                maxlength: 46
                            },
                            password: {
                                required: true,
                                nowhitespace:true,
                                minlength: 8,
                                maxlength: 46
                            },
                            repeatPassword: {
                                required: true,
                                nowhitespace:true,
                                minlength: 8,
                                maxlength: 46,
                                equalTo: "#inputRepeatPassword"
                            },
                            first: {
                                required: true,
                                nowhitespace:true
                            },
                            last: {
                                required: true,
                                nowhitespace:true
                            },
                            patronymic:{
                                required: true,
                                nowhitespace:true
                            },
                            series:{
                                required: true,
                                passportseries:true
                            },
                            number:{
                                required: true,
                                min: 1,
                                max:9999999
                            },
                            workName:{
                                required: true
                            },
                            workPosition:{
                                required: true
                            },
                            workIncome:{
                                required: true,
                                min: 1
                            }
                        }

                    }
            );
        });
    </script>
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        <div class="form-action">
            <p class="name">Введите регистационные данные</p>
            <div class="form-action">
                <form class="form-horizontal" role="form" id="registrationForm" action="" method="post">
                    <div class="form-group">
                        <label for="inputUsername" class="col-sm-4 control-label">Логин пользователя</label>
                        <div class="col-xs-6">
                            <input type="text" class="form-control" id="inputUsername" placeholder="Логин пользователя" name="username">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputPassword" class="col-sm-4 control-label">Пароль</label>
                        <div class="col-xs-6">
                            <input type="password" class="form-control" id="inputPassword" placeholder="Пароль" name="password">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputRepeatPassword" class="col-sm-4 control-label">Повторите пароль</label>
                        <div class="col-xs-6">
                            <input type="password" class="form-control" id="inputRepeatPassword" placeholder="Повторите пароль" name="repeatPassword">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputFirst" class="col-sm-4 control-label">Имя</label>
                        <div class="col-xs-6">
                            <input type="text" class="form-control" id="inputFirst" placeholder="Имя пользователя" name="first">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputLast" class="col-sm-4 control-label">Фамилия</label>
                        <div class="col-xs-6">
                            <input type="text" class="form-control" id="inputLast" placeholder="Фамилия пользователя" name="last">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputPatronymic" class="col-sm-4 control-label">Отчество</label>
                        <div class="col-xs-6">
                            <input type="text" class="form-control" id="inputPatronymic" placeholder="Отчество пользователя" name="patronymic">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputSeries" class="col-sm-4 control-label">Серия паспорта</label>
                        <div class="col-xs-6">
                            <input type="text" class="form-control" id="inputSeries" placeholder="Серия паспорта" name="series">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="inputNumber" class="col-sm-4 control-label">Номер паспорта</label>
                        <div class="col-xs-6">
                            <input type="text" class="form-control" id="inputNumber" placeholder="Номер паспорта" name="number">
                        </div>
                    </div>
                    <input type="hidden" name="workName" id="inputWorkName" value="Creditex"/>
                    <input type="hidden" id="inputWorkPosition" name="workPosition" value=""/>
                    <input type="hidden" id="inputWorkIncome"  name="workIncome" value="0"/>
                    <div class="form-group">
                        <label for="inputRole" class="col-sm-4 control-label">Выполняемая в системе роль</label>
                        <div class="col-xs-6">
                            <select class="form-control" id="inputRole" name="role">
                                <option value="ROLE_ACCOUNT_MANAGER">Специалист по работе с клиентами</option>
                                <option value="ROLE_SECURITY_MANAGER">Служба безопасности</option>
                                <option value="ROLE_COMMITTEE_MANAGER">Член комитета</option>
                                <option value="ROLE_DEPARTMENT_HEAD">Глава отдела</option>
                                <option value="ROLE_OPERATION_MANAGER">Операционист</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-4 col-xs-6">
                            <button type="submit" class="button">Зарегистрировать</button>
                        </div>
                    </div>
                </form>
                [#if isError??]
                    <p>${isError}</p>
                [/#if]
            </div>
            <div class="content">
                <ul class="nav-menu">
                    <li><a href="[@spring.url '/admin'/]">Вернуться назад</a>
                    </li>
                </ul>
            </div>
        </div>

    [/@creditex.body]
[/@creditex.root]