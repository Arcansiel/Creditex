[#ftl]
[#-- @ftlvariable name="data" type="org.kofi.creditex.model.UserData" --]
[#-- @ftlvariable name="isError" type="String" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]
[@creditex.root]
    [@creditex.head "Main page"]
        [@creditex.includeBootstrapCss/]
        [@creditex.addValidator/]
    <script type="text/javascript">
        $(function(){
            $("#changeDataForm").validate(
                    {
                        rules:{
                            first:{
                                required:true,
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
                            passportSeries:{
                                required:true,
                                passportseries:true,
                                minlength:2,
                                maxlength:2
                            },
                            passportNumber:{
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
                                required:true
                            }
                        }
                    }
            );
        });
    </script>
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.account_manager/]
        <div class="form-action">
            <p class="name">Введите данные клиента</p>
            <form class="form-horizontal" role="form" id="changeDataForm" action="" method="post">
                <input type="hidden" name="id" value="${data.id}">
                <div class="form-group">
                    <label for="inputFirst" class="col-sm-4 control-label">Имя</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="inputFirst" placeholder="Имя клиента" name="first" value="${data.first}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputLast" class="col-sm-4 control-label">Фамилия</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="inputLast" placeholder="Фамилия клиента" name="last" value="${data.last}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPatronymic" class="col-sm-4 control-label">Отчество</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="inputPatronymic" placeholder="Отчество клиента" name="patronymic" value="${data.patronymic}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputSeries" class="col-sm-4 control-label">Серия паспорта</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="inputSeries" placeholder="Серия паспорта" name="passportSeries" value="${data.passportSeries}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputNumber" class="col-sm-4 control-label">Номер паспорта</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="inputNumber" placeholder="Номер паспорта" name="passportNumber" value="${data.passportNumber}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputWorkName" class="col-sm-4 control-label">Место работы</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="inputWorkName" placeholder="Место работы" name="workName" value="${data.workName}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputWorkPosition" class="col-sm-4 control-label">Занимаемая должность</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="inputWorkPosition" placeholder="Занимаемая должность" name="workPosition" value="${data.workPosition}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputWorkIncome" class="col-sm-4 control-label">Доход</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="inputWorkIncome" placeholder="Доход" name="workIncome" value="${data.workIncome}">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-4 col-xs-6">
                        <button type="submit" class="button">Обработать</button>
                    </div>
                </div>
            </form>
            [#if isError??]
                <p>${isError}</p>
            [/#if]
        </div>
        <div class="content">
            <ul class="nav-menu">
                <li><a href="[@spring.url '/account/'/]">Вернуться назад</a>
                </li>
            </ul>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]