[#ftl]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Создание кредитного продукта"]
        [@creditex.includeBootstrap /]
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
                                required: true,
                                min: 0
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
        <p class="name"><a href="[@spring.url '/department_head/'/]">На главную страницу</a></p>
        <div class="form-action">
            <p class="name">Данные нового кредитного продукта</p>
            [#include "l_error_info.ftl" /]
            <form action="" method="post" class="form-horizontal" role="form" id="productForm">
                <div class="form-group">
                    <label for="product.active" class="col-sm-5 control-label">Состояние кредитного продукта</label>
                    <div class="col-xs-4">
                    <select id="product.active" name="active" class="form-control">
                        <option value="true">Активный</option>
                        <option value="false">Неактивный</option>
                    </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="product.name" class="col-sm-5 control-label">Название кредитного продукта</label>
                    <div class="col-xs-4"><input type="text" id="product.name" name="name" value="" class="form-control"></div>
                </div>
                <div class="form-group">
                    <label for="product.type" class="col-sm-5 control-label">Тип кредитного продукта</label>
                    <div class="col-xs-4">
                    <select id="product.type" name="type" class="form-control">
                        <option value="Annuity">Аннуитетный платёж</option>
                        <option value="Residue">Платёж по фактическому остатку</option>
                        <option value="Percent">Единовременный платёж</option>
                    </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="product.percent" class="col-sm-5 control-label">Годовой процент кредитной ставки</label>
                    <div class="col-xs-4"><input type="text" id="product.percent" name="percent" value="" class="form-control"></div>
                </div>
                <div class="form-group">
                    <label for="product.minCommittee" class="col-sm-5 control-label">Минимальная сумма для рассмотрения кредитным комитетом</label>
                    <div class="col-xs-4"><input type="text" id="product.minCommittee" name="minCommittee" value="" class="form-control"></div>
                </div>
                <div class="form-group">
                    <label for="product.minMoney" class="col-sm-5 control-label">Минимальная сумма кредита</label>
                    <div class="col-xs-4"><input type="text" id="product.minMoney" name="minMoney" value="" class="form-control"></div>
                </div>
                <div class="form-group">
                    <label for="product.maxMoney" class="col-sm-5 control-label">Максимальная сумма кредита</label>
                    <div class="col-xs-4"><input type="text" id="product.maxMoney" name="maxMoney" value="" class="form-control"></div>
                </div>
                <div class="form-group">
                    <label for="product.minDuration" class="col-sm-5 control-label">Минимальная длительность кредита</label>
                    <div class="col-xs-4"><input type="text" id="product.minDuration" name="minDuration" value="" class="form-control"></div>
                </div>
                <div class="form-group">
                    <label for="product.maxDuration" class="col-sm-5 control-label">Максимальная длительность кредита</label>
                    <div class="col-xs-4"><input type="text" id="product.maxDuration" name="maxDuration" value="" class="form-control"></div>
                </div>
                <div class="form-group">
                    <label for="product.finePercent" class="col-sm-5 control-label">Процент за день просрочки платежа</label>
                    <div class="col-xs-4"><input type="text" id="product.finePercent" name="debtPercent" value="" class="form-control"></div>
                </div>
                <div class="form-group">
                    <label for="product.priorRepayment" class="col-sm-5 control-label">Возможность досрочного погашения кредита</label>
                    <div class="col-xs-4">
                    <select id="product.priorRepayment" name="prior" class="form-control">
                        <option value="NotAvailable">Невозможно</option>
                        <option value="Available">Возможно без штрафа</option>
                        <option value="AvailableFineInterest">Возможно со штрафом на процентную ставку</option>
                        <option value="AvailableFinePercentSum">Возможно со штрафом на сумму процентов</option>
                    </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="product.priorRepaymentPercent" class="col-sm-5 control-label">Штраф за досрочное погашение кредита (%)</label>
                    <div class="col-xs-4"><input type="text" id="product.priorRepaymentPercent" name="priorRepaymentPercent" value="" class="form-control"></div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-5 col-sm-10">
                        <button type="submit" class="button">Создать</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]