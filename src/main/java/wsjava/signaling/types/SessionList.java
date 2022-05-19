package wsjava.signaling.types;

import java.util.ArrayList;

import javax.websocket.Session;

public class SessionList extends ArrayList<Session> {
  public Session findById(String id) {
    Session target = this.get(0); // Temporal session to avoid "returning not initialized variable" error
    for (Session session : this) {
      if (session.getId().equals(id)) {
        target = session;
      }
    }
    return target;
  }
}
