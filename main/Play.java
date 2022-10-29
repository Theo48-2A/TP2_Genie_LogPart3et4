import java.text.NumberFormat;
import java.util.*;

public class Play {

// Les variables static (finales aussi) qui correspondent au type de pièce qu'il est possible d'avoir  
  private static final String comedy = "comedy";
  private static final String tragedy = "tragedy";
//-----------------------------------------------------------------------------------------------


  private String name;
  private String type;

  public Play(String name, String type) {
    this.name = Objects.requireNonNull(name);
    this.type = Objects.requireNonNull(testType(type));
    

  }
  
  //Obtenir le nom
  public String getName(){
     return this.name;
  }
  
  
  //Obtenir le titre
  public String getType(){
     return this.type;
  }

  public static String testType(String type){  //Fonction qui regarde si le type saisi est correct  
     if(type.equals(comedy)){
        return type;
     }
     else if(type.equals(tragedy)){
        return type;
     }
 
     return null;
  }
  
  
  
}







