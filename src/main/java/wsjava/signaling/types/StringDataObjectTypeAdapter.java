package wsjava.signaling.types;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class StringDataObjectTypeAdapter extends TypeAdapter<StringDataObject> {
  @Override
  public StringDataObject read(final JsonReader in) throws IOException {
    final StringDataObject offerObject = new StringDataObject();
    in.beginObject();
    while(in.hasNext()) {
      switch(in.nextName()) {
        case "from":
          offerObject.from = in.nextString();
          break;
        case "to":
          offerObject.to = in.nextString();
          break;
        case "offer":
          offerObject.offer = in.nextString();
          break;
      }
    }
    in.endObject();
    return offerObject;
  }

  @Override
  public void write(final JsonWriter out, StringDataObject offerObject) throws IOException {
    out.beginObject();
    out.name("from").value(offerObject.from);
    out.name("to").value(offerObject.to);
    out.name("offer").value(offerObject.offer);
    out.endObject();
  }
}
