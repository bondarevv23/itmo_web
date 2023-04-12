<#-- @ftlvariable name="p" type="ru.itmo.web.hw4.model.Post" -->
<#import "./common.ftlh" as c>

<@c.page>
    <#list posts?sort_by("id")?reverse as p>
        <@c.makePost p.title p.text p.userId></@c.makePost>
    </#list>
</@c.page>
