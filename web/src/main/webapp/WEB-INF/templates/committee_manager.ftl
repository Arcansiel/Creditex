[#ftl]
[#import "creditex.ftl" as creditex]

[@creditex.root]
    [@creditex.head "Член кредитного комитета"]
    [/@creditex.head]
    [@creditex.body]
    <div class="page">
        <div class="content">

            <ul class="nav-menu">

                <li><a href="#">Заявки</a>
                    <ul>
                        <li><a href="[@spring.url '/committee_manager/appliances/running/'/]">Заявки с открытым голосованием</a></li>
                        <li><a href="[@spring.url '/committee_manager/appliances/finished/'/]">Заявки с завершённым голосованием</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
    [/@creditex.body]
[/@creditex.root]