<#import "commons.ftlh" as c>

<@c.page>
    <div class="articles">
        <template>
            <article>
                <div class="title">Codeforces Round #510 (Div. 2)</div>
                <div class="information"></div>
                <div class="body"></div>
                <div class="footer">
                    <div class="left">
                        <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                        <span class="positive-score">+173</span>
                        <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
                    </div>
                    <div class="right">
                        <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                        <div class="time"></div>
                        <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                        <a href="#">68</a>
                    </div>
                </div>
            </article>
        </template>
    </div>

    <script>
        ajax(
            {
                action: "action"
            },
            (response) => {
                response["articles"].forEach(function (article) {
                    const $tmpl = $($(".articles").find("template").prop("content")).clone();
                    $tmpl.find("article .title").text(article.title);
                    $tmpl.find("article .body").text(article.text);
                    $tmpl.find("article .footer .left .positive-score").text("+" + Math.floor(Math.random() * 100));
                    $tmpl.find("article .footer .right .time").text(article.creationTime);
                    $tmpl.find("article .footer .right a").text(Math.floor(Math.random() * 45));
                    $tmpl.find("article .information").text(
                        "By " + response["users"].find(user => user["id"] === article["userId"]).login +
                        ", at " + article.creationTime
                    );
                    $(".articles").append($tmpl);
                });
            }
        );
    </script>
</@c.page>
