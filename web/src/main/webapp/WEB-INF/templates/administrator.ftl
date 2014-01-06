[#ftl]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Администратор"]

    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        <div class="content">
            <ul class="nav-menu">
                <li>
                    <a>Регистрация</a>
                    <ul>
                        <li><a href="[@spring.url '/admin/register'/]">Зарегистрировать сотрудника</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]