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
                [#if client?? && credit?? && payment??]
                    <table>
                        <tr>
                            <th class="name">Кредитный продукт</th>
                            <th class="amount">Деньги на счёте</th>
                            <th class="amount">Основной долг</th>
                            <th class="amount">Сумма текущих платежей</th>
                        </tr>
                        <tr>
                            <td class="name">${credit.product.name?html}</td>
                            <td class="amount">${credit.currentMoney}</td>
                            <td class="amount">${credit.originalMainDebt}</td>
                            <td class="amount">${payment}</td>
                        </tr>
                    </table>
                [/#if]
                <p class="name">Операция</p>
                [#if error??]
                    <p>${error?html}</p>
                [/#if]
                <p>
                    <label>Тип операции</label>
                    <select name="type">
                        <option value="Deposit">Deposit</option>
                        <option value="Withdrawal">Withdrawal</option>
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