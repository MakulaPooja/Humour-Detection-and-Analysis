import java.io.*;
import java.util.*;
public class feature_vector {
	
	public static HashMap<String,String> bigram_dict=new HashMap<String,String>();
	public static String[] cls_tag=new String[10002];

	public static void features()
	{
		int x=1;
		Global.file_append("/home/kanv/Humor_project/vector_Mix10000Bigram.arff", "@relation Features");
		try
        {
            FileInputStream fis = new FileInputStream("/home/kanv/Humor_project/bigram_mix16000_top10.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
            String line;
            //bigrams
            bigram_dict.clear();
            while((line = br.readLine())!=null)
            {
            	line=line.substring(0,line.indexOf(":")).trim();
            	//System.out.println(line);
            	bigram_dict.put(line, " ");
            }
			int s1=bigram_dict.size();
            while(x<=s1)
            {
            	Global.file_append("/home/kanv/Humor_project/vector_Mix10000Bigram.arff", "@ATTRIBUTE word"+(x)+"  NUMERIC");
            	x++;
            }
            x--;
            //for pos/neg
            //Global.file_append("/home/kanv/workspace/tweets_req/JNU/Tweets/vector_jnu4970BigramSentiNRC", "@ATTRIBUTE word"+(x)+"  NUMERIC");
            fis.close();
            System.out.println(bigram_dict.size()+" "+x);
        
        }catch(IOException f){}    
	}
	
	public static void main(String[] args)
	{
		
		features();
		Global.file_append("/home/kanv/Humor_project/vector_Mix10000Bigram.arff", "@ATTRIBUTE class {y,n}");
		Global.file_append("/home/kanv/Humor_project/vector_Mix10000Bigram.arff", "@Data");
		
		//Map<String, String> treeMap = new TreeMap<String, String>(dict);
		
		//File folder = new File("/home/kanv/Sem5/IR/IR_project/Hindi_Tweets");
		//File[] listOfFiles = folder.listFiles();
		
		//for (File file : listOfFiles) 
		//{
		  //  if (file.isFile()) 
		   // {
		        //System.out.println(file.getName());
		    	String row="";
		        try
		        {
		        	//reading annotated class tags from file....
		        	String line;
		            FileInputStream fis2 = new FileInputStream("/home/kanv/Humor_project/newfile.txt");
		            BufferedReader br2 = new BufferedReader(new InputStreamReader(fis2,"UTF-8"));
		            int k=1;
		            while((line = br2.readLine())!=null)
		            {
		            	String tag=line.trim().toLowerCase();
		            	//int tg=-1;
		            	if(tag.equals("1"))
		            		cls_tag[k]="y";
		            		//tg=0;
		            	else if(tag.equals("0"))
		            		cls_tag[k]="n";
		            		//tg=1;
		            	/*else if(tag.equals("pr+"))
		            		cls_tag[k]="pr+";
		            		//tg=2;
		            	else if(tag.equals("pr-"))
		            		cls_tag[k]="pr-";
		            		//tg=3;
		            	else if(tag.equals("uu"))
		            		cls_tag[k]="uu";*/
		            	k++;
		            		//tg=4;
		            }
		        
		            
		            br2.close();
		            fis2.close();
		            //creating vector
		            //System.out.println(cls_tag.length);
		            //for(int dd = 0 ; dd < cls_tag.length ; dd ++)
		            //	System.out.println(cls_tag[dd]);
		            FileInputStream fis3 = new FileInputStream("/home/kanv/Humor_project/MIX16000.txt");
		            BufferedReader br3 = new BufferedReader(new InputStreamReader(fis3,"UTF-8"));
		            int cnt=1;
		            while((line = br3.readLine())!=null)
		            {
		            	row="";//for each tweet
		            	//String splitter[];
		            	//splitter=line.split("\t");
		           
		            	//System.out.println(splitter[1]);
		            	String tweet=Twokenize.tokenized(line).trim();
		            	String words[]=tweet.split(" ");
		            	//int c=0;
		            	//System.out.println(dict.size());
		            	for (Map.Entry<String, String> entry : bigram_dict.entrySet())
						{
						    //System.out.println(entry.getKey()+" : "+entry.getValue());
		            		
						    String x=entry.getKey();
						    //System.out.println(x);
						    if(tweet.contains(x))//if bigram present in tweet
						    {
						    	row+="1,";
						    }
						    else
						    {
						    	row+="0,";
						    }
						    //c++;
						    
						}
		            	String cls=cls_tag[cnt];
		            	//String cls = "y";
		            	System.out.println(cls_tag[cnt]);
		            	Global.file_append("/home/kanv/Humor_project/vector_Mix10000Bigram.arff", row+cls);
		            	if(cnt==10000)break;
		            	cnt++;
		            	//System.out.println(row);
		            }
		            fis3.close();
		            br3.close();
		            
		        }
		        catch(IOException f){} 
		  	
		   // }
		//}//end of for
	}//end of main()
	
	/*public static TreeMap<String, Integer> SortByValue (HashMap<String, Integer> map) 
	{
		ValueComparator vc =  new ValueComparator(map);
		TreeMap<String,Integer> sortedMap = new TreeMap<String,Integer>(vc);
		sortedMap.putAll(map);
		return sortedMap;
	}*/
	
	
}

/*class ValueComparator implements Comparator<String> 
{
	 
    Map<String, Double> map;
 
    public ValueComparator(Map<String, Double> base) {
        this.map = base;
    }
 
    public int compare(String a, String b) {
        if (map.get(a) >= map.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys 
    }
}*/