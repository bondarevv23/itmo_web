<#import "commons.ftlh" as c>

<@c.page>
    <div class="form-box">
        <div class="header">Write Notice</div>
        <div class="body">
            <form method="post" action="">
                <div class="field">
                    <div class="name">
                        <label for="login">Content</label>
                    </div>
                    <div class="value">
                        <textarea id="content" name="content">${makeNoticeForm.content!}</textarea>
                    </div>
                    <@c.error "makeNoticeForm.content"/>
                </div>
                <div class="button-field">
                    <input type="submit" value="Write">
                </div>
            </form>
        </div>
    </div>
</@c.page>