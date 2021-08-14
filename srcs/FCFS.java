import java.util.ArrayList;
import java.util.List;

class FCFS extends Scheduling {
	static List<Result> run(List<Process> jobList) {
		
		do {

			while (jobList.size() != 0) {  // jobList가 비어있지 않을떄까지
				Process frontJob = jobList.get(0); // jobList의 첫번째 값 반환
				if (frontJob.arriveTime == runTime) { // 첫번째 값의 도착시간이 현재 runTime과 같다면?
					readyQueue.add(new ReadyQueueElement(frontJob.processID, frontJob.burstTime, 0,frontJob.priority));  // readyQueue에 입력!
					jobList.remove(0); // jobList 첫번째 값 remove
				} else {
					break;
				}
			}

			if (currentProcess == 0) {  
				if (readyQueue.size() != 0) { // readyQueue
					ReadyQueueElement rq = readyQueue.get(0); // 첫번째 값 반환(도착시간 순서대로sorting 되어있기 때문)
					resultList.add(new Result(rq.processID, runTime, rq.burstTime, rq.waitingTime, rq.priority)); // resultList에 추가
					cpuDone = rq.burstTime; // cpuDone은 실행할 프로세서의 burstTime
					cpuTime = 0; // cpuTime 초기화
					currentProcess = rq.processID; // currentProcess는 실행할 프로세서의 ProcessID
					readyQueue.remove(0); // readyQueue의 첫번째 위치 값 remove

				}
			} else {
				if (cpuTime == cpuDone) { //cpuTime이 실행할 프로세서의 burstTime과 같아졌다면
					currentProcess = 0; // currentProcess는 다시 초기값으로
					continue;  // continue;
				}
			}

			cpuTime++;
			runTime++;
			for (int i = 0; i < readyQueue.size(); i++) { //  readyQueue에 있는 프로세서들의 waitingTime 증가
				readyQueue.get(i).waitingTime++;
			}

		} while (jobList.size() != 0 || readyQueue.size() != 0 || currentProcess != 0);
		readyQueue.clear(); //readyQueue clear
		runTime = 0; // runTime reset
		return resultList;

	}
}