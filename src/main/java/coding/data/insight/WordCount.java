package coding.data.insight;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;

/**
 *  This program performs the word-count task using the Map-Reduce algorithm.
 *  This class contains two inner classes, the {@link Map} and the {@link Reduce},
 *  to deal with the map and reduce jobs respectively.
 */
public class WordCount extends Configured implements Tool {
	
	/**
	 *	The inner map class produces key-value results, in which the key is a word
	 *	in the texts (converted to lower case) and the value is 1.
	 */
	public static class Map extends Mapper<Object, Text, Text, IntWritable> {
		private static final IntWritable ONE = new IntWritable(1);
		private Text word = new Text();

		@Override
		protected void map(Object key, Text value, Context context)
				throws IOException, InterruptedException {

			StringTokenizer tokenizer = new StringTokenizer(value.toString(), " \t\n\r\f\",.:;?!-_*{}[]()'");
			while (tokenizer.hasMoreTokens()) {
				String token = tokenizer.nextToken().toLowerCase().trim();
				word.set(token);
				context.write(word, ONE);
			}
		}
	}
	
	/**
	 * 	The inner reduce class produces the final key-value results, in which the key
	 * 	is a word in the texts and the value is the word's count.
	 */
	public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable> {
		private IntWritable count = new IntWritable();

		@Override
		protected void reduce(Text key, Iterable<IntWritable> values, Context context) 
				throws IOException, InterruptedException {

			int sum = 0;
			for (IntWritable v : values) {
				sum += v.get();
			}
			count.set(sum);
			context.write(key, count);
		}
	}

	/**
	 * 	Run the Map-Reduce program and return 0 if running successfully, 1 otherwise.
	 */
	public int run(String[] args) throws Exception {
        Job job = new Job(getConf());
		job.setJarByClass(WordCount.class);
		job.setJobName("Word-Count");
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);		
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);
        
        // specify the input and output directories
		FileInputFormat.setInputPaths(job, new Path("wc_input"));
		FileOutputFormat.setOutputPath(job, new Path("wc_output"));

		boolean success = job.waitForCompletion(true);
		return success ? 0 : 1; 
	}

}
