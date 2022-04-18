package wsjava.signaling.types;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class MessageObjectTypeAdapter extends TypeAdapter<MessageObject> {
  @Override
  public MessageObject read(final JsonReader in) throws IOException {
    final MessageObject messageObject = new MessageObject();
    in.beginObject();
    while (in.hasNext()) {
      switch(in.nextName()) {
        case "dataType":
          messageObject.dataType = DataType.toDataType(in.nextString());
          break;
        case "listData":
          messageObject.listData = new ArrayList<String>(Arrays.asList(in.nextString().split(",")));
          break;
        case "stringData":
          messageObject.stringData = in.nextString();
          break;
        case "log":
          messageObject.log = in.nextString();
          break;
        case "timeStamp":
          messageObject.timeStamp = in.nextString();
          break;
      }
    }
    in.endObject();

    return messageObject;
  }

  @Override
  public void write(final JsonWriter out, MessageObject messageObject) throws IOException {
    out.beginObject();
    DataType dataType = messageObject.dataType;
    out.name("dataType").value(dataType.toString());
    if (dataType.equals(DataType.List)) {
      out.name("listData").value(String.join(",", messageObject.listData));
    } else if (dataType.equals(DataType.String)) {
      out.name("stringData").value(messageObject.stringData);
    }
    out.name("log").value(messageObject.log);
    out.name("timeStamp").value(messageObject.timeStamp);
    out.endObject();
  }
}
