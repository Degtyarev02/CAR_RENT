<#import "parts/main.ftlh" as m>
<@m.head_part>
    <#list american_muscle as muscle>
        <div>
            <div>${muscle.carName}</div>
            <div>${muscle.description}</div>
            <div>${muscle.priceForHour}</div>
            <#if !muscle.inRent>
                <form action="/rent/${muscle.id}" method="post">
                    <input type="submit" value="Арендовать">
                </form>
            </#if>
        </div>
    <#else>
        <div>No american cars</div>
    </#list>
</@m.head_part>