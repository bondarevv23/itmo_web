<#import "./common.ftlh" as c>

<@c.page>
    <div class="datatable">
        <div class="caption">Submissions</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Name</th>
                <th>Handle</th>
            </tr>
            </thead>
            <tbody>
            <#list users as user>
                <tr>
                    <td>${user.id}</td>
                    <td><@c.userlink user user.name></@c.userlink></td>
                    <td>${user.handle}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>
