import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

/*
 * 							----------------------
 * 							|	 comment.java    |
 * 							----------------------
 * 
 * 		- Inserts a header comment and method block comments in your program -
 * 
 * 						*****************************
 * 
 * 								Niven Francis
 * 
 */

public class comment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public comment(boolean mg, boolean ag, String name, String date, String clas, String prof, String ta, String prog, String type) {
		try {
			
			// Opens Files
			
			BufferedReader br = new BufferedReader(new FileReader("file_to_comment.txt"));		// File to be modified
			BufferedWriter bw = new BufferedWriter(new FileWriter("file_commented.txt"));		// Finished file
			
			String line;
			
			line = br.readLine();
			
			if(type.equals("JAVA")) {
				while(line.contains("import ") || line.equals("")) {
					if(line.contains(" class "))
						break;
					bw.write(line + "\n");
					line = br.readLine();
				}
			}
			if(type.equals("C")) {
				while(line.contains("#include ") || line.equals("")) {
					if(line.contains(" class "))
						break;
					bw.write(line + "\n");
					line = br.readLine();
				}
			}
			
			// Writes header comment to file. Modify to however you want your header file to be
			
			bw.write("/*" + "\n");
			if(!name.isEmpty())
				bw.write(" * Name: " + name + "\n");				// Name
			if(!date.isEmpty())
				bw.write(" * Date: " + date + "\n");				// Date
			if(!clas.isEmpty())
				bw.write(" * Class: " + clas + "\n");				// Class
			if(!prof.isEmpty())
				bw.write(" * Professor: " + prof + "\n");			// Professor
			if(!ta.isEmpty())
				bw.write(" * TA: " + ta + "\n");
			if(!prog.isEmpty())
				bw.write(" * Program Name: " + prog + "\n");				// Program Name
			if(!name.isEmpty() || !date.isEmpty() || !clas.isEmpty() 
					|| !prof.isEmpty() || !ta.isEmpty() || !prog.isEmpty())
				bw.write(" *\n");
			
			if(line == null || line.length() == 0) {											// Checks if empty file
				if((line = br.readLine()) == null) {
					System.out.println("Empty or misconstructed file. Make sure class line is either the first or second line. Stopping program.");
					System.exit(1);
				}
			}
			
			String[] l = line.split(" ");
			if(type.equals("JAVA"))
				bw.write(" * ---" + l[2] + ".java---	\n");
			if(type.equals("C"))
				bw.write(" * ---" + l[2] + ".c---	\n");// File Name, grabs from 'public class' line
			bw.write(" * Description: " + "\n");												// Description for you to fill out afterwards
			if(mg) {
				bw.write(" *\n");
				if(type.equals("JAVA"))
					bw.write(" * ---Methods---" + "\n");
				if(type.equals("C"))
					bw.write(" * ---Functions---" + "\n");
				bw.write(getMethods());
			}
			bw.write("*/" + "\n\n");
			
			
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String getMethods() {
		String result = "";
		try {
			@SuppressWarnings("resource")
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