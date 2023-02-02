package wsjava.signaling.utils;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import wsjava.signaling.MessageObject;
import wsjava.signaling.adapters.MessageObjectTypeAdapter;

public class MessageEncoder implements Encoder.Text<MessageObject> {
  final GsonBuilder gsonBuilder = new GsonBuilder();
  private Gson gson;

  @Override
  public String encode(MessageObject message) {
    gsonBuilder.registerTypeAdapter(MessageObject.class, new MessageObjectTypeAdapter());
    gsonBuilder.setPrettyPrinting();
    this.gson = gsonBuilder.create();
    return this.gson.toJson(message);
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
