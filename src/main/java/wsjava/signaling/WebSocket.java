package wsjava.signaling;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

import wsjava.signaling.types.MessageObject;
import wsjava.signaling.types.DataType;
import wsjava.signaling.utils.CurrentTime;
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
    this.send(DataType.LocalId, session.getId(), String.format("Connected to Server (ID : %s)", session.getId()), session);
    this.broadcast(DataType.Peers, peerIds);
  }

  @OnMessage
  public void OnMessage(String txt, Session session){
    System.out.println(String.format("Text message received from: %s", session.getId()));
  }

  @OnClose
  public void OnClose(Session session) {
    WebSocket.peerSessions.remove(session);
    WebSocket.peerIds.remove(session.getId());
    this.broadcast(DataType.Peers, peerIds);
  }

  public void broadcast(DataType dataType, String txt) {
    String broadcastLog = String.format("String data broadcasted: ", txt);
    for (Session s: WebSocket.peerSessions) {
      this.send(dataType, txt, broadcastLog, s);
    }
  }
  public void broadcast(DataType dataType, ArrayList<String> list) {
    String broadcastLog = String.format("Peer list updated (%s peers online)", list.size());
    for (Session s: WebSocket.peerSessions) {
      this.send(dataType, list, broadcastLog, s);
    }
  }

  public void send(DataType dataType, String txt, String log, Session session) {
    try {
      String timeStamp = CurrentTime.get();
      MessageObject res = new MessageObject(dataType, txt, log, timeStamp);
      session.getBasicRemote().sendObject(res);
    } catch (IOException | EncodeException err) {
      err.printStackTrace();
    }
  }
  public void send(DataType dataType, ArrayList<String> peers, String log, Session session) {
    try {
      String timeStamp = CurrentTime.get();
      MessageObject res = new MessageObject(dataType, peers, log, timeStamp);
      session.getBasicRemote().sendObject(res);
    } catch (IOException | EncodeException err) {
      err.printStackTrace();
    }
  }
}
