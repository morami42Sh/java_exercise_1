import java.util.Scanner;

public interface Command {
	public String name();
	
	public boolean run(Scanner sc);
}

