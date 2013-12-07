[#ftl]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Login page"]

    [/@creditex.head]
    [@creditex.body]
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
    [/@creditex.body]
[/@creditex.root]