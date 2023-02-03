package wsjava.signaling.utils;

import java.nio.ByteBuffer;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

// import com.google.gson.Gson;
// import com.google.gson.GsonBuilder;
import com.google.protobuf.InvalidProtocolBufferException;
import wsjava.signaling.pbsws.Wsjava;
import wsjava.signaling.pbsws.Wsjava.MessageObject;

public class MessageDecoder implements Decoder.Binary<Wsjava.MessageObject>{
  // final GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(MessageObject.class, new MessageObjectTypeAdapter()).setPrettyPrinting();
  // final Gson gson = gsonBuilder.create();

  @Override
  public void init(EndpointConfig endpointConfig) {
      // Custom initialization logic
  }

  @Override
  public void destroy() {
      // Close resources
  }

  @Override
  public MessageObject decode(ByteBuffer bytes) throws DecodeException {
    MessageObject mso = MessageObject.getDefaultInstance();
    try {
      mso = Wsjava.MessageObject.parseFrom(bytes);
    } catch (InvalidProtocolBufferException e) {
      e.printStackTrace();
    }
    return mso;
  }

  @Override
  public boolean willDecode(ByteBuffer bytes) {
    return true;
  }
}
