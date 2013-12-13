[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Операционист / операция"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">

        <div class="form-action">
            [#if error??]
                <p>${error?html}</p>
            [/#if]
            <p class="name"><a href=[@spring.url "/operation_manager/"/]>Другой клиент</a></p>
            <p class="name"><a href=[@spring.url "/operation_manager/operation/list/"/]>Список операций</a></p>

            [#if payment??]
                <p class="name">Текущий платёж</p>
                <table>
                    <tr>
                        <th class="name">Номер платежа</th>
                        <th class="start_date">Крайняя дата</th>
                        <th class="amount">Сумма платежа</th>
                        <th class="amount">Сумма по основному долгу</th>
                        <th class="amount">Сумма по процентам</th>
                        <th class="name">Просрочен</th>
                    </tr>
                    <tr>
                        <td class="name">${payment.number?html}</td>
                        <td class="start_date">${payment.paymentEnd?html}</td>
                        <td class="amount">${payment.requiredPayment?html}</td>
                        <td class="amount">${(payment.requiredPayment - payment.percents)?html}</td>
                        <td class="amount">${payment.percents?html}</td>
                        <td class="name">${payment.expired?c}</td>
                    </tr>
                </table>
            [#else]
                <p class="name">Текущий платёж отсутствует</p>
            [/#if]


        [#if expired?? && expired]
            <p class="name">Текущий кредит (имеются задолженности)</p>
        [#else]
            <p class="name">Текущий кредит</p>
        [/#if]
            <table>
                <tr>
                    <th class="name">ID кредита</th>
                    <th class="name">Кредитный продукт</th>
                    <th class="amount">Деньги на счёте</th>
                    <th class="amount">Основной долг</th>
                    <th class="amount">Процентный долг</th>

                    [#if expired?? && expired]
                        <th class="amount">Сумма просроченных платежей</th>
                        <th class="amount">Начисленные пени</th>
                        <th class="amount">Текущая задолженность по платежам</th>
                    [#else]
                        <th class="name">Задолженности по платежам</th>
                    [/#if]
                </tr>
                <tr>
                    <td class="name">${credit.id?html}</td>
                    <td class="name">${credit.product.name?html}</td>
                    <td class="amount">${credit.currentMoney}</td>
                    <td class="amount">${credit.originalMainDebt}</td>
                    <td class="amount">${credit.currentPercentDebt}</td>

                    [#if expired?? && expired]
                        <td class="amount">${credit.mainFine}</td>
                        <td class="amount">${credit.percentFine}</td>
                        <td class="amount">${credit.mainFine + credit.percentFine}</td>
                    [#else]
                        <td class="name">Нет задолженностей</td>
                    [/#if]
                </tr>
            </table>

            <form action="" method="post" class="form">
                <p class="name">Операция</p>
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