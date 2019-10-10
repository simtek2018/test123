import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;

public class FileUtils {
	
	// Create a file browser to find our question file if not where we 
	// think it is
	public String getQuestionFile() {
		String fileName = "C:\\Java_Workspace\\FileIO\\FileIOTestFile.txt";
		File file = new File(fileName);
		if (file.exists()) {
			return fileName;
		}
		
		JFileChooser chooser = new JFileChooser(".");	
		int result = chooser.showOpenDialog(null);   
		if (result == JFileChooser.APPROVE_OPTION) {            
			fileName = chooser.getSelectedFile().toString(); 
		} else {
			System.exit(0);
		}
		return fileName;
	}

	
	public ArrayList<QnA> readFile(String fileName) {
		FileReader fr;
		
		ArrayList<QnA> lst = new ArrayList<QnA>();
		
		try {
			fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			// The first should be question....
			String line = br.readLine();
			while (line != null) {
				QnA qna = new QnA();			
				qna.setQuestion(line);
				
				// This get the answer
				line = br.readLine();
				qna.setAnswer(line);
				
				// This get the blank
				line = br.readLine();
				
				// This gets the next question
				line = br.readLine();
				lst.add(qna);
			}
		} catch (IOException e) {
			System.out.println("-------------------");
			e.printStackTrace();
		} 	
		return lst;
	}
	

}
