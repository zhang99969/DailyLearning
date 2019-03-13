import java.util.*;

public class CandidateElimination3 {
	
	public static int gnum=0;
	static ArrayList<String[]> gg=  new ArrayList<String[]>();
	//static String[] g0={"?","?","?","?","?" };
	static String[] newg0()
	{
		String[] g0={"?","?","?","?","?" };
		return g0;	
	}
	
	static void print(String[] h,String name)
	{
		System.out.print(name+"\n");
		for(int k=0;k<5;k++)
		{System.out.print(h[k]+" ");}
		//System.out.print("\n");
	}
	
	static boolean allwen(String[] h)//all are "?"
	{
		for(int k=0;k<5;k++)
		{if(h[k]!="?") return false;}
		return true;
	}
	static boolean alreadyhave(String[] h)
	{
		int truenum=0;
		for(int i=0;i<gg.size();i++)
		{
			for(int j=0;j<5;j++)
			if(gg.get(i)[j].equals(h[j]))
				truenum++;
				if(truenum==5) return true;
				else truenum=0;
		}
		return false;
	}

	
	public   static   ArrayList  removeDuplicate(ArrayList list)  {       
		  for  ( int  i  =   0 ; i  <  list.size()  -   1 ; i ++ )  {       
		      for  ( int  j  =  list.size()  -   1 ; j  >  i; j -- )  {       
		           if  (list.get(j).equals(list.get(i)))  {       
		              list.remove(j);       
		            }        
		        }        
		      }        
		    return list;       
		}  
	

	static boolean moreS(String[] h,String[] d)
	{// h spefic than d, h sunny, d,sunny/?
		int num=h.length;
		int samenum=0;
		for(int i=0;i<num;i++)
		{
		if(h[i]!=d[i] && d[i]!="?")	
			return false;
		if(h[i]==d[i])
			samenum++;
		}
		if(samenum==num)
			return false;
		return true;
	}
	
	static boolean moreG(String[] h,String[] d)
	{
		int num=h.length;
		int samenum=0;
		for(int i=0;i<num;i++)
		{
		if(h[i]!=d[i] && h[i]!="?")	
			return false;
		if(h[i]==d[i])
			samenum++;
		}
		if(samenum==num)
			return false;
		return true;
	}
	
	static boolean same(String[] h,String[] d)
	{
		int num=h.length;
		int samenum=0;
		for(int i=0;i<num;i++)
		{
		if(h[i]==d[i])
			samenum++;
		}
		if(samenum==num)
			return true;
		else return false;
	}
	/*
	static String[][] resort(String[][] gg)
	{
		int flag=0;
		int delnum=0;
	//	int firstnull=-1,firstnotnull=-1;
		//System.out.print("length "+gg[0].length);
		for(int i=0;i<gg.length-1-delnum;i++)
		{
			for(int j=0;j<gg[i].length;j++)
			if(gg[i][j]!="?")
			{	
				flag=1;
			}			
			if(flag==0)//该行为空
			{
				for(int ii=i;ii<gg.length-2-delnum;ii++)
				{
				gg[ii]=gg[ii+1];
				}
				i--;
				delnum++;
			}
			flag=0;
		}
		return gg;
	}*/
	
	static void printtheg()
	{
		System.out.print("\ng\n");
		for (int i = 0; i < gg.size(); i++) 
		{
			for(int j=0;j<5;j++)
			{
				System.out.print(gg.get(i)[j]+" ");
			}
			System.out.print("\n");
		}
		
	}
	static void CE(String[] s,String[] g)
	{
		String[][] EnjoySport={{"Japan","Honda","Blue","1980","Economy","Positive"},
		{"Japan","Toyota","Green","1970","Sports","Negative"},
		{"Japan","Toyota","Blue","1990","Economy","Positive"},
		{"USA","Chrysler","Red","1980","Economy","Negative"},
		{"Japan","Honda","White","1980","Economy","Positive"},
		{"Japan","Toyota","Green","1980","Economy","Positive"},
		{"Japan","Honda","Red","1990","Economy","Negative"} };
		int rownum=EnjoySport.length; //行数	
	//	String[][] gg=new String[6][5];	
		gg.add( newg0() );
		//printtheg();
	/*	for(int i=0;i<5;i++)
			for(int j=0;j<5;j++)
				gg[i][j]="?";*/
	//	int[] gchange={0,0,0,0,0,0};

		for(int i=0;i<rownum;i++)
		{
			System.out.print("\nnum="+i);
			if(EnjoySport[i][5]=="Positive")
			{
				System.out.print(" Pos ");
				for(int ggg=0;ggg<5;ggg++) System.out.print(EnjoySport[i][ggg]+" ");
			
				for(int ii=0;ii<gg.size();ii++)
				{
					if(moreG(gg.get(ii),EnjoySport[i]) )
					{//keep
					}
					else
					{
					//	System.out.print("i="+i);
						gg.remove(ii);	gnum--; ii=0;
					}
				}
			/*	for(int ii=0;ii<5;ii++)
				{	for(int j=0;j<5;j++)
					{
						System.out.print("\n+++"+gg[ii][j]+" "+EnjoySport[i][j]);
						if(gg[ii][j]!=EnjoySport[i][j]&&gg[ii][j]!="?")
						{
							System.out.print(" on ");
							gg[ii][j]="?";
						}
						
					}
				}
				System.out.print("+++\n");*/
				for(int j=0;j<5;j++)
				{			
					if(s[j]==EnjoySport[i][j] || s[j]=="?" )
					{//do nothing
					}
					else if(s[j]=="#") 
					{
						s[j]=EnjoySport[i][j];	
					}
					else 
					{
						s[j]="?";
					}
				}
			}
			else if(EnjoySport[i][5]=="Negative")
			{
				System.out.print(" Neg ");		
				for(int ggg=0;ggg<5;ggg++) System.out.print(EnjoySport[i][ggg]+" ");
				
				//for (int ii=0;ii<gg.size()&&ii<10;ii++) {
				for(int j=0;j<5;j++)
				{	
					if(EnjoySport[i][j]!=s[j] )//&& gg.get(i)[j]=="?") 
					{
						for(int ii=0;ii<gg.size();ii++)
						{
							//System.out.print("+");
						//	System.out.print(gg.size());
						//System.out.print("【");printtheg();System.out.print("】");
						//System.out.print(" in1 ");
						String[] gnow=gg.get(ii).clone();//取gnow
						//System.out.print(gnum);
						// for(int ggg=0;ggg<5;ggg++) System.out.print(gnow[ggg]+" ");
						// for(int ggg=0;ggg<5;ggg++) System.out.print(EnjoySport[i][ggg]+" ");
						 //System.out.print("]"); 
						 if(moreG(gnow,EnjoySport[i]))//gnow更一般
						{
							//System.out.print(" in2 ");
							if(gnow[j]=="?")	//
							{			
								if(allwen(gnow)) //gnow全是"?"
								{
								}				
								gnow[j]=s[j]; //gnow赋值 
								//for(int ggg=0;ggg<5;ggg++) System.out.print(gnow[ggg]+"+");
								if(!alreadyhave(gnow))
								{ 
								gg.set(ii,gnow); //gnow添加回gg
								//System.out.print(" in3 ");
								gg.add(  newg0()  );//gg增加全"?"行
								gnum++;
								}							
								//把gnow添加到gg中							
								j++;				 	
							// 	System.out.print(" in4 \n");
						 	}
						}
						 if(j>=5) break;
					//	 printtheg();
						 if(ii==gg.size()-1&&  gg.get(ii)==newg0() )
							 break;}
					}
				//		System.out.print("【【【");printtheg();System.out.print("】】】");
						// for(int ggg=0;ggg<5;ggg++) System.out.print(gnow[ggg]+" ");System.out.print("]");
				}
			}
		 /*
		 		int gnumber=0;
				for(int j=0;j<5;j++)
				{
					for(int ii=0;ii<gg.length;ii++)
					{System.out.print("\n---"+EnjoySport[i][j]+" "+s[j]+" "+gg[ii][j]+" "+gnumber);
					if(EnjoySport[i][j]!=s[j] && gg[ii][j]=="?")
						if(s[j]!="?")
						{
							System.out.print(" in ");
							//g[j]=s[j]+"|";
							gg[gnumber][j]=s[j];
							gnumber++;
						//	gchange[gnumber]=1;
						}
					}
				}				
				System.out.print("---\n");*/		
			System.out.print("\n");
			print(s,"s");
			printtheg();		
		}			
		/*for(int ii=0;ii<5;ii++)
		{for(int jj=0;jj<5;jj++)
			{System.out.print(gg[ii][jj]+" ");}
		System.out.print("\n");
		}*/	
	}
	public static void main(String[] args){	
		String[] S={ "#","#","#","#","#" };	
		String[] G={ "?","?","?","?","?" };	
		CE(S,G);
	}
}