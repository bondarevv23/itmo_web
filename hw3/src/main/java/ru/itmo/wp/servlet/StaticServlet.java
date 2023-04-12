package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class StaticServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] urls = request.getRequestURI().split("\\+");
        response.setContentType(getServletContext().getMimeType(urls[0]));
        for (String url : urls) {
            File file = getFile(url);
            if (file.isFile()) {
                try (OutputStream outputStream = response.getOutputStream()) {
                    Files.copy(file.toPath(), outputStream);
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                response.setContentType(null);
            }
        }
    }

    private File getFile(String uri) {
        File fileToSrc = new File(getServletContext().getRealPath("."), "../../src/main/webapp/static/" + uri);
        if (fileToSrc.isFile()) {
            return fileToSrc;
        }
        return new File(getServletContext().getRealPath("/static"), uri);
    }
}
