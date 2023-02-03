package wsjava.signaling;

import java.io.IOException;
import java.util.ArrayList;

import javax.websocket.server.ServerEndpoint;

// import com.google.gson.Gson;

import org.springframework.stereotype.Component;

import wsjava.signaling.pbsws.Wsjava.DataType;
import wsjava.signaling.pbsws.Wsjava.MessageObject;
import wsjava.signaling.pbsws.Wsjava.StringDataObject;
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

  @OnOpen
  public void OnOpen(Session session) {
    WebSocket.peerSessions.add(session);
    WebSocket.peerIds.add(session.getId());
    this.send(DataType.LocalId, session.getId(), String.format("Connected to the signaling server (ID : %s)", session.getId()), session);
    this.broadcast(DataType.Peers, peerIds);
  }

  @OnMessage
  public void OnMessage(MessageObject mso, Session session){
    System.out.println(String.format("Text message received from peer ID: %s", session.getId()));
    StringDataObject ofo = mso.getStringDataObject();
    this.handleSDP(mso.getDataType(), ofo);
  }

  private void handleSDP(DataType dataType, StringDataObject offerObject) {
    Session destinationSession = WebSocket.peerSessions.findById(offerObject.getTo());
    String log = String.format("%1$s received from peer ID: %2$s", dataType.toString(), offerObject.getFrom());
    this.send(dataType, offerObject, log, destinationSession);
    System.out.println(String.format("Sent an SDP to %s", destinationSession.getId()));
  }

  @OnClose
  public void OnClose(Session session) {
    WebSocket.peerSessions.remove(session);
    WebSocket.peerIds.remove(session.getId());
    this.broadcast(DataType.Peers, peerIds);
  }

  public void broadcast(DataType dataType, StringDataObject sdo) {
    String broadcastLog = String.format("StringDataObject broadcasted");
    for (Session s: WebSocket.peerSessions) {
      this.send(dataType, sdo, broadcastLog, s);
    }
  }
  public void broadcast(DataType dataType, ArrayList<String> list) {
    String broadcastLog = String.format("Peers list updated (%s peers online)", list.size());
    for (Session s: WebSocket.peerSessions) {
      this.send(dataType, list, broadcastLog, s);
    }
  }

  public void send(DataType dataType, StringDataObject sdo, String log, Session session) {
    try {
      String timeStamp = CurrentTime.get();
      MessageObject.Builder msoBuilder = MessageObject.newBuilder();
      msoBuilder.setDataType(dataType).setStringDataObject(sdo).setLog(log).setTimeStamp(timeStamp);
      MessageObject mso = msoBuilder.build();
      session.getBasicRemote().sendObject(mso);
    } catch (IOException | EncodeException err) {
      err.printStackTrace();
    }
  }
  public void send(DataType dataType, ArrayList<String> peers, String log, Session session) {
    try {
      String timeStamp = CurrentTime.get();
      MessageObject.Builder msoBuilder = MessageObject.newBuilder();
      msoBuilder.setDataType(dataType).addAllListData(peers).setLog(log).setTimeStamp(timeStamp);
      MessageObject mso = msoBuilder.build();
      session.getBasicRemote().sendObject(mso);
    } catch (IOException | EncodeException err) {
      err.printStackTrace();
    }
  }
  public void send(DataType dataType, String stringData, String log, Session session) {
    try {
      String timeStamp = CurrentTime.get();
      MessageObject.Builder msoBuilder = MessageObject.newBuilder();
      msoBuilder.setDataType(dataType).setStringData(stringData).setLog(log).setTimeStamp(timeStamp);
      MessageObject mso = msoBuilder.build();
      session.getBasicRemote().sendObject(mso);
    } catch (IOException | EncodeException err) {
      err.printStackTrace();
    }
  }
}
