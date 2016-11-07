import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class Class_Distribution 
{
	public static void main(String[] args) throws IOException
	{
		File folder = new File("/home/kanv/Humor_project/annotated-jokes");
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) 
		{
			if (file.isFile())
			{
				try
		        {
		            FileInputStream fis = new FileInputStream("/home/kanv/Humor_project/annotated-jokes/"+file.getName());
		            BufferedReader br = new BufferedReader(new InputStreamReader(fis,"UTF-8"));
		            String line;
		            //int num_tweets=1;
		            double t_data = 0,cls1 = 0, cls2 = 0, cls3 = 0;
		            double cls1_tp1 = 0,cls1_tp2 = 0,cls1_tp3 = 0,cls1_tp4 = 0;
		            double cls2_tp1 = 0,cls2_tp2 = 0,cls2_tp3 = 0,cls2_tp4 = 0;
		            double cls3_tp1 = 0,cls3_tp2 = 0,cls3_tp3 = 0,cls3_tp4 = 0;
		            while((line = br.readLine())!=null)
		            {
		            	//line=Twokenize.tokenized(line).trim();
		            	//System.out.println(line);
		            	if(line.contains("<class>") && line.contains("</class>") && line.contains("<type>")
		            			&& line.contains("</type>"))
		            	{
			            	String cls[] = line.split("<class>");
			            	String pr[] = cls[1].split("</class>");
			            	
			            	String tp[] = line.split("<type>");
			            	String tr[] = tp[1].split("</type>");
			            	if(pr[0].isEmpty() || tr[0].isEmpty())continue;
			            	else 
			            	{
			            		t_data+=1;
			            		if ( pr[0].equals("1") )
			            		{	
			            			cls1++;
			            			if(tr[0].equals("1")) cls1_tp1++;
			            			if(tr[0].equals("2")) cls1_tp2++;
			            			if(tr[0].equals("3")) cls1_tp3++;
			            			if(tr[0].equals("4")) cls1_tp4++;
			       
			            		}
			            		else if ( pr[0].equals("2") )
			            		{
			            			cls2++;
			            			if(tr[0].equals("1")) cls2_tp1++;
			            			if(tr[0].equals("2")) cls2_tp2++;
			            			if(tr[0].equals("3")) cls2_tp3++;
			            			if(tr[0].equals("4")) cls2_tp4++;
			            		}
			            		else if ( pr[0].equals("3") )
			            		{
			            			cls3++;
			            			if(tr[0].equals("1")) cls3_tp1++;
			            			if(tr[0].equals("2")) cls3_tp2++;
			            			if(tr[0].equals("3")) cls3_tp3++;
			            			if(tr[0].equals("4")) cls3_tp4++;
			            		}
			            		System.out.println(pr[0]);
			            	}
		            
		            	}
		            }
		            double dist_cls1 = (cls1/t_data)*100;
		            double dist_cls2 = (cls2/t_data)*100;
		            double dist_cls3 = (cls3/t_data)*100;
		            
		            double dist_cls1_tp1 = (cls1_tp1/cls1)*100;
		            double dist_cls1_tp2 = (cls1_tp2/cls1)*100;
		            double dist_cls1_tp3 = (cls1_tp3/cls1)*100;
		            double dist_cls1_tp4 = (cls1_tp4/cls1)*100;
		            
		            double dist_cls2_tp1 = (cls2_tp1/cls2)*100;
		            double dist_cls2_tp2 = (cls2_tp2/cls2)*100;
		            double dist_cls2_tp3 = (cls2_tp3/cls2)*100;
		            double dist_cls2_tp4 = (cls2_tp4/cls2)*100;
		            
		            double dist_cls3_tp1 = (cls3_tp1/cls3)*100;
		            double dist_cls3_tp2 = (cls3_tp2/cls3)*100;
		            double dist_cls3_tp3 = (cls3_tp3/cls3)*100;
		            double dist_cls3_tp4 = (cls3_tp4/cls3)*100;
		            
		            System.out.println("cls1: " + dist_cls1);
		            System.out.println("\t cls1,tp1: " + dist_cls1_tp1);
		            System.out.println("\t cls1,tp2: " + dist_cls1_tp2);
		            System.out.println("\t cls1,tp3: " + dist_cls1_tp3);
		            System.out.println("\t cls1,tp4: " + dist_cls1_tp4);
		            
		            System.out.println("cls2: " + dist_cls2);
		            System.out.println("\t cls2,tp1: " + dist_cls2_tp1);
		            System.out.println("\t cls2,tp2: " + dist_cls2_tp2);
		            System.out.println("\t cls2,tp3: " + dist_cls2_tp3);
		            System.out.println("\t cls2,tp4: " + dist_cls2_tp4);
		            
		            System.out.println("cls3: " + dist_cls3);
		            System.out.println("\t cls3,tp1: " + dist_cls3_tp1);
		            System.out.println("\t cls3,tp2: " + dist_cls3_tp2);
		            System.out.println("\t cls3,tp3: " + dist_cls3_tp3);
		            System.out.println("\t cls3,tp4: " + dist_cls3_tp4);
		        }
				catch(Exception e){e.printStackTrace();}
			}
		
		}
	}
}