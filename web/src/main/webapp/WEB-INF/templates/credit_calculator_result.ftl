[#ftl]
[#-- @ftlvariable name="product" type="org.kofi.creditex.model.Product" --]
[#-- @ftlvariable name="form" type="org.kofi.creditex.web.model.CreditCalculatorForm" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]
[#import "l_data.ftl" as l_data]

[@creditex.root]
    [@creditex.head "План платежей"]
        [@creditex.includeBootstrapCss/]
        [@creditex.addValidator/]
        <script type="text/javascript">
            $(function(){
                $("#applicationForm").validate({
                    rules:{
                        request:{
                            required:true,
                            min:${product.minMoney},
                            max:${product.maxMoney}
                        },
                        duration:{
                            required:true,
                            min:${product.minDuration},
                            max:${product.maxDuration}
                        }
                    }
                });
            });
        </script>
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.credit_calculator_title /]
        <p class="page-link"><a href="[@spring.url '/credit_calculator/products/' /]">Выбрать кредитный продукт</a></p>
        <div class="data-table">
            [@l_data.product_view_table product /]
        </div>
        <div class="form-action">
            <p class="name">Данные кредита</p>
            <form class="form-horizontal" role="form" id="applicationForm" action="[@spring.url '/credit_calculator/calculate/' /]" method="post">
                <input type="hidden" name="productId" value="${product.id?string("0")}"/>
                <div class="form-group">
                    <label for="inputRequest" class="col-sm-4 control-label">Деньги</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="inputRequest" placeholder="Требуемая сумма денег" name="request" value="${form.request}">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputDuration" class="col-sm-4 control-label">Длительность</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="inputDuration" placeholder="Длительность кредита" name="duration" value="${form.duration}">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-4 col-xs-6">
                        <button type="submit" class="button">Вычислить</button>
                    </div>
                </div>
            </form>
        </div>
        <div class="data-table">
            <table>
                <tr>
                    <th class="amount">Общий долг</th>
                    <th class="amount">Основной долг</th>
                    <th class="amount">Проценты</th>
                </tr>
                <tr>
                    <td class="amount">[#if total??]${total}[/#if]</td>
                    <td class="amount">[#if mainDebt??]${mainDebt}[/#if]</td>
                    <td class="amount">[#if percents??]${percents}[/#if]</td>
                </tr>
            </table>
        </div>
        <div class="data-table">
            <p class="name">Платежи</p>
            <table>
                <thead>
                <tr>
                    <th class="name">Номер платежа</th>
                    <th class="amount">Сумма платежа</th>
                    <th class="amount">Сумма по основному долгу</th>
                    <th class="amount">Сумма по процентам</th>
                    <th class="start_date">Платёж от</th>
                    <th class="start_date">Платёж до</th>
                </tr>
                </thead>
                <tbody>
                    [#if payments??]
                        [#list payments as payment]
                        <tr>
                            <td class="name">${payment.number}</td>
                            <td class="amount">${payment.requiredPayment}</td>
                            <td class="amount">${payment.requiredPayment - payment.percents}</td>
                            <td class="amount">${payment.percents}</td>
                            <td class="start_date">${payment.paymentStart}</td>
                            <td class="start_date">${payment.paymentEnd}</td>
                        </tr>
                        [/#list]
                    [/#if]
                </tbody>
            </table>

        </div>

    </div>
    [/@creditex.body]
[/@creditex.root]
