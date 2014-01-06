[#ftl]
[#-- @ftlvariable name="product" type="org.kofi.creditex.model.Product" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Условия кредита"]
        [@creditex.includeBootstrapCss/]
        [@creditex.addValidator/]
    <script type="text/javascript">
        $(function(){
            $("#applicationForm").validate(
                    {
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

                    }
            );
        });
    </script>
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.account_manager/]
        <div class="form-action">
            <p class="name">Введите данные кредита</p>
            <form class="form-horizontal" role="form" id="applicationForm" action="" method="post">
                <div class="form-group">
                    <label for="inputRequest" class="col-sm-4 control-label">Деньги</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="inputRequest" placeholder="От ${product.minMoney} до ${product.maxMoney}" name="request">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputDuration" class="col-sm-4 control-label">Длительность</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="inputDuration" placeholder="От ${product.minDuration} до ${product.maxDuration}" name="duration">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-4 col-xs-6">
                        <button type="submit" class="button">Обработать</button>
                    </div>
                </div>
            </form>
        </div>
        <div class="content">
            <ul class="nav-menu">
                <li><a href="[@spring.url '/account/credit/application'/]">Вернуться назад</a>
                </li>
            </ul>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]