<#import "parts/main.ftlh" as m>
<@m.head_part>
    <div class="container">
        <h2 class="sign-in-label">Please sign up</h2>

        <div class="init-form">
            <form action="/registration" method="post" class="login-form">
                <input type="text" name="username" placeholder="Username"/>
                <input type="password" name="password" placeholder="Password"/>
                <div><input type="password" name="password2" placeholder="Confirm password"/></div>
                <input type="submit" value="Confirm" class="submit"/>
                <a href="/login" class="registration">Sign in</a>
            </form>
        </div>
    </div>
</@m.head_part>