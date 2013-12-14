[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Служба безопасности / отчёты по заявке"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
         <div class="form-action">
             <p class="name"><a href=[@spring.url "/security_manager/"/]>Назад на главную страницу</a></p>
             <p class="name"><a href=[@spring.url "/security_manager/appliances/"/]>Назад к списку заявок</a></p>
        [#if application??]
             <p class="name"><a href=[@spring.url '/security_manager/appliance/check/outer/${application.id?string("0")}'/]>Проверка по внешним базам</a></p>

             <p class="name">Заявка на кредит</p>
             <table>
                 <tr>
                     <th class="name">ID заявки</th>
                     <th class="name">ФИО клиента</th>
                     <th class="passport">Серия и номер паспорта</th>
                     <th class="start_date">Поступление заявки</th>
                     <th class="name">Кредитный продукт</th>
                     <th class="amount">Запрашиваемая сумма</th>
                     <th class="duration">Длительность кредитования</th>
                 </tr>
                 <tr>
                     <td class="name">${application.id?string("0")}</td>
                     <td class="name">${application.client.userData.last?html} ${application.client.userData.first?html} ${application.client.userData.patronymic?html}</td>
                     <td class="passport">${application.client.userData.passportSeries?html} ${application.client.userData.passportNumber}</td>
                     <td class="start_date">${application.applicationDate}</td>
                     <td class="name">${application.product.name}</td>
                     <td class="amount">${application.request}</td>
                     <td class="duration">${application.duration}</td>
                 </tr>
             </table>
             <form method="post" action=[@spring.url '/security_manager/appliance/confirm/${application.id?string("0")}'/]>
                 <table>
                     <td class="comment"><textarea name="comment" ></textarea></td>
                     <td class="action">
                         <p>
                             <label>
                                 <input type="radio" name="confirmation" value="true" />
                                 Утвердить</label>
                             <br />
                             <label>
                                 <input type="radio" name="confirmation" value="false" checked/>
                                 Отклонить</label>
                             <br />
                         </p>
                     </td>
                     <td class="submit-button">
                         <button type="submit" class="button">Принять</button>
                     </td>
                 </table>
             </form>
        [/#if]
        [#if credits??]
             <p class="name">Текущие кредиты клиента</p>
             <table>
                 <tr>
                     <th class="name">ID</th>
                     <th class="name">Продукт</th>
                     <th class="start_date">Начало кредитования</th>
                     <th class="duration">Длительность</th>
                     <th class="start_date">Конец кредитования</th>
                     <th class="amount">Сумма кредита</th>
                     <th class="amount">Основной долг</th>
                     <th class="amount">Процентный долг</th>
                     <th class="amount">Долг по платежам</th>
                     <th class="amount">Пеня</th>
                     <th class="name">Активный кредит</th>
                 </tr>
                 [#list credits as credit]
                 <tr>
                     <td class="name">${credit.id}</td>
                     <td class="name">${credit.product.name?html}</td>
                     <td class="start_date">${credit.creditStart}</td>
                     <td class="duration">${credit.duration}</td>
                     <td class="start_date">${credit.creditEnd}</td>
                     <td class="amount">${credit.originalMainDebt}</td>
                     <td class="amount">${credit.currentMainDebt}</td>
                     <td class="amount">${credit.currentPercentDebt}</td>
                     <td class="amount">${credit.mainFine}</td>
                     <td class="amount">${credit.percentFine}</td>
                     <td class="name">${credit.running?c}</td>
                 </tr>
                 [/#list]
             </table>
        [/#if]
        [#if expired??]
             <p class="name">Просроченные кредиты клиента</p>
             <table>
                 <tr>
                     <th class="name">ID</th>
                     <th class="name">Продукт</th>
                     <th class="start_date">Начало кредитования</th>
                     <th class="duration">Длительность</th>
                     <th class="start_date">Конец кредитования</th>
                     <th class="amount">Сумма кредита</th>
                     <th class="amount">Основной долг</th>
                     <th class="amount">Процентный долг</th>
                     <th class="amount">Долг по платежам</th>
                     <th class="amount">Пеня</th>
                     <th class="name">Активный кредит</th>
                 </tr>
                 [#list expired as credit]
                     <tr>
                         <td class="name">${credit.id}</td>
                         <td class="name">${credit.product.name?html}</td>
                         <td class="start_date">${credit.creditStart}</td>
                         <td class="duration">${credit.duration}</td>
                         <td class="start_date">${credit.creditEnd}</td>
                         <td class="amount">${credit.originalMainDebt}</td>
                         <td class="amount">${credit.currentMainDebt}</td>
                         <td class="amount">${credit.currentPercentDebt}</td>
                         <td class="amount">${credit.mainFine}</td>
                         <td class="amount">${credit.percentFine}</td>
                         <td class="amount">${credit.running?c}</td>
                     </tr>
                 [/#list]
             </table>
        [/#if]
        [#if unreturned??]
             <p class="name">Невозвращённые кредиты клиента</p>
             <table>
                 <tr>
                     <th class="name">ID</th>
                     <th class="name">Продукт</th>
                     <th class="start_date">Начало кредитования</th>
                     <th class="duration">Длительность</th>
                     <th class="start_date">Конец кредитования</th>
                     <th class="amount">Сумма кредита</th>
                     <th class="amount">Основной долг</th>
                     <th class="amount">Процентный долг</th>
                     <th class="amount">Долг по платежам</th>
                     <th class="amount">Пеня</th>
                     <th class="name">Активный кредит</th>
                 </tr>
                 [#list unreturned as credit]
                     <tr>
                         <td class="name">${credit.id}</td>
                         <td class="name">${credit.product.name?html}</td>
                         <td class="start_date">${credit.creditStart}</td>
                         <td class="duration">${credit.duration}</td>
                         <td class="start_date">${credit.creditEnd}</td>
                         <td class="amount">${credit.originalMainDebt}</td>
                         <td class="amount">${credit.currentMainDebt}</td>
                         <td class="amount">${credit.currentPercentDebt}</td>
                         <td class="amount">${credit.mainFine}</td>
                         <td class="amount">${credit.percentFine}</td>
                         <td class="amount">${credit.running?c}</td>
                     </tr>
                 [/#list]
             </table>
        [/#if]
        [#if priors??]
             <p class="name">Завки клиента на досрочное погашение</p>
             <table>
                 <tr>
                     <th class="start_date">Дата</th>
                     <th class="name">ID кредита</th>
                     <th class="name">Удовлетворена</th>
                     <th class="comment">Комментарий</th>
                 </tr>
                [#list priors as prior]
                 <tr>
                     <td class="start_date">${prior.applicationDate}</td>
                     <td class="name">${prior.credit.id}</td>
                     <td class="name">[#if (prior.acceptance)??]${prior.acceptance?html}[/#if]</td>
                     <td class="comment">${prior.comment?html}</td>
                 </tr>
                [/#list]
             </table>
        [/#if]
        [#if prolongations??]
             <p class="name">Завки клиента на пролонгацию</p>
             <table>
                 <tr>
                     <th class="start_date">Дата</th>
                     <th class="name">ID кредита</th>
                     <th class="duration">Длительность пролонгации</th>
                     <th class="name">Удовлетворена</th>
                     <th class="comment">Комментарий</th>
                 </tr>
                 [#list prolongations as prolongation]
                     <tr>
                         <td class="start_date">${prolongation.applicationDate}</td>
                         <td class="name">${prolongation.credit.id}</td>
                         <td class="duration">${prolongation.duration}</td>
                         <td class="name">[#if (prolongation.acceptance)??]${prolongation.acceptance?html}[/#if]</td>
                         <td class="comment">${prolongation.comment?html}</td>
                     </tr>
                 [/#list]
             </table>
        [/#if]
         </div>
    </div>
    [/@creditex.body]
[/@creditex.root]