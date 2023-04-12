<#import "commons.ftlh" as c>

<@c.page>
    <div class="form-box">
        <div class="header">New Article</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="create"/>
                <div class="field">
                    <div class="name">
                        <label for="title">Title</label>
                    </div>
                    <div class="value">
                        <input id="title" name="title"/>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label>Text</label>
                    </div>
                    <div class="value">
                        <label>
                            <textarea rows="5" cols="20" name="text"></textarea>
                        </label>
                    </div>
                </div>
                <div class="error"></div>
                <div class="button-field">
                    <input type="submit" value="Create">
                </div>
            </form>
        </div>
    </div>

    <script>
        $(function () {
            $(".form-box form").submit(function () {
                const $title = $(this).find("input[name='title']");
                const $text = $(this).find("textarea[name='text']");
                const $error = $(this).find(".error");

                ajax(
                    {
                        action: "create",
                        title: $title.val(),
                        text: $text.val()
                    },
                    (response) => {
                        if (!check_error(response, $error)) {
                            $(".form-box form")[0].reset();
                            $error.text("");
                            if (response["message"]) {
                                notify(response["message"]);
                            }
                        }
                    }
                );

                return false;
            });
        })
    </script>
</@c.page>
