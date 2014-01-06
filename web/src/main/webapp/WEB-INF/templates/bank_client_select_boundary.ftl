[#ftl]
[#-- @ftlvariable name="product" type="org.kofi.creditex.model.Product" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Подобрать кредитные продукты"]
    [@creditex.includeBootstrapCss/]
    [@creditex.addValidator/]
    <script type="text/javascript">
        $(function(){
            $("#applicationForm").validate({
                rules:{
                    request:{
                        required:true,
                        min:1
                    },
                    duration:{
                        required:true,
                        min:1
                    }
                }
            });
        });
    </script>
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.bank_client/]
        <div class="form-action">
            <p class="name">Введите данные кредита</p>
            <form class="form-horizontal" role="form" id="applicationForm" action="" method="post">
                <div class="form-group">
                    <label for="inputRequest" class="col-sm-4 control-label">Деньги</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="inputRequest" placeholder="Требуемая сумма денег" name="request">
                    </div>
                </div>
                <div class="form-group">
                    <label for="inputDuration" class="col-sm-4 control-label">Длительность</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="inputDuration" placeholder="Длительность кредита" name="duration">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-4 col-xs-6">
                        <button type="submit" class="button">Подобрать кредитные продукты</button>
                    </div>
                </div>
            </form>
        </div>
        <div class="content">
            <ul class="nav-menu">
                <li><a href="[@spring.url '/client'/]">Вернуться назад</a>
                </li>
            </ul>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]