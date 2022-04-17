package wsjava.signaling;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import wsjava.signaling.types.MessageObject;
import wsjava.signaling.utils.MessageDecoder;
import wsjava.signaling.utils.MessageEncoder;

import javax.websocket.OnOpen;
import javax.websocket.OnMessage;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.Session;

@Component
@ServerEndpoint(
  value = "/ws",
  decoders = MessageDecoder.class, 
  encoders = MessageEncoder.class
)
public class WebSocket {
  public static ArrayList<Session> peerSessions = new ArrayList<>();
  public static ArrayList<String> peerIds = new ArrayList<>();

  @OnOpen
  public void OnOpen(Session session) {
    WebSocket.peerSessions.add(session);
    WebSocket.peerIds.add(session.getId());
    this.send(session.getId(), String.format("Connection established (ID : %s)", session.getId()), session);
    String peerIdsLog = String.format("%s online peers detected", peerIds.size());
    this.send(peerIds, peerIdsLog, session);
    System.out.println(String.format("Session opened: %s", session.getId()));
    System.out.println(peerIdsLog);
  }

  @OnMessage
  public void OnMessage(String txt, Session session){
    System.out.println(String.format("Text message received from: %s", session.getId()));
    this.send(session.getId(), String.format("Connection established. Your ID is %s", session.getId()), session);
  }

  @OnClose
  public void OnClose(Session session) {
    String res = String.format("Session Closed: %s", session.getId());
    WebSocket.peerSessions.remove(session);
    WebSocket.peerIds.remove(session.getId());
    System.out.println(res);
  }

  public void send(String txt, String log, Session session) {
    try {
      MessageObject res = new MessageObject(txt, log);
      session.getBasicRemote().sendObject(res);
    } catch (IOException | EncodeException err) {
      err.printStackTrace();
    }
  }
  public void send(ArrayList<String> peers, String log, Session session) {
    try {
      MessageObject res = new MessageObject(peers, log);
      session.getBasicRemote().sendObject(res);
    } catch (IOException | EncodeException err) {
      err.printStackTrace();
    }
  }
}
