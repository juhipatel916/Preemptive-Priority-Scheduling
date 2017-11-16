import java.io.*;
import java.lang.ProcessBuilder.Redirect;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class PPH {
	public static void main(String arg[])
	{
       int noProcesses;
       int preemtive;
       int quantum=0;
       try
       {
          FileReader f=new FileReader("input.data");
    	  Scanner s = new Scanner(f);
          noProcesses = s.nextInt();
          preemtive = s.nextInt();
          if(preemtive == 1)
        	  quantum=s.nextInt();
          else
        	  s.nextLine();
          
          PCB pcb[]=new PCB[noProcesses];
          for(int i=0;i<pcb.length;i++)
          {
        	  int at=s.nextInt();
        	  int bt=s.nextInt();
        	  int pr=s.nextInt();
        	  pcb[i]=new PCB(at, bt, pr);
          }
          LinkedList<Integer> queue=prepareQueue(pcb);
          startPph(pcb,queue,quantum);       
       }
       catch(Exception e){
    	   System.out.print(e);
       }
}

	private static LinkedList<Integer> prepareQueue(PCB[] pcb) {
		LinkedList<Integer> readyQ=new LinkedList<Integer>();
		for(int i=0;i<pcb.length;i++)
		{
			if(readyQ.isEmpty())
				readyQ.add(i);
			else
			{
				int addAt=-1;
				int j=0;
				while(j<readyQ.size())
			 	{
					if(pcb[i].getArivalTime()>pcb[readyQ.get(j)].getArivalTime())
					{
					addAt = 0;
					j++;
					}
					else break;
				}
				if(addAt==-1)
				{
					readyQ.addFirst(i);
				}
				else if(j==readyQ.size())
				{
					readyQ.addLast(i);
				}
				else
				{
					readyQ.add(j,i);
				}
				
			}
		}
		return readyQ;
	}

	private static void startPph(PCB[] pcb, LinkedList<Integer> queue, int quantum) {
		int time=0;
		int runingP=0;
		boolean first = true;
		int st=0;
		String output;
		try{
			FileWriter f=new FileWriter("output.data");
			BufferedWriter b=new BufferedWriter(f);
			while(!queue.isEmpty())
			{
			
				LinkedList<Integer> availableP=new LinkedList<Integer>();
				for(int i=0;i<queue.size();i++)
				{
					if(pcb[queue.get(i)].getArivalTime()<=time)
					{
						if(availableP.isEmpty())
							availableP.add(queue.get(i));
						else
						{
							int addAt=-1;
							int j=0;
							while(j<availableP.size())
							{
								if(pcb[queue.get(i)].priority<pcb[availableP.get(j)].priority)
								{
									addAt = 0;
									j++;
								}
								else break;
							}
							if(addAt==-1)
								availableP.addFirst(queue.get(i));
							else if(j==queue.size())
								availableP.addLast(queue.get(i));
							else		
								availableP.add(j,queue.get(i));
						}
					}
				}
			
				if(availableP.isEmpty())
				{
					if(first)
					{
						runingP=-1;
						first=false;
					}
					if(runingP!=-1)
					{
						output=st+" "+time+" "+(runingP+1);
						b.append(output);
						b.newLine();
						st=time;
					}
					runingP=-1;
					time++;
				}
				else
				{
					int runP=availableP.remove();	
					if(first)
					{
						runingP=runP;
						first=false;
					}
					if(runingP!=runP)
					{
						if(runingP==-1)
						{}	
						else
						{
							output=st+" "+time+" "+(runingP+1);
							b.append(output);
							b.newLine();
						}
						st=time;
					}
					runingP=runP;
					int temp=pcb[runP].getBurstTime();
					if(temp>=quantum)
					{
						temp-=quantum;
						pcb[runP].setBurstTime(temp);
						if(temp<1)
							queue.removeFirstOccurrence(runP);
						time+=quantum;
					}
					else
					{
						int it = temp;
						temp=0;
						pcb[runP].setBurstTime(temp);
						if(temp<1)
							queue.removeFirstOccurrence(runP);	
						time+=it;
					}
					if(queue.isEmpty())
					{
						output=st+" "+time+" "+(runingP+1);
						b.append(output);
						b.newLine();
					}
				}
			}
			b.close();
		}
		catch(Exception e){}
		}
}