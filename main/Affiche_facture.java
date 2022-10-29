import java.util.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Objects;

public class Affiche_facture{

   private String contenu_Facture;     //Le contenu de la facture
   private String nom_client;  		// Nom du client
   private String[][] tableau_pieces_infos;    //Le tableau qui contient chaque pièce avec ses informations [[nom][nb_spectateurs][cout]]
   private String[] tableau_fin_info;         //Le tableau qui contient le cout total ainsi que les points de fidelité gagnés

   public Affiche_facture(String contenu_Facture, String nom_client, String[][] tableau_pieces_infos, String[] tableau_fin_info){
      this.contenu_Facture = Objects.requireNonNull(contenu_Facture);
      this.nom_client = Objects.requireNonNull(nom_client);
      this.tableau_pieces_infos = Objects.requireNonNull(tableau_pieces_infos);
      this.tableau_fin_info = Objects.requireNonNull(tableau_fin_info);

   }

   //Obtenir le Contenu de la facture
   public String getContenu_facture(){
      return this.contenu_Facture;
   }
   
   //Obtenir le nom du client
   public String getNomclient(){
      return this.nom_client;
   }
   
   //Obtenir le tableau qui contient les informations des pièces de théâtre
   public String[][] getTableau_pieces_infos(){
      return this.tableau_pieces_infos;
   }

   //Obtenir le tableau qui contient la somme dû par le client ainsi que les points de fidélité qu'il a gagné
   public String[] getTableau_fin_info(){
      return this.tableau_fin_info;
   }



   public boolean toText(){
   
     //chemin vers les fichiers txt générés : "../../../fichiers_html_text_generes/Text/CeFichierDeBoss.txt"

      PrintWriter Facture_text = null;
      String Facture = this.getContenu_facture();
        
      try {
         Facture_text = new PrintWriter("fichiers_html_text_generes/Text/" + this.getNomclient() + "_Facture_text.txt");
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
         Facture_HTML = new PrintWriter("fichiers_html_text_generes/HTML/" + this.getNomclient() + "_Facture_HTML.html");
      } 
      catch (FileNotFoundException e) {
         System.out.println("Erreur lors de la création du fichier: " + e.getMessage());

      }
      Objects.requireNonNull(Facture_HTML).println(Facture);
      Facture_HTML.close();
        
      return Facture;   
   }




   public String Transform_text_en_html(){
   
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
      
      //fin balise head
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
      

      tampon = "    <li><b>Client : </b>" + this.getNomclient() + "</li>\n";
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
      
      

      // Les deux dernieres lignes 
      
      //Total owed			
      tampon = "    <tr>\n" 
      				+ "      <th COLSPAN=\"2\" style=\"text-align:right\">" + "Total owed:" + "</th>\n" 
      			     +    "      <td>" +  this.getTableau_fin_info()[0]  +  "</td>\n" 
      			 + "    </tr>\n"; 
      html.append(tampon);
      			 
      
      //Fidelity points earned			                  
      tampon = "    <tr>\n" 
                                + "      <th COLSPAN=\"2\" style=\"text-align:right\">" + "Fidelity points earned:" + "</th>\n"
                                + "      <td>" +  this.getTableau_fin_info()[1]  +  "</td>\n" 
                         + "    </tr>\n";  
      html.append(tampon);
      
      
                 
      
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
