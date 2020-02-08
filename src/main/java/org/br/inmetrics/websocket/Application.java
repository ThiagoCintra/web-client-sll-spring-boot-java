package org.br.inmetrics.websocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.DeploymentException;
import javax.websocket.EndpointConfig;
import javax.websocket.MessageHandler;
import javax.websocket.Session;

import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.tyrus.client.ClientManager;
import org.glassfish.tyrus.client.ClientProperties;
import org.glassfish.tyrus.server.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Application {

	static {
	    javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
	    new javax.net.ssl.HostnameVerifier(){

	        public boolean verify(String hostname,
	                javax.net.ssl.SSLSession sslSession) {
	            if (hostname.equals("localhost")) {
	                return true;
	            }
	            return false;
	        }
	    });
	}

	
	public static void main(String[] args)
			throws DeploymentException, IOException, URISyntaxException, InterruptedException {

		//cria o client
		final ClientEndpointConfig cec = ClientEndpointConfig.Builder.create().build();
		ClientManager client = ClientManager.createClient();

		SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setTrustAll(true); 
        
		//cria a sessao para comunicação .
		Session session = client.connectToServer(new javax.websocket.Endpoint() {

			@Override
			public void onOpen(Session session, EndpointConfig config) {
				System.out.println("Conexão aberta.");

				session.addMessageHandler(new MessageHandler.Whole<String>() {

					public void onMessage(String message) {
						System.out.println("[CLIENT] received: " + message);
					}

				});
			}
			//uri do socket 
		}, null, new URI("wss://localhost:8090/spring"));

		session.getBasicRemote().sendText("PING");

	}

}
