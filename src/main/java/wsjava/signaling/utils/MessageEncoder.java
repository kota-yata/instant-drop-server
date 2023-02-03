package wsjava.signaling.utils;

import java.nio.ByteBuffer;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.protobuf.InvalidProtocolBufferException;

// import com.google.gson.Gson;
// import com.google.gson.GsonBuilder;

import wsjava.signaling.pbsws.Wsjava;
import wsjava.signaling.pbsws.Wsjava.MessageObject;

public class MessageEncoder implements Encoder.Binary<Wsjava.MessageObject> {
  // final GsonBuilder gsonBuilder = new GsonBuilder();
  // private Gson gson;
  @Override
  public void init(EndpointConfig endpointConfig) {
      // Custom initialization logic
  }

  @Override
  public void destroy() {
      // Close resources
  }

  @Override
  public ByteBuffer encode(MessageObject mso) throws EncodeException {
    byte[] bytes = mso.toByteArray();
    ByteBuffer buffer = ByteBuffer.wrap(bytes);
    return buffer;
  }
}
