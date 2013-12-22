[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]
[#import "l_data.ftl" as l_data]
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

            [#if credit??]

                [#if (credit.user)??]
                    [@l_data.client_view_table credit.user "Клиент"/]
                    <p class="name">
                        <a href="[@spring.url '/security_manager/client/check/${credit.user.id?string("0")}'/]">Проверка клиента по внутренним базам</a>
                    </p>
                [/#if]

                [@l_data.credit_view_table credit "Кредит" true true true /]

            [#if credit_notifications_count??]
                <p class="name">Количество уведомлений по данному кредиту: ${credit_notifications_count}</p>
            [/#if]

                <div class="form-action">
                    <a id="notification_form_anchor" />
                    <form action="[@spring.url '/security_manager/notification/credit/${credit.id?string("0")}'/]"
                          method="post"
                          class="form"
                          id="notificationForm">
                        <p class="name">Уведомление клиента</p>
                        <p>
                            <label>Тип уведомления</label>
                            <select name="type">
                                [#if type?? && type.name() == "Unreturned"]
                                    <option value="Unreturned">Невозвращённый кредит</option>
                                    <option value="Expired">Просроченный платёж</option>
                                [#else]
                                    <option value="Expired">Просроченный платёж</option>
                                    <option value="Unreturned">Невозвращённый кредит</option>
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
                    [@l_data.product_view_table credit.product "Сведения о кредитном продукте"/]
                [/#if]

            [/#if]

        </div>

    </div>
    [/@creditex.body]
[/@creditex.root]