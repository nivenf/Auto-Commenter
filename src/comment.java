import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*
 * 							----------------------
 * 							|	 comment.java    |
 * 							----------------------
 * 
 * 		- Inserts a header comment and method block comments in your program -
 * 
 * 
 * 
 * 						*********** USAGE ***********
 * 
 * 	Modify base.txt by placing corresponding answers after the colon with no space
 * 
 * 			Place file that needs comments in file_commented.txt
 * 
 * 		Make sure file has no method block comments or header comment
 * 			
 * 		Add descriptions to header comment and method block comments
 * 
 * 						*****************************
 * 
 * 
 * 			Notes: I shoved everything in the main method. Cause why not.
 * 			TODO: 	-Add glossary of methods in header comment
 * 					-Autofill getter methods
 * 					-Possibly saving/loading settings for a more customized experience
 * 					-Tidy code to make it more streamlined and less cluttered
 * 					-Finds constructor and changes block comment
 * 
 * 								Niven Francis
 * 
 */

public class comment {
	public static void main(String[] args) {
		
		
		try {
			
			// Opens Files
			
			BufferedReader br = new BufferedReader(new FileReader("file_to_comment.txt"));		// File to be modified
			BufferedWriter bw = new BufferedWriter(new FileWriter("file_commented.txt"));		// Finished file
			BufferedReader bb = new BufferedReader(new FileReader("base.txt"));					// Basis for header comment
			
			String line;
			
			
			// Writes header comment to file. Modify to however you want your header file to be
			
			bw.write("/*" + "\n");
			line = bb.readLine();
			bw.write(" * Name: " + line.substring(line.indexOf(":") + 1) + "\n");				// Name
			line = bb.readLine();
			bw.write(" * Date: " + line.substring(line.indexOf(":") + 1) + "\n");				// Date
			line = bb.readLine();
			bw.write(" * Class: " + line.substring(line.indexOf(":") + 1) + "\n");				// Class
			line = bb.readLine();
			bw.write(" * Professor: " + line.substring(line.indexOf(":") + 1) + "\n");			// Professor
			line = bb.readLine();
			bw.write(" * Program Name: " + line.substring(line.indexOf(":") + 1));				// Program Name
			line = br.readLine();
			
			if(line == null || line.length() == 0) {											// Checks if empty file
				if((line = br.readLine()) == null) {
					System.out.println("Empty or misconstructed file. Make sure class line is either the first or second line. Stopping program.");
					System.exit(1);
				}
			}
			
			String[] l = line.split(" ");
			bw.write("\n *\n * ---" + l[2] + ".java---	\n");									// File Name, grabs from 'public class' line
			bw.write(" * Description: " + "\n");												// Description for you to fill out afterwards
		/*	bw.write(" *\n");
			bw.write(" * ---Methods---" + "\n");
			bw.write(getMethods());
		*/	bw.write("*/" + "\n\n");
			
			
			// Goes through code to see where to add method block comments
			
			while(line != null) {
				String tempLine = null;
				boolean flag = false;
				
				if(line.contains("@Override")) {												// If it's an @Override method, store that line 
					tempLine = line;															// for later so it doesn't get misplaced
					line = br.readLine();
					flag = true;
				}
				
				
				// Finds method. Has to contain:
				//		public OR private	open parenthesis	close parenthesis	open brace
				
				if((line.contains("public") || line.contains("private")) && line.contains("(") && line.contains(")") && line.contains("{")) {
					String[] parts = line.split(" ");
					
					String method_name = line.substring(0, line.indexOf('('));					// Grabs method name
					for(int x = method_name.length() - 1; x >= 0; x--) {
						if(method_name.charAt(x) == ' ') {
							method_name = method_name.substring(x+1, method_name.length());
							break;
						}
					}
					
					String parameters = "none";													// Grabs parameters
					if(line.indexOf("(") != (line.indexOf(")") - 1)) {
						int temp = line.indexOf(")");
						parameters = line.substring(line.indexOf("(")+1, temp);
					}
					
					
					String returns = "none";													// Grabs return value
					if(parts[1].equals("static") && !parts[1].contains("("))
						returns = parts[2];
					else if(!parts[1].contains("("))
						returns = parts[1];
					
					
					// Writes method block comment to file before method
					
					bw.write("\n\t" + "/* Method Name: " + method_name + "()" + "\n");
					bw.write("\t" + " * " + "\n");
					bw.write("\t" + " * Parameters: " + parameters + "\n");
					bw.write("\t" + " * " + "\n");
					bw.write("\t" + " * Returns: " + returns + "\n");
					bw.write("\t" + " * " + "\n");
					bw.write("\t" + " * Description: " + "\n");
					bw.write("\t" + "*/ " + "\n");
				}
				
				
				// For @Override
				if(flag) {
					flag = false;
					bw.write(tempLine + "\n");
				}
				
				bw.write(line + "\n");
				line = br.readLine();
			}
			
			// CLoses files
			br.close();
			bw.close();
			bb.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Finished, not implemented (TODO: Asking & saving user settings) see lines 81-84
	private static String getMethods() {
		String result = "";
		try {
			BufferedReader br = new BufferedReader(new FileReader("file_to_comment.txt"));
			String line;
			while((line = br.readLine()) != null) {
				if((line.contains("public") || line.contains("private")) && line.contains("(") && line.contains(")") && line.contains("{")) {
					String method_name = line.substring(0, line.indexOf('('));					// Grabs method name
					for(int x = method_name.length() - 1; x >= 0; x--) {
						if(method_name.charAt(x) == ' ') {
							method_name = method_name.substring(x+1, method_name.length());
							break;
						}
					}
					
					String parameters = "";														// Grabs parameters
					if(line.indexOf("(") != (line.indexOf(")") - 1)) {
						int temp = line.indexOf(")");
						parameters = line.substring(line.indexOf("(")+1, temp);
					}
					
					result += " *\t" + method_name + "(" + parameters + ")\n";
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}