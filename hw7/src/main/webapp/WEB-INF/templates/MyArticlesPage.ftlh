<#-- @ftlvariable name="myArticles" type="ru.itmo.wp.model.domain.Article[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="datatable">
        <div class="caption">My articles</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Display</th>
            </tr>
            </thead>
            <tbody>
                <#list myArticles as article>
                    <tr>
                        <td>${article.id}</td>
                        <td>${article.title}</td>
                        <td>
                            <button id="${article.id}">${article.hidden?then('hidden', 'visible')}</button>
                        </td>
                    </tr>
                </#list>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            $("button").click(function() {
                ajax(
                    {
                        action: "changeDisplay",
                        articleId: this.id
                    },
                    (response) => {
                        $(this).text(response["displayType"] ? 'hidden' : 'visible');
                    }
                );
            });
        })
    </script>
</@c.page>
