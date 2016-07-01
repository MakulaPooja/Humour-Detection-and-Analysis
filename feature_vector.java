import java.io.*;
import java.util.*;
public class feature_vector {
	
	//public static HashMap<String,String> bigram_dict=new HashMap<String,String>();
	public static HashMap<String,String> stopwords=new HashMap<String,String>();
	public static HashMap<String,String> cmu_dict=new HashMap<String,String>();
	public static Map<String,String> antonyms_dict=new HashMap<String,String>();
	public static HashMap<String,String> adult_slang=new HashMap<String,String>();
	public static String[] cls_tag=new String[10002];

	public static void features()
	{
		int x=1;
		Global.file_append("/home/kanv/Humor_project/vector_Mix10000_AllFeatures.arff", "@relation Features");
		try
        {
            FileInputStream fis = new FileInputStream("/home/kanv/Humor_project/english_stopwords");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
            String line;
            //stopwords
            while((line=br.readLine())!=null)
            {
            	stopwords.put(line.trim().toLowerCase(),"");
            	System.out.println("stopwords:"+line.trim());
            }
            fis.close();
            br.close();
            //cmu_dictionary
            FileInputStream fis2 = new FileInputStream("/home/kanv/Humor_project/cmudict");
            BufferedReader br2 = new BufferedReader(new InputStreamReader(fis2,"UTF-8"));
            while((line=br2.readLine())!=null)
            {
            	line = line.trim();
            	String[] pr = line.split(" ");
            	cmu_dict.put(pr[0].toLowerCase(),pr[2]+" "+pr[pr.length - 1]);//word,(start and end pronunciation of english words)
            	System.out.println("cmudict: "+pr[0]+":"+pr[2]+","+pr[pr.length - 1]);
            }
            Global.file_append("/home/kanv/Humor_project/vector_Mix10000_AllFeatures.arff", "@ATTRIBUTE word"+(x)+"  NUMERIC");
            fis2.close();
            br2.close();
            x++;
            
            //antonyms
            FileInputStream fis3 = new FileInputStream("/home/kanv/Humor_project/antonyms.txt");
            BufferedReader br3 = new BufferedReader(new InputStreamReader(fis3,"UTF-8"));
            while((line=br3.readLine())!=null)
            {
            	line = line.trim();
            	String[] ants = line.split(" ");
            	antonyms_dict.put(ants[0].toLowerCase(), ants[1].toLowerCase());//(word,ant.)
            	System.out.println("antonyms: "+ants[0]+","+ants[1]);
            }
            Global.file_append("/home/kanv/Humor_project/vector_Mix10000_AllFeatures.arff", "@ATTRIBUTE word"+(x)+"  NUMERIC");
            fis3.close();
            br3.close();
            x++;
            
            //adult slang
            FileInputStream fis4 = new FileInputStream("/home/kanv/Humor_project/adult.txt");
            BufferedReader br4 = new BufferedReader(new InputStreamReader(fis4,"UTF-8"));
            while((line=br4.readLine())!=null)
            {
            	line = line.trim();
            	adult_slang.put(line.toLowerCase(), ""); 
            	System.out.println("adult slang: "+line);
            }
            Global.file_append("/home/kanv/Humor_project/vector_Mix10000_AllFeatures.arff", "@ATTRIBUTE word"+(x)+"  NUMERIC");
            fis4.close();
            br4.close();
            x++;
            //bigrams
            /*bigram_dict.clear();
            while((line = br.readLine())!=null)
            {
            	line=line.substring(0,line.indexOf(":")).trim();
            	//System.out.println(line);
            	bigram_dict.put(line, " ");
            }
			int s1=bigram_dict.size();
            while(x<=s1)
            {
            	Global.file_append("/home/kanv/Humor_project/vector_Mix10000_AllFeatures.arff", "@ATTRIBUTE word"+(x)+"  NUMERIC");
            	x++;
            }
            x--;*/
            //for pos/neg
            //Global.file_append("/home/kanv/workspace/tweets_req/JNU/Tweets/vector_jnu4970BigramSentiNRC", "@ATTRIBUTE word"+(x)+"  NUMERIC");
            
            //System.out.println(bigram_dict.size()+" "+x);
        
        }catch(IOException f){}    
	}
	
	public static void main(String[] args)
	{
		
		features();
		Global.file_append("/home/kanv/Humor_project/vector_Mix10000_AllFeatures.arff", "@ATTRIBUTE class {y,n}");
	    Global.file_append("/home/kanv/Humor_project/vector_Mix10000_AllFeatures.arff", "@Data");
		
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
		            	k++;
		            		
		            }
		            br2.close();
		            fis2.close();
		            
		            FileInputStream fis3 = new FileInputStream("/home/kanv/Humor_project/MIX16000.txt");
		            BufferedReader br3 = new BufferedReader(new InputStreamReader(fis3,"UTF-8"));
		            int cnt=1;
		            while((line = br3.readLine())!=null)
		            {
		            	row="";//for each tweet
		            	String tweet=Twokenize.tokenized(line).trim();
		            	String words[]=tweet.split(" ");
		            	int alli_flag = 0;
		            	for(String word:words)
		            	{
		            		//check for stop words
		            		int sflag = 0;
		            		for(Map.Entry<String, String> entry: stopwords.entrySet())
		            		{
		            			if(word.equals(entry.getKey()))
		            			{
		            				sflag = 1;
		            				break;
		            			}
		            		}
		            		int pflag1 = 0;
		            		if(sflag == 0)//not a stopword
		            		{
		            			//check for pronunciation
		            			String f_word="",l_word="";
		            			for(Map.Entry<String, String> entry: cmu_dict.entrySet())
		            			{
		            				
		            				if(word.toLowerCase().equals(entry.getKey()))//word exists in cmu_dict
		            				{
		            					String pr = entry.getValue();//pronunciation(f,l)
		            				    f_word = pr.split(" ")[0];
		            				    l_word = pr.split(" ")[1];
		            				    pflag1 = 1;
		            				    break;
		            				}
		            			}
		            			
		            			if(pflag1==1)//word in cmu_dict
		            			{
		            				for(String rem:words)//remaining corpus words 
			            			{
			            				if(!rem.equals(word))
			            				{
			            					String f_rem="",l_rem="";
			            					int pflag2 = 0;
			            					for(Map.Entry<String, String> entry: cmu_dict.entrySet())
			            					{
			            							if(rem.toLowerCase().equals(entry.getKey()))//rem in cmu_dict
			            							{
			            								String pr_rem = entry.getValue();
			            								f_rem = pr_rem.split(" ")[0];
			            								l_rem = pr_rem.split(" ")[1];
			            								pflag2 = 1;
			            								break;
			            							}
			            					}
			            					if(pflag2==1)//rem in cmu_dict
			            					{
			            						if(f_word.equals(f_rem) || l_word.equals(l_rem))//alliteration present
			            						{
			            							alli_flag = 1;
			            							System.out.println("f_sound:"+f_word+" "+f_rem);
			            							System.out.println("l_sound:"+l_word+" "+l_rem);
			            							break;
			            						}
			            					}
			            				}
			            				if(alli_flag == 1)break;
			            			}
		            				if(alli_flag == 1)break;
		            			}//end if pflag = 1       		
		            		}// end if sflag = 0
		            	}//end for
		            	if(alli_flag == 1)
        					row+="1,";//alliteration present
        				else
        					row+="0,";//alliteration not present
		            	
		            	//check for antonymy
		            	int ant_flag = 0;
		            	for(String word:words)
		            	{
		            		String antonym = "";
		            		int ant_word_flag = 0; 
		            		for(Map.Entry<String, String> entry: antonyms_dict.entrySet())
		            		{
		            			if(word.toLowerCase().equals(entry.getKey()))
		            			{
		            				antonym = entry.getValue();
		            				ant_word_flag = 1;
		            				break;
		            			}
		            		}
		            		if(ant_word_flag==1)
		            		{
		            			for(String rem:words)
		            			{
		            				if(rem.toLowerCase().equals(antonym))
		            				{
		            					ant_flag = 1;//antonymy present
		            					break;
		            				}
		            			}
		            		}
		            		if(ant_flag == 1)break;
		            		
		            	}
		            	if(ant_flag == 1)row+="1,";
		            	else row+="0,";
		            	
		            	//ad sl
		            	int ad_sl = 0;
		            	for(Map.Entry<String, String> entry: adult_slang.entrySet())
		            	{
		            		if(tweet.toLowerCase().contains(entry.getKey()))
		            		{
		            			ad_sl = 1;
		            			break;
		            		}
		            	}
		            	if(ad_sl==1)row+="1,";
		            	else row+="0,";
		            	
		            	String cls=cls_tag[cnt];
		            	//String cls = "y";
		            	System.out.println(cls_tag[cnt] + ","+cnt);
		            	Global.file_append("/home/kanv/Humor_project/vector_Mix10000_AllFeatures.arff", row+cls);
		            	if(cnt==10000)break;
		            	cnt++;
		            	//System.out.println(row);
		            }
		            fis3.close();
		            br3.close();
     
		        }
		        catch(IOException f){} 
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