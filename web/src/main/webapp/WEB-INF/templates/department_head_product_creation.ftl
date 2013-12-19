[#ftl]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Создание кредитного продукта"]
        [@creditex.addValidator/]
    <script type="text/javascript">
        $(function(){
            $("#productForm").validate(
                    {
                        rules:{
                            active: {
                                required: true
                            },
                            name: {
                                required: true,
                                minlength: 1,
                                maxlength: 48
                            },
                            type: {
                                required: true
                            },
                            percent: {
                                required: true,
                                min: 0
                            },
                            minCommittee: {
                                required: true,
                                min: 0
                            },
                            minMoney:{
                                required: true,
                                min: 1
                            },
                            maxMoney:{
                                required: true,
                                min: 1
                            },
                            minDuration:{
                                required: true,
                                min: 1
                            },
                            maxDuration:{
                                required: true,
                                min: 1
                            },
                            debtPercent:{
                                required: true,
                                min: 0
                            },
                            prior:{
                                required: true
                            },
                            priorRepaymentPercent:{
                                required: true
                            }
                        }

                    }
            );
        });
    </script>
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.department_head /]
        [@creditex.goback/]
        <div class="form-action">
            <p class="name"><a href="[@spring.url '/department_head/'/]">На главную страницу</a></p>
            <p class="name">Данные нового кредитного продукта</p>
            <form action="" method="post" class="form" id="productForm">
                <p>
                    <label for="product.active">Состояние кредитного продукта</label>
                    <select id="product.active" name="active">
                        <option value="true">Активный</option>
                        <option value="false">Неактивный</option>
                    </select>
                </p>
                <p>
                    <label for="product.name">Название кредитного продукта</label>
                    <input type="text" id="product.name" name="name" value="">
                </p>
                <p>
                    <label for="product.type">Тип кредитного продукта</label>
                    <select id="product.type" name="type">
                        <option value="Annuity">Аннуитетный платёж</option>
                        <option value="Residue">Платёж по фактическому остатку</option>
                        <option value="Percent">Единовременный платёж</option>
                    </select>
                </p>
                <p>
                    <label for="product.percent">Годовой процент кредитной ставки</label>
                    <input type="text" id="product.percent" name="percent" value="">
                </p>
                <p>
                    <label for="product.minCommittee">Минимальная сумма для рассмотрения кредитным комитетом</label>
                    <input type="text" id="product.minCommittee" name="minCommittee" value="">
                </p>
                <p>
                    <label for="product.minMoney">Минимальная сумма кредита</label>
                    <input type="text" id="product.minMoney" name="minMoney" value="">
                </p>
                <p>
                    <label for="product.maxMoney">Максимальная сумма кредита</label>
                    <input type="text" id="product.maxMoney" name="maxMoney" value="">
                </p>
                <p>
                    <label for="product.minDuration">Минимальная длительность кредита</label>
                    <input type="text" id="product.minDuration" name="minDuration" value="">
                </p>
                <p>
                    <label for="product.maxDuration">Максимальная длительность кредита</label>
                    <input type="text" id="product.maxDuration" name="maxDuration" value="">
                </p>
                <p>
                    <label for="product.finePercent">Процент за день просрочки платежа</label>
                    <input type="text" id="product.finePercent" name="debtPercent" value="">
                </p>
                <p>
                    <label for="product.priorRepayment">Возможность досрочного погашения кредита</label>
                    <select id="product.priorRepayment" name="prior">
                        <option value="NotAvailable">Невозможно</option>
                        <option value="Available">Возможно без штрафа</option>
                        <option value="AvailableFineInterest">Возможно со штрафом на процентную ставку</option>
                        <option value="AvailableFinePercentSum">Возможно со штрафом на сумму процентов</option>
                    </select>
                </p>
                <p>
                    <label for="product.priorRepaymentPercent">Штраф за досрочное погашение кредита (%)</label>
                    <input type="text" id="product.priorRepaymentPercent" name="priorRepaymentPercent" value="">
                </p>
                <p><button type="submit" class="button">Создать</button></p>
            </form>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]