package indiana.cgl.hadoop.pagerank.helper;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.*;


import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer.Context;
import org.apache.hadoop.mapreduce.Reducer;


public class SortResultsReduce extends Reducer<LongWritable, Text, LongWritable, Text>{	
public void reduce(LongWritable key, Iterable<Text> values,
		Context context) throws IOException, InterruptedException {
	
	Map<Double, String> tree = new TreeMap(Collections.reverseOrder());

	for(Text v: values){
		String loop = v.toString();
		String[] s = loop.split("=");
		System.out.println(Double.parseDouble(s[0]));
		System.out.println(s[1]);
		tree.put(Double.parseDouble(s[0]), s[1]);
	}
	
	key = null;
	for(Map.Entry t: tree.entrySet()){
		context.write(key, new Text(t.getValue().toString()));
	}
	
	}
}
