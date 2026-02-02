package net.onlyplaygames;

import net.onlyplaygames.controller.InitServlet;
import net.onlyplaygames.controller.RequestServlet;
import net.onlyplaygames.service.BetProviderService;
import net.onlyplaygames.service.ChiliGameplayService;
import net.onlyplaygames.service.DefaultBetProviderService;
import net.onlyplaygames.service.DefaultChiliGameplayService;
import net.onlyplaygames.service.DefaultSpinDataProviderService;
import net.onlyplaygames.service.SimpleGameEngineService;
import net.onlyplaygames.service.GameEngineService;
import net.onlyplaygames.service.SpinDataProviderService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class JettyRun {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        SpinDataProviderService spinDataProviderService = new DefaultSpinDataProviderService();
        BetProviderService betProviderService = new DefaultBetProviderService();
        ChiliGameplayService chiliGameplayService = new DefaultChiliGameplayService();
        GameEngineService gameEngineService = new SimpleGameEngineService(spinDataProviderService, betProviderService,
            chiliGameplayService);
        ServletContextHandler handler = new ServletContextHandler();
        InitServlet initServlet = new InitServlet(gameEngineService);
        RequestServlet requestServlet = new RequestServlet(gameEngineService);

        handler.addServlet(new ServletHolder(initServlet), "/game_init");
        handler.addServlet(new ServletHolder(requestServlet), "/game_request");
        server.setHandler(handler);


        server.start();
        server.join();
    }
}
