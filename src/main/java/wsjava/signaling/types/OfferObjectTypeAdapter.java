package wsjava.signaling.types;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class OfferObjectTypeAdapter extends TypeAdapter<OfferObject> {
  @Override
  public OfferObject read(final JsonReader in) throws IOException {
    final OfferObject offerObject = new OfferObject();
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
  public void write(final JsonWriter out, OfferObject offerObject) throws IOException {
    out.beginObject();
    out.name("from").value(offerObject.from);
    out.name("to").value(offerObject.to);
    out.name("offer").value(offerObject.offer);
    out.endObject();
  }
}
