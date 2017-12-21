import java.io.IOException;
import java.util.*; 
import org.apache.hadoop.fs.Path; 
import org.apache.hadoop.io.*; 
//import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.util.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class MapReduce
{ 
	public static final int userId=17; 
	public static class Map extends Mapper<LongWritable, Text, Text, IntWritable>
	{ 
		private Text word = new Text();
		public void map(LongWritable key, Text value,Context context) throws IOException,InterruptedException
		{
			StringTokenizer tokenizer = new StringTokenizer(value.toString(), ","); 
			if( Integer.parseInt(tokenizer.nextToken()) == userId )
			{
				 word.set(tokenizer.nextToken());
				 int hitCount = Integer.parseInt(tokenizer.nextToken()); 
				 context.write(word,new IntWritable(hitCount));
			}
		}
	}//class map ends

	public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable>
	{
		public void reduce(Text key, Iterable<IntWritable> values,Context context) throws IOException,InterruptedException
		{ 
			int hitcount =0;
			//while(values.hasNext()){ 
			//	output.collect(key, values.next());
			//	}
			for (IntWritable value : values) 
			{
			/*
			 * Add the value to the word count counter for this key.
			 */
		   		hitcount += value.get();
		 	}
	  /*
	   * Call the write method on the Context object to emit a key
	   * and a value from the reduce method. 
	   */
	  		context.write(key, new IntWritable(hitcount));
		}
	}//reduce ends

public static void main(String []args) throws Exception
{
	System.out.println("\n-------------WELCOME TO MAPREDUCE JOB -------");
	System.out.println("\nMapReduce Job Starting...\n"); 
	Configuration conf = new Configuration();
	Job job = Job.getInstance(conf, "MapReduce");
	job.setJarByClass(MapReduce.class);
	//JobConf conf = new JobConf(MapReduce.class); 
	job.setJobName("MapReduce Job"); 
	job.setMapperClass(Map.class);
	job.setCombinerClass(Reduce.class);
	job.setReducerClass(Reduce.class);
	job.setOutputKeyClass(Text.class); 
	job.setOutputValueClass(IntWritable.class); 
	//job.setInputFormat(TextInputFormat.class); 
	//job.setOutputFormat(TextOutputFormat.class);
	FileInputFormat.addInputPath(job, new Path(args[0]));
	FileOutputFormat.setOutputPath(job, new Path(args[1]));
	System.exit(job.waitForCompletion(true) ? 0 : 1);
	//JobClient.runJob(conf);
}//main ends
}
