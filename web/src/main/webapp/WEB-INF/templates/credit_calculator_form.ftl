[#ftl]
[#-- @ftlvariable name="product" type="org.kofi.creditex.model.Product" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]
[#import "l_data.ftl" as l_data]

[@creditex.root]
    [@creditex.head "Параметры кредита"]
        [@creditex.includeBootstrapCss/]
        [@creditex.addValidator/]
        <script type="text/javascript">
            $(function(){
                $("#applicationForm").validate({
                    rules:{
                        request:{
                            required:true,
                            min:${product.minMoney},
                            max:${product.maxMoney}
                        },
                        duration:{
                            required:true,
                            min:${product.minDuration},
                            max:${product.maxDuration}
                        }
                    }
                });
            });
        </script>
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.credit_calculator_title /]
        <p class="page-link"><a href="[@spring.url '/credit_calculator/products/' /]">Выбрать кредитный продукт</a></p>
        <div class="data-table">
            [@l_data.product_view_table product /]
        </div>
        <div class="form-action">
            <p class="name">Введите данные кредита</p>
            <form class="form-horizontal" role="form" id="applicationForm" action="[@spring.url '/credit_calculator/calculate/' /]" method="post">
                <input type="hidden" name="productId" value="${product.id?string("0")}"/>
                <div class="form-group">
                    <label for="inputRequest" class="col-sm-4 control-label">Деньги</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="inputRequest" placeholder="Требуемая сумма денег"
                               name="request" [#if request??]value="${request?string('0')}"[/#if]>
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputDuration" class="col-sm-4 control-label">Длительность</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="inputDuration" placeholder="Длительность кредита"
                               name="duration" [#if duration??]value="${duration?string('0')}"[/#if]>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-4 col-xs-6">
                        <button type="submit" class="button">Вычислить</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]
