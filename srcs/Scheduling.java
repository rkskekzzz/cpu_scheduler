import java.util.ArrayList;
import java.util.List;

class Scheduling {  // 부모클래스
	
	static int currentProcess = 0;
	static int cpuTime = 0;
	static int cpuDone = 0;
	static int runTime = 0;
	
	static List<Result> resultList = new ArrayList<Result>();
	static List<ReadyQueueElement> readyQueue = new ArrayList<ReadyQueueElement>();

	static List<Result> run(List<Process> jobList) {
		return null;
	}
}