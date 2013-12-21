[#ftl]
[#-- @ftlvariable name="product" type="org.kofi.creditex.model.Product" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Main page"]
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
        <div class="form-action">
            <p class="name">Введите данные клиента</p>
            <form action="" method="post" class="form" id="applicationForm">
                <p>
                    <label for="requestedMoney">Деньги</label>
                    <input type="text" id="requestedMoney" name="request">
                </p>
                <p>
                    <label for="duration">Длительность</label>
                    <input type="text" id="duration" name="duration">
                </p>
                [#if isError??]
                <p>There was error in data</p>
                [/#if]
                <p class="a-center"><button type="submit" class="button">Обработать</button></p>
            </form>
        </div>
        <div class="content">
            <ul class="nav-menu">
                <li><a href="[@spring.url '/account/application'/]">Вернуться назад</a>
                </li>
            </ul>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]