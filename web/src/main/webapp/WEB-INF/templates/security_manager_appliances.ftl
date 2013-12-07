[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Служба безопасности / заявки на рассмотрение"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        <div class="form-action">
        <p class="name">Заявки, нуждающиеся в проверке</p>
        <table>
            <tr>
                <th class="name">ФИО клиента</th>
                <th class="start_date">Поступление заявки</th>
                <th class="passport">Серия и номер паспорта</th>
                <th class="amount">Запрашиваемая сумма</th>
                <th class="duration">Длительность кредитования</th>
                <th class="comment">Комментарий</th>
                <th class="action"></th>
                <th class="submit-button"></th>
            </tr>

            [#list applications as app]
            <tr>
                <form name="" method="post" action="">
                    <td class="name"><a href="/security_manager_appliance_check/${app.id}" target="_blank">${app.client.userData.last} ${app.client.userData.first} ${app.client.userData.patronymic}</a></td>
                    <td class="start_date">FIX IT ! (DATE) !</td>
                    <td class="passport">${app.client.userData.passportSeries} ${app.client.userData.passportNumber}</td>
                    <td class="amount">${app.request}</td>
                    <td class="duration">${app.duration}</td>
                    <td class="comment"><textarea name="comment" ></textarea></td>
                    <td class="action">
                        <p>
                            <label>
                                <input type="radio" name="confirmation" value="1" />
                                Утвердить</label>
                            <br />
                            <label>
                                <input type="radio" name="confirmation" value="0" />
                                Отклонить</label>
                            <br />
                        </p>
                    </td>
                    <td class="submit-button">
                        <input type="hidden" name="id" value="${app.id}"/>
                        <button type="submit" class="button">Принять</button>
                    </td>
                </form>
            </tr>
            [/#list]
         </table>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]