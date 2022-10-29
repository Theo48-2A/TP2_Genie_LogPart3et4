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
                "hamlet",  new Play("Hamlet", TypePiecesOk.TRAGEDY),
                "as-like", new Play("As You Like It", TypePiecesOk.COMEDY),
                "othello", new Play("Othello", TypePiecesOk.TRAGEDY));

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
                "hamlet",  new Play("Hamlet", TypePiecesOk.TRAGEDY),
                "as-like", new Play("As You Like It", TypePiecesOk.COMEDY),
                "othello", new Play("Othello", TypePiecesOk.TRAGEDY));

        Invoice invoice = new Invoice("BigCo", List.of(
                new Performance("hamlet", 55),
                new Performance("as-like", 35),
                new Performance("othello", 40)));

        StatementPrinter statementPrinter = new StatementPrinter();
        var result = (statementPrinter.print(invoice, plays))[1];

        verify(result);
    }
    
   // Ce test n'est plus utile car maintenant une enum gere les types de pieces
/*
    @Test
    void statementWithNewPlayTypes() {

        Assertions.assertThrows(NullPointerException.class, () -> {
            new Play("Henry V", TypePiecesOk.COMEDY);
            new Play("As You Like It", TypePiecesOk.TRAGEDY);
        });
    }
}
