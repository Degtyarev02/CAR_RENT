<#import "parts/main.ftlh" as m>
<@m.head_part>
    <div class="init-container">
        <div class="init-form">
            <h2 class="sign-in-label">Восстановить пароль</h2>
            <form action="/restore" method="post" class="login-form">
                <#if badCredentials??>
                    <div class="login-error error">
                        <span>${badCredentials}</span>
                    </div>
                </#if>
                <input type="text" name="username" placeholder="Имя пользователя"/>
                <input type="password" name="password" placeholder="Пароль"/>
                <input type="password" name="password2" placeholder="Подтвердите пароль"/>
                <div class="activate-message">
                    <span>
                        После успешного восстановления пароль, на почту придет ссылка, для подтверждения аккаунта
                    </span>
                </div>
                <input type="submit" value="Войти" class="submit"/>
            </form>
        </div>
    </div>
</@m.head_part>