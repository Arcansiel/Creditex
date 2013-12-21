[#ftl]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Статистика"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.department_head /]
        [@creditex.goback/]
        <p class="name"><a href="[@spring.url '/department_head/'/]">На главную страницу</a></p>
        <div class="data-table">
            <p class="name">Общая статистика</p>
            [#if credits??]
                <p class="name">Статистика по кредитам</p>
                <table>
                    <thead>
                    <th class="name">Всего кредитов</th>
                    <th class="name">Открытых кредитов</th>
                    <th class="name">Погашеных кредитов</th>
                    <th class="name">Кредитов с просроченными платежами</th>
                    <th class="name">Невозвращённых кредитов</th>
                    </thead>
                    <tbody>
                    <td class="name">[#if (credits.countAll)??]${credits.countAll}[/#if]</td>
                    <td class="name">[#if (credits.countActive)??]${credits.countActive}[/#if]</td>
                    <td class="name">[#if (credits.countInActive)??]${credits.countActive}[/#if]</td>
                    <td class="name">[#if (credits.countExpired)??]${credits.countExpired}[/#if]</td>
                    <td class="name">[#if (credits.countUnreturned)??]${credits.countUnreturned}[/#if]</td>
                    </tbody>
                </table>
            [/#if]
            [#if clients??]
                <p class="name">Статистика по клиентам</p>
            [/#if]
            [#if applications??]
                <p class="name">Статистика по заявкам</p>
            [/#if]
            [#if payments??]
                <p class="name">Статистика по платежам</p>
            [/#if]

        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]