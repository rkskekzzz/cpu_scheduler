import java.util.ArrayList;
import java.util.List;

class SJF extends Scheduling {
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
				int minIndex = 0;  // 가장 짧은 실행시간을 가지는 프로세서의 index
				if (readyQueue.size() != 0) {	
					for (int i = 1; i < readyQueue.size(); i++) {  // readyQueue의 크기만큼 loop를 돌며 최소 burstTime을 가지는 index값을 찾아냄
						int curBurstTime = readyQueue.get(i).burstTime;
						if (curBurstTime < readyQueue.get(minIndex).burstTime) {
							minIndex = i;  
						}

					}
					ReadyQueueElement rq = readyQueue.get(minIndex);  // ReadyQueueElement에 해당 index의 위치에 있는 값을 반환
					resultList.add(new Result(rq.processID, runTime, rq.burstTime, rq.waitingTime, rq.priority));  // resultList에 추가
					cpuDone = rq.burstTime;  // cpuDone은 실행할 프로세서의 burstTime
					cpuTime = 0;  // cpuTime 초기화
					currentProcess = rq.processID; // currentProcess는 실행할 프로세서의 ProcessID
					readyQueue.remove(minIndex); // readyQueue의 index위치 값 remove

				}
			} else {
				if (cpuTime == cpuDone) {  //cpuTime이 실행할 프로세서의 burstTime과 같아졌다면
					currentProcess = 0;  // currentProcess는 다시 초기값으로
					continue;  // continue;
				}
			}

			cpuTime++; 
			runTime++;   
			for (int i = 0; i < readyQueue.size(); i++) {  //  readyQueue에 있는 프로세서들의 waitingTime 증가
				readyQueue.get(i).waitingTime++;
			}

		} while (jobList.size() != 0 || readyQueue.size() != 0 || currentProcess != 0);
		
		runTime = 0; // runTime 초기화
		return resultList; // 결과반환
	}
}
