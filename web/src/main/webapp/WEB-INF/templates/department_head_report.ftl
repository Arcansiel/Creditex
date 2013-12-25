[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Отчёт"]
        [@creditex.tableProcess "listtable" "list" 10 /]
        [@creditex.addValidator false/]
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
                <form method="get" action="[@spring.url '/department_head/report/list/'/]" class="form" id="reportForm">
                    <p>
                        <label for="period_filed">Период (последних дней)</label>
                        <input type="text" id="period_filed" name="period"
                               [#if period??]value="${period?string("0")}"[#else]value="1"[/#if]
                                />
                    </p>
                    <p class="a-center"><button type="submit" class="button">Получить отчёт</button></p>
                </form>
            </div>

            <div class="holder"></div>
            <table id="listtable">
                <thead>
                <tr>
                    <th class="name">ID</th>
                    <th class="start_date">Дата</th>
                    <th class="amount">Текущие деньги банка</th>
                    <th class="amount">Доход</th>
                    <th class="amount">Расход</th>
                    <th class="name">Кредиты</th>
                    <th class="name">Открытые/Просроченные/Невозвращённые кредиты</th>
                    <th class="name">Заявки на кредиты</th>
                    <th class="name">Заявки на досрочное погашение</th>
                    <th class="name">Заявки на пролонгацию</th>
                    <th class="name">Кредитные продукты</th>
                    <th class="name">Операции</th>
                    [#if overall?? && overall]
                        <th class="name">Все кредиты</th>
                        <th class="name">Все открытые/закрытые/просроченные/невозвращённые кредиты</th>
                        <th class="name">Все заявки на кредиты</th>
                        <th class="name">Все заявки на досрочное погашение</th>
                        <th class="name">Все заявки на пролонгацию</th>
                    [/#if]
                </tr>
                </thead>
                <tbody id="list">
                    [#if reports??][#list reports as report]
                    <tr>
                        <td class="name">${report.id}</td>
                        <td class="start_date">${report.dayDate?html}</td>
                        <td class="amount">${report.currentBankMoney}</td>
                        <td class="amount">${report.income}</td>
                        <td class="amount">${report.outcome}</td>
                        <td class="name">${report.credits}</td>
                        <td class="name">${report.runningCredits} / ${report.expiredCredits} / ${report.unreturnedCredits}</td>
                        <td class="name">${report.creditApplications}</td>
                        <td class="name">${report.priorRepaymentApplications}</td>
                        <td class="name">${report.prolongationApplications}</td>
                        <td class="name">${report.products}</td>
                        <td class="name">${report.operations}</td>
                    [#if overall?? && overall]
                        <td class="name">${report.overallCredits}</td>
                        <td class="name">
                        ${report.overallRunningCredits} /
                        ${report.overallClosedCredits} /
                        ${report.overallExpiredCredits} /
                        ${report.overallUnreturnedCredits}</td>
                        <td class="name">${report.overallCreditApplications}</td>
                        <td class="name">${report.overallPriorRepaymentApplications}</td>
                        <td class="name">${report.overallProlongationApplications}</td>
                    [/#if]
                    </tr>
                    [/#list][/#if]
                </tbody>
            </table>

        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]