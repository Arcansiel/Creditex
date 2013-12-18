[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]
[#import "creditex_data.ftl" as creditex_data]
[@creditex.root]
    [@creditex.head "Служба безопасности / заявка на кредит"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.security_manager /]
        [@creditex.goback /]
         <div class="data-table">
             <p class="name"><a href="[@spring.url '/security_manager/'/]">На главную страницу</a></p>
             <p class="name"><a href="[@spring.url '/security_manager/appliances/'/]">Список заявок</a></p>
        [#if application??]
            <p class="name"><a href="[@spring.url '/security_manager/client/check/${application.client.id?string("0")}'/]">Проверка клиента по внутренним базам</a></p>
            <p class="name"><a href="[@spring.url '/security_manager/client/check/outer/${application.client.id?string("0")}'/]">Проверка клиента по внешним базам</a></p>

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
             <form method="post" action="[@spring.url '/security_manager/appliance/confirm/${application.id?string("0")}'/]">
                 <table>
                     <td class="comment"><textarea name="comment" ></textarea></td>
                     <td class="action">
                         <p>
                             <label>
                                 <input type="radio" name="acceptance" value="true" />
                                 Утвердить</label>
                             <br />
                             <label>
                                 <input type="radio" name="acceptance" value="false" checked/>
                                 Отклонить</label>
                             <br />
                         </p>
                     </td>
                     <td class="submit-button">
                         <button type="submit" class="button">Принять</button>
                     </td>
                 </table>
             </form>

            [#if (application.product)??]
                [@creditex_data.product_view_table application.product /]
            [/#if]

        [/#if]

         </div>
    </div>
    [/@creditex.body]
[/@creditex.root]