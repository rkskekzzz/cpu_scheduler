class Result {
	public int processID;
	public int startP;
	public int burstTime;
	public int waitingTime;
	public int priority;
	public int responseTime;

	public Result(int processID, int startP, int burstTime, int waitingTime) {

		this.processID = processID;
		this.startP = startP;
		this.burstTime = burstTime;
		this.waitingTime = waitingTime;
	}

	public Result(int processID, int startP, int burstTime, int waitingTime, int priority) {

		this.processID = processID;
		this.startP = startP;
		this.burstTime = burstTime;
		this.waitingTime = waitingTime;
		this.priority = priority;
	}

}
