import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 *  This program performs the task of calculating the running median for
 *  the number of words per line of texts.
 */
public class MedMain {
	
	public static void main(String[] args) throws IOException {
		String input = "wc_input";  // input directory
		String output = "wc_output/med_result.txt"; // output directory
        
        File inFolder = new File(input);
		BufferedWriter rmWrt = new BufferedWriter(new FileWriter(output, false));
		Scanner scanner = null;
		RunningMedian rm = new RunningMedian();
        
        // If multiple texts present, iterate them in alphabetical order.
        File[] files = inFolder.listFiles();
        Arrays.sort(files);
		for (File file : files) {
			try {
				scanner = new Scanner(file);
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					String[] words = line.split("\\W");
					rm.addNumber(words.length);
					rmWrt.write(Double.toString(rm.getMedian()));
					rmWrt.newLine();
				}
			} catch (FileNotFoundException e) {
				System.err.println("Cannot find the file");
			} finally {
				if (scanner != null)
					scanner.close();
			}
		}
        
		rmWrt.close();
	}
    
}
