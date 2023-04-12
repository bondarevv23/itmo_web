package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MessageServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        switch (request.getRequestURI()) {
            case ("/message/auth"):
                authorization(request, response);
                break;
            case ("/message/findAll"):
                getAllMessages(request, response);
                break;
            case ("/message/add"):
                addMessage(request);
                break;
            default:
                throw new ServletException ("unknown uri: " + request.getRequestURI());
        }
    }

    private static void authorization(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getSession().getAttribute("user") != null) {
            setJsonResponse(response, request.getSession().getAttribute("user"));
            return;
        }
        String userName = request.getParameter("user");
        if (Objects.isNull(userName) || userName.trim().isEmpty()) {
            setJsonResponse(response, "");
            return;
        }
        HttpSession session = request.getSession();
        session.setAttribute("user", userName);
        setJsonResponse(response, userName);
    }

    private static void getAllMessages(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!((String) request.getSession().getAttribute("user")).isEmpty()) {
            setJsonResponse(response, messages);
        }
    }

    private static void setJsonResponse(HttpServletResponse response, Object obj) throws IOException {
        response.setContentType("application/json");
        response.getWriter().print(new Gson().toJson(obj));
        response.getWriter().flush();
    }

    private static void addMessage(HttpServletRequest request) throws ServletException {
        String user = ((String) request.getSession().getAttribute("user"));
        String text = request.getParameter("text");
        if (Objects.isNull(text)) {
            throw new ServletException ("expected 'text' parameter in post request: " + request.getRequestURI());
        }
        text = text.trim();
        if (text.length() == 0) {
            return;
        }
        messages.add(new Message(user, text));
    }

    private static final List<Message> messages = new ArrayList<>();

    private static class Message {
        public final String user;
        public final String text;

        public Message(String user, String text) {
            this.user = user;
            this.text = text;
        }
    }
}
