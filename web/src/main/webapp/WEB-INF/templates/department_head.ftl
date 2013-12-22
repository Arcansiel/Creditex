[#ftl]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Глава кредитного отдела"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.department_head /]
        <div class="content">
            [#include "l_error_info.ftl" /]
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
                        <li><a href="[@spring.url '/department_head/product/list/activated/'/]">Список активных кредитных продуктов</a></li>
                        <li><a href="[@spring.url '/department_head/product/list/deactivated/'/]">Список неактивных кредитных продуктов</a></li>
                        <li><a href="[@spring.url '/department_head/product/create/'/]">Создать кредитный продукт</a></li>
                        <li><a href="[@spring.url '/department_head/statistics/products/'/]">Статистика по кредитным продуктам</a></li>
                    </ul>
                </li>
                <li><a href="#">Заявки на пролонгацию</a>
                    <ul>
                        <li><a href="[@spring.url '/department_head/prolongation/list/'/]">Список заявок на пролонгацию</a></li>
                    </ul>
                </li>
                <li><a href="#">Заявки на досрочное погашение</a>
                    <ul>
                        <li><a href="[@spring.url '/department_head/prior/list/'/]">Список заявок на досрочное погашение</a></li>
                    </ul>
                </li>
                <li><a href="#">Архив</a>
                    <ul>
                        <li><a href="[@spring.url '/department_head/statistics/'/]">Статистика</a></li>
                        <li><a href="[@spring.url '/department_head/report/'/]">Отчёт</a></li>
                        <li><a href="[@spring.url '/department_head/credits/active/list/'/]">Открытые кредиты</a></li>
                        <li><a href="[@spring.url '/department_head/credits/returned/list/'/]">Погашеные кредиты</a></li>
                        <li><a href="[@spring.url '/department_head/clients/list/'/]">Клиенты банка</a></li>
                        <li><a href="[@spring.url '/department_head/client/search/'/]">Поиск клиента</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]