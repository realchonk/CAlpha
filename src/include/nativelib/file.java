package include.nativelib;

import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.Flushable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import de.longcity.interpreter.IncludeLib;

public class file extends IncludeLib {
	private Map<File, Closeable> openFiles;
	@Override
	protected void init() {
		super.init();
		openFiles = new HashMap<>();
	}
	@Override
	protected String getNameSpace() {
		return "";
	}
	@NativeFunction(name = "fopen", paramtypes = {"String", "int"})
	public File fopen(String path, int mode) {
		File file = new File(path);
		Closeable obj = fopen0(file, mode);
		if(obj == nullptr) {
			System.err.println("Invalid mode! "+mode);
			return new File(nullptr.toString());
		}
		return file;
	}
	@NativeFunction(name = "fclose", paramtypes = "File")
	public void fclose(File file) {
		Closeable closeable = openFiles.get(file);
		try {
			closeable.close();
		} catch (IOException e) {
			System.err.println("Can't close "+(file).getName());
		}
	}
	@NativeFunction(name = "fcloseall")
	public int fcloseall() {
		try {
			int ret = 0;
			for(Entry<File, Closeable> e : openFiles.entrySet()) {
				e.getValue().close();
				openFiles.remove(e.getKey());
				ret++;
			}
			return ret;
		} catch (IOException e) {
			System.err.println("Can't close files!");
		}
		return 0;
	}
	@NativeFunction(name = "fflush", paramtypes = "File")
	public void fflush(File file) {
		Closeable c = openFiles.get(file);
		if(c instanceof Flushable) {
			try {
				((Flushable)c).flush();
			} catch (IOException e) {
				System.err.println("An error occured by flushing "+file.getName());
			}
		}
	}
	@NativeFunction(name = "fflushall")
	public int fflushall() {
		int ret = 0;
		for(Entry<File, Closeable> e : openFiles.entrySet()) {
			if(e.getValue() instanceof Flushable) {
				try {
					((Flushable)e.getValue()).flush();
					ret++;
				} catch (IOException ex) {
					System.err.println("Failed to flush "+e.getKey().getName()+"!");
				}
			}
		}
		return ret;
	}
	@NativeFunction(name = "fdelete", paramtypes = "File")
	public boolean fdelete(File file) {
		try {
			((Closeable)openFiles.remove(file)).close();
			return file.delete();
		} catch (Exception e) {
			System.err.println("Can't delete and close "+file.getName());
			return false;
		}
	}
	
	@NativeFunction(name = "fread", paramtypes = "File")
	public String fread(File file) {
		Closeable c = openFiles.get(file);
		if(c instanceof Scanner) {
			return ((Scanner)c).nextLine();
		}
		System.err.println("Invalid mode for reading!");
		return nullptr.toString();
	}
	@NativeFunction(name = "fwrite", paramtypes = {"File", "String"})
	public void fwrite(File file, String line) {
		Closeable c = openFiles.get(file);
		if(c instanceof FileWriter) {
			try {
				((FileWriter)c).write(line+"\n");
			} catch (IOException e) {
				System.err.println("An error occured while writing \""+line+"\" to "+file.getName());
			}
			return;
		}
		System.err.println("Invalid mode for writing!");
	}
	@NativeFunction(name = "fappend", paramtypes = {"File", "String"})
	public void fappend(File file, String line) {
		Closeable c = openFiles.get(file);
		if(c instanceof FileWriter) {
			try {
				((FileWriter)c).write(line+"\n");
			} catch (IOException e) {
				System.err.println("An error occured while writing \""+line+"\" to "+file.getName());
			}
			return;
		}
		System.err.println("Invalid mode for writing!");
	}
	@NativeFunction(name = "fputc", paramtypes = {"File", "char"})
	public void fputc(File file, char ch) {
		Closeable c = openFiles.get(file);
		if(c instanceof FileWriter) {
			try {
				((FileWriter)c).write(ch);
			} catch (IOException e) {
				System.err.println("An error occured while writing '"+ch+"' to "+file.getName());
			}
			return;
		}
		System.err.println("Invalid mode for writing!");
	}
	@NativeFunction(name = "fputi", paramtypes = {"File", "int"})
	public void fputi(File file, int i) {
		Closeable c = openFiles.get(file);
		if(c instanceof FileWriter) {
			try {
				((FileWriter)c).write(i);
			} catch (IOException e) {
				System.err.println("An error occured while writing "+i+" to "+file.getName());
			}
			return;
		}
		System.err.println("Invalid mode for writing!");
	}
	
	private Closeable fopen0(File file, int mode) {
		Closeable ret = nullptr;
		try {
			switch (mode) {
			case 0:
				ret = new Scanner(file);
				break;
			case 1:
				ret = new FileWriter(file);
				break;
			case 2:
				ret = new FileWriter(file, true);
				break;
			}
		} catch (Exception e) {
			System.err.println("An error occured!");
			return nullptr;
		}
		openFiles.put(file, ret);
		return ret;
	}
}
