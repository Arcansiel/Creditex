[#ftl]
[#import "creditex.ftl" as creditex]
[#import "l_data.ftl" as l_data]
[@creditex.root]
    [@creditex.head "Заявка на пролонгацию"]
        [@creditex.includeBootstrap /]
        [@l_data.confirmation_form_validation /]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        [@creditex.department_head /]
    [@creditex.goback /]
    <div class="data-table">
        <p class="page-link"><a href="[@spring.url '/department_head/'/]">На главную страницу</a></p>
        <p class="page-link"><a href="[@spring.url '/department_head/prolongation/list/'/]">Назад к списку заявок на пролонгацию</a></p>
        [#if prolongation??]

            <p class="page-link"><a href="[@spring.url '/department_head/client/${prolongation.client.id?string("0")}'/]">Информация о клиенте</a></p>

            <p class="name">Заявка на пролонгацию</p>
            <table>
                <tr>
                    <th class="name">ID заявки</th>
                    <th class="name">ФИО клиента</th>
                    <th class="passport">Серия и номер паспорта</th>
                    <th class="start_date">Поступление заявки</th>
                    <th class="duration">Запрашиваемая длительность пролонгации</th>
                    <th class="comment">Комментарий клиента</th>
                </tr>

                <tr>
                    <td class="name">${prolongation.id?string("0")}</td>
                    <td class="name">${prolongation.client.userData.last?html} ${prolongation.client.userData.first?html} ${prolongation.client.userData.patronymic?html}</td>
                    <td class="passport">${prolongation.client.userData.passportSeries?html} ${prolongation.client.userData.passportNumber?string("0000000")}</td>
                    <td class="start_date">${prolongation.applicationDate}</td>
                    <td class="duration">${prolongation.duration}</td>
                    <td class="comment">${prolongation.comment?html}</td>
                </tr>
            </table>

            [#if prolongation.acceptance?? && prolongation.acceptance.name() == "InProcess"]
                [@l_data.confirmation_form
                "post"
                '/department_head/prolongation/${prolongation.id?string("0")}/set_head_approved/'
                /]
            [/#if]

            [#if (prolongation.credit)??]
                [@l_data.credit_view_table prolongation.credit "Состояние кредита" /]
            [/#if]

            [#if (prolongation.credit.product)??]
                [@l_data.product_view_table prolongation.credit.product /]
            [/#if]

        [/#if]
    [/@creditex.body]
[/@creditex.root]