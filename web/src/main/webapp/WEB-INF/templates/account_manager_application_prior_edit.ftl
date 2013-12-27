[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Заявка на пролонгацию кредита"]
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
        [@creditex.account_manager/]
        <div class="form-action">
            <p class="name">Введите данные о предварительном погашении кредита</p>
            <form class="form-horizontal" role="form" id="applicationForm" action="" method="post">
                <div class="form-group">
                    <label for="inputComment" class="col-sm-4 control-label">Комментарий</label>
                    <div class="col-xs-6">
                        <input type="text" class="form-control" id="inputComment" placeholder="Комментарий о причине подачи заявки" name="comment">
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
                <li><a href="[@spring.url '/account'/]">Вернуться назад</a>
                </li>
            </ul>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]