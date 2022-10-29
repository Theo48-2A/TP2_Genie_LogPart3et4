import java.text.NumberFormat;
import java.util.*;

public class Play {

  private String name;
  private TypePiecesOk type;


  public Play(String name, TypePiecesOk type) {
    this.name = Objects.requireNonNull(name);   //On déclenche une exeption si le nom est null
    this.type = type;
    

  }
  
  public String getName(){
     return this.name;
  }

  public TypePiecesOk getType(){
     return this.type;
  }

  
}



