[#ftl]
[#-- @ftlvariable name="product" type="org.kofi.creditex.model.Product" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]
[#import "l_data.ftl" as l_data]

[@creditex.root]
    [@creditex.head "Кредитный калькулятор"]
        [@creditex.includeBootstrapCss/]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.credit_calculator_title /]
        [#include "l_error_info.ftl" /]
        <p class="page-link"><a href="[@spring.url '/credit_calculator/products/' /]">Выбрать кредитный продукт</a></p>
        <p class="page-link"><a href="[@spring.url '/credit_calculator/find_products/' /]">Подобрать кредитный продукт</a></p>
    </div>
    [/@creditex.body]
[/@creditex.root]
