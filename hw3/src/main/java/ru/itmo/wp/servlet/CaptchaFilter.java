package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class CaptchaFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        switch (request.getMethod()) {
            case "POST":
                filterPost(request, response, chain);
                break;
            case "GET":
                filterGet(request, response, chain);
                break;
            default:
                chain.doFilter(request, response);
        }
    }

    private void filterPost(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (!request.getRequestURI().equals(CAPTCHA_HTML)) {
            chain.doFilter(request, response);
            return;
        }
        HttpSession session = request.getSession();
        String inputValueVar = request.getParameter(INPUT_VALUE);
        String captchaValueVar = (String) session.getAttribute(CAPTCHA_VALUE);
        if (!Objects.isNull(inputValueVar) && inputValueVar.equals(captchaValueVar)) {
            session.setAttribute(CAPTCHA_PASSED, Boolean.TRUE);
            response.sendRedirect(INDEX_HTML);
            return;
        }
        reloadCaptcha(request, response);
    }

    private void filterGet(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        for (String uri : ALLOWED_URLS) {
            if (request.getRequestURI().equals(uri)) {
                chain.doFilter(request, response);
                return;
            }
        }
        if (Boolean.TRUE.equals(request.getSession().getAttribute(CAPTCHA_PASSED))) {
            chain.doFilter(request, response);
            return;
        }
        if (request.getRequestURI().equals(REQUEST_CAPTCHA_IMG)) {
            generateCaptchaValue(request);
            sendCaptchaImage(request, response);
            return;
        }
        request.getSession().setAttribute(CAPTCHA_GENERATED, true);
        request.getSession().setAttribute(REQUEST_URI, request.getRequestURI());
        reloadCaptcha(request, response);
    }

    private void reloadCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.setAttribute(CAPTCHA_PASSED, Boolean.FALSE);
        generateCaptchaValue(request);
        response.sendRedirect(CAPTCHA_HTML);
    }

    private void generateCaptchaValue(HttpServletRequest request) {
        if (Boolean.TRUE.equals(request.getSession().getAttribute(CAPTCHA_GENERATED))) {
            return;
        }
        String randomNum = String.valueOf(ThreadLocalRandom.current().nextInt(100, 1000));
        request.getSession().setAttribute(CAPTCHA_VALUE, randomNum);
    }

    private static void sendCaptchaImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        HttpSession session = request.getSession();
        String captchaValueVar = (String) session.getAttribute(CAPTCHA_VALUE);
        response.getOutputStream().write(ImageUtils.toPng(captchaValueVar));
    }

    private static final String INPUT_VALUE = "inputValue";
    private static final String CAPTCHA_VALUE = "captchaValue";
    private static final String CAPTCHA_PASSED = "captchaPassed";
    private static final String REQUEST_URI = "requestUri";
    private static final String CAPTCHA_GENERATED = "captchaGenerated";

    private static final String[] ALLOWED_URLS = {"/captcha.html", "/css/captcha.css"};
    private static final String REQUEST_CAPTCHA_IMG = "/request/captchaImg";
    private static final String CAPTCHA_HTML = "/captcha.html";
    private static final String INDEX_HTML = "/index.html";
}
