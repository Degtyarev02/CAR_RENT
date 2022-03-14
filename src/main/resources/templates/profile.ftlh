<#import "parts/main.ftlh" as m>
<@m.head_part>
    <div class="main_info">
        <img src="../static/img/bird.png" alt="" width="256" height="256">
        <div class="name">
            <span>${currentUser.firstName!""} </span>
            <span>${currentUser.lastName!""} </span>
            <div class="username">
                <span>@${currentUser.username}</span>
            </div>
            <div class="balance">
                <span>Текущий баланс: ${currentUser.balance}</span>
            </div>
            <a href="/profile/edit" class="edit">Редактировать</a>
        </div>
    </div>
</@m.head_part>