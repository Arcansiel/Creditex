[#ftl]
[#-- @ftlvariable name="id" type="java.lang.Number" --]
[#-- @ftlvariable name="payments" type="java.util.List<org.kofi.creditex.model.Payment>" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Main page"]
    [@creditex.tableProcess "paymentTable" "payments"/]
    <script>
        $(function(){
                    $("#creditRegistration").click(function(){
                        window.open("[@spring.url '/account/credit/application/${id}/register'/]");
                        window.location.replace("[@spring.url '/account'/]");
                    });
                }
        );
    </script>
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.account_manager/]
        <div class="data-table">
            <p class="name">Платежи</p>
            <div class="holder"></div>
            <table id="paymentTable">
                <thead>
                <tr>
                    <th>Номер платежа</th>
                    <th>Сумма платежа</th>
                    <th>Платёж от</th>
                    <th>Платёж до</th>
                </tr>
                </thead>
                <tbody id="payments">
                    [#if payments??]
                        [#list payments as payment]
                        <tr>
                            <td>${payment.number}</td>
                            <td>${payment.requiredPayment}</td>
                            <td>${payment.paymentStart}</td>
                            <td>${payment.paymentEnd}</td>
                        </tr>
                        [/#list]
                    [/#if]
                </tbody>

            </table>
        </div>
        <div class="content">
            <ul class="nav-menu">
                <li><a href="#">Операции с заявкой</a>
                    <ul>
                        <li><a id="creditRegistration">Зарегистрировать кредит</a></li>
                        <li><a href="[@spring.url '/account/credit/application/${id}/abort'/]">Отменить заявку на кредит</a></li>
                    </ul>
                </li>
                <li><a href="[@spring.url '/account'/]">Назад</a>
                </li>
            </ul>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]