package generator;

import gen.GrammarLexer;
import gen.GrammarParser;
import generator.helpers.CustomState;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.TokenStream;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    private static String getGrammarName() {
        System.out.println("1 - calculator    2 - kotlinArray     3 - notLL");
        Scanner in = new Scanner(System.in);
        int id = in.nextInt();
        if (id == 1) {
            return "example/calculator/calculator.txt";
        } else if (id == 2) {
            return "example/kotlinArray/kotlinArray.txt";
        } else {
            return "example/notLL/notLL.txt";
        }
    }

    public static void main(String[] args) throws IOException {
        String fileName = getGrammarName();

        GrammarLexer lexer = new GrammarLexer(CharStreams.fromFileName(fileName));
        TokenStream tokenStream = new CommonTokenStream(lexer);
        GrammarParser parser = new GrammarParser(tokenStream);

        Grammar grammar = parser.start().grammar;
        grammar.constructFirst();
        grammar.constructFollow();
        grammar.checkLL1();

        LexerGenerator lexerGenerator = new LexerGenerator(grammar);
        ParserGenerator parserGenerator = new ParserGenerator(grammar);
    }
}
