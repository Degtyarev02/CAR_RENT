<#import "parts/main.ftlh" as m>
<@m.head_part>
    <div class="init-container">
        <div class="init-form">
            <h2 class="sign-in-label">Пожалуйста войдите</h2>
            <form action="/login" method="post" class="login-form">
                <input type="text" name="username" placeholder="Username"/>
                <input type="password" name="password" placeholder="Password"/>
                <input type="submit" value="Войти" class="submit"/>
                <a href="/registration" class="registration">Зарегистрироваться</a>
            </form>
        </div>
    </div>
</@m.head_part>