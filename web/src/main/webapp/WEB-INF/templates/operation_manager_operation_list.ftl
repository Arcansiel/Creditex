[#ftl]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Операционист / список операций пользователя"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">

    <div class="form-action">
        [#if error??]
            <p>${error?html}</p>
        [/#if]
        <p class="name"><a href="/operation_manager/">Другой клиент</a></p>
        <p class="name"><a href="/operation_manager_operation/">Новая операция</a></p>
        <p class="name">Все операции пользователя по текущему кредиту</p>
        <table>
            <tr>
                <th class="name">Дата операции</th>
                <th class="name">Тип операции</th>
                <th class="name">Сумма операции</th>
            </tr>

            [#list operations as operation]
            <tr>
                <td class="name">${operation.operationDate?string}</td>
                <td class="name">${operation.type?string}</td>
                <td class="name">${operation.amount?string}</td>
            </tr>
            [/#list]
        </table>
    </div>

    </div>
    [/@creditex.body]
[/@creditex.root]