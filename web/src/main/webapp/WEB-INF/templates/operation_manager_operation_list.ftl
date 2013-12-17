[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Операционист / история операций по кредиту"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.operation_manager /]
        [@creditex.goback /]
        <div class="form-action">
            <p class="name"><a href="[@spring.url '/operation_manager/'/]">Другой клиент</a></p>
            <p class="name"><a href="[@spring.url '/operation_manager/payments/'/]">Ближайшие платежи</a></p>
            <p class="name"><a href="[@spring.url '/operation_manager/operation/'/]">Новая операция</a></p>

            <p class="name">История операций по текущему кредиту</p>
            <table>
                <tr>
                    <th class="start_date">Дата операции</th>
                    <th class="name">Тип операции</th>
                    <th class="amount">Сумма операции</th>
                    <th class="name">Операционист</th>
                </tr>

                [#if operations??]
                    [#list operations as operation]
                        <tr>
                            <td class="start_date">${operation.operationDate?html}</td>
                            <td class="name">${operation.type?html}</td>
                            <td class="amount">${operation.amount?html}</td>
                            <td class="name">${operation.operator.username?html}</td>
                        </tr>
                    [/#list]
                [#else]
                <tr><td colspan="4">Данные отсутствуют</td></tr>
                [/#if]
            </table>
        </div>

    </div>
    [/@creditex.body]
[/@creditex.root]