package kotlinArray;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KotlinArrayLexer {
	public StringBuilder input;
	private int currPosition = 0;
	public List<CompiledToken> compiledTokens = new ArrayList<>();
	public List<CompiledToken> compiledSkipTokens = new ArrayList<>();
	public List<KotlinArrayTokens> usedTokens = new ArrayList<>();
	public List<String> parsedTokens = new ArrayList<>();

	public KotlinArrayLexer(String input) throws Exception {
		this.input = new StringBuilder(input);

		compiledTokens.add(new CompiledToken(KotlinArrayTokens.DECL, "var"));
		compiledTokens.add(new CompiledToken(KotlinArrayTokens.ARR, "Array"));
		compiledTokens.add(new CompiledToken(KotlinArrayTokens.LB, "\\<"));
		compiledTokens.add(new CompiledToken(KotlinArrayTokens.RB, "\\>"));
		compiledTokens.add(new CompiledToken(KotlinArrayTokens.COLON, "\\:"));
		compiledTokens.add(new CompiledToken(KotlinArrayTokens.SEMICOLON, "\\;"));
		compiledTokens.add(new CompiledToken(KotlinArrayTokens.COMMA, "\\,"));
		compiledTokens.add(new CompiledToken(KotlinArrayTokens.NAME, "[a-zA-Z]+"));

		compiledSkipTokens.add(new CompiledToken(KotlinArrayTokens.SPACE, "[ \n\t\r]+"));

		constructTokens();
	}

	public KotlinArrayTokens getCurrToken() {
		return usedTokens.get(currPosition);
	}

	public void toNextToken() {
		currPosition++;
	}

	public String getCurrTokenStringRepresentation() {
		return parsedTokens.get(currPosition);
	}

	private void constructTokens() throws Exception {
		while (input.length() > 0) {
			KotlinArrayTokens currToken = findToken();
			if (currToken != null) {
				usedTokens.add(currToken);
			} else if (!findSkipToken()) {
				throw new Exception("Cannot match tokens");
			}
		}
		usedTokens.add(KotlinArrayTokens.$);
	}

	private KotlinArrayTokens findToken() {
		for (CompiledToken compiled : compiledTokens) {
			Matcher matcher = compiled.pattern.matcher(input.toString());
			if (matcher.lookingAt()) {
				parsedTokens.add(input.substring(0, matcher.end()));
				input.delete(0, matcher.end());
				return compiled.token;
			}
		}
		return null;
	}

	private boolean findSkipToken() {
		for (CompiledToken compiled : compiledSkipTokens) {
			Matcher matcher = compiled.pattern.matcher(input.toString());
			if (matcher.lookingAt()) {
				input.delete(0, matcher.end());
				return true;
			}
		}
		return false;
	}

	private static class CompiledToken {
		public KotlinArrayTokens token;
		public Pattern pattern;

		public CompiledToken(KotlinArrayTokens token, String pattern) {
			this.token = token;
			this.pattern = Pattern.compile(pattern);
		}
	}
}