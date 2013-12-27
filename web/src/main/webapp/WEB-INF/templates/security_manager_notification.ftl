[#ftl]
[#-- @ftlvariable name="credit" type="org.kofi.creditex.model.Credit" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]
[#import "l_data.ftl" as l_data]
[@creditex.root]
    [@creditex.head "Уведомление"]
    [@creditex.addValidator/]
    [@creditex.includeBootstrapCss/]
    [@creditex.includeTableCloth/]
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
            [@creditex.sorting table="clientTable" theme = "default" sortable=false class = "data-table"/]
            [@creditex.sorting table="creditTable" theme = "default" sortable=false class = "data-table"/]
            [@creditex.sorting table="productTable" theme = "default" sortable=false class = "data-table"/]
        });
    </script>
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.security_manager /]
        [@creditex.goback]
            <li><a href="[@spring.url '/security_manager/'/]">На главную страницу</a></li>
            <li><a href="[@spring.url '/security_manager/credits/expired/'/]">Кредиты с задержкой платежей</a></li>
            <li><a href="[@spring.url '/security_manager/credits/unreturned/'/]">Невозвращённые кредиты</a></li>
        [/@creditex.goback]

        [#if credit??]
            [#if (credit.user)??]
            <div class="data-table">
                [@l_data.client_view_table client=credit.user caption="Клиент" tableId="clientTable"/]
            </div>
                <div class="content">
                    <ul class="nav-menu">
                        <li><a href="[@spring.url '/security_manager/client/check/${credit.user.id?string("0")}'/]">Проверка клиента по внутренним базам</a>
                        </li>
                    </ul>
                </div>
            [/#if]
        <div class="data-table">
            [@l_data.credit_view_table credit=credit caption="Кредит" show_running=true show_product_name=true show_last_notification=true tableId="creditTable"/]
        </div>
        [#if credit_notifications_count??]
            <p class="name">Количество уведомлений по данному кредиту: ${credit_notifications_count}</p>
        [/#if]

            <div class="form-action">
                <a id="notification_form_anchor"></a>
                <p class="name">Уведомление клиента</p>
                <form action="[@spring.url '/security_manager/notification/credit/${credit.id?string("0")}'/]"
                      method="post"
                      class="form-horizontal"
                      id="notificationForm" role="form">
                    <div class="form-group">
                        <label for="selectIdEl" class="col-sm-4 control-label">Тип уведомления</label>
                        <div class="col-xs-6">
                        <select name="type" id="selectIdEl" class="form-control" >
                            [#if type?? && type.name() == "Unreturned"]
                                <option value="Unreturned">Невозвращённый кредит</option>
                                <option value="Expired">Просроченный платёж</option>
                            [#else]
                                <option value="Expired">Просроченный платёж</option>
                                <option value="Unreturned">Невозвращённый кредит</option>
                            [/#if]
                        </select>
                            </div>
                    </div>
                    <div class="form-group">
                        <label for="message" class="col-sm-4 control-label">Сообщение</label>
                        <div class="col-xs-6">
                            <textarea name="message" id="message" class="form-control" style="height: 200px;"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-4 col-xs-6">
                            <button type="submit" class="button">Отправить уведомление</button>
                        </div>
                    </div>
                </form>
            </div>


            [#if (credit.product)??]
            <div class="data-table">
                [@l_data.product_view_table product=credit.product caption="Сведения о кредитном продукте" tableId="productTable"/]
            </div>
            [/#if]

        [/#if]

        </div>
    [/@creditex.body]
[/@creditex.root]