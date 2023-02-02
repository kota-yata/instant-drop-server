package wsjava.signaling.utils;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import wsjava.signaling.MessageObject;
import wsjava.signaling.adapters.MessageObjectTypeAdapter;

public class MessageDecoder implements Decoder.Text<MessageObject>{
  final GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(MessageObject.class, new MessageObjectTypeAdapter()).setPrettyPrinting();
  final Gson gson = gsonBuilder.create();

  @Override
  public MessageObject decode(String s) throws DecodeException {
    return this.gson.fromJson(s, MessageObject.class);
  }

  @Override
  public boolean willDecode(String s) {
      return (s != null);
  }

  @Override
  public void init(EndpointConfig endpointConfig) {
      // Custom initialization logic
  }

  @Override
  public void destroy() {
      // Close resources
  }
}
