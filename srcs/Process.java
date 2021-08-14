class Process implements Comparable<Process> {
	int processID;
	int arriveTime;
	int burstTime;
	int priority;

	public Process(int processID, int arriveTime, int burstTime, int priority) {
		this.processID = processID;
		this.arriveTime = arriveTime;
		this.burstTime = burstTime;
		this.priority = priority;
	}

	@Override
	public int compareTo(Process o) {
		if (this.arriveTime < o.arriveTime)
			return -1;
		else if (this.arriveTime > o.arriveTime)
			return 1;
		return 0;
	}

}