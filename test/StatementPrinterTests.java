import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.approvaltests.Approvals.verify;

public class StatementPrinterTests {

    // Test d'acceptation pour le texte
    @Test
    void testAcceptationText() {
        Map<String, Play> plays = Map.of(
                "hamlet",  new Play("Hamlet", "tragedy"),
                "as-like", new Play("As You Like It", "comedy"),
                "othello", new Play("Othello", "tragedy"));

        Invoice invoice = new Invoice("BigCo", List.of(
                new Performance("hamlet", 55),
                new Performance("as-like", 35),
                new Performance("othello", 40)));

        StatementPrinter statementPrinter = new StatementPrinter();
        var result = (statementPrinter.print(invoice, plays))[0];

        verify(result);
    }
    
    // Test d'acceptation pour le HTML
    @Test
    void testAcceptationHtml() {
        Map<String, Play> plays = Map.of(
                "hamlet",  new Play("Hamlet", "tragedy"),
                "as-like", new Play("As You Like It", "comedy"),
                "othello", new Play("Othello", "tragedy"));

        Invoice invoice = new Invoice("BigCo", List.of(
                new Performance("hamlet", 55),
                new Performance("as-like", 35),
                new Performance("othello", 40)));

        StatementPrinter statementPrinter = new StatementPrinter();
        var result = (statementPrinter.print(invoice, plays))[1];

        verify(result);
    }
    
    // Ce test n'a plus raison d'être car maintenant la validité d'un type de pièce se fait depuis la création de l'objet.
/*
    @Test
    void statementWithNewPlayTypes() {
        Map<String, Play> plays = Map.of(
                "henry-v",  new Play("Henry V", "history"),
                "as-like", new Play("As You Like It", "pastoral"));

        Invoice invoice = new Invoice("BigCo", List.of(
                new Performance("henry-v", 53),
                new Performance("as-like", 55)));

        StatementPrinter statementPrinter = new StatementPrinter();
        Assertions.assertThrows(Error.class, () -> {
            statementPrinter.print(invoice, plays);
        });
    }
*/  
    //Maintenant on vérifie que l'on est bien un NullPointerException quand on saisit un mauvais type de pièce à la création d'un objet play
    @Test
    void statementWithNewPlayTypes() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            new Play("Henry V", "history");
            new Play("As You Like It", "pastoral");
        });
    }
}
