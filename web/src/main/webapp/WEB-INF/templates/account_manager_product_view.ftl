[#ftl]
[#-- @ftlvariable name="product" type="org.kofi.creditex.model.Product" --]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Кредитный продукт"]
    [@creditex.includeJQuery/]
    [@creditex.includeTableCloth/]
    <script type="text/javascript">
        $(function(){
            $("#foo").click(function(){
                history.go(-1);
            });
            [@creditex.sorting table="productTable" theme = "default" sortable=false class = "data-table"/]
        });
    </script>
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        <div class="data-table">
            <p class="name">Данные по кредиту</p>
            <table id="productTable">
                <thead>
                <tr>
                    <th>Параметр</th>
                    <th>Данные кредита</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>Название кредитного продукта</td>
                    <td>${product.name}</td>
                </tr>
                <tr>
                    <td>Тип кредитного продукта</td>
                    <td>${product.type}</td>
                </tr>
                <tr>
                    <td>Годовой процент кредитной ставки</td>
                    <td>${product.percent?string("0.####")}%</td>
                </tr>
                <tr>
                    <td>Минимальная сумма для рассмотрения кредитным комитетом</td>
                    <td>${product.minCommittee}</td>
                </tr>
                <tr>
                    <td>Минимальная сумма кредита</td>
                    <td>${product.minMoney}</td>
                </tr>
                <tr>
                    <td>Максимальная сумма кредита</td>
                    <td>${product.maxMoney}</td>
                </tr>
                <tr>
                    <td>Минимальная длительность кредита</td>
                    <td>${product.minDuration}</td>
                </tr>
                <tr>
                    <td>Максимальная длительность кредита</td>
                    <td>${product.maxDuration}</td>
                </tr>
                <tr>
                    <td>Процент за день просрочки платежа</td>
                    <td>${product.debtPercent?string("0.####")}%</td>
                </tr>
                <tr>
                    <td>Возможность предварительного возврата кредита</td>
                    <td>${product.prior}</td>
                </tr>
                <tr>
                    <td>Штраф за предварительный возврат кредита</td>
                    <td>${product.priorRepaymentPercent?string("0.####")}%</td>
                </tr>
                </tbody>
            </table>
        </div>
        [@creditex.returnButton/]
    </div>
    [/@creditex.body]
[/@creditex.root]