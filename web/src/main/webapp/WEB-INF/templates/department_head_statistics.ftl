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
                    <td class="name">[#if (credits.countInActive)??]${credits.countInActive}[/#if]</td>
                    <td class="name">[#if (credits.countExpired)??]${credits.countExpired}[/#if]</td>
                    <td class="name">[#if (credits.countUnreturned)??]${credits.countUnreturned}[/#if]</td>
                    </tbody>
                </table>
            [/#if]
            [#if clients??]
                <p class="name">Статистика по клиентам</p>
                <table>
                    <thead>
                    <th class="name">Всего клиентов</th>
                    <th class="name">Enabled клиентов</th>
                    <th class="name">Disabled клиентов</th>
                    <th class="name">Клиентов-должников</th>
                    <th class="name">Клиентов без долгов</th>
                    <th class="name">Клиентов с заявками на досрочное погашение</th>
                    <th class="name">Клиентов с удовлетворёнными заявками на досрочное погашение</th>
                    <th class="name">Клиентов с заявками на пролонгацию</th>
                    <th class="name">Клиентов с удовлетворёнными заявками на пролонгацию</th>
                    </thead>
                    <tbody>
                    <td class="name">[#if (clients.countAll)??]${clients.countAll}[/#if]</td>
                    <td class="name">[#if (clients.countEnabled)??]${clients.countEnabled}[/#if]</td>
                    <td class="name">[#if (clients.countDisabled)??]${clients.countDisabled}[/#if]</td>
                    <td class="name">[#if (clients.countDebtors)??]${clients.countDebtors}[/#if]</td>
                    <td class="name">[#if (clients.countFree)??]${clients.countFree}[/#if]</td>
                    <td class="name">[#if (clients.countPriors)??]${clients.countPriors}[/#if]</td>
                    <td class="name">[#if (clients.countPriorsAccepted)??]${clients.countPriorsAccepted}[/#if]</td>
                    <td class="name">[#if (clients.countProlongations)??]${clients.countProlongations}[/#if]</td>
                    <td class="name">[#if (clients.countProlongationsAccepted)??]${clients.countProlongationsAccepted}[/#if]</td>
                    </tbody>
                </table>
            [/#if]
            [#if applications??]
                <p class="name">Статистика по заявкам на кредиты</p>
                <table>
                    <thead>
                    <th class="name">Количество всех</th>
                    <th class="name">Принятых</th>
                    <th class="name">Отклонённых</th>
                    <th class="name">Отменённых</th>
                    <th class="name">В рассмотрении</th>
                    </thead>
                    <tbody>
                    <td class="name">[#if (applications.countAll)??]${applications.countAll}[/#if]</td>
                    <td class="name">[#if (applications.countAccepted)??]${applications.countAccepted}[/#if]</td>
                    <td class="name">[#if (applications.countRejected)??]${applications.countRejected}[/#if]</td>
                    <td class="name">[#if (applications.countAborted)??]${applications.countAborted}[/#if]</td>
                    <td class="name">[#if (applications.countInProcess)??]${applications.countInProcess}[/#if]</td>
                    </tbody>
                </table>
            [/#if]
            [#if priors??]
                <p class="name">Статистика по заявкам на досрочное погашение</p>
                <table>
                    <thead>
                    <th class="name">Количество всех</th>
                    <th class="name">Принятых</th>
                    <th class="name">Отклонённых</th>
                    <th class="name">Отменённых</th>
                    <th class="name">В рассмотрении</th>
                    </thead>
                    <tbody>
                    <td class="name">[#if (prolongations.countAll)??]${prolongations.countAll}[/#if]</td>
                    <td class="name">[#if (prolongations.countAccepted)??]${prolongations.countAccepted}[/#if]</td>
                    <td class="name">[#if (prolongations.countRejected)??]${prolongations.countRejected}[/#if]</td>
                    <td class="name">[#if (prolongations.countAborted)??]${prolongations.countAborted}[/#if]</td>
                    <td class="name">[#if (prolongations.countInProcess)??]${prolongations.countInProcess}[/#if]</td>
                    </tbody>
                </table>
            [/#if]
            [#if prolongations??]
                <p class="name">Статистика по заявкам на пролонгацию</p>
                <table>
                    <thead>
                    <th class="name">Количество всех</th>
                    <th class="name">Принятых</th>
                    <th class="name">Отклонённых</th>
                    <th class="name">Отменённых</th>
                    <th class="name">В рассмотрении</th>
                    </thead>
                    <tbody>
                    <td class="name">[#if (prolongations.countAll)??]${prolongations.countAll}[/#if]</td>
                    <td class="name">[#if (prolongations.countAccepted)??]${prolongations.countAccepted}[/#if]</td>
                    <td class="name">[#if (prolongations.countRejected)??]${prolongations.countRejected}[/#if]</td>
                    <td class="name">[#if (prolongations.countAborted)??]${prolongations.countAborted}[/#if]</td>
                    <td class="name">[#if (prolongations.countInProcess)??]${prolongations.countInProcess}[/#if]</td>
                    </tbody>
                </table>
            [/#if]
            [#if payments??]
                <p class="name">Статистика по платежам</p>
                <table>
                    <thead>
                    <th class="name">Количество всех</th>
                    <th class="name">Закрытых вовремя</th>
                    <th class="name">Закрытых просроченных</th>
                    <th class="name">Открытых</th>
                    <th class="name">Открытых просроченных</th>
                    </thead>
                    <tbody>
                    <td class="name">[#if (payments.countAll)??]${payments.countAll}[/#if]</td>
                    <td class="name">[#if (payments.countClosedNotExpired)??]${payments.countClosedNotExpired}[/#if]</td>
                    <td class="name">[#if (payments.countClosedExpired)??]${payments.countClosedExpired}[/#if]</td>
                    <td class="name">[#if (payments.countNotClosedNotExpired)??]${payments.countNotClosedNotExpired}[/#if]</td>
                    <td class="name">[#if (payments.countNotClosedExpired)??]${payments.countNotClosedExpired}[/#if]</td>
                    </tbody>
                </table>
            [/#if]

        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]