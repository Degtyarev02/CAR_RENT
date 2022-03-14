<#import "parts/main.ftlh" as m>
<@m.head_part>
    <form action="/profile/edit" method="post" class="profile-edit-form">
        <input type="text" name="firstname" placeholder="Имя" value=${currentUser.firstName!" "} />
        <input type="text" name="lastname" placeholder="Фамилия" value=${currentUser.lastName!" "} />
        <input type="submit" value="Подтвердить" class="submit"/>
    </form>
</@m.head_part>