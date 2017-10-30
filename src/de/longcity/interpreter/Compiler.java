package de.longcity.interpreter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public final class Compiler {
	private static Map<String, String> defines = new HashMap<>();
	private static List<String> includes = new ArrayList<>();
	private static int pp;
	private Compiler() {}
	public static byte compile(File in, File out) {
		try {
			return compile(new FileInputStream(in), new FileOutputStream(out));
		} catch (FileNotFoundException e) {
			return 1;
		}
	}
	public static byte compile(InputStream in, OutputStream out) {
		try {
			Scanner scanner = preprocess(in);
			OutputStreamWriter writer = new OutputStreamWriter(out);
			String write = "";
			String line;
			
			while(scanner.hasNextLine()) {
				line = scanner.nextLine();
				if(line.isEmpty() || line.startsWith("//")) continue;
				write += toBinary(line) + "\n";
			}
			if(!write.isEmpty()) {
				writer.write(write + "\n");
			}
			
			scanner.close();
			writer.flush();
			writer.close();
			return 0;
		} catch (Exception e) {
			return 2;
		}
	}
	public static byte preprocess(File in, File out) {
		try {
			defines.clear();
			includes.clear();
			defines.put("LANG_NAME", Main.LANG_NAME);
			defines.put("LANG_VERSION", Main.LANG_VERSION);
			defines.put("LANG_INTERN_VERSION", Main.LANG_INTERN_VERSION);
			defines.put("__CALPHA__", "\"CAlpha" + Main.LANG_INTERN_VERSION + "\"");
			if(Main.__DEBUG) defines.put("__DEBUG", "");
			Scanner scanner = preprocess(new FileInputStream(in));
			FileWriter fw = new FileWriter(out);
			
			while(scanner.hasNextLine())fw.write(scanner.nextLine()+"\n");
			
			scanner.close();
			fw.close();
			if(pp != 0) {
				System.err.println("Please close last #if !");
			}
			return 0;
		} catch (FileNotFoundException e) {
			return 1;
		} catch (Exception e) {
			return 2;
		}
	}
	public static byte link(File file, File... others) {
		try {
			for(File other : others) {
				link(file, other);
			}
			return 0;
		} catch (FileNotFoundException e) {
			return 1;
		} catch (Exception e) {
			return 2;
		}
	}
	// PRIVATE ACCESS
	private static void link(File file, File other) throws FileNotFoundException, Exception {
		Scanner scanner = new Scanner(other);
		FileWriter fw = new FileWriter(file, true);
		while(scanner.hasNextLine()) {
			fw.append(scanner.nextLine()+"\n");
		}
		scanner.close();
		fw.close();
	}
	private static Scanner preprocess(InputStream in) throws Exception{
		pp = 0;
		Scanner scanner = new Scanner(in);
		String preprocessedCode = "";
		
		String line;
		while(scanner.hasNextLine()) {
			line = preprocess0(scanner.nextLine());
			if(line == null) {
				scanner.close();
				throw new Exception();
			}
			preprocessedCode += line;
		}
		
		scanner.close();
		return new Scanner(preprocessedCode);
	}
	private static String preprocess0(String line) throws Exception{
		if(line.isEmpty() || line.startsWith("//")) return "";
		if(line.startsWith("#include")) {
			String path;
			boolean mode;
			if(line.contains("<") && line.contains(">")) {
				mode = true;
				path = line.substring(line.indexOf('<')+1, line.indexOf('>'));
			} else {
				if(!line.contains("\""))return null;
				mode = false;
				path = line.substring(line.indexOf('"')+1, line.lastIndexOf('"'));
			}
			return include(path, mode);
		}else if(line.startsWith("#define ")) {
			String sub = line.substring("#define ".length());
			if(sub.indexOf(' ') == -1) {
				defines.put(sub, "");
				return "";
			}
			String name = sub.substring(0, sub.indexOf(' ')).trim();
			if(Character.isDigit(name.charAt(0)))return "";
			String value = sub.substring(name.length()+1).trim();
			defines.put(name, value);
			return "";
		}else if(line.startsWith("#undef")) {
			String name = line.substring(line.indexOf(' ')+1).trim();
			defines.remove(name);
			return "";
		}else if(line.startsWith("#ifdef")) {
			String name = line.substring(line.indexOf(' ')+1).trim();
			if(defines.containsKey(name)) pp = 1;
			else pp = 2;
			return "";
		}else if(line.startsWith("#ifndef")) {
			String name = line.substring(line.indexOf(' ')+1).trim();
			if(defines.containsKey(name)) pp = 2;
			else pp = 1;
			return "";
		}else if(line.startsWith("#endif")) {
			if(pp == 0) {
				System.err.println("No #if-preprocessor-instruction found!");
			}else pp = 0;
			return "";
		}else if(line.startsWith("#else")) {
			if(pp == 1) pp = 2;
			else if(pp == 2) pp = 1;
			else {
				System.err.println("No #if-preprocessor-instruction found!");
			}
			return "";
		}else if(line.startsWith("#error")) {
			if(pp == 2) return "";
			System.err.println(line.substring(line.indexOf(' ')+1).trim());
			return null;
		}
		
		for(Entry<String, String> e : defines.entrySet()) {
			line=line.replace(e.getKey(), e.getValue());
		}
		if(pp == 0 || pp == 1)
			return line + "\n";
		else return "";
	}
	private static String include(String path, boolean mode) throws Exception{
		if(mode) {
			if(includes.contains("__std_"+path))return "";
			if(Main.__DEBUG) {
				if(new File("src").exists()) {
					Scanner scanner = preprocess(new FileInputStream(new File("src/include/"+path)));
					String ret = "";
					while(scanner.hasNextLine())ret += scanner.nextLine() + "\n";
					scanner.close();
					includes.add("__std_"+path);
					return ret + "\n";
				}
			}
			Scanner scanner = preprocess(Compiler.class.getClassLoader().getResourceAsStream("include/"+path));
			String ret = "";
			while(scanner.hasNextLine())ret += scanner.nextLine() + "\n";
			scanner.close();
			includes.add("__std_"+path);
			return ret + "\n";
		} else {
			if(includes.contains(path))return "";
			Scanner scanner = preprocess(new FileInputStream(new File(path)));
			String ret = "";
			while(scanner.hasNextLine())ret += scanner.nextLine() + "\n";
			scanner.close();
			includes.add(path);
			return ret + "\n";
		}
	}
	private static String toBinary(String text) {
		return new BigInteger(text.getBytes()).toString(2);
	}
}
