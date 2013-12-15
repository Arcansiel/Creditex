[#ftl]
[#-- @ftlvariable name="products" type="java.util.List<org.kofi.creditex.model.Product>" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Main page"]

    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        <div class="form-action">
            <p class="name">Введите данные клиента</p>
            <form action="[@spring.url '/account_manager/client/credit/application/process/'/]" method="post" class="form">
                <p>
                    <label for="requestedMoney">Деньги</label>
                    <input type="text" id="requestedMoney" name="request">
                </p>
                <p>
                    <label for="duration">Длительность</label>
                    <input type="text" id="duration" name="duration">
                </p>
                <p>
                    <label for="product">Продукт</label>
                    <select id="product" name="productId">
                        [#if products??]
                            [#list products as product]
                            <option value="${product.id}">${product.name}</option>
                            [/#list]
                        [/#if]
                    </select>
                </p>
                [#if isError??]
                <p>There was error in data</p>
                [/#if]
                <p class="a-center"><button type="submit" class="button">Обработать</button></p>
            </form>
        </div>
        <div class="content">
            <ul class="nav-menu">
                <li><a href="[@spring.url '/account_manager/client/'/]">Вырнуться назад</a>
                </li>
            </ul>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]