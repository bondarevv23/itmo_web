package ru.itmo.wp.web.page;

import com.google.common.base.Strings;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class IndexPage extends AbstractPage {
    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        view.put("articles", articleService.getAllReverseOrder());
        view.put("users", userService.findAll());
    }
}
