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
                                required:true
                            },
                            last:{
                                required:true
                            },
                            patronymic:{
                                required:true
                            },
                            passportSeries:{
                                required:true,
                                minlength:2,
                                maxlength:2
                            },
                            passportNumber:{
                                required:true,
                                min:1
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
            <form action="[@spring.url '/change_user_data/process/'/]" method="post" class="form" id="changeDataForm">
                <input type="hidden" name="id" value="${data.id}">
                <p>
                    <label for="name_field">Имя</label>
                    <input type="text" id="name_field" name="first" value="${data.first}">
                </p>
                <p>
                    <label for="last_field">Фамилия</label>
                    <input type="text" id="last_field" name="last" value="${data.last}">
                </p>
                <p>
                    <label for="patronymic_field">Отчество</label>
                    <input type="text" id="patronymic_field" name="patronymic" value="${data.patronymic}">
                </p>
                <p>
                    <label for="series_field">Серия паспорта</label>
                    <input type="text" id="series_field" name="passportSeries" value="${data.passportSeries}">
                </p>
                <p>
                    <label for="number_filed">Номер паспорта</label>
                    <input type="text" id="number_filed" name="passportNumber" value="${data.passportNumber}">
                </p>
                <p>
                    <label for="work_name_filed">Место работы</label>
                    <input type="text" id="work_name_filed" name="workName" value="${data.workName}">
                </p>
                <p>
                    <label for="position_filed">Занимаемая должность</label>
                    <input type="text" id="position_filed" name="workPosition" value="${data.workPosition}">
                </p>
                <p>
                    <label for="income_filed">Доход</label>
                    <input type="text" id="income_filed" name="workIncome" value="${data.workIncome}">
                </p>
                <p class="a-center"><button type="submit" class="button">Обработать</button></p>
            </form>
            [#if isError??]
                <p>${isError}</p>
            [/#if]
        </div>
        <div class="content">
            <ul class="nav-menu">
                <li><a href="[@spring.url '/account_manager/client/'/]">Вырнуться назад</a>
                </li>
            </ul>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]