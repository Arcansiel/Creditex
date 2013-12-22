[#ftl]
[#-- @ftlvariable name="credit" type="org.kofi.creditex.model.Credit" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Main page"]
        [@creditex.tableProcess "paymentTable" "payments"/]
    <script type="text/javascript">
        $(function(){
            [@creditex.sorting table="creditTable" theme = "default" sortable=false class = "data-table"/]
        });
    </script>
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.bank_client/]
        <div class="data-table">
            <table id="creditTable">
                <thead>
                <tr>
                    <th>Описание поля</th>
                    <th>Значение</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>Дата начала кредита</td>
                    <td>${credit.creditStart}</td>
                </tr>
                <tr>
                    <td>Длительность кредита</td>
                    <td>${credit.duration}</td>
                </tr>
                <tr>
                    <td>Текущий основной долг</td>
                    <td>${credit.currentMainDebt}</td>
                </tr>
                <tr>
                    <td>Начальный основной долг</td>
                    <td>${credit.originalMainDebt}</td>
                </tr>
                <tr>
                    <td>Текущая пеня</td>
                    <td>${credit.mainFine+credit.percentFine}</td>
                </tr>
                <tr>
                    <td>Текущие деньги на счету</td>
                    <td>${credit.currentMoney}</td>
                </tr>
                <tr>
                    <td>Кредитный продукт</td>
                    <td>${credit.product.name}</td>
                </tr>
                </tbody>
            </table>
        </div>
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
                    <th>Погашен ли платёж</th>
                    <th>Просрочен ли платёж</th>
                </tr>
                </thead>
                <tbody id="payments">
                    [#if credit.payments??]
                        [#list credit.payments as payment]
                        <tr>
                            <td>${payment.number}</td>
                            <td>${payment.requiredPayment}</td>
                            <td>${payment.paymentStart}</td>
                            <td>${payment.paymentEnd}</td>
                            <td>${payment.paymentClosed?string("Да","Нет")}</td>
                            <td>${payment.paymentExpired?string("Да","Нет")}</td>
                        </tr>
                        [/#list]
                    [/#if]
                </tbody>

            </table>
        </div>
        [@creditex.returnButton/]
    </div>
    [/@creditex.body]
[/@creditex.root]