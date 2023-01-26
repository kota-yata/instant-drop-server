package wsjava.signaling.types;

public class StringDataObject {
  public String from;
  public String to;
  public String offer;

  public StringDataObject(){}

  public StringDataObject(String from, String to, String offer) {
    this.from = from;
    this.to = to;
    this.offer = offer;
  }
}
