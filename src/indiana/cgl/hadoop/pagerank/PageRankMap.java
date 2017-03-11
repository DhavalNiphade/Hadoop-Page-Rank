package indiana.cgl.hadoop.pagerank;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;
 
public class PageRankMap extends Mapper<LongWritable, Text, LongWritable, Text> {

// each map task handles one line within an adjacency matrix file
// key: file offset
// value: <sourceUrl PageRank#targetUrls>
	public void map(LongWritable key, Text value, Context context)
		throws IOException, InterruptedException {
			int numUrls = context.getConfiguration().getInt("numUrls",1);
			String line = value.toString();
			
			//System.out.println("Line : " +line);
						
			StringBuffer sb = new StringBuffer();
			// instance an object that records the information for one webpage
			RankRecord rrd = new RankRecord(line);
			int sourceUrl, targetUrl;
			// double rankValueOfSrcUrl;
			//System.out.println("Rank Record Target URL Size : "+rrd.targetUrlsList.size());
			
			//System.out.println("Key : " + key + "\t Target URL : " + rrd.targetUrlsList);
			//System.out.println("Source URL: " + rrd.sourceUrl + "\t Target URL : " + rrd.targetUrlsList);
			
			if (rrd.targetUrlsList.size()<=0){
				// there is no out degree for this webpage; 
				// scatter its rank value to all other urls
				double rankValuePerUrl = rrd.rankValue/(double)numUrls;
				for (int i=0;i<numUrls;i++){
					context.write(new LongWritable(i), new Text(String.valueOf(rankValuePerUrl)));
				}
			} else { //Generalizing the whole setup
				/*Write your code here*/	
				
				double rankValueUrl = rrd.rankValue/(double)rrd.targetUrlsList.size(); //Set to the same number
				for(int i=0 ; i<rrd.targetUrlsList.size() ; i++){
					context.write(new LongWritable(rrd.targetUrlsList.get(i)) , new Text(String.valueOf(rankValueUrl)));
					sb.append("#"+rrd.targetUrlsList.get(i));
					
					//System.out.println("Target URL : " + rrd.targetUrlsList.get(i)+ "Page Rank : " + rankValueUrl);
				}
				//System.out.print("SB : " + sb);
				//System.out.print("\n");
			} //for
			
			//System.out.println("Source : " +rrd.sourceUrl+ "SB : " + sb.toString());
			context.write(new LongWritable(rrd.sourceUrl), new Text(sb.toString()));
		} // end map

}
