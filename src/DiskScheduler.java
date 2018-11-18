import java.util.Arrays;
import java.util.Stack;

public class DiskScheduler {
	int[] cylinders = {2055, 1175, 2304, 2700, 513, 1680, 256, 1401, 4922, 3692};
	int startIndex = 2255;
	int lastIndex = 1723;
	int maxIndex = 4999;
	public DiskScheduler() {
		this.FCFS(cylinders);
		this.STF(cylinders);
		this.scan(cylinders);
		this.look(cylinders);
		this.cScan(cylinders);
		this.cLook(cylinders);
	}
	public void FCFS(int[] cylinders) {
		int currentIndex = startIndex;
		System.out.println("~~~~FCFS:~~~~");
		int totalDist = 0;
		int dist = 0;
		for (int i: cylinders) {
			System.out.println("Pending Request: "+currentIndex+"	# Cylinders Movement: "+dist);
			dist = Math.abs(currentIndex-i);
			totalDist = totalDist + dist;
			currentIndex = i;
		}
		System.out.println("Pending Request: "+ currentIndex +"	# Cylinders Movement: "+dist+"\nTotal Distance: "+ totalDist +"\nOrder: "+ Arrays.toString(cylinders)+ "\n");
	}
	public void STF(int[] cylinders) {
		System.out.println("~~~~STF:~~~~");
		int[] distArray = new int[cylinders.length];
		Stack<Integer> order = new Stack<>();
		for(int i = 0; i<cylinders.length;i++) {
			distArray[i] = Math.abs(cylinders[i]-startIndex);
		}
		int currentIndex = startIndex;
		int dist = 0;
		int totalDist = 0;
		boolean running = true;
		while (running) {
			System.out.println("Pending Request: "+currentIndex+"	# Cylinders Movement: "+dist);
			int min = 9999;
			int minIndex = 0;
			for (int i = 0; i<distArray.length; i++) {
				if (distArray[i]<min && distArray[i]>=0) {
					min = distArray[i];
					minIndex = i;
				}
			}
			dist = Math.abs(currentIndex-cylinders[minIndex]);
			currentIndex = cylinders[minIndex];
			totalDist = totalDist+dist;
			order.push(currentIndex);
			int counter = 0;
			for (int i = 0;i<distArray.length;i++) {
				if(distArray[i]>=0) {
					distArray[i] = Math.abs(cylinders[i]-currentIndex);
				}
				else {
					counter++;
				}
			}
			distArray[minIndex]=-1;
			if(counter==9) {
				running = false;
			}
		}
		System.out.print("Total Distance: "+totalDist+"\nOrder: [");
		for (Integer i = 0;i < order.size();i++) {
			if (i!=order.size()-1)
				System.out.print(order.get(i)+", ");
			else
				System.out.println(order.get(i)+"]\n");
		}
	}
	public void scan(int[] cylinders) {
		System.out.println("~~~~Scan~~~~");
		Arrays.sort(cylinders);
		int currentIndex = startIndex;
		int totDist = 0;
		int dist = 0;
		Stack<Integer> lowCylinders = new Stack<>();
		Stack<Integer> highCylinders = new Stack<>();
		System.out.println("Pending Request: "+currentIndex+"	# Cylinders Movement: "+dist);
		for (int i = 0; i<cylinders.length;i++) {
			if (cylinders[i]<currentIndex) {
				lowCylinders.push(cylinders[i]);
			}
			else {
				highCylinders.push(cylinders[i]);
			}
		}
		for (int i: highCylinders) {
			dist = Math.abs(currentIndex-i);
			totDist = totDist+dist;
			currentIndex = i;
			System.out.println("Pending Request: "+currentIndex+"	# Cylinders Movement: "+dist);
		}
		boolean reverse = true;
		for (int i = lowCylinders.size()-1;i>=0;i--) {
			if(reverse) {
				int reverseDist = maxIndex-currentIndex;
				dist = Math.abs(currentIndex-lowCylinders.get(i))+(reverseDist*2);
				totDist = totDist+dist;
				currentIndex = lowCylinders.get(i);
				System.out.println("Pending Request: "+currentIndex+"	# Cylinders Movement: "+dist);
				reverse = false;
				highCylinders.push(currentIndex);
			}
			else {
				dist = Math.abs(currentIndex-lowCylinders.get(i));
				totDist = totDist+dist;
				currentIndex = lowCylinders.get(i);
				System.out.println("Pending Request: "+currentIndex+"	# Cylinders Movement: "+dist);
				highCylinders.push(currentIndex);
			}
			
		}
		System.out.print("Total Distance: "+totDist+"\nOrder: [");
		for (int i = 0; i<highCylinders.size(); i++) {
			if (i!=highCylinders.size()-1)
				System.out.print(highCylinders.get(i)+", ");
			else
				System.out.println(highCylinders.get(i)+"]\n");
		}
	}
	public void look(int[] cylinders) {
		System.out.println("~~~~Look~~~~");
		Arrays.sort(cylinders);
		int currentIndex = startIndex;
		int totDist = 0;
		int dist = 0;
		Stack<Integer> lowCylinders = new Stack<>();
		Stack<Integer> highCylinders = new Stack<>();
		System.out.println("Pending Request: "+currentIndex+"	# Cylinders Movement: "+dist);
		for (int i = 0; i<cylinders.length;i++) {
			if (cylinders[i]<currentIndex) {
				lowCylinders.push(cylinders[i]);
			}
			else {
				highCylinders.push(cylinders[i]);
			}
		}
		for (int i: highCylinders) {
			dist = Math.abs(currentIndex-i);
			totDist = totDist+dist;
			currentIndex = i;
			System.out.println("Pending Request: "+currentIndex+"	# Cylinders Movement: "+dist);
		}
		for (int i = lowCylinders.size()-1;i>=0;i--) {
				dist = Math.abs(currentIndex-lowCylinders.get(i));
				totDist = totDist+dist;
				currentIndex = lowCylinders.get(i);
				System.out.println("Pending Request: "+currentIndex+"	# Cylinders Movement: "+dist);
				highCylinders.push(currentIndex);	
		}
		System.out.print("Total Distance: "+totDist+"\nOrder: [");
		for (int i = 0; i<highCylinders.size(); i++) {
			if (i!=highCylinders.size()-1)
				System.out.print(highCylinders.get(i)+", ");
			else
				System.out.println(highCylinders.get(i)+"]\n");
		}
	}
	public void cScan(int[] cylinders) {
		System.out.println("~~~~C-Scan~~~~");
		Arrays.sort(cylinders);
		int currentIndex = startIndex;
		int totDist = 0;
		int dist = 0;
		Stack<Integer> lowCylinders = new Stack<>();
		Stack<Integer> highCylinders = new Stack<>();
		System.out.println("Pending Request: "+currentIndex+"	# Cylinders Movement: "+dist);
		for (int i = 0; i<cylinders.length;i++) {
			if (cylinders[i]<currentIndex) {
				lowCylinders.push(cylinders[i]);
			}
			else {
				highCylinders.push(cylinders[i]);
			}
		}
		for (int i: highCylinders) {
			dist = Math.abs(currentIndex-i);
			totDist = totDist+dist;
			currentIndex = i;
			System.out.println("Pending Request: "+currentIndex+"	# Cylinders Movement: "+dist);
		}
		boolean reverse = true;
		for (int i = 0; i<lowCylinders.size(); i++) {
			if(reverse) {
				int reverseDist = (maxIndex-currentIndex) + maxIndex;
				dist = lowCylinders.get(i)+reverseDist;
				totDist = totDist+dist;
				currentIndex = lowCylinders.get(i);
				System.out.println("Pending Request: "+currentIndex+"	# Cylinders Movement: "+dist);
				reverse = false;
				highCylinders.push(currentIndex);
			}
			else {
				dist = Math.abs(currentIndex-lowCylinders.get(i));
				totDist = totDist+dist;
				currentIndex = lowCylinders.get(i);
				System.out.println("Pending Request: "+currentIndex+"	# Cylinders Movement: "+dist);
				highCylinders.push(currentIndex);
			}
			
		}
		System.out.print("Total Distance: "+totDist+"\nOrder: [");
		for (int i = 0; i<highCylinders.size(); i++) {
			if (i!=highCylinders.size()-1)
				System.out.print(highCylinders.get(i)+", ");
			else
				System.out.println(highCylinders.get(i)+"]\n");
		}
	}
	public void cLook(int[] cylinders) {
		System.out.println("~~~~C-Look~~~~");
		Arrays.sort(cylinders);
		int currentIndex = startIndex;
		int totDist = 0;
		int dist = 0;
		Stack<Integer> lowCylinders = new Stack<>();
		Stack<Integer> highCylinders = new Stack<>();
		System.out.println("Pending Request: "+currentIndex+"	# Cylinders Movement: "+dist);
		for (int i = 0; i<cylinders.length;i++) {
			if (cylinders[i]<currentIndex) {
				lowCylinders.push(cylinders[i]);
			}
			else {
				highCylinders.push(cylinders[i]);
			}
		}
		for (int i: highCylinders) {
			dist = Math.abs(currentIndex-i);
			totDist = totDist+dist;
			currentIndex = i;
			System.out.println("Pending Request: "+currentIndex+"	# Cylinders Movement: "+dist);
		}
		boolean reverse = true;
		for (int i = 0; i<lowCylinders.size(); i++) {
			if(reverse) {
				dist = currentIndex-lowCylinders.get(i);
				totDist = totDist+dist;
				currentIndex = lowCylinders.get(i);
				System.out.println("Pending Request: "+currentIndex+"	# Cylinders Movement: "+dist);
				reverse = false;
				highCylinders.push(currentIndex);
			}
			else {
				dist = Math.abs(currentIndex-lowCylinders.get(i));
				totDist = totDist+dist;
				currentIndex = lowCylinders.get(i);
				System.out.println("Pending Request: "+currentIndex+"	# Cylinders Movement: "+dist);
				highCylinders.push(currentIndex);
			}
			
		}
		System.out.print("Total Distance: "+totDist+"\nOrder: [");
		for (int i = 0; i<highCylinders.size(); i++) {
			if (i!=highCylinders.size()-1)
				System.out.print(highCylinders.get(i)+", ");
			else
				System.out.println(highCylinders.get(i)+"]\n");
		}
	}
	
	public static void main(String args[]) {
		new DiskScheduler();
	}
}
