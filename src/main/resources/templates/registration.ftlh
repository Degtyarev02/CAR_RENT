<#import "parts/main.ftlh" as m>
<@m.head_part>
    <div class="init-container">
        <div class="init-form">
            <h2 class="sign-in-label">Зарегистрироваться</h2>
            <form action="/registration" method="post" class="login-form">
                <input type="text" name="username" placeholder="Username"/>
                <input type="password" name="password" placeholder="Password"/>
                <input type="text" name="email" placeholder="Email"/>
                <div><input type="password" name="password2" placeholder="Confirm password"/></div>
                <input type="submit" value="Подтвердить" class="submit"/>
                <a href="/login" class="registration">Войти</a>
            </form>
        </div>
    </div>
</@m.head_part>