public class Performance {

  private String playID;  //Identifiant performence
  private int audience;   //Nombre de spectateurs

  public Performance(String playID, int audience) {
    this.playID = playID;
    this.audience = audience;
  }
  
  public String getPlayid(){
     return this.playID;
  }
  
  public int getAudience(){
     return this.audience;
  }
  
  
}
