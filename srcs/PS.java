import java.util.ArrayList;
import java.util.List;

class PS extends Scheduling {
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
			if (currentProcess == 0) {  // SJF와 작동원리 동일
				int minIndex = 0;
				if (readyQueue.size() != 0) {  
					for (int i = 1; i < readyQueue.size(); i++) {  // 우선순위가 가장 높은 프로세서의 index 반환
						int curPriority = readyQueue.get(i).priority;  
						if (curPriority < readyQueue.get(minIndex).priority) {
							minIndex = i;
						}

					}
					ReadyQueueElement rq = readyQueue.get(minIndex);
					resultList.add(new Result(rq.processID, runTime, rq.burstTime, rq.waitingTime, rq.priority));
					cpuDone = rq.burstTime;
					cpuTime = 0;
					currentProcess = rq.processID;
					readyQueue.remove(minIndex);

				}
			} else {
				if (cpuTime == cpuDone) {
					currentProcess = 0;
					continue;
				}
			}

			cpuTime++;
			runTime++;
			// System.out.println("CT : " + cpuTime + " , RT : " + runTime);
			for (int i = 0; i < readyQueue.size(); i++) {  // 대기중인 프로세서 waitingTime증가
				readyQueue.get(i).waitingTime++;
			}

		} while (jobList.size() != 0 || readyQueue.size() != 0 || currentProcess != 0);
		runTime = 0;
		return resultList;
	}
}