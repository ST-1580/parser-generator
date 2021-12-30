package kotlinArray;

import java.util.ArrayList;
import java.util.List;

public class KotlinArrayParser {
	public KotlinArrayLexer lexer;
	public Node tree;

	public KotlinArrayParser(KotlinArrayLexer lexer) throws Exception {
		this.lexer = lexer;
		buildTree();
	}

	public void buildTree() throws Exception {
		tree = build_s();
		if (lexer.getCurrToken() != KotlinArrayTokens.$) {
			throw new Exception("The end of the input. Expected $, but get " + lexer.getCurrToken());
		}
	}

	private class Node {
		public String name;
		public List<Node> children = new ArrayList<>();

		public Node(String name) {
			this.name = name;
		}

		public void addChild(Node node) {
			children.add(node);
		}
	}

	private class Node_types extends Node {
		public Node_types() {
			super("types");
		}
	}

	private class Node_s extends Node {
		public Node_s() {
			super("s");
		}
	}

	private class Node_otherTypes extends Node {
		public Node_otherTypes() {
			super("otherTypes");
		}
	}

	private class Node_arrayAndLB extends Node {
		public Node_arrayAndLB() {
			super("arrayAndLB");
		}
	}

	private class Node_inside extends Node {
		public Node_inside() {
			super("inside");
		}
	}

	private Node_types build_types() throws Exception {
		Node_types res = new Node_types();
		switch (lexer.getCurrToken()) {
			case NAME: {
				if (KotlinArrayTokens.NAME != lexer.getCurrToken()) {
					throw new Exception("Expected NAME token, but get " + lexer.getCurrToken());
				}
				res.addChild(new Node("NAME"));
				lexer.toNextToken();

				Node_otherTypes node0 = build_otherTypes();
				res.addChild(node0);

				return res;
			}
			default: {
				throw new Exception("Unexpected token in state types");
			}
		}
	}

	private Node_s build_s() throws Exception {
		Node_s res = new Node_s();
		switch (lexer.getCurrToken()) {
			case $: {
				return res;
			}
			case DECL: {
				if (KotlinArrayTokens.DECL != lexer.getCurrToken()) {
					throw new Exception("Expected DECL token, but get " + lexer.getCurrToken());
				}
				res.addChild(new Node("DECL"));
				lexer.toNextToken();

				if (KotlinArrayTokens.NAME != lexer.getCurrToken()) {
					throw new Exception("Expected NAME token, but get " + lexer.getCurrToken());
				}
				res.addChild(new Node("NAME"));
				lexer.toNextToken();

				if (KotlinArrayTokens.COLON != lexer.getCurrToken()) {
					throw new Exception("Expected COLON token, but get " + lexer.getCurrToken());
				}
				res.addChild(new Node("COLON"));
				lexer.toNextToken();

				Node_arrayAndLB node0 = build_arrayAndLB();
				res.addChild(node0);

				Node_inside node1 = build_inside();
				res.addChild(node1);

				if (KotlinArrayTokens.SEMICOLON != lexer.getCurrToken()) {
					throw new Exception("Expected SEMICOLON token, but get " + lexer.getCurrToken());
				}
				res.addChild(new Node("SEMICOLON"));
				lexer.toNextToken();

				return res;
			}
			default: {
				throw new Exception("Unexpected token in state s");
			}
		}
	}

	private Node_otherTypes build_otherTypes() throws Exception {
		Node_otherTypes res = new Node_otherTypes();
		switch (lexer.getCurrToken()) {
			case RB:
			case $: {
				return res;
			}
			case COMMA: {
				if (KotlinArrayTokens.COMMA != lexer.getCurrToken()) {
					throw new Exception("Expected COMMA token, but get " + lexer.getCurrToken());
				}
				res.addChild(new Node("COMMA"));
				lexer.toNextToken();

				Node_types node0 = build_types();
				res.addChild(node0);

				return res;
			}
			default: {
				throw new Exception("Unexpected token in state otherTypes");
			}
		}
	}

	private Node_arrayAndLB build_arrayAndLB() throws Exception {
		Node_arrayAndLB res = new Node_arrayAndLB();
		switch (lexer.getCurrToken()) {
			case ARR: {
				if (KotlinArrayTokens.ARR != lexer.getCurrToken()) {
					throw new Exception("Expected ARR token, but get " + lexer.getCurrToken());
				}
				res.addChild(new Node("ARR"));
				lexer.toNextToken();

				if (KotlinArrayTokens.LB != lexer.getCurrToken()) {
					throw new Exception("Expected LB token, but get " + lexer.getCurrToken());
				}
				res.addChild(new Node("LB"));
				lexer.toNextToken();

				return res;
			}
			default: {
				throw new Exception("Unexpected token in state arrayAndLB");
			}
		}
	}

	private Node_inside build_inside() throws Exception {
		Node_inside res = new Node_inside();
		switch (lexer.getCurrToken()) {
			case ARR: {
				Node_arrayAndLB node0 = build_arrayAndLB();
				res.addChild(node0);

				Node_inside node1 = build_inside();
				res.addChild(node1);

				if (KotlinArrayTokens.RB != lexer.getCurrToken()) {
					throw new Exception("Expected RB token, but get " + lexer.getCurrToken());
				}
				res.addChild(new Node("RB"));
				lexer.toNextToken();

				return res;
			}
			case NAME: {
				Node_types node0 = build_types();
				res.addChild(node0);

				if (KotlinArrayTokens.RB != lexer.getCurrToken()) {
					throw new Exception("Expected RB token, but get " + lexer.getCurrToken());
				}
				res.addChild(new Node("RB"));
				lexer.toNextToken();

				return res;
			}
			default: {
				throw new Exception("Unexpected token in state inside");
			}
		}
	}

}