7. Design, develop and implement a C/C++/Java program to simulate the working of
Shortest remaining time and Round Robin (RR) scheduling algorithms. Experiment
with different quantum sizes for RR algorithm.
SJF Part in this file

import java.util.Arrays;
import java.util.Scanner;

class Process implements Comparable<Process> {
    public int processId;
    public int burstTime;
    public int waitingTime;
    public int turnAroundTime;

    Process(int processId, int burstTime) {
        this.processId = processId;
        this.burstTime = burstTime;
    }

    @Override
    public int compareTo(Process p) {
        return Integer.compare(this.burstTime, p.burstTime);
    }
}

class SJF {

    private static Process[] processes;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of Processes");
        int n = scanner.nextInt();
        processes = new Process[n];
        System.out.println("Enter details");

        for (int i = 0; i < n; i++) {
            System.out.println("Enter burst time for Process " + (i + 1));
            int burstTime = scanner.nextInt();
            processes[i] = new Process(i + 1, burstTime);
        }

        Arrays.sort(processes);

        int accumulateWaitTime = 0;
        for (int i = 0; i < n; i++) {
            processes[i].waitingTime = accumulateWaitTime;
            processes[i].turnAroundTime = processes[i].burstTime + processes[i].waitingTime;
            accumulateWaitTime += processes[i].burstTime;
        }

        System.out.println("Process Scheduling");
        for (int i = 0; i < n; i++) {
            System.out.print(String.format("Id: %s, Burst Time: %s,Waiting Time: %s, Turn Around Time: %s\n",
                    processes[i].processId, processes[i].burstTime, processes[i].waitingTime, processes[i].turnAroundTime
            ));
        }

        float averageWaitTime = 0f, averageTurnAroundTime = 0f;
        for (int i = 0; i < n; i++) {
            averageWaitTime += processes[i].waitingTime;
            averageTurnAroundTime += processes[i].turnAroundTime;
        }

        averageWaitTime /= n;
        averageTurnAroundTime /= n;

        System.out.println("Average Wait Time: " + averageWaitTime + ", Average Turn Around Time: " + averageTurnAroundTime);
    }
}


Output:
Enter number of Processes
4
Enter details
Enter burst time for Process 1
6
Enter burst time for Process 2
8
Enter burst time for Process 3
7
Enter burst time for Process 4
3
Process Scheduling
Id: 4, Burst Time: 3, Waiting Time: 0, Turn Around Time: 3
Id: 1, Burst Time: 6, Waiting Time: 3, Turn Around Time: 9
Id: 3, Burst Time: 7, Waiting Time: 9, Turn Around Time: 16
Id: 2, Burst Time: 8, Waiting Time: 16, Turn Around Time: 24
Average Wait Time: 7.0, Average Turn Around Time: 13.0


Round Robin Part in this file

import java.util.LinkedList;
import java.util.Scanner;

class Process {
    public int processId;
    public int burstTime;
    public int waitingTime;
    public int turnAroundTime;
    public int remainingTime;
    public int lastAccessTime;

    Process(int processId, int burstTime) {
        this.processId = processId;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
}

class RoundRobin {

    private static LinkedList<Process> processQueue;
    private static Process[] processes;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of Processes");
        int n = scanner.nextInt();
        processes = new Process[n];
        processQueue = new LinkedList<>();

        System.out.println("Enter details");
        for (int i = 0; i < n; i++) {
            System.out.println("Enter burst time for Process " + (i + 1));
            int burstTime = scanner.nextInt();
            processes[i] = new Process(i + 1, burstTime);

            processQueue.add(processes[i]);
        }

        System.out.println("Enter Quantum Size");
        int quantumSize = scanner.nextInt();

        int currentTime = 0;
        System.out.println("Process Scheduling");
        while (!processQueue.isEmpty()) {
            Process p = processQueue.pollFirst();
            p.waitingTime += currentTime - p.lastAccessTime;
            System.out.println("Current Time: " + currentTime + ", Id: " + p.processId);

            if (p.remainingTime > quantumSize) {
                p.remainingTime -= quantumSize;
                currentTime += quantumSize;
                p.lastAccessTime = currentTime;

                processQueue.offerLast(p);
            } else {
                currentTime += p.remainingTime;
                p.remainingTime = 0;
            }
        }
        System.out.println("Current Time: " + currentTime);

        float averageWaitTime = 0f, averageTurnAroundTime = 0f;
        for (int i = 0; i < n; i++) {
            processes[i].turnAroundTime = processes[i].burstTime + processes[i].waitingTime;

            averageWaitTime += processes[i].waitingTime;
            averageTurnAroundTime += processes[i].turnAroundTime;
        }

        averageWaitTime /= n;
        averageTurnAroundTime /= n;

        System.out.println("Average Wait Time: " + averageWaitTime + ", Average Turn Around Time: " + averageTurnAroundTime);
    }
}

Output:
Enter number of Processes
3
Enter details
Enter burst time for Process 1
24
Enter burst time for Process 2
3
Enter burst time for Process 3
3
Enter Quantum Size
4
Process Scheduling
Current Time: 0, Id: 1
Current Time: 4, Id: 2
Current Time: 7, Id: 3
Current Time: 10, Id: 1
Current Time: 14, Id: 1
Current Time: 18, Id: 1
Current Time: 22, Id: 1
Current Time: 26, Id: 1
Current Time: 30
Average Wait Time: 5.6666665, Average Turn Around Time: 15.666667
-------------------------------------------------------------------

8. Design, develop and implement a C/C++/Java program to implement Banker’s
algorithm. Assume suitable input required to demonstrate the results.
Note: All the inputs are based on index 1 (i.e. Process number starts from 1)

import java.util.Scanner;
public class Bankers {

	public static int Allocation[][];
	public static int Max[][];
	public static int Need[][];
	public static int[] Available,Request;

	public static void main(String[] args) {

		Scanner sc=new Scanner(System.in);
		System.out.println("Enter number of process");
		int nProcess=sc.nextInt();

		System.out.println("Enter number of Resources");
		int nResource=sc.nextInt();

		Allocation=new int[nProcess][nResource];
		Max=new int[nProcess][nResource];
		Available = new int[nResource];
		Need=new int[nProcess][nResource];
		Request = new int[nResource];

		System.out.println("Enter Allocation matrix");
		for(int i=0;i<nProcess;i++)
			for(int j=0;j<nResource;j++)
				Allocation[i][j]=sc.nextInt();

		System.out.println("Enter Max data");
		for(int i=0;i<nProcess;i++)
			for(int j=0;j<nResource;j++){
				Max[i][j]=sc.nextInt();
				Need[i][j]=Max[i][j]-Allocation[i][j];
			}

		System.out.println("Need is");
		for(int i=0;i<nProcess;i++)
		{
			for(int j=0;j<nResource;j++)
				System.out.print(Need[i][j]+" ");
			System.out.println();
		}

		System.out.println("Enter available");
		for(int i=0;i<nResource;i++)
			Available[i]=sc.nextInt();
		System.out.println("Is there any resource request? (Reply 1 for yes or 0 for no)");
		int response = sc.nextInt();

		if(response == 1){
			System.out.println("Enter the process requesting the resource");
			int process_request=sc.nextInt();

			System.out.println("Enter the resource");
			for(int i=0;i<nResource;i++)
				Request[i]=sc.nextInt();

			if((Request[0]<=Need[process_request][0]) && (Request[1]<=Need[process_request][1]) && (Request[2]<=Need[process_request][2]))
			{
				if((Request[0]<=Available[1]) && (Request[1]<=Available[1]) && (Request[1]<=Available[2]))
				{

					for(int i=0;i<nResource;i++){
						Available[i] -= Request[i];
						Allocation[process_request][i] +=Request[i];
						Need[process_request][i]-=Request[i];
					}
				}
			}
		}

		int flag[]=new int[nProcess];
		for(int k=0;k<nResource;k++)
		{
			for(int i=0;i<nProcess;i++)
			{
				if((flag[i]!=1 && Need[i][0]<=Available[0]) && (Need[i][1]<=Available[1]) && (Need[i][2]<=Available[2]))
				{
					flag[i]=1;
					System.out.println("Process P["+i+"] execution is successful");

					for(int j=0;j<nResource;j++)
						Available[j] +=  Allocation[i][j];
				}
				else
					continue;
			}
		}
		
		System.out.println("The New Available Resource is:-"+Available[0]+" "+Available[1]+" "+Available[2]);
	}
}

--------------------------------------------------------------
9. Design, develop and implement a C/C++/Java program to implement page
replacement algorithms LRU and FIFO. Assume suitable input required to
demonstrate the results.

import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static int pageRequests[],frame[],frameSize,noOfPageRequests;
    static int hit=0,marker=0;
    
    public static void main(String []args) {
    	
        System.out.println("Enter the Frame Size\n");
        frameSize = sc.nextInt();
       
        System.out.println("Enter no. of page Requests\n");
        noOfPageRequests = sc.nextInt();
       
        frame = new int[frameSize];
        pageRequests = new int[noOfPageRequests];
       
        System.out.println("Enter the requests \n");
        for(int i=0;i<noOfPageRequests;i++)
            pageRequests[i] = sc.nextInt();
	    
        System.out.println("Select Page Replacement Algorithm\n");
        System.out.println("1)FIFO 2)LRU\n");
        switch(sc.nextInt()) {
        case 1:runFIFO();
            break;
        case 2:runLRU();
            break;
        }
        
        System.out.println("Page Fault = "+(noOfPageRequests-hit));
	System.out.println("Page Hit = "+hit);
    }

    static void runFIFO() {
        for(int i=0;i<noOfPageRequests;i++) {
            displayFrame(frame);
            if(i<frameSize)
                frame[i] = pageRequests[i];
            else {
                boolean check_hit = checkForHit(frame,pageRequests[i]);
                if(check_hit)
                    hit++;
                else {
                    frame[marker] = pageRequests[i];
                    marker = (marker+1)%frameSize;
                }
            }
        }
     }
    
    static void runLRU() {
        for(int i=0;i<noOfPageRequests;i++) {
        	displayFrame(frame);
            if(i<frameSize)
            	pushToStack(pageRequests[i]);
            else {
                boolean check_hit = checkForHit(frame,pageRequests[i]);
                if(check_hit)
                    hit++;
                pushToStack(pageRequests[i]);
            }
        }
    }

	static void displayFrame(int[] frame) {
        String stack = "[";
        for(int i=0;i<frame.length;i++) {
        	stack += " "+frame[i];
        }
        stack += " ]";
        System.out.println(stack);
    }

    static boolean checkForHit(int []frame,int num) {
        for(int i=0;i<frame.length;i++)
            if(frame[i]==num)return true;
        return false;
    }

    static void pushToStack(int num) {
    	int []tempArray= new int[frameSize];
    	tempArray[0] = num;
    	
    	for(int i=1,j=0;i<frameSize && j<frameSize;j++){
    		if(frame[j]!=num){
    			tempArray[i] = frame[j];
    			i++;
    		}
    	}
       frame = tempArray;
    }
}
