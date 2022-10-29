import java.text.NumberFormat;
import java.util.*;
//import java.util.concurrent.atomic.AtomicLong;


public class Customer{

   private String nom;
   private int numeroClient;
   private int soldePointsFidelite;


   public Customer(String nom, int numeroClient, int soldePointsFidelite){
      this.nom = nom;
      this.numeroClient = numeroClient;
      this.soldePointsFidelite= soldePointsFidelite;
   }
   
   
   
   public String getNom(){
      return this.nom;
   }
   

   public int getNumeroClient(){
      return this.numeroClient;
   }
   

   public int getSoldePointsFidelite(){
      return this.soldePointsFidelite;
   }
 
   public void setSoldePointsFidelite(int nbPoints){
      this.soldePointsFidelite = nbPoints;
   }
 
   
 
 
 
 
 
 
   

}




