// -------------------------------------------------------------------
// - Foundations of Aspect-Oriented Programming for J2EE Development -
// - Renaud Pawlak, Lionel Seinturier, Jean-Philippe Retaillé        -
// - APress                                                          -
// -                                                                 -
// - Chapter 8: Design Patterns and AOP                              -
// -------------------------------------------------------------------

package aop.patterns.command;

import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * Command for saving data in a file.
 * 
 * @author Renaud Pawlak
 * @author Jean-Philippe Retaille
 * @author Lionel Seinturier
 */
public class FileSaver implements Command {
	
	private String fileName;
	
	public FileSaver(String fileName) {
			this.fileName = fileName;
	}

	public void execute(Object receiver) {
		Stats stats = (Stats)receiver;
		
		try {
			FileOutputStream output = new FileOutputStream(fileName);
			PrintWriter writer = new PrintWriter(output);
			writer.println("STATISTICS");
			writer.println("Total number of orders: "+stats.getOrders());
			writer.println("Total amount: "+stats.getTotalAmount());
			writer.println("Statut: "+stats.getStatus());
			writer.flush();
			writer.close();
		}
		catch (Exception e) {
			System.err.println(e);
		}
	}
}
