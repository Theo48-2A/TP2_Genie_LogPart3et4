import java.text.NumberFormat;
import java.util.*;



public class StatementPrinter{

  public String[] print(Invoice invoice, Map<String, Play> plays) {
  
    float totalAmount = 0;  // Somme totale du
    int volumeCredits = 0;  // Points de fidélité gagnés
    String tampon = String.format("Statement for %s, (client_id : %s)\n", invoice.getCustomer().getNom(), invoice.getCustomer().getNumeroClient());  // Un tampon qui va servir à insérer dans le StringBuffer
    StringBuffer result = new StringBuffer(tampon);	//Notre StringBuffer qui a remplacé le String de départ 
 
    NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);

    int taille_hash_map = plays.size();
    String[][] tableau_pieces_infos = new String[taille_hash_map][3];   // tableau qui va contenir les infos sur les pièces
    

    int i = 0;   // Le compteur pour itérer dans le tableau d'infos sur les pièces
    

    for (Performance perf : invoice.getPerformances()) {	// On parcourt toutes les performances d'un client
      Play play = plays.get(perf.getPlayid());    //On récupère le play correspondant dans le HashMap grace à la clé
      float thisAmount = 0;	// La somme du pour la performance en cours ( dans le for)

      switch (play.getType()) {	// On effectue un traitement spécifique celon le type de pièce
        case TRAGEDY:		// Si la pièce est une tragedy
          thisAmount = 400;	// Le cout de la piece est mis à 400
          if (perf.getAudience() > 30) { 	// Si l'audience de la perf a dépassée 30
            thisAmount += 10 * (perf.getAudience() - 30);    // Le cout de la pièce augmente
          }
          break;
        case COMEDY:		// Si la pièce est une comedy
          thisAmount = 300;     // Le cout de la piece est mis à 300
          if (perf.getAudience() > 20) {   // Si l'audience de la perf a dépassée 20
            thisAmount += 100 + 5 * (perf.getAudience() - 20);     // Le cout de la pièce augmente
          }
          thisAmount += 3 * perf.getAudience();	// Le cout de la pièce augmente encore
          break;
          
      }

      // add volume credits
      volumeCredits += Math.max(perf.getAudience() - 30, 0);
      
      // add extra credit for every ten comedy attendees
      if ((TypePiecesOk.COMEDY).equals(play.getType())) volumeCredits += Math.floor(perf.getAudience() / 5);

      // print line for this order
      tampon = String.format("  %s: %s (%s seats)\n", play.getName(), frmt.format(thisAmount), perf.getAudience());
      result.append(tampon);
      
      totalAmount += thisAmount;
      
      
      
      tableau_pieces_infos[i][0] = play.getName();
      tableau_pieces_infos[i][1] = String.valueOf(perf.getAudience());
      tableau_pieces_infos[i][2] = String.valueOf(frmt.format(thisAmount));
      i = i + 1;
    }
    
    
    tampon = String.format("You earned %s credits\n", volumeCredits);
    result.append(tampon);
    
    int actualCredit = invoice.getCustomer().getSoldePointsFidelite();
    invoice.getCustomer().setSoldePointsFidelite(actualCredit + volumeCredits);
    actualCredit = invoice.getCustomer().getSoldePointsFidelite();
    
    
    if(actualCredit >= 150){     //Un réduction va s'appliquer
       tampon = "You have a reduction of 15 $ for your fidelity\n";
       result.append(tampon);

       actualCredit -= 150;
           
       tampon = String.format("Your total credit : %s (%s - 150)\n", actualCredit, (actualCredit+150));
       result.append(tampon);
       
       totalAmount = totalAmount - 15;       
        
    }
    else{  //Pas de réduction disponible
       tampon = String.format("Your total credit : %s\n", actualCredit);
       result.append(tampon); 
    }
    
    tampon = String.format("Amount owed is %s\n", frmt.format(totalAmount));
    result.append(tampon);
        
    float[] tableau_fin_info= new float[2];
    //tableau_fin_info[0] = frmt.format(totalAmount);
    tableau_fin_info[0] = totalAmount;
    tableau_fin_info[1] = volumeCredits;
    
    Affiche_facture affiche_facture = new Affiche_facture(result.substring(0), invoice.getCustomer(), tableau_pieces_infos, tableau_fin_info);
    affiche_facture.toText();
    String facture_html = affiche_facture.toHTML();
    
    //Mise à jour des points de fidélité du client
    invoice.getCustomer().setSoldePointsFidelite(actualCredit);
    
    String[] tab = new String[2];
    tab[0] = result.substring(0);
    tab[1] = facture_html;
    return tab;
  }
  
  

}
