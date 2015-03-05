package coding.data.insight;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;

/**
 *	This program performs the task of counting all the words from the 
 *	text files contained in a directory.
 */
public class WCMain {
	
	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new Configuration(), new WordCount(), args);
		System.exit(res);
	}
	
}
