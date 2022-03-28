<#macro nav_bar>
    <header>
        <div class="navbar">
            <ul class="menu">
                <li><a class="navbar_elements" href="/">Главная</a></li>
                <li><a class="navbar_elements" href="/profile">Профиль</a></li>
                <#if currentUser.isAdmin()><li><a class="navbar_elements" href="/admin/">Панель админа</a></li></#if>
            </ul>
            <form action="/logout" method="post" class="logout">
                <button type="submit">X</button>
            </form>
        </div>
        <div class="open-navbar">
            <img class="arrow" src="/static/img/rightarrow.png" width="16" height="16" alt="">
        </div>
    </header>
</#macro>