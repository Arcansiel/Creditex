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
    [#if report??]
        [@creditex.includeCharts/]
        [@creditex.charts includeOverall=true containerOverall="overallContainer" includeCurrent=true includeIO=true includeBankMoney=true
        containerCurrent="currentContainer" includeRegistered=true
        containerRegistered="registeredContainer" containerIO="ioContainer"
        containerBankMoney="bankMoneyContainer" data="${report}"/]
    [/#if]
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
                <form class="form-horizontal" role="form"
                      method="get" action="[@spring.url '/department_head/report/list/'/]" id="reportForm">
                    <div class="form-group">
                        <label for="period_filed" class="col-sm-2 control-label">Период (последних дней)</label>
                        <div class="col-sm-10" style="width: 250px">
                        <input type="text" id="period_filed" name="period" class="form-control"
                               [#if period??]value="${period?string("0")}"[#else]value="1"[/#if]
                                />
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="button">Получить отчёт</button>
                        </div>
                    </div>
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
                    <th class="name">Просроченные/Невозвращённые кредиты</th>
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
                        <td class="name">${report.expiredCredits} / ${report.unreturnedCredits}</td>
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

        [#if report??]
            <div id="bankMoneyContainer" style="width: 100%; height: 440px;"></div>
            <div id="overallContainer" style="width: 100%; height: 440px;"></div>
            <div id="ioContainer" style="width: 100%; height: 440px;"></div>
            <div id="currentContainer" style="width: 100%; height: 440px;"></div>
            <div id="registeredContainer" style="width: 100%; height: 440px;"></div>
        [/#if]
    </div>
    [/@creditex.body]
[/@creditex.root]