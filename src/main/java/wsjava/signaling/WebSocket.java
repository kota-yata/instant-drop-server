package wsjava.signaling;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;

import org.springframework.stereotype.Component;

import wsjava.signaling.types.MessageObject;
import wsjava.signaling.types.OfferObject;
import wsjava.signaling.types.SessionList;
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
  public static SessionList peerSessions = new SessionList();
  public static ArrayList<String> peerIds = new ArrayList<>();
  private static Gson gson = new Gson();

  @OnOpen
  public void OnOpen(Session session) {
    WebSocket.peerSessions.add(session);
    WebSocket.peerIds.add(session.getId());
    this.send(DataType.LocalId, session.getId(), String.format("Connected to the signaling server (ID : %s)", session.getId()), session);
    this.broadcast(DataType.Peers, peerIds);
  }

  @OnMessage
  public void OnMessage(String txt, Session session){
    System.out.println(String.format("Text message received from peer ID: %s", session.getId()));
    MessageObject messageObject = gson.fromJson(txt, MessageObject.class);
    // Assume that message from peers can only be either an offer or an answer
    OfferObject offerObject = gson.fromJson(messageObject.stringData, OfferObject.class);
    this.handleSDP(messageObject.dataType, offerObject, messageObject.stringData);
  }

  private void handleSDP(DataType dataType, OfferObject offerObject, String stringified) {
    Session destinationSession = WebSocket.peerSessions.findById(offerObject.to);
    String log = String.format("%1$s received from peer ID: %2$s", dataType.toString(), offerObject.from);
    System.out.println(stringified);
    this.send(DataType.Offer, stringified, log, destinationSession);
    System.out.println(String.format("Sent a text message to %s", destinationSession.getId()));
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
    String broadcastLog = String.format("Peers list updated (%s peers online)", list.size());
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
