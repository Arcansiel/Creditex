[#ftl]
[#-- @ftlvariable name="product" type="org.kofi.creditex.web.model.ProductForm" --]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Main page"]
    <script type="text/javascript">
        var el = document.getElementById('foo');
        el.onclick = showFoo;
        function showFoo() {
            history.go(-1);
            return false;
        }
    </script>
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        <div class="form-action">
            <p class="name">Данные о кредитном продукте</p>
            <form action="" method="post" class="form">
                <input type="hidden" value="${product.id}" name="id">
                <p>
                    <label for="product.name">Название кредитного продукта</label>
                    <input type="text" id="product.name" name="product.name" value="${product.name}">
                </p>
                <p>
                    <label for="product.type">Тип кредитного продукта</label>
                    <input type="text" id="product.type" name="product.type" value="${product.type}">
                </p>
                <p>
                    <label for="product.percent">Годовой процент кредитной ставки</label>
                    <input type="text" id="product.percent" name="product.percent" value="${product.percent}">
                </p>
                <p>
                    <label for="product.minCommittee">Минимальная сумма для рассмотрения кредитным комитетом</label>
                    <input type="text" id="product.minCommittee" name="product.minCommittee" value="${product.minCommittee}">
                </p>
                <p>
                    <label for="product.minMoney">минимальная сумма кредита</label>
                    <input type="text" id="product.minMoney" name="product.minMoney" value="${product.minMoney}">
                </p>
                <p>
                    <label for="product.maxMoney">Максимальная сумма кредита</label>
                    <input type="text" id="product.maxMoney" name="product.maxMoney" value="${product.maxMoney}">
                </p>
                <p>
                    <label for="product.minDuration">Минимальная длительность кредита</label>
                    <input type="text" id="product.minDuration" name="product.minDuration" value="${product.minDuration}">
                </p>
                <p>
                    <label for="product.maxDuration">Максимальная длительность кредита</label>
                    <input type="text" id="product.maxDuration" name="product.maxDuration" value="${product.maxDuration}">
                </p>
                <p>
                    <label for="product.finePercent">Процент за день просрочки платежа</label>
                    <input type="text" id="product.finePercent" name="product.finePercent" value="${product.finePercent}">
                </p>
                <p>
                    <label for="product.priorRepayment">Возможность предварительного возврата кредита</label>
                    <input type="text" id="product.priorRepayment" name="product.priorRepayment" value="${product.priorRepayment}">
                </p>
                <p>
                    <label for="product.priorRepaymentPercent">Штраф за предварительный возврат кредита</label>
                    <input type="text" id="product.priorRepaymentPercent" name="product.priorRepaymentPercent" value="${product.priorRepaymentPercent}">
                </p>
            </form>
        </div>
        <div class="content">
            <ul class="nav-menu">
                <li><a href="[@spring.url '/account_manager/client/'/]" id="foo">Вырнуться назад</a>
                </li>
            </ul>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]