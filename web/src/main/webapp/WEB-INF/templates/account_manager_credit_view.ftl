[#ftl]
[#-- @ftlvariable name="credit" type="org.kofi.creditex.model.Credit" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Main page"]
    <script type="text/javascript">
        var el = document.getElementById('foo');
        el.onclick = showFoo;
        function showFoo() {
            history.go(-1);
            return false;
        }
    </script>
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        <div class="form-action">
            <p class="name">Данные о кредите</p>
            <form action="" method="post" class="form">
                <input type="hidden" value="${credit.id}" name="id">
                <p>
                    <label for="credit.start">Дата начала кредита</label>
                    <input type="text" id="credit.start" name="credit.start" value="${credit.creditStart}">
                </p>
                <p>
                    <label for="credit.duration">Длительность кредита</label>
                    <input type="text" id="credit.duration" name="type" value="${credit.duration}">
                </p>
                <p>
                    <label for="credit.currentMainDebt">Текущий основной долг</label>
                    <input type="text" id="credit.currentMainDebt" name="credit.currentMainDebt" value="${credit.currentMainDebt}">
                </p>
                <p>
                    <label for="credit.originalMainDebt">Начальный основной долг</label>
                    <input type="text" id="credit.originalMainDebt" name="credit.originalMainDebt" value="${credit.originalMainDebt}">
                </p>
                <p>
                    <label for="credit.fine">Текущая пеня</label>
                    <input type="text" id="credit.fine" name="credit.fine" value="${credit.mainFine+credit.percentFine}">
                </p>
                <p>
                    <label for="number_filed">Текущие деньги на счету</label>
                    <input type="text" id="number_filed" name="minDuration" value="${credit.currentMoney}">
                </p>
                <p>
                    <label for="number_filed">Кредитный продукт</label>
                    <a href="[@spring.url '/account_manager/product/view/'+'${credit.product.id}'+'/'/]">Перейти к ${credit.product.name}</a>
                </p>
            </form>
        </div>
        <div class="data-table">
            <p class="name">Платежи</p>
            <table>
                <tr>
                    <th>Номер платежа</th>
                    <th>Сумма платежа</th>
                    <th>Платёж от</th>
                    <th>Платёж до</th>
                    <th>Погашен ли платёж</th>
                    <th>Просрочен ли платёж</th>
                </tr>
                [#if credit.payments??]
                    [#list credit.payments as payment]
                        <tr>
                            <td>${payment.number}</td>
                            <td>${payment.requiredPayment}</td>
                            <td>${payment.paymentStart}</td>
                            <td>${payment.paymentClosed?string("Да","Нет")}</td>
                            <td>${payment.paymentClosed?string("Да","Нет")}</td>
                            <td>${payment.paymentExpired?string("Да","Нет")}</td>
                        </tr>
                    [/#list]
                [/#if]
            </table>
        </div>
        <div class="content">
            <ul class="nav-menu">
                <li><a href="[@spring.url '/account_manager/client/'/]" id="foo">Вырнуться назад</a>
                </li>
            </ul>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]