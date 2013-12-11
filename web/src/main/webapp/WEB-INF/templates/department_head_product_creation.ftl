[#ftl]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Создание кредитного продукта"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        <div class="form-action">
            <p class="name">Данные нового кредитного продукта</p>
            <form action="" method="post" class="form">
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
                    <label for="product.minMoney">минимальная сумма кредита</label>
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
                    <input type="text" id="product.finePercent" name="finePercent" value="">
                </p>
                <p>
                    <label for="product.priorRepayment">Возможность предварительного возврата кредита</label>
                    <select id="product.priorRepayment" name="priorRepayment">
                        <option value="NotAvailable">Невозможно</option>
                        <option value="Available">Возможно без штрафа</option>
                        <option value="AvailableFineInterest">Возможно со штрафом на процентную ставку</option>
                        <option value="AvailableFinePercentSum">Возможно со штрафом на сумму процентов</option>
                    </select>
                </p>
                <p>
                    <label for="product.priorRepaymentPercent">Штраф за предварительный возврат кредита (%)</label>
                    <input type="text" id="product.priorRepaymentPercent" name="priorRepaymentPercent" value="">
                </p>
                <p><button type="submit" class="button">Создать</button></p>
            </form>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]