package com.hackathon.mapreduce;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class AnalyzeData {

//This Class is used for reading the Input data from the file and generate key value pairs
	public static class MyMapper extends Mapper<LongWritable, Text, Text, LongWritable>{
		Text kword = new Text();
		LongWritable vword = new LongWritable();
		public void map(LongWritable key, Text value, Context context) throws InterruptedException, IOException
		{
			String line = value.toString();
			String[] parts = line.split("\\n");
			long element = Long.valueOf(parts[0]);
			kword.set("ResultSet");
			vword.set(element);
			context.write(kword, vword);
		}
	}
	
	//This Class is used for reading key value pairs and perform the business computations.
	public static class MyReducer extends Reducer<Text, LongWritable, Text, Text>
	{
		Text vword = new Text();
		public void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException
		{
		
			long count = 0; 
			long min = Long.MAX_VALUE;
			long max = Long.MIN_VALUE;
			long sum = 0;
			long sumSquared = 0;
			double mean=0d;
			double variance =0d;
			double stDeviation =0d;
			double firstQuarterPercentile =0d;
            double secondQuarterPercentile =0d;
            double thirdQuarterPercentile =0d;
            List<Integer> cloneValues = new ArrayList<Integer>();
            long value;
            while (values.iterator().hasNext()) {
                ++count;
                value = values.iterator().next().get();
                min = Math.min(min, value);
                max = Math.max(max, value);
                sum += value;
                sumSquared += value * value;               
                cloneValues.add((int) value);
              }
          
			
            mean = (double) sum/count;
            
            variance = (sumSquared - (sum * sum) / count) / (count - 1);
            
            stDeviation = Math.sqrt(variance);
            Collections.sort(cloneValues);
            firstQuarterPercentile= findPercentile(cloneValues,0.25);
            secondQuarterPercentile=findPercentile(cloneValues,0.50);
            thirdQuarterPercentile=findPercentile(cloneValues,0.75);
            
            String listElements ="\nSorted Input Elements are:\t";
            
            for (Integer n:cloneValues){
            	listElements += n+"\t";
            }
            
            vword.set(listElements+"\nCounter:\t" + count+ "\nMinVal:\t" + min+ "\nMaxVal:\t" + max+"\nsum:\t" + sum + "\nMean:\t" + mean+ "\nStandardDeviation:\t" + stDeviation+ "\n25Percentile:\t"+firstQuarterPercentile + "\n50Percentile:\t"+secondQuarterPercentile + "\n75Percentile:\t"+thirdQuarterPercentile );
    			
			context.write(key, vword);
		}

		//This is the method for calculating percentile from a given list of integers
		private double findPercentile(List<Integer> data, double percentile) {
		
			double index = percentile*(data.size()+1);
		    int lower = (int)Math.floor(index);
		   
		    if(lower<0) { 
		       return data.get(0);
		    }
		    if(lower>data.size()-1) { 
		       return data.get(data.size()-1);
		    }
		    double fraction = index-lower;
		    // linear interpolation
		    double result=data.get(lower-1) + fraction*(data.get(lower)-data.get(lower-1));
		    
		    return result;
			
		
		}
	}

	//This is the method for defining the MapReduce Driver i.e Main Method to invoke Mapper and Reducer class.
	public static void main(String args[]) throws IOException, InterruptedException, ClassNotFoundException
	{
		Configuration conf = new Configuration();
		
		Job job = new Job(conf, "Statistical Analysis of large dataset");
		job.setJarByClass(AnalyzeData.class);		
		
		//To set Mapper and Reduce classes
		job.setMapperClass(MyMapper.class);
		job.setReducerClass(MyReducer.class);
		
		//Output Key-Value data types Type
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(LongWritable.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);		
		
		job.setNumReduceTasks(2);
		
		//To inform input output Formats to MapReduce Program 
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);		
		
		//Inform input and output File or Directory locations
		FileInputFormat.addInputPath(job, new Path("/user/biadmin/hackathon/input.txt"));
		FileOutputFormat.setOutputPath(job, new Path("/user/biadmin/hackathon/Output"));		
		
		//Inform the job termination criteria
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}

