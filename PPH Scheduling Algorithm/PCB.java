
public class PCB {
int arivalTime;
int burstTime;
int priority;
public PCB(int AT,int BT, int PR)
{
	arivalTime=AT;
	burstTime=BT;
	priority=PR;	
	}
public int getArivalTime() {
	return arivalTime;
}
public void setArivalTime(int arivalTime) {
	this.arivalTime = arivalTime;
}
public int getBurstTime() {
	return burstTime;
}
public void setBurstTime(int burstTime) {
	this.burstTime = burstTime;
}
public int getPriority() {
	return priority;
}
public void setPriority(int priority) {
	this.priority = priority;
}



}
