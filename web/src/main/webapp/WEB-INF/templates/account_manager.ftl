[#ftl]
[#-- @ftlvariable name="report" type="java.lang.String" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Специалист по работе с клиентами"]
        [@creditex.includeBootstrapCss/]
        [@creditex.addValidator/]
    <script type="text/javascript">
        $(function(){
            $("#clientDataForm").validate(
                    {
                        rules:{
                            first:{
                                required: true,
                                nowhitespace:true
                            },
                            last:{
                                required: true,
                                nowhitespace:true
                            },
                            patronymic:{
                                required: true,
                                nowhitespace:true
                            },
                            passportSeries:{
                                required: true,
                                passportseries: true
                            },
                            passportNumber:{
                                required:true,
                                min:0,
                                max:9999999
                            }
                        },
                        messages:{
                            first:{
                                nowhitespace:"Введите строку без пробелов."
                            },
                            last:{
                                nowhitespace:"Введите строку без пробелов."
                            },
                            patronymic:{
                                nowhitespace:"Введите строку без пробелов."
                            },
                            passportSeries:{
                                passportseries:"Введите две заглавные латинские буквы."
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
            <form class="form-horizontal" role="form" id="clientDataForm" action="" method="post">
                <div class="form-group">
                    <label for="inputFirst" class="col-sm-4 control-label">Имя</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="inputFirst" placeholder="Имя клиента" name="first">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputLast" class="col-sm-4 control-label">Фамилия</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="inputLast" placeholder="Фамилия клиента" name="last">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputPatronymic" class="col-sm-4 control-label">Отчество</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="inputPatronymic" placeholder="Отчество клиента" name="patronymic">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputSeries" class="col-sm-4 control-label">Серия паспорта</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="inputSeries" placeholder="Серия паспорта" name="passportSeries">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputNumber" class="col-sm-4 control-label">Номер паспорта</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="inputNumber" placeholder="Номер паспорта" name="passportNumber">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-4 col-xs-6">
                        <button type="submit" class="button">Обработать</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]