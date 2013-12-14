[#ftl]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Глава кредитного отдела"]

    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        <div class="content">

            <ul class="nav-menu">

                <li><a href="#">Заявки на кредиты</a>
                    <ul>
                        <li><a href="[@spring.url '/department_head/appliances/committee_approved/'/]">Одобренные кредитным комитетом</a></li>
                        <li><a href="[@spring.url '/department_head/appliances/committee_rejected/'/]">Отклонённые кредитным комитетом</a></li>
                        <li><a href="[@spring.url '/department_head/appliances/committee_vote/'/]">С открытым голосованием</a></li>
                    </ul>
                </li>
                <li><a href="#">Кредитные продукты</a>
                    <ul>
                        <li><a href="[@spring.url '/department_head/product/list/'/]">Списки кредитных продуктов</a></li>
                        <li><a href="[@spring.url '/department_head/product/create/'/]">Создать кредитный продукт</a></li>
                    </ul>
                </li>
                <li><a href="#">Заявки на пролонгацию</a>
                    <ul>
                        <li><a href="[@spring.url '/department_head/prolongation/list/'/]">Список заявок на пролонгацию</a></li>
                    </ul>
                </li>
                <li><a href="#">Отчёты</a>
                    <ul>
                        <li><a href="[@spring.url '/department_head/report/'/]">Отчёт</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]