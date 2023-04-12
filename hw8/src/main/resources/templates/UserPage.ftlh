<#import "commons.ftlh" as c>

<@c.page>
    <#if user??>
        <h1>
            ${user.login}
        </h1>
        <ul>
            <li>id: ${user.id}</li>
            <li>creation time: ${user.creationTime}</li>
        </ul>
    <#else>
        No such user
    </#if>
</@c.page>
