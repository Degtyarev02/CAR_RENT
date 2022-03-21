<#import "parts/main.ftlh" as m>
<#import "parts/header.ftlh" as h>
<@m.head_part>
    <@h.nav_bar></@h.nav_bar>
    <section class="profile-section">
        <div class="profile-container">
            <div class="avatar">
                <img src="/static/img/bird.png" alt="" width="240" height="240">
            </div>
            <div class="main-info">
                <div class="personal-info">
                    <span class="name-surname">${currentUser.firstName!""} ${currentUser.lastName!""}</span>
                    <span class="username">@${currentUser.username}</span>
                    <span class="balance">Баланс: ${currentUser.balance}руб</span>
                </div>
                <div class="buttons">
                    <form action="/profile/edit" method="get">
                        <button type="submit">Редактировать</button>
                    </form>
                    <button>Пополнить баланс</button>
                </div>
            </div>
        </div>
    </section>

    <section class="rent-section">
        <div class="rents">
            <#list applications as app>
                <div class="rent-item">
                    <span>${app.car.carName}</span>
                    <div class="progress">
                        <div class="progress-bar" role="progressbar" style="width: ${((app.getDiff()/60)*100)?round}%;
                                background: ${app.isActive()?then("#ffa300", "#ae0000")};"
                             aria-valuenow="25"
                             aria-valuemin="0" aria-valuemax="100">${app.getDiff()}/60 мин
                        </div>
                    </div>
                    <button>Продлить на час</button>
                </div>
            <#else>
                <span>Нет заявок на аренду</span>
            </#list>
        </div>
    </section>
</@m.head_part>