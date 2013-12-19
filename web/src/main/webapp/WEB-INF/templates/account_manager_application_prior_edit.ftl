[#ftl]
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
                            comment:{
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
        <div class="form-action">
            <p class="name">Введите данные о предварительном возврате кредита</p>
            <form action="" method="post" class="form" id="applicationForm">
                <p>
                    <label for="comment_field">Комментарий</label>
                    <input type="text" id="comment_field" name="comment">
                </p>
                <p class="a-center"><button type="submit" class="button">Обработать</button></p>
            </form>
        </div>
        <div class="content">
            <ul class="nav-menu">
                <li><a href="[@spring.url '/account_manager/client/'/]">Вырнуться назад</a>
                </li>
            </ul>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]