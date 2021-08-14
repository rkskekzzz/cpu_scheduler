import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class RR extends Scheduling {
	static List<Result> run(List<Process> jobList) {
		System.out.print("timeslice를 입력하시오 : "); // RR알고리즘에 필요한 timeslice 입력
		Scanner sc1 = new Scanner(System.in);
		int timeSlice = sc1.nextInt();

		int queueIndex = 0; // 원형큐처럼 동작시키기 위한 queueIndex 설정
		int[] accumulateBurstTime = new int[jobList.size()]; // 누적 실행시간 계산을위한 배열 초기화
		for (int i = 0; i < accumulateBurstTime.length; i++) {
			accumulateBurstTime[i] = 0;
		}
		
		do {
			while (jobList.size() != 0) { //RR은 모든 프로세서가 동일한 시간에 도착했다고 가정
				Process frontJob = jobList.get(0); 
				readyQueue.add(new ReadyQueueElement(frontJob.processID, frontJob.burstTime, 0, frontJob.priority));
				jobList.remove(0);
			}

			if (cpuTime < timeSlice) {  // 현재 cpuTime이 설정한 timeslice보다 작을경우
				accumulateBurstTime[queueIndex]++; // 누적실행시간 증가
			} else {
				queueIndex = (queueIndex + 1) % readyQueue.size();  // cpuTime이 timeslice와 같아지는 순간 queueindex 1 증가(원형큐처럼)
				cpuTime = 0;  // cpuTime 초기화
				continue;
			}
			cpuTime++;
			runTime++;
			for (int i = 0; i < readyQueue.size(); i++) {  // 대기중인 프로세서들의 대기시간 증가
				if (i == queueIndex)
					continue;
				readyQueue.get(i).waitingTime++;
			}
			if (accumulateBurstTime[queueIndex] == readyQueue.get(queueIndex).burstTime) { // 누적실행시간이 실행시간과 같아지면 결과에 추가
				ReadyQueueElement rq = readyQueue.get(queueIndex);
				resultList.add(new Result(rq.processID, runTime, rq.burstTime, rq.waitingTime, rq.priority));
				readyQueue.remove(queueIndex); 
				deleteArray(accumulateBurstTime, queueIndex); // List의 내용을 지우면 누적실행시간 배열의 내용도 동시에 지워줘야한다.
				cpuTime = 0;
				if (queueIndex == readyQueue.size()) // List의 끝 값을 지우게 되면 index를 한개 줄여주어야함
					queueIndex--;
				continue;
			}

		} while (jobList.size() != 0 || readyQueue.size() != 0);

		System.out.println();
		runTime = 0;
		return resultList;
	}

	public static void deleteArray(int[] a, int index) {  // 배열을 지우기 위한 메서드
		for (int i = index; i < a.length - 1; i++) {
			a[i] = a[i + 1];
		}
	}
}
