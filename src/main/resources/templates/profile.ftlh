<#import "parts/main.ftlh" as m>
<#import "parts/header.ftlh" as h>
<#import "parts/footer.ftlh" as f>
<@m.head_part>
    <@h.nav_bar></@h.nav_bar>
    <div class="container-height">
        <section class="profile-section">
            <div class="profile-container">
                <div class="avatar">
                    <img src="/static/img/racer.png" alt="" width="240" height="240">
                </div>
                <div class="main-info">
                    <div class="personal-info">
                        <span class="name-surname">${currentUser.firstName!""} ${currentUser.lastName!""}</span>
                        <span class="username">@${currentUser.username}</span>
                        <span class="balance">Баланс: ${currentUser.balance}руб</span>
                    </div>
                    <div class="buttons">
                        <button class="js-modal-open" value="edit_id">Редактировать</button>
                        <button class="js-modal-open" value="balance_id">Пополнить баланс</button>
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
                            <div class="progress-bar" role="progressbar"
                                 style="width: ${((app.getDiff()/60)*100)?round}%;
                                         background: ${app.isActive()?then("#ffa300", "#ae0000")};"
                                 aria-valuenow="25"
                                 aria-valuemin="0" aria-valuemax="100">${app.getDiff()}/60 мин
                            </div>
                        </div>
                        <form method="post" action="/rent/${app.car.id}" class="modal__dialog">
                            <button type="submit">Продлить на час</button>
                        </form>
                    </div>
                <#else>
                    <span>Нет заявок на аренду</span>
                </#list>
            </div>
        </section>

        <@f.footer></@f.footer>
    </div>


    <div class="review-modal" id="balance_id">
        <form method="post" action="/profile/balance" class="modal__dialog">
            <div class="modal__dialog-header">
                <button class="js-modal-close modal__dialog-header-close-btn">&times;</button>
            </div>
            <div class="modal__dialog-body">
                <h2 class="review-greeting">Пополнить баланс</h2>
                <div class="modal__dialog-body-inputs_fields">
                    <input type="text" name="balance" class="input" placeholder="Пополнить баланс">
                </div>
            </div>
            <div class="modal__dialog-footer">
                <button class="btn_close" type="submit">Подтвердить</button>
            </div>
        </form>
    </div>

    <div class="review-modal" id="edit_id">
        <form method="post" action="/profile/edit" class="modal__dialog">
            <div class="modal__dialog-header">
                <button class="js-modal-close modal__dialog-header-close-btn">&times;</button>
            </div>
            <div class="modal__dialog-body">
                <h2 class="review-greeting">Редактировать</h2>
                <div class="modal__dialog-body-inputs_fields">
                    <input class="input" type="text" name="firstname" placeholder="Имя"
                           value="${currentUser.firstName!" "}"
                    />
                    <input class="input" type="text" name="lastname" placeholder="Фамилия"
                           value="${currentUser.lastName!" "}"/>
                </div>
            </div>
            <div class="modal__dialog-footer">
                <button class="btn_close" type="submit">Подтвердить</button>
            </div>
        </form>
    </div>
</@m.head_part>