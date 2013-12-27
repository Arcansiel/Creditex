[#ftl]
[#-- @ftlvariable name="isError" type="String" --]
[#import "creditex.ftl" as creditex]
[#import "spring.ftl" as spring]

[@creditex.root]
    [@creditex.head "Main page"]
        [@creditex.includeBootstrapCss/]
        [@creditex.addValidator/]
    <script type="text/javascript">
    $(function(){
        $("#loginForm").validate(
                {
                    rules:{
                        j_username:{
                            required:true
                        },
                        j_password:{
                            required:true
                        }
                    }
                }
        );
    });
    </script>
    [/@creditex.head]
    [@creditex.body]
    <div class="identification inner-box">
        <p class="name">Вход в систему</p>
        <form class="form-horizontal" role="form" id="loginForm" action="/j_spring_security_check" method="post">
            <div class="form-group">
                <label for="inputEmail3" class="col-sm-2 control-label">Login</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="inputEmail3" placeholder="Login" name="j_username">
                </div>
            </div>
            <div class="form-group">
                <label for="inputPassword3" class="col-sm-2 control-label">Password</label>
                <div class="col-sm-10">
                    <input type="password" class="form-control" id="inputPassword3" placeholder="Password" name="j_password">
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" class="button">Sign in</button>
                </div>
            </div>
        </form>
        [#if isError??]
            <p>${isError}</p>
        [/#if]

    </div>
    [/@creditex.body]
[/@creditex.root]