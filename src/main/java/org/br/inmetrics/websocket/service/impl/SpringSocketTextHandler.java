package org.br.inmetrics.websocket.service.impl;

import java.io.IOException;

import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SpringSocketTextHandler extends StompSessionHandlerAdapter{
	
	@Override
	public void handleFrame(StompHeaders headers, Object payload) {
	    System.out.println(payload.toString());
	}

}
