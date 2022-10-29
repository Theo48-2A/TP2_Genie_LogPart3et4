import java.util.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Objects;
import java.text.NumberFormat;



public class Affiche_facture{

   private String contenu_Facture;     //Le contenu de la facture
   private Customer client;  		// Nom du client
   private String[][] tableau_pieces_infos;    //Le tableau qui contient chaque pièce avec ses informations [[nom][nb_spectateurs][cout]]
   private float[] tableau_fin_info;         //Le tableau qui contient le cout total ainsi que les points de fidelité gagnés

   public Affiche_facture(String contenu_Facture, Customer client, String[][] tableau_pieces_infos, float[] tableau_fin_info){
      this.contenu_Facture = Objects.requireNonNull(contenu_Facture);
      this.client = Objects.requireNonNull(client);
      this.tableau_pieces_infos = Objects.requireNonNull(tableau_pieces_infos);
      this.tableau_fin_info = Objects.requireNonNull(tableau_fin_info);

   }

   public String getContenu_facture(){
      return this.contenu_Facture;
   }
   
   public Customer getClient(){
      return this.client;
   }
   
   public String[][] getTableau_pieces_infos(){
      return this.tableau_pieces_infos;
   }

   public float[] getTableau_fin_info(){
      return this.tableau_fin_info;
   }



   public boolean toText(){
   
     //chemin vers les fichiers txt générés : "../../../fichiers_html_text_generes/Text/CeFichierDeBoss.txt"

      PrintWriter Facture_text = null;
      String Facture = this.getContenu_facture();
        
      try {
         Facture_text = new PrintWriter("fichiers_html_text_generes/Text/" + this.getClient().getNom() + "_Facture_text.txt");
      } 
      catch (FileNotFoundException e) {
         System.out.println("Erreur lors de la création du fichier: " + e.getMessage());

      }
      Objects.requireNonNull(Facture_text).println(Facture);
      Facture_text.close();
        
      return true;   
   }
   

  
   
   public String toHTML(){
   
   //chemin vers les fichiers html générés : "../../../fichiers_html_text_generes/HTML/CeFichierDeBoss.txt"
     
     
      PrintWriter Facture_HTML = null;
      String Facture = Transform_text_en_html();    //Transformation de la facture text en html
        
      try {
         Facture_HTML = new PrintWriter("fichiers_html_text_generes/HTML/" + this.getClient().getNom() + "_Facture_HTML.html");
      } 
      catch (FileNotFoundException e) {
         System.out.println("Erreur lors de la création du fichier: " + e.getMessage());

      }
      Objects.requireNonNull(Facture_HTML).println(Facture);
      Facture_HTML.close();
        
      return Facture;   
   }




   public String Transform_text_en_html(){
      //Pour afficher les sommes d'argent
      NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US); 
      
   //On va insérer petit à petit le code html dans un StringBuffer

      //Balise doctype html
      String tampon = "<!doctype html>\n";  
      StringBuffer html = new StringBuffer(tampon); 
      
      //debut balise html
      tampon = "<html lang=\"fr\">\n";  
      html.append(tampon);
      
      //debut balise head
      tampon = "<head>\n"; 
      html.append(tampon);
      
      //Balise meta charset
      tampon = "  <meta charset=\"utf-8\">\n"; 
      html.append(tampon);
      
      //Balise titre
      tampon = "  <title>Facture</title>\n";  
      html.append(tampon);
      
      // Balise style
      tampon = "  <style>\n" 
      				+ "     td,th {\n"
      				+ "         border: 1px solid rgb(190, 190, 190);\n"
      				+ "         padding: 10px;\n"
      				+ "     }\n"
      				+ "     table {\n"
      				+ "         border: 2px solid rgb(200, 200, 200);\n"
      				+ "         font-family: sans-serif;\n"
      				+ "         font-size: .8rem;\n"
      				+ "     }\n"
      				+ "     i {\n"
      				+ "         display: inline-block;\n"
      				+ "         margin-top: 30px;\n"
      				+ "     }\n"
      				+ "  </style>\n"; 
      html.append(tampon);
      
      //Balise script pour js (meme s'il n'y en a pas ici)
      tampon = "  <script src=\"script.js\"></script>\n";
      html.append(tampon);
      
      //Debut balise head
      tampon = "</head>\n";
      html.append(tampon);

      //Debut balise body
      tampon = "<body>\n";
      html.append(tampon);
      
      //un titre en h1
      tampon = "  <h1>Invoice</h1>\n";
      html.append(tampon);
      

      tampon = "  <ul>\n";
      html.append(tampon);
      

      tampon = "    <li><b>Client Identifier : </b>" + this.getClient().getNumeroClient() + "</li>\n";
      html.append(tampon);


      tampon = "    <li><b>Client Name: </b>" + this.getClient().getNom() + "</li>\n";
      html.append(tampon);
      

      tampon = "  </ul>\n";
      html.append(tampon);


     
      
      //Maintenant le tableau: --------------------------------------------------------------------------------------------------------------------------------------
      
      tampon = "  <table>\n";
      html.append(tampon);

      tampon = "    <tr>\n" 
                        +  "      <th>Pièce</th>\n" 
                        +  "      <th>Seats sold</th>\n" 
                        +  "      <th>Price</th>\n"          
                   + "    </tr>\n"; 
      html.append(tampon);
            
      // Cette partie est itérée par un for car dépendante du nombre de pièces à traiter
      
      for(String piece_info[] : this.getTableau_pieces_infos()){


		 
         tampon = "    <tr>\n" +
         				"      <td>" +  piece_info[0] + "</td>\n"	
         			       +"      <td>" +  piece_info[1] + "</td>\n"
         			       +"      <td style=\"text-align:right\">" +  piece_info[2] + "</td>\n"	
         		    +  "    </tr>\n";
         html.append(tampon);
      }
      
      

      // La fin
      // 2 possibilité d'affichage, celon si le client a droit à la reduction de 15$ ou non, on utilise un if et un else pour cela

      float tmp;  // Un tampon dont on va se servir pour que le code soit plus propre
      int tmpB;

      if(this.getClient().getSoldePointsFidelite() >= 150){
         tampon = "    <tr>\n" 
      				+ "      <th COLSPAN=\"3\"style=\"text-align:right\">" + "Congratulations, you have won a $15 reduction for your fidelity." + "</th>\n" 

      			 + "    </tr>\n"; 
         html.append(tampon);
         
         tmp = this.getTableau_fin_info()[0];
         
         tampon = "    <tr>\n" 
      				+ "      <th COLSPAN=\"2\" style=\"text-align:right\">" + "Total owed:" + "</th>\n" 
      			     +    "      <td>" + frmt.format(tmp) + " (" + frmt.format((tmp + 15)) + " - " + frmt.format(15)+ ")" +  "</td>\n" 
      			 + "    </tr>\n"; 
         html.append(tampon);
      			 
      
         //Fidelity points earned
         
         tmp = this.getTableau_fin_info()[1];
         			                  
         tampon = "    <tr>\n" 
                                + "      <th COLSPAN=\"2\" style=\"text-align:right\">" + "Fidelity points earned:" + "</th>\n"
                                + "      <td>" +  String.valueOf((int)tmp)  +  "</td>\n" 
                         + "    </tr>\n";  
         html.append(tampon);
      
         //Nombres de points de fidelité total
         
         tmpB = this.getClient().getSoldePointsFidelite();
         
         tampon = "    <tr>\n" 
                                + "      <th COLSPAN=\"2\" style=\"text-align:right\">" + "Fidelity points total:" + "</th>\n"
                                + "      <td>" + String.valueOf(tmpB-150) + " (" +  String.valueOf(tmpB) + "-" + "150" + ")" + "</td>\n" 
                         + "    </tr>\n";  
         html.append(tampon); 
         
      }
      
      
      else{	
         
         tmp = this.getTableau_fin_info()[0];
      		
         tampon = "    <tr>\n" 
      				+ "      <th COLSPAN=\"2\" style=\"text-align:right\">" + "Total owed:" + "</th>\n" 
      			     +    "      <td>" + frmt.format(tmp) +  "</td>\n" 
      			 + "    </tr>\n"; 
         html.append(tampon);
      			 
      
         //Fidelity points earned
         
         tmp = this.getTableau_fin_info()[1];
         			                  
         tampon = "    <tr>\n" 
                                + "      <th COLSPAN=\"2\" style=\"text-align:right\">" + "Fidelity points earned:" + "</th>\n"
                                + "      <td>" +  String.valueOf((int)tmp)  +  "</td>\n" 
                         + "    </tr>\n";  
         html.append(tampon);
      
         //Nombres de points de fidelité total
         
         tmpB = this.getClient().getSoldePointsFidelite();
         
         tampon = "    <tr>\n" 
                                + "      <th COLSPAN=\"2\" style=\"text-align:right\">" + "Fidelity points total:" + "</th>\n"
                                + "      <td>" +  String.valueOf(tmpB)  +  "</td>\n" 
                         + "    </tr>\n";  
         html.append(tampon);           
      }
      
      //Fermeture balise table
      tampon = "  </table>\n";
      html.append(tampon);
      
      
      
   
     //fin tableau: --------------------------------------------------------------------------------------------------------------------------------------------

     
      tampon = "  <i>Payment is required under 30 days. We can break your knees if you don't do so.</i>\n";
      html.append(tampon);
      
      
      
      //Fermeture body
      tampon = "</body>\n";  
      html.append(tampon);
      
        

      //Fermeture html
      tampon = "</html>";
      html.append(tampon);
      
      
      return html.substring(0);   //On retourne la page html générée
   }

}
