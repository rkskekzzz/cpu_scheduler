import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class Main {
	static List<Process> pList = new ArrayList<Process>();
	static List<Result> result = new ArrayList<Result>();
	static boolean fi = true;

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int index; // 입력버튼값
		
		getProcess(); // 프로세서 입력받기
		if (fi == false) // 파일 없으면 프로그램 종료
			return;

		System.out.println("<입력 파일>");
		System.out.println("PID\tArriveT\tBurstT\tPriorty");
		for (Process p : pList) {
			System.out.print(p.processID + "\t");
			System.out.print(p.arriveTime + "\t");
			System.out.print(p.burstTime + "\t");
			System.out.println(p.priority);
		}
		List<Process> cloneList = new ArrayList<>();
		System.out.println("--------------------------------------");
		while (true) {
			
			cloneList.addAll(pList); // 입력받은 List cloneList에 복사
			System.out.println("스케줄링 알고리즘을 선택하세요(1,2,3,4,5)");
			System.out.println("1. FCFS");
			System.out.println("2. SJF");
			System.out.println("3. PS");
			System.out.println("4. RR");
			System.out.println("5. SRTF");
			System.out.print("번호입력 : ");

			index = sc.nextInt();
			System.out.println("--------------------------------------");
			switch (index) {  // 1->FCFS , 2-> SJF, 3->PS, 4->RR, 5->STRF
			case 1:
				result = FCFS.run(cloneList);
				System.out.print("<FCFS scheduling>");
				break;
			case 2:
				result = SJF.run(cloneList);
				System.out.print("<SJF scheduling>");
				break;
			case 3:
				result = PS.run(cloneList);
				System.out.print("<PS scheduling>");
				break;
			case 4:
				result = RR.run(cloneList);
				System.out.print("<RR scheduling>");
				break;
			case 5:
				result = SRTF.run(cloneList);
				System.out.print("<SRTF scheduling>");
				break;
			default:
				break;

			}

			System.out.println();

			printResult(result); // result 출력

			System.out.println("\n계속하시겠습니까? (Y / N)"); 
			System.out.print("입력 : ");
			String exit = sc.next();
			System.out.println("--------------------------------------");
			
			cloneList.clear(); // cloneList clear
			result.clear(); // result clear
			
			if (exit.equals("N") || exit.equals("n")) {  // 프로그램 종료 조건
				
				break;
			}
			
		}
		System.out.println("\t프로그램 종료..!");

	}

	public static void printResult(List<Result> result) { // result List 출력 메서드
		int totalWT = 0;
		int totalBT = 0;
		System.out.println("PID\tBurstT\tWaitT\tTurnAT\tPriority");
		for (Result r : result) {
			System.out.print(r.processID + "\t");
			System.out.print(r.burstTime + "\t");
			System.out.print(r.waitingTime + "\t");
			System.out.print(r.burstTime + r.waitingTime + "\t");
			System.out.println(r.priority + "\t");
			totalWT += r.waitingTime;
			totalBT += r.burstTime;
		}
		double averageWT = totalWT / result.size();
		System.out.println("-총 대기시간 : " + totalWT);
		System.out.println("-평균 대기시간 : " + averageWT);
		System.out.println("-총 진행시간 : " + totalBT);
	}

	private static void getProcess() throws IOException {  // 프로세서 입력 메서드
		System.out.print("파일입력!!!");

		JFileChooser jfc = new JFileChooser();  //JFilechooser 을 이용한 파일 탐색기로 파일 입력하기
		int returnVal = jfc.showOpenDialog(null);
		if (returnVal == 0) {
			File file = jfc.getSelectedFile();
			if (file.exists()) {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String brLine;
				while ((brLine = br.readLine()) != null) { // 한줄씩 읽어와서 공백단위로 구별
					String[] s = brLine.split(" "); 
					Process p = new Process(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2]),
							Integer.parseInt(s[3]));
					pList.add(p);
				}
				br.close();
			}

		} else {
			System.out.println("파일 없음"); // 파일 입력 없을 시
			fi = false;
		}

		// 리스트 sorting
		Collections.sort(pList); // 입력받은 리스트 sorting -> compartTo override : 도착시간 빠른 순서로 sorting
	}

}
