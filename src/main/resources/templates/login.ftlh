<#import "parts/main.ftlh" as m>
<@m.head_part>
    <div class="container">
        <h2 class="sign-in-label">Please sign in</h2>
        <div class="init-form">
            <form action="/login" method="post" class="login-form">
                <input type="text" name="username" placeholder="Username"/>
                <input type="password" name="password" placeholder="Password"/>
                <input type="submit" value="Sign In" class="submit"/>
                <a href="/registration" class="registration">Sign up</a>
            </form>
        </div>
    </div>
</@m.head_part>