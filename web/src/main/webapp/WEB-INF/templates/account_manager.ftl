[#ftl]
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
            <form action="" method="post" class="form" id="clientDataForm">
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
                <p class="a-center"><button type="submit" class="button">Обработать</button></p>
            </form>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]