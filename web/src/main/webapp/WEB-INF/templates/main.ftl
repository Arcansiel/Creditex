[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Main page"]

    [/@creditex.head]
    [@creditex.body]
        <div class="page">
            <ul class="roles">
                <li>
                    <p><img src="[@spring.url '/img/consultant-logo.jpg'/]"  alt="" /></p>
                    <p>Специалист по работе с клиентом</p>
                </li>
                <li>
                    <p><img src="[@spring.url '/img/security-logo.jpg'/]"  alt="" /></p>
                    <p>Сотрудник службы безопасности</p>
                </li>
                <li>
                    <p><img src="[@spring.url '/img/oparator-logo.jpg'/]"  alt="" /></p>
                    <p>Операционист</p>
                </li>
                <li>
                    <p><img src="[@spring.url '/img/credit-committee-logo.jpg'/]"  alt="" /></p>
                    <p>Член кредитного комитета</p>
                </li>
                <li>
                    <p><img src="[@spring.url '/img/boss-logo.jpg'/]"  alt="" /></p>
                    <p>Начальник кредитного отдела</p>
                </li>
                <li>
                    <p><img src="[@spring.url '/img/client-bank-logo.jpg'/]"  alt="" /></p>
                    <p>Клиент банка</p>
                </li>
            </ul>
        </div>
    <div class="identification inner-box">
        <p class="name">Вход в систему</p>
        <div id="login-form">
            <form method="post" action="/j_spring_security_check">
                <p><label> Логин</label>
                    <input name="j_username" type="text" /></p>
                <p><label> Пароль </label>
                    <input name="j_password" type="password" /></p>
                <p><button type="submit" class="button">Войти</button></p>
            </form>
        </div>
    </div>
    <div class="description">
        <strong>Автоматизированная система “Кредитекс” </strong>позволяет произвести автоматизацию работы кредитного отдела банка максимально приближенно к реальности, обеспечивает учёт и расчёт задолженности и процентах по кредиту, предоставить инструмент планирования платежей.

        <ul class="about">
            <li>
                <a href="#">О системе</a>
            </li>
            <li>
                <a href="#">Разработчики</a>
            </li>
            <li>
                <a href="#">Документация</a>
            </li>
            <li>
                <a href="#">Служба поддежрки</a>
            </li>
        </ul>
    </div>
    [/@creditex.body]
[/@creditex.root]