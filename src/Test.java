import de.longcity.interpreter.Main;

public class Test {
	public static void main(String[] args) {
		Main.main("-p", "-o", "app.pp", "app.ca");
		Main.main("-i", "app.pp");
	}
}
