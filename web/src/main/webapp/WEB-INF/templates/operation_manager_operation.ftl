[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]
[#import "l_data.ftl" as l_data]
[@creditex.root]
    [@creditex.head "Операционист / операция"]
        [@creditex.includeBootstrap /]
        [@creditex.addValidator/]
    <script type="text/javascript">
        $(function(){
            $("#operationForm").validate(
                    {
                        rules:{
                            type: {
                                required: true
                            },
                            amount: {
                                required: true,
                                min: 1
                            }
                        }
                    }
            );
        });
    </script>
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.operation_manager /]


        <div class="data-table">
            <p class="page-link"><a href="[@spring.url '/operation_manager/'/]">Другой клиент</a></p>
            <p class="page-link"><a href="[@spring.url '/operation_manager/operation/list/'/]">История операций</a></p>
            <p class="page-link"><a href="[@spring.url '/operation_manager/payments/'/]">Ближайшие платежи</a></p>

            [#assign current_amount=0 /]
            [#if payment??]
                [#assign current_amount=(payment.requiredPayment) /]
                <p class="name">Текущий платёж</p>
                <table>
                    <tr>
                        <th class="name">Номер платежа</th>
                        <th class="start_date">Начальная дата</th>
                        <th class="start_date">Крайняя дата</th>
                        <th class="amount">Сумма платежа</th>
                        <th class="amount">Сумма по основному долгу</th>
                        <th class="amount">Сумма по процентам</th>
                        <th class="name">Просрочен</th>
                    </tr>
                    <tr>
                        <td class="name">${payment.number?html}</td>
                        <td class="start_date">${payment.paymentStart?html}</td>
                        <td class="start_date">${payment.paymentEnd?html}</td>
                        <td class="amount">${payment.requiredPayment?html}</td>
                        <td class="amount">${(payment.requiredPayment - payment.percents)?html}</td>
                        <td class="amount">${payment.percents?html}</td>
                        <td class="name">${payment.paymentExpired?c}</td>
                    </tr>
                </table>
            [#else]
                <p class="name">Текущий платёж отсутствует</p>
            [/#if]

        [#if credit??]
            [#assign expired=(credit.mainFine > 0 || credit.percentFine > 0) /]
            [#if expired]
                <p class="name">Текущий кредит (имеются задолженности)</p>
            [#else]
                <p class="name">Текущий кредит</p>
            [/#if]
                <table>
                    <tr>
                        <th class="name">ID кредита</th>
                        <th class="name">Кредитный продукт</th>
                        <th class="amount">Сумма кредита</th>
                        <th class="amount">Деньги на счёте</th>
                        <th class="amount">Основной долг</th>
                        <th class="amount">Процентный долг</th>
                        <th class="amount">Общий долг</th>

                        [#if expired]
                            <th class="amount">Сумма просроченных платежей</th>
                            <th class="amount">Начисленные пени</th>
                            <th class="amount">Текущая задолженность по платежам</th>
                        [#else]
                            <th class="name">Задолженности по платежам</th>
                        [/#if]

                        <th class="name">Досрочное погашение</th>
                        <th class="name">Количество пролонгаций</th>
                    </tr>
                    <tr>
                        <td class="name">${credit.id?html}</td>
                        <td class="name">${credit.product.name?html}</td>
                        <td class="amount">${credit.originalMainDebt}</td>
                        <td class="amount">${credit.currentMoney}</td>
                        <td class="amount">${credit.currentMainDebt}</td>
                        <td class="amount">${credit.currentPercentDebt}</td>
                        <td class="amount">${credit.currentMainDebt + credit.currentPercentDebt}</td>

                        [#if expired]
                            <td class="amount">${credit.mainFine}</td>
                            <td class="amount">${credit.percentFine}</td>
                            <td class="amount">${credit.mainFine + credit.percentFine}</td>
                        [#else]
                            <td class="name">Нет задолженностей</td>
                        [/#if]

                        <td class="name">${credit.priorRepayment?c}</td>
                        <td class="name">${credit.prolongations}</td>
                    </tr>
                </table>


            [#assign
            expired_amount=(credit.mainFine + credit.percentFine)
            total_amount=(expired_amount + current_amount)
            money=(credit.currentMoney)
            required_expired = (expired_amount - money)
            required_current = (current_amount - money)
            required_total = (total_amount - money)
            /]

            [#if required_expired < 0][#assign required_expired=0 /][/#if]
            [#if required_current < 0][#assign required_current=0 /][/#if]
            [#if required_total < 0][#assign required_total=0 /][/#if]

                <p class="name">Необходимые суммы платежей :</p>
                <table>
                    <tr>
                        <th class="amount">Просроченные платежи + пени</th>
                        <th class="amount">Текущий платёж</th>
                        <th class="amount">Общая сумма (просроченные + пени + текущий)</th>
                        <th class="amount">Требуется внести для погашения : просроченные + пени</th>
                        <th class="amount">Требуется внести для погашения : текущий платёж</th>
                        <th class="amount">Требуется внести для погашения : просроченные + пени + текущий</th>
                    </tr>
                    <tr>
                        <td class="amount">${expired_amount}</td>
                        <td class="amount">${current_amount}</td>
                        <td class="amount">${total_amount}</td>
                        <td class="amount">${required_expired}</td>
                        <td class="amount">${required_current}</td>
                        <td class="amount">${required_total}</td>
                    </tr>
                </table>

        [/#if]

            <div class="form-action">
                [#include "l_error_info.ftl" /]
                <form class="form-horizontal" role="form"
                      action="" method="post" id="operationForm">
                    <p class="name a-center">Операция</p>
                    <div class="form-group">
                        <label class="col-sm-5 control-label">Тип операции</label>
                        <div class="col-xs-4" >
                            <select name="type" class="form-control">
                                <option value="Deposit">Положить деньги на счёт</option>
                                <option value="Withdrawal">Снять деньги со счёта</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="amount_filed" class="col-sm-5 control-label">Сумма</label>
                        <div class="col-xs-4" >
                            <input type="text" class="form-control" id="amount_filed" name="amount" value="">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-5 col-sm-10">
                            <button type="submit" class="button">Выполнить операцию</button>
                        </div>
                    </div>
                </form>
            </div>

            [#if (credit.product)??]
                [@l_data.product_view_table credit.product /]
            [/#if]

        </div>

    </div>
    [/@creditex.body]
[/@creditex.root]