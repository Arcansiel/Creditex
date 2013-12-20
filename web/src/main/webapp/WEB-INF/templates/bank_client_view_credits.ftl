[#ftl]
[#-- @ftlvariable name="credits" type="java.util.List<org.kofi.creditex.model.Credit>" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Main page"]
        [@creditex.tableProcess "creditTable" "credits"/]
    <script type="text/javascript">
        $(function(){
            $("#returnLink").click(function(){
                history.back();
            });
        });
    </script>
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        <div class="data-table">
            <p class="name">Список кредитов</p>
            <div class="holder"></div>
            <table id="creditTable">
                <thead>
                <tr>
                    <th>Дата начала</th>
                    <th>Длительность</th>
                    <th>Текущий основной долг</th>
                    <th>Пеня</th>
                    <th>Текущие деньги на счету</th>
                    <th>Начальный основной долг</th>
                    <th>Кредитный продукт</th>
                    <th>Просмотреть</th>
                </tr>
                </thead>
                <tbody id="credits">
                    [#if credits??]
                        [#list credits as credit]
                        <tr>
                            <td>${credit.creditStart}</td>
                            <td>${credit.duration}</td>
                            <td>${credit.currentMainDebt}</td>
                            <td>${credit.mainFine+credit.percentFine}</td>
                            <td>${credit.currentMoney}</td>
                            <td>${credit.originalMainDebt}</td>
                            <td><a href="[@spring.url '/bank_client/product/${credit.product.id}/view/'/]">${credit.product.name}</a></td>
                            <td><a href="[@spring.url '/bank_client/credit/${credit.id}/view/'/]">Просмотреть</a></td>
                        </tr>
                        [/#list]
                    [/#if]
                </tbody>
            </table>
        </div>
        <div class="content">
            <ul class="nav-menu">
                <li><a href="[@spring.url ''/]" id ="returnLink">Вернуться назад</a>
                </li>
            </ul>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]