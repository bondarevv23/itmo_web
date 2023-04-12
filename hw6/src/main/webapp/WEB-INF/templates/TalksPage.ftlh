<#-- @ftlvariable name="talks" type="ru.itmo.wp.model.domain.DTO.TalkDTO[]" -->
<#-- @ftlvariable name="text" type="java.lang.String" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="form-box">
        <div class="header">Send message</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="sendMessage"/>
                <div class="field">
                    <div class="name">
                        <label>Message</label>
                    </div>
                    <div class="value">
                        <textarea rows="5" cols="20" name="text">${text!}</textarea>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label>Target user</label>
                    </div>
                    <div class="value">
                        <select required size="1" name="targetUser">
                            <option selected disabled>choose target</option>
                            <#list users as u>
                                <#if user??>
                                    <#if user.id != u.id>
                                        <option value="${u.login}">${u.login}</option>
                                    </#if>
                                <#else>
                                    <option value="${u.login}">${u.login}</option>
                                </#if>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="error">
                    ${error!}
                </div>
                <div class="button-field">
                    <input type="submit" value="Send">
                </div>
            </form>
        </div>
    </div>

    <div class="datatable">
        <div class="caption">Messages</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>From</th>
                <th>To</th>
                <th>Text</th>
                <th>Time</th>
            </tr>
            </thead>
            <tbody>
            <#if talks??>
                <#list talks as talk>
                <tr>
                    <td>${talk.id}</td>
                    <td>${talk.sourceUserLogin}</td>
                    <td>${talk.targetUserLogin}</td>
                    <td>${talk.text}</td>
                    <td>${talk.creationTime}</td>
                </tr>
            </#list>
            </#if>
            </tbody>
        </table>
    </div>
</@c.page>
