package indiana.cgl.hadoop.pagerank.helper;

import indiana.cgl.hadoop.pagerank.RankRecord;
import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;
import java.util.*;


public class SortResultsMap extends Mapper<LongWritable, Text, LongWritable, Text>{
		
	public void map(LongWritable key, Text value, Context context)
	throws IOException, InterruptedException {
		
		//String strLine = value.toString();
		//RankRecord rrd = new RankRecord(strLine);
		
		long temp = (long)key.get();
		
		String temp1, temp2;
		temp1 = String.valueOf(value);
		temp2 = String.valueOf(temp);
		
		temp2 += "="+temp1;
		
		//System.out.println("Key : " + temp + "#" + " Value : " +temp2);
		
		context.write(new LongWritable(key.get()), new Text(temp2));
		
		}
	
}


