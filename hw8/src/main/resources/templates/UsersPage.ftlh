<#import "commons.ftlh" as c>

<#function if cond then else="">
    <#if cond>
        <#return then>
    <#else>
        <#return else>
    </#if>
</#function>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
                <th>Disabled</th>
            </tr>
            </thead>
            <tbody>
            <#if users?size==0>
                <tr class="noData">
                    <td colspan="3">
                        No data
                    </td>
                </tr>
            <#else>
                <#list users as user>
                <tr>
                    <td class="id">${user.id}</td>
                    <td class="login">${user.login}</td>
                    <td class="creationTime">${user.creationTime}</td>
                    <td class="disabled">
                        <form method="post" action="">
                            <input type="hidden" name="id" value="${user.id}">
                            <input type="hidden" name="disabled" value="${if(user.disabled, "0", "1")}">
                            <div class="button-field">
                                <input type="submit" value="${if(user.disabled, "disabled", "enabled")}">
                            </div>
                        </form>
                    </td>
                </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</@c.page>
