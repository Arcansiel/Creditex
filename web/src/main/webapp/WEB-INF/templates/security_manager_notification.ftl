[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]
[#import "creditex_data.ftl" as creditex_data]
[@creditex.root]
    [@creditex.head "Уведомление"]
        [@creditex.addValidator/]
    <script type="text/javascript">
        $(function(){
            $("#notificationForm").validate(
                    {
                        rules:{
                            type: {
                                required: true
                            },
                            message: {
                                required: false,
                                maxlength: 4000
                            }
                        }
                    }
            );
        });
    </script>
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.security_manager /]
        [@creditex.goback /]

        <div class="data-table">
            <p class="name"><a href="[@spring.url '/security_manager/'/]">На главную страницу</a></p>
            <p class="name"><a href="[@spring.url '/security_manager/credits/expired/'/]">Кредиты с задержкой платежей</a></p>
            <p class="name"><a href="[@spring.url '/security_manager/credits/unreturned/'/]">Невозвращённые кредиты</a></p>

            [#if client?? && credit??]

                [@creditex_data.client_view_table client "Клиент"/]

                [@creditex_data.credit_view_table credit "Кредит"/]

                <div class="form-action">
                    <form action="[@spring.url '/security_manager/notification/client/${client.id?string("0")}/credit/${credit.id?string("0")}'/]"
                          method="post"
                          class="form"
                          id="notificationForm">
                        <p class="name">Уведомление клиента</p>
                        <p>
                            <label>Тип уведомления</label>
                            <select name="type">
                                [#if type?? && type.name() == "Expired"]
                                    <option value="Expired">Просроченный платёж</option>
                                    <option value="Unreturned">Невозвращённый кредит</option>
                                [#else]
                                    <option value="Unreturned">Невозвращённый кредит</option>
                                    <option value="Expired">Просроченный платёж</option>\
                                [/#if]
                            </select>
                        </p>
                        <p>
                            <label>Сообщение</label>
                            <textarea name="message" ></textarea>
                        </p>
                        <p class="a-center"><button type="submit" class="button">Отправить уведомление</button></p>
                    </form>
                </div>


                [#if (credit.product)??]
                    [@creditex_data.product_view_table credit.product "Сведения о кредитном продукте"/]
                [/#if]

            [/#if]

        </div>

    </div>
    [/@creditex.body]
[/@creditex.root]