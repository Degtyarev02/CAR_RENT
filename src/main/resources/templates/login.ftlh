<#import "parts/main.ftlh" as m>
<@m.head_part>
    <div class="init-container">
        <div class="init-form">
            <h2 class="sign-in-label">Пожалуйста войдите</h2>
            <form action="/login" method="post" class="login-form">
                <#if RequestParameters.error??>
                    <div class="login-error error">
                        <span>Неверное имя пользователя или пароль</span>
                    </div>
                </#if>
                <input type="text" name="username" placeholder="Имя пользователя"/>
                <input type="password" name="password" placeholder="Пароль"/>
                <div class="add-ons">
                    <div class="remember-me">
                        <input type="checkbox" id="remember-me" name="remember-me">
                        <span>Запомнить меня</span>
                    </div>
                    <div class="restore-password">
                        <a href="/restore">Забыли пароль?</a>
                    </div>
                </div>
                <input type="submit" value="Войти" class="submit"/>
                <a href="/registration" class="registration">Зарегистрироваться</a>
            </form>
        </div>
    </div>
</@m.head_part>