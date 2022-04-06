<#import "parts/main.ftlh" as m>
<@m.head_part>
    <div class="init-container">
        <div class="init-form">
            <h2 class="sign-in-label">Зарегистрироваться</h2>
            <form action="/registration" method="post" class="login-form">
                <#if usernameError??>
                    <div class="registration-error error">
                        <span>${usernameError}</span>
                    </div>
                </#if>
                <#if emailError??>
                    <div class="registration-error error">
                        <span>${emailError}</span>
                    </div>
                </#if>
                <#if emailError??>
                    <div class="registration-error error">
                        <span>${emailError}</span>
                    </div>
                </#if>
                <#if passwordEqualsError??>
                    <div class="registration-error error">
                        <span>${passwordEqualsError}</span>
                    </div>
                </#if>
                <#if userExistException??>
                    <div class="registration-error error">
                        <span>${userExistException}</span>
                    </div>
                </#if>
                <input type="text" name="username" placeholder="Имя пользователя"/>
                <input type="text" name="email" placeholder="Email"/>
                <input type="password" name="password" placeholder="Пароль"/>
                <input type="password" name="password2" placeholder="Подтвердить пароль"/>
                <div class="activate-message">
                    <span>
                        После успешной регистрации, на почту придет ссылка, для регистрации аккаунта
                    </span>
                </div>
                <input type="submit" value="Подтвердить" class="submit"/>
                <a href="/login" class="registration">Войти</a>
            </form>
        </div>
    </div>
</@m.head_part>