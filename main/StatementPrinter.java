import java.text.NumberFormat;
import java.util.*;

public class StatementPrinter {

  public String[] print(Invoice invoice, Map<String, Play> plays) {
  
    float totalAmount = 0;  // Somme totale du
    int volumeCredits = 0;  // Points de fidélité gagnés
    String tampon = String.format("Statement for %s\n", invoice.getCustomer());  // Un tampon qui va servir à insérer dans le StringBuffer
    StringBuffer result = new StringBuffer(tampon);	//Notre StringBuffer qui a remplacé le String de départ 
 
    NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);

    int taille_hash_map = plays.size();
    String[][] tableau_pieces_infos = new String[taille_hash_map][3];   // tableau qui va contenir les infos sur les pièces
    

    int i = 0;   // Le compteur pour itérer dans le tableau d'infos sur les pièces
    

    for (Performance perf : invoice.getPerformances()) {	// On parcourt toutes les performances d'un client
      Play play = plays.get(perf.getPlayid());    //On récupère le play correspondant dans le HashMap grace à la clé
      float thisAmount = 0;	// La somme du pour la performance en cours ( dans le for)

      switch (play.getType()) {	// On effectue un traitement spécifique celon le type de pièce
        case "tragedy":		// Si la pièce est une tragedy
          thisAmount = 400;	// Le cout de la piece est mis à 400
          if (perf.getAudience() > 30) { 	// Si l'audience de la perf a dépassée 30
            thisAmount += 10 * (perf.getAudience() - 30);    // Le cout de la pièce augmente
          }
          break;
        case "comedy":		// Si la pièce est une comedy
          thisAmount = 300;     // Le cout de la piece est mis à 300
          if (perf.getAudience() > 20) {   // Si l'audience de la perf a dépassée 20
            thisAmount += 100 + 5 * (perf.getAudience() - 20);     // Le cout de la pièce augmente
          }
          thisAmount += 3 * perf.getAudience();	// Le cout de la pièce augmente encore
          break;
          
        // On a retiré default car il n'a plus d'utilité, comme on vérifie le type depuis Play
      }

      // add volume credits
      volumeCredits += Math.max(perf.getAudience() - 30, 0);  //Si l'audience est supérieur à 30 alors le client gagne des points de fidélité
      
      // add extra credit for every ten comedy attendees
      if ("comedy".equals(play.getType())) volumeCredits += Math.floor(perf.getAudience() / 5);

      // print line for this order
      

      tampon = String.format("  %s: %s (%s seats)\n", play.getName(), frmt.format(thisAmount), perf.getAudience());
      result.append(tampon);
      
      totalAmount += thisAmount;
      
      
      
      tableau_pieces_infos[i][0] = play.getName();
      tableau_pieces_infos[i][1] = String.valueOf(perf.getAudience());
      tableau_pieces_infos[i][2] = String.valueOf(frmt.format(thisAmount));
      i = i + 1;
    }
    
    tampon = String.format("Amount owed is %s\n", frmt.format(totalAmount));
    result.append(tampon);


    

    tampon = String.format("You earned %s credits\n", volumeCredits);
    result.append(tampon);
    
    
    
    String[] tableau_fin_info= new String[2];
    tableau_fin_info[0] = frmt.format(totalAmount);
    tableau_fin_info[1] = String.valueOf(volumeCredits);
    
    
    // Creer l objet Affiche facture
    Affiche_facture affiche_facture = new Affiche_facture(result.substring(0), invoice.getCustomer(), tableau_pieces_infos, tableau_fin_info);
    affiche_facture.toText();
    String facture_html = affiche_facture.toHTML();
    
    String[] tab = new String[2];
    tab[0] = result.substring(0);
    tab[1] = facture_html;
    return tab;
  }

}
