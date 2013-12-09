[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Операционист / операция"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">

        <div class="form-action">
            <p class="name"><a href=[@spring.url "/operation_manager/"/]>Другой клиент</a></p>
            <p class="name"><a href=[@spring.url "/operation_manager/operation/list/"/]>Список операций</a></p>

            <form action="" method="post" class="form">
                    <table>
                        <tr>
                            <th class="name">Кредитный продукт</th>
                            <th class="amount">Деньги на счёте</th>
                            <th class="amount">Основной долг</th>
                            <th class="amount">Сумма текущих платежей</th>
                            <th class="amount">Платёж по основному долгу</th>
                            <th class="amount">Платёж по процентам</th>
                            <th class="amount">Начисленные пени</th>
                        </tr>
                        <tr>
                            <td class="name">[#if credit??]${credit.product.name?html}[/#if]</td>
                            <td class="amount">[#if credit??]${credit.currentMoney}[/#if]</td>
                            <td class="amount">[#if credit??]${credit.originalMainDebt}[/#if]</td>
                            <td class="amount">[#if payment??]${payment}[/#if]</td>
                            <td class="amount">[#if creditpayment??]${creditpayment}[/#if]</td>
                            <td class="amount">[#if percentspayment??]${percentspayment}[/#if]</td>
                            <td class="amount">[#if finepayment??]${finepayment}[/#if]</td>
                        </tr>
                    </table>
                <p class="name">Операция</p>
                [#if error??]
                    <p>${error?html}</p>
                [/#if]
                <p>
                    <label>Тип операции</label>
                    <select name="type">
                        <option value="Deposit">Платёж по кредиту</option>
                        <option value="Withdrawal">Снять деньги со счёта</option>
                    </select>
                </p>
                <p>
                    <label for="amount_filed" class="col-sm-10">Сумма</label>
                    <input type="text" id="amount_filed" name="amount" value="0">
                </p>
                <p class="a-center"><button type="submit" class="button">Выполнить операцию</button></p>
            </form>
        </div>

    </div>
    [/@creditex.body]
[/@creditex.root]