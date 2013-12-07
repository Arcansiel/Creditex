[#ftl]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Операционист / операция"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">

    <div class="form-action">
        <p class="name"><a href="/operation_manager/">Другой клиент</a></p>
        <p class="name"><a href="/operation_manager_operation_list/">Список операций</a></p>
        <form action="" method="post" class="form">
            <p class="name">Операция</p>
            [#if error??]
                <p>${error?html}</p>
            [/#if]
            <p>
                <label>Тип операции</label>
                <select name="type">
                    <option value="Withdrawal">Withdrawal</option>
                    <option value="Deposit">Deposit</option>
                </select>
            </p>
            <p>
                <label for="amount_filed" class="col-sm-10">Сумма</label>
                <input type="text" id="amount_filed" name="amount">
            </p>
            <p class="a-center"><button type="submit" class="button">Выполнить операцию</button></p>
        </form>
    </div>

    </div>
    [/@creditex.body]
[/@creditex.root]