package de.longcity.interpreter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class Main {
	static {
		LANG_NAME = "\"CAlpha\"";
		LANG_VERSION = "301017";
		LANG_INTERN_VERSION = "17";
	}
	public static final String LANG_NAME, LANG_VERSION, LANG_INTERN_VERSION;
	public static final boolean __DEBUG = false;
	private Main() {}
	/**
	 * @author stuer
	 * mode = run(0), compile(1)
	 */
	private static class Flags {
		static int mode;
		static String input, output;
		static List<File> linkfiles;
	}
	public static void main(String... args) {
		try {
			interprete(args);
		} catch (ArrayIndexOutOfBoundsException e) {
			printHelp();
			return;
		}
		if(Flags.input == null) {
			printHelp();
			return;
		}
		if(Flags.mode == 0) {
			byte b = Interpreter.interpreter.interprete(new File(Flags.input), true);
			if(b == 1) {
				System.out.println("File "+Flags.input+" not found!");
			}else if(b == 2) {
				System.out.println("An error occured while running!");
			}
		} else if(Flags.mode == 1) {
			byte b = Interpreter.interpreter.interprete(new File(Flags.input), false);
			if(b == 1) {
				System.out.println("File "+Flags.input+" not found!");
			}else if(b == 2) {
				System.out.println("An error occured while interpreting!");
			}
		} else if(Flags.mode == 2) {
			if(Flags.output == null) Flags.output = "a.bin";
			byte b = Compiler.compile(new File(Flags.input), new File(Flags.output));
			if(b == 1) {
				System.out.println("File "+Flags.input+" not found!");
			}else if(b == 2) {
				System.out.println("An error occured while compiling!");
			}
		} else if(Flags.mode == 3) {
			if(Flags.output == null) Flags.output = "a.pp";
			byte b = Compiler.preprocess(new File(Flags.input), new File(Flags.output));
			if(b == 1) {
				System.out.println("File "+Flags.input+" not found!");
			}else if(b == 2) {
				System.out.println("An error occured while preprocessing!");
			}
		} else if(Flags.mode == 4) {
			if(Flags.output == null) Flags.output = Flags.input;
			byte b = Compiler.link(new File(Flags.input), Flags.linkfiles.toArray(new File[Flags.linkfiles.size()]));
			if(b == 1) {
				System.out.println("File "+Flags.input+" not found!");
			}else if(b == 2) {
				System.out.println("An error occured while linking!");
			}
		}else {
			printHelp();
		}
	}
	private static void interprete(String[] args) throws ArrayIndexOutOfBoundsException {
		Flags.mode = 0;
		Flags.input = null;
		Flags.output = null;
		for(int i = 0; i < args.length; i++) {
			switch (args[i]) {
			case "-i":
				Flags.mode = 1;
				break;
			case "-c":
				Flags.mode = 2;
				break;
			case "-p":
				Flags.mode = 3;
				break;
			case "-link":
				Flags.linkfiles = new ArrayList<>();
				Flags.mode = 4;
				i++;
				for(;i < args.length; i++) {
					Flags.linkfiles.add(new File(args[i]));
				}
				return;
			case "-o":
				Flags.output = args[i+1];
				i++;
				break;
			default:
				Flags.input = args[i];
				break;
			}
		}
	}
	private static void printHelp() {
		System.out.println("Run: " + LANG_NAME + " <file>");
		System.out.println("Interprete: " + LANG_NAME + " -i <file>");
		System.out.println("Compile: " + LANG_NAME + " -c [-o <output>] <file>");
		System.out.println("Preprocess: " + LANG_NAME + " -p [-o <output>] <file>");
		System.out.println("Link: " + LANG_NAME + "[-o <output>] <file> -link <file1> <file2>...");
	}
}
