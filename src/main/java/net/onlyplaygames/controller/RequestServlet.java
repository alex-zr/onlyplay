package net.onlyplaygames.controller;

import java.io.IOException;
import java.util.Objects;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.onlyplaygames.service.GameEngineService;
import net.onlyplaygames.util.JsonUtil;

@RequiredArgsConstructor
public class RequestServlet extends HttpServlet {
    private final GameEngineService gameEngineService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        if (Objects.isNull(id) || id.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("text/plain");
            response.getWriter().write("Error: Missing or empty 'id' parameter.");
            return;
        }
        if (!gameEngineService.isValidIdParam(id)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("text/plain");
            response.getWriter().write("Error: Incorrect value of 'id' parameter, %s".formatted(id));
            return;
        }
        Object responseObject = gameEngineService.processRequest(id);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println(JsonUtil.toJson(responseObject));
    }
}
