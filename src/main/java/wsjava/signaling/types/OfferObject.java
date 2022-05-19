package wsjava.signaling.types;

public class OfferObject {
  public String from;
  public String to;
  public String offer;

  public OfferObject(){}

  public OfferObject(String from, String to, String offer) {
    this.from = from;
    this.to = to;
    this.offer = offer;
  }
}
