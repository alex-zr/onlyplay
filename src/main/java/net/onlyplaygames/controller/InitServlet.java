package net.onlyplaygames.controller;

import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.onlyplaygames.service.GameEngineService;

@RequiredArgsConstructor
public class InitServlet extends HttpServlet {
    private final GameEngineService gameEngineService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        gameEngineService.init();
    }
}
