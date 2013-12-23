[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Отчёт"]
    [@creditex.addValidator /]
    <script type="text/javascript">
        $(function(){
            $("#reportForm").validate(
                    {
                        rules:{
                            period: {
                                required: true,
                                min: 1
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
        [@creditex.goback /]
        <div class="data-table">
            <p class="name"><a href="[@spring.url '/department_head/'/]">На главную страницу</a></p>
            <p class="name">Отчёт за несколько дней</p>
            [#include "l_error_info.ftl" /]
            <div class="form-action">
                <form method="get" action="[@spring.url '/department_head/report/'/]" class="form" id="reportForm">
                    <p>
                        <label for="period_filed">Период (дней)</label>
                        <input type="text" id="period_filed" name="period" value="1" />
                    </p>
                    <p class="a-center"><button type="submit" class="button">Получить отчёт</button></p>
                </form>
            </div>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]