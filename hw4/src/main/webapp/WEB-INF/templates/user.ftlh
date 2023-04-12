<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#if handle?has_content>
        <#assign key = "handle">
        <#assign value = handle>
    <#elseif user_id?has_content>
        <#assign key = "id">
        <#assign value = user_id>
    </#if>
    <#assign user = c.findBy(users, key, value)!/>

    <#if user?has_content>
        <div class="user-block">
            <div class="user-text-block">
                <h1><@c.userlink user user.name/></h1>
                <ul>
                    <li>id: ${user.id}</li>
                    <li>handle: ${user.handle}</li>
                    <li>name: ${user.name}</li>
                </ul>
            </div>
            <img src="/img/no-title.jpeg" alt="no-title">
        </div>
        <div class="prevNext-user-links">
            <#assign user_index = users?seq_index_of(user)>
            <@getRowAncer users user_index-1 "←"/>
            <@getRowAncer users user_index+1 "→"/>
        </div>
    <#else>
        No such user
    </#if>
</@c.page>

<#macro getRowAncer users index value>
    <#if (0 <= index) && (index < users?size)>
        <@c.userlink users[index] value/>
    <#else>
        <a class="non-active">${value}</a>
    </#if>
</#macro>
