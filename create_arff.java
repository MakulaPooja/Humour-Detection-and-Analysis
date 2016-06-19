import java.io.*;
import java.util.*;
import java.io.File;

public class create_arff
{
	public static HashMap<String,Integer> list=new HashMap<String,Integer>(); //string, freq
	public static void main(String[] args) 
	{
		//File folder = new File("/home/kanv/Sem5/IR/IR_project/Hindi_Tweets");
		//File[] listOfFiles = folder.listFiles();

		//for (File file : listOfFiles)
		//{
		 //   if (file.isFile()) 
		  //  {
		        //System.out.println(file.getName());
		        try
		        {
		            FileInputStream fis = new FileInputStream("/home/kanv/Humor_project/MIX16000.txt");
		            BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
		            String line;
		            //System.out.println(file.getName());
		            //get bigrams from whole corpus....
		            int num_tweets=1;
		            while((line = br.readLine())!=null)
		            {
		            	//line=line.trim();
		            	//String splitter[];
		            	//splitter=line.split("\t");
		            	//line=Twokenize.tokenized(splitter[1]).trim();
		            	line=Twokenize.tokenized(line).trim();
		            	String words[]=line.split(" ");
		            	for(int i=0;i<words.length-1;i++)
		            	{
		            		String bigram= (words[i]+" "+words[i+1]).trim() ;//bigrams
		            		if(list.containsKey(bigram))
		            		{
		            			int k=list.get(bigram);
		            			//System.out.println(bigram);
		            			k=k+1;
		            			list.put(bigram, k);
		            		}
		            		else
		            		{
		            			list.put(bigram, 1);
		            		}
		            	}
		            	//if(num_tweets==4970)break;
		            	num_tweets++;
		            	
		            }
		            fis.close();
		        }catch(IOException f){}    
		    //}
		//}
		
		TreeMap<String, Integer> sortedMap = SortByValue(list);
		double counter=0,top20=sortedMap.size()*(0.1);
		System.out.println(top20);
		for (Map.Entry<String, Integer> entry : sortedMap.entrySet())
		{
		    System.out.println(entry.getKey()+" : "+entry.getValue());
		    Global.file_append("/home/kanv/Humor_project/bigram_mix16000_top10.txt", entry.getKey()+" : "+entry.getValue());
		    counter++;
		    if(counter > top20)break;
		}
	}//end of main()
	
	public static TreeMap<String, Integer> SortByValue (HashMap<String, Integer> map) 
	{
		ValueComparator vc =  new ValueComparator(map);
		TreeMap<String,Integer> sortedMap = new TreeMap<String,Integer>(vc);
		sortedMap.putAll(map);
		return sortedMap;
	}
}

class ValueComparator implements Comparator<String> 
{
	 
    Map<String, Integer> map;
 
    public ValueComparator(Map<String, Integer> base) {
        this.map = base;
    }
 
    public int compare(String a, String b) {
        if (map.get(a) >= map.get(b)) {
        	return -1;
        } else {
            return 1;
        } // returning 0 would merge keys 
    }
}