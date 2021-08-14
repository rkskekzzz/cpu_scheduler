import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class SRTF extends Scheduling {
	static List<Result> run(List<Process> jobList) {

		int[] accumulateBurstTime = new int[jobList.size()];
		for (int i = 0; i < accumulateBurstTime.length; i++) {
			accumulateBurstTime[i] = 0;
		}
		
		do {
			int minIndex = 0;
			
			while (jobList.size() != 0) {  // jobList가 비어있지 않을떄까지
				Process frontJob = jobList.get(0); // jobList의 첫번째 값 반환
				if (frontJob.arriveTime == runTime) { // 첫번째 값의 도착시간이 현재 runTime과 같다면?
					readyQueue.add(new ReadyQueueElement(frontJob.processID, frontJob.burstTime, 0,frontJob.priority));  // readyQueue에 입력!
					jobList.remove(0); // jobList 첫번째 값 remove
				} else {
					break;
				}
			}
			
			for (int i = 1; i < readyQueue.size(); i++) {  // readyqueue 크기 만큼 돌면서 남은 실행시간이 가장 적은 프로세서의 index 찾음
				int reaminBurstTime = readyQueue.get(i).burstTime - accumulateBurstTime[i];
				if (reaminBurstTime < readyQueue.get(minIndex).burstTime - accumulateBurstTime[minIndex]) {
					minIndex = i;
				}
			}

			accumulateBurstTime[minIndex]++; //누적실행시간 ++


			cpuTime++;
			runTime++;
			
			for (int i = 0; i < readyQueue.size(); i++) {  // 대기중인 프로세서 대기시간 증가
				if (i == minIndex)
					continue;
				readyQueue.get(i).waitingTime++;
			}
			
			if (accumulateBurstTime[minIndex] == readyQueue.get(minIndex).burstTime) {  //누적시간이 실행시간과 같으면  결과에 추가
				ReadyQueueElement rq = readyQueue.get(minIndex);
				resultList.add(new Result(rq.processID, runTime, rq.burstTime, rq.waitingTime, rq.priority));
				readyQueue.remove(minIndex); // index값 readyqueue 삭제
				deleteArray(accumulateBurstTime, minIndex); // index값 배열 삭제
				cpuTime = 0;
				continue;
			}

		} while (jobList.size() != 0 || readyQueue.size() != 0);

		System.out.println();
		runTime = 0;
		return resultList;
	}

	public static void deleteArray(int[] a, int index) {
		for (int i = index; i < a.length - 1; i++) {
			a[i] = a[i + 1];
		}
	}
}
