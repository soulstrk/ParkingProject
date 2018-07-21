package com.parking;

import java.beans.FeatureDescriptor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import com.Dao.Dao;
import com.Dto.CarDto;
import com.Dto.CustomerDto;
import com.Dto.StatisDto;
import com.Dto.parkingDto;

public class StartSys {
	private static Dao dao = new Dao();
	private static Scanner sc = new Scanner(System.in);
	//private static int paymentFee;
	
	public StartSys() {
		while(true) {
		System.out.println("----------------------------------------------");
		System.out.println("     1.고객    2.관리자   3.현재 주차장 보기  4.시스템종료");
		System.out.println("----------------------------------------------");
		System.out.print("선택>");
		int selectNum1 = sc.nextInt();
		if (selectNum1 == 1) {
			System.out.println("--------------------------------------------------------");
			System.out.println("      1.입차\t       2.출차\t\t3.선결제");
			System.out.println("--------------------------------------------------------");
			System.out.println("선택>");
			int selectNum2 = sc.nextInt();
			if (selectNum2 == 1) {
				entrance();
			} else if (selectNum2 == 2) {
				out();
			} else if (selectNum2 == 3) {
				payment();
				dao.commit();
			}
		} else if (selectNum1 == 2) {
			System.out.println("관리자 비밀번호 입력>");
			String pwd = sc.next();
			System.out.println();
			loading();
			System.out.println();
			if(pwd.equals("admin") || pwd.equals("ADMIN")) {
				System.out.println(" 1.누적통계     2.현재주차된 회원정보");
				System.out.println();
				System.out.print(">");
				int sel = sc.nextInt();
				if(sel == 1) {
					System.out.println();
					System.out.println("------------------------------------------------------------");
					System.out.println("                     HTA주차장 기록 대장");
					System.out.println();
					int sum = statistics();
					System.out.println();
					System.out.println("총 누적 요금 [ "+sum+"원 ]");
					System.out.println();System.out.println();System.out.println();
				} else if(sel == 2) {
					clientPrint(5);
					while(true) {
					System.out.println(); 
					System.out.println("\t 정렬");
					System.out.println("   [1]차량번호순\t[2]이름\t    [3]할인혜택\t[4]종료");
					System.out.print(">");
					int n = sc.nextInt();
					if(n == 1) clientPrint(1);
					else if(n == 2) clientPrint(2);
					else if(n == 3) clientPrint(3);
					else if(n == 4) {
						System.out.print("종료중");
						for (int i = 0; i < 20; i++) {
							try {
								Thread.sleep(50);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							System.out.print(">");
						}
						System.out.println(); System.out.println();
						break;
					}
					}
				}
			}
		} else if (selectNum1 == 3) {
			seeParking();
		} else if (selectNum1 ==4) {
			dao.close();
			System.out.println(" 시스템을 종료중 입니다. ");
			for (int i = 0; i < 30; i++) {
				try {
					Thread.sleep(70);
					System.out.print(">");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(); System.out.println();
			System.out.println(" 시스템이 종료되었습니다.");
			System.exit(0);
		}
		}
	}

	public void entrance() { // 입차시 고객정보 삽입
		System.out.println("----------------------------------");
		System.out.println("|              할인혜택                          |");
		System.out.println("|          국가유공자 할인 50%        |");
		System.out.println("|           소형차 할인 20%          |");
		System.out.println("|          차량이부제 할인 40%        |");
		System.out.println("----------------------------------");
		System.out.println("|          요금 10분당 700원                     |");
		System.out.println("----------------------------------");
		System.out.println();System.out.println();System.out.println();
		System.out.println();
		System.out.println("[ 차량번호를 입력해주세요 ]");
		String car_number = sc.next();
		System.out.println("[ 회원이름을 입력해주세요 ]");
		String name = sc.next();
		System.out.println("[ 차종을 입력해주세요 ]");
		String car_name = sc.next();
		System.out.println("[ 1.소형  2.중형  3.대형 ]\n >");
		int sizeNum = sc.nextInt();
		String car_size = null;
		if (sizeNum == 1) {
			car_size = "소형";
		} else if (sizeNum == 2) {
			car_size = "중형";
		} else {
			car_size = "대형";
		}

		System.out.println("[ 1.국가유공자 2.차량이부제 3.해당없음 ] \n >");
		int discountNum = sc.nextInt();
		String discount = null;
		if (discountNum == 1) {
			discount = "국가유공자";
		} else if (discountNum == 2) {
			discount = "차량이부제";
		} else {
			discount = null;
		}
		System.out.println("---------------------------------------");
		System.out.println("|              실시간 남은 자리                           |");
		System.out.println("|                                     |");
		System.out.println("|         A구역 [ 현재 남은 자리:" + A_vacant() + "]         |");
		System.out.println("|         B구역 [ 현재 남은 자리:" + B_vacant() + "]         |");
		System.out.println("|         C구역 [ 현재 남은 자리:" + C_vacant() + "]         |");
		System.out.println("---------------------------------------");
		System.out.println("구역선택>");
		String area = sc.next();
		area = area.toUpperCase();

		CustomerDto cDto = new CustomerDto(car_number, name, car_name, car_size, area, discount);
		int w = dao.customerInput(cDto);
		int n = dao.carInput(car_number); // 차랑변호랑 입차시간만 입력
		int ranNum = 0;
		while(true) { // 이미 존재하는 주차번호면 다시 실행
			ranNum = (int) (Math.random()*30)+1;
			int cnt = dao.get_area(area.charAt(0), ranNum);
			if(cnt == -1) {
				break;
			}else if(cnt == 1){
				continue;
			}
		}
		int p = dao.areaNum(area.charAt(0),car_number,ranNum);

		if (w != -1 && n != -1) { // 입력이 잘못된 경우 롤백
			System.out.println("환영합니다");
			System.out.println();
			try {
				/*System.out.println("주차중");
				for (int i = 0; i < 30; i++) {
					Thread.sleep(90);
					System.out.print(">");
				}*/
				System.out.println();
				Thread.sleep(1500);
				System.out.println("--------------------------------------------");
				System.out.println();
				System.out.println("[ " + car_number + " ]차량 [" + area + "]구역 " + p + "번 자리 주차완료");
				System.out.println();
				Thread.sleep(800);
				int a = dao.carUpdate(car_number, area, p); // 구역과 구역번호 추가
				if(a != -1) {
				dao.commit();
				}
				SimpleDateFormat sdf = new SimpleDateFormat("[ 입 차 시 간 ] yyyy/MM/dd HH시 mm분");
				Thread.sleep(80);
				String abc = sdf.format(new Date());
				System.out.println(abc);
				System.out.println();
				System.out.println("--------------------------------------------");
				System.out.println(); System.out.println();
			} catch (InterruptedException e) {
				System.out.println(e.getMessage());
			}
		} else
			dao.rollback();

	}

	public void out() { // 출차 메소드
		CustomerDto dto = new CustomerDto();
		CarDto carDto = new CarDto();
		System.out.println("출차 차번호>");
		String car_number = sc.next();
		dto = dao.getCustomer(car_number);
		carDto = dao.getEntranceTime(car_number);
		int checkNum = paymentCheck(car_number);
		if(checkNum == 1) {
			System.out.println();
			System.out.println("차량번호:"+carDto.getCar_number()+"     선 결제가 완료된 차량입니다");
			System.out.println();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println();
			System.out.println("---------------------------");
			System.out.println("                   안녕히 가세요.");
			System.out.println("     "+carDto.getArea()+"구역 " + carDto.getArea_num()+"번 자리 출차 완료");
			System.out.println("---------------------------");
			System.out.println();
			
			int w = dao.deleteAll(car_number);
			if(w != -1) {
				dao.commit();
			}else {
				dao.rollback();
			}
		}else {
		String entranceTime = carDto.getEntrance(); // 입차시간
		String size = dto.getCar_size(); // 나가는 차 크기
		String discount = dto.getDiscount(); // 나가는 차 할인혜택
		if(discount == null) { discount = "해당없음"; }
		int fee=0;
		fee = feeCal(entranceTime,size,discount); // 계산된 요금
		SimpleDateFormat exitDate = new SimpleDateFormat("MM-dd HH:mm");
		String exit_time = exitDate.format(new Date()); // 출차시간
		dao.insertStatis(car_number, exit_time, fee, dto.getCar_name()); // 누적테이블에 저장
		System.out.println("");
		try {
			Thread.sleep(2000);
		if(discount.equals("국가유공자")) {
			System.out.println("                                            ◆국가유공자 차량◆");
			System.out.println("나온 요금:"+fee+"원 [ 50% 할인 적용 금액 ]");
		}else if(size.equals("소형")) {
			System.out.println("                                            ◆소형 차량◆");
			System.out.println("나온 요금:"+fee+"원 [ 40% 할인 적용 금액 ]");
		}else if(discount.equals("차량이부제")) {
			System.out.println("                                            ◆차량이부제 차량◆");
			System.out.println("나온 요금:"+fee+"원 [ 20% 할인 적용 금액 ]");
		}else {
			System.out.println("나온 요금:"+fee+"원 [ 할인 적용대상이 아닙니다 ]");
		}
			Thread.sleep(2000);
			
			System.out.println();
			System.out.println();
			System.out.println("---------------------------");
			System.out.println("                   안녕히 가세요.");
			System.out.println("     "+carDto.getArea()+"구역 " + carDto.getArea_num()+"번 자리 출차 완료");
			System.out.println("---------------------------");
			System.out.println();
			
			int w = dao.deleteAll(car_number);
			if(w != -1) {
				dao.commit();
			}else {
				dao.rollback();
			}
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		}
	}

	public int A_vacant() { // 구역 남은 자리 갯수
		return dao.vacant('A');
	}

	public int B_vacant() {
		return dao.vacant('B');
	}

	public int C_vacant() {
		return dao.vacant('C');
	}

	
	// 소형차 할인 40%
	// 국가유공자 50%
	// 차량이부제 20%
	// 가장높은 할인혜택 하나만 적용~! 
	// 18/07/06 12시33분
	public static int feeCal(String entranceTime,String size,String discount) { // 요금계산 메소드 10분당 700원
		entranceTime = entranceTime.substring(3, entranceTime.length() - 1);
		entranceTime = entranceTime.replace('/', '-').replace('시', ':');
		System.out.println();
		System.out.println();
		System.out.println("---------------------------------");
		System.out.println(entranceTime+"          입차시간");
		
		SimpleDateFormat exitDate = new SimpleDateFormat("MM-dd HH:mm");
		String exit = exitDate.format(new Date()); // 출차시간
		System.out.println(exit+"          출차시간(현재시간)");
		System.out.println("---------------------------------");
		try {
			Date inTime = new SimpleDateFormat("MM-dd HH:mm").parse(entranceTime);
			Date outTime = new SimpleDateFormat("MM-dd HH:mm").parse(exit);
			long parkingTime = outTime.getTime() - inTime.getTime();
			parkingTime /= 1000;
			parkingTime /= 60;
			double fee = (double)(parkingTime*70.0);
			if(discount.equals("국가유공자")) {
				fee = fee*0.5;
			    fee = Math.round(fee);
				fee /= 100.0;
				fee = Math.round(fee);
				fee *= 100;
				return (int)fee;
			}else if(size.equals("소형")) {
				fee = fee*0.6;
			    fee = Math.round(fee);
				fee /= 100.0;
				fee = Math.round(fee);
				fee *= 100;
				return (int)fee;
			}else if(discount.equals("차량이부제")) {
				fee = fee*0.8;
			    fee = Math.round(fee);
				fee /= 100.0;
				fee = Math.round(fee);
				fee *= 100;
				return (int)fee;
			}else {
			    fee = Math.round(fee);
				fee /= 100.0;
				fee = Math.round(fee);
				fee *= 100;
				return (int)fee;
			}
		} catch (Exception e) {	
			System.out.println(e.getMessage());
			return -1;
		}
	}
	
	public int statistics() {
		ArrayList<StatisDto> list = new ArrayList<StatisDto>();
		StatisDto dto = null;
		list = dao.getStatis();
		int sum = 0;
		
		for (int i = 0; i < list.size(); i++) {
			dto = list.get(i);
			System.out.println(dto);
			sum += dto.getFee();
		}
		
		return sum;
	}
	
	public void seeParking() {
		parkingDto dto = dao.getParking();
		int[] a_arr = dao.getA_area();
		int[] b_arr = dao.getB_area();
		int[] c_arr = dao.getC_area();
		
		System.out.println("\n");
		System.out.print("       총 남은 자리:"+dto.getSum_empty()+"       A구역          남은자리:"+dto.getA_empty());
		System.out.println();
		a_arr = dao.getA_area();
		b_arr = dao.getB_area();
		c_arr = dao.getC_area();
		
		for (int i = 0; i < 30; i++) {
			try {
				Thread.sleep(8);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(i%2 == 0) {System.out.print("\t");}
			if(zonze(a_arr,i+1)) {
				if(i%6 == 0) {
					System.out.println();
					/*if(i%12 == 0) {
						System.out.println();
					}*/
				}
				System.out.print("[  ■  ]");
			}else {
			if(i%6 == 0) {
				System.out.println();
				/*if(i%12 == 0) {
					System.out.println();
				}*/
			}
			if(i < 9) {
			System.out.print("[ 0"+(i+1)+"  ]");
			}else {
			System.out.print("[ "+(i+1)+"  ]");
			}
			}
		}
		System.out.println("\n");
		System.out.print("                     B구역          남은자리:"+dto.getB_empty());
		System.out.println();
		for (int i = 0; i < 30; i++) {
			try {
				Thread.sleep(8);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(i%2 == 0) {System.out.print("\t");}
			if(zonze(b_arr,i+1)) {
				if(i%6 == 0) {
					System.out.println();
					/*if(i%12 == 0) {
						System.out.println();
					}*/
				}
				System.out.print("[  ■  ]");
			}else {
			if(i%6 == 0) {
				System.out.println();
				/*if(i%12 == 0) {
					System.out.println();
				}*/
			}
			if(i < 9) {
			System.out.print("[ 0"+(i+1)+"  ]");
			}else {
			System.out.print("[ "+(i+1)+"  ]");
			}
			}
		}
		System.out.println("\n");
		System.out.print("                     C구역          남은자리:"+dto.getC_empty());
		System.out.println();
		for (int i = 0; i < 30; i++) {
			try {
				Thread.sleep(8);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(i%2 == 0) {System.out.print("\t");}
			if(zonze(c_arr,i+1)) {
				String size = carSizeCheck("C",i+1);
				if(i%6 == 0) {
					System.out.println();
					/*if(i%12 == 0) {
						System.out.println();
					}*/
				}
				if(size.equals("소형")) {
					System.out.print("[  ▲  ]");
				}else {
				System.out.print("[  ■  ]");
				}
			}else {
			if(i%6 == 0) {
				System.out.println();
				/*if(i%12 == 0) {
					System.out.println();
				}*/
			}
			if(i < 9) {
			System.out.print("[ 0"+(i+1)+"  ]");
			}else {
			System.out.print("[ "+(i+1)+"  ]");
			}
			}
		}
		System.out.println();
		System.out.println();
		System.out.println(" 소형차량 : "+dto.getS_car()+"대                                           소형  : ▲");
		System.out.println(" 중/대형차량 : "+dto.getMl_car()+"대                           중형,대형  : ■");
		System.out.println();
		System.out.println();
		System.out.println();
	}
	
	public static void clientPrint(int n) {
		ArrayList<CustomerDto> list = dao.getCustomerAll(n);
		CustomerDto dto = null;
		CarDto carDto = null;
		System.out.println("차량번호\t이름\t차종\t크기\t할인혜택\t주차구역");
		System.out.println();
		for (int i = 0; i < list.size(); i++) {
			dto = list.get(i);
			carDto = dao.getEntranceTime(dto.getCar_number());
			if(dto.getDiscount() == null) {
				dto.setDiscount(" ");
			}
			try {
				Thread.sleep(30);
			}catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(dto+"\t"+carDto.getArea()+"구역"+carDto.getArea_num()+"번");
		}
		
	}
	
	public static int payment() {
		CustomerDto dto = new CustomerDto();
		CarDto cDto = new CarDto();
		String entranceTime = "";
		System.out.println(" 결제하실 차량번호를 입력해주세요 >");
		String car_number = sc.next();
		dto = dao.getCustomer(car_number);
		cDto = dao.getEntranceTime(car_number);
		int fee = feeCal(cDto.getEntrance(),dto.getCar_size(),dto.getDiscount());
		SimpleDateFormat exitDate = new SimpleDateFormat("MM-dd HH:mm");
		String exit_time = exitDate.format(new Date()); // 출차시간
		dao.insertStatis(car_number, exit_time, fee, dto.getCar_name());
		System.out.println();
		if(dto.getDiscount().equals("국가유공자")) {
			System.out.println("                                            ◆국가유공자 차량◆");
			System.out.println("나온 요금:"+fee+"원 [ 50% 할인 적용 금액 ]");
		}else if(dto.getCar_size().equals("소형")) {
			System.out.println("                                            ◆소형 차량◆");
			System.out.println("나온 요금:"+fee+"원 [ 40% 할인 적용 금액 ]");
		}else if(dto.getDiscount().equals("차량이부제")) {
			System.out.println("                                            ◆차량이부제 차량◆");
			System.out.println("나온 요금:"+fee+"원 [ 20% 할인 적용 금액 ]");
		}else {
			System.out.println("나온 요금:"+fee+"원 [ 할인 적용대상이 아닙니다 ]");
		}
		System.out.println();
		System.out.println("            결제 방식을 선택해주세요.");
		System.out.println("|\t        \t|");
		System.out.println("|\t1.신용카드\t\t|");
		System.out.println("|\t        \t|");
		System.out.println("|\t2.현금\t\t|");
		System.out.println("|\t        \t|");
		System.out.println("|\t3.교통카드\t\t|");
		System.out.println("|\t        \t|");
		System.out.println(">");
		int g = sc.nextInt();
		switch (g) {
		case 1: credit();
			break;
		case 2: cash();
			break;
		case 3: tMoney();
		}
		System.out.println(); System.out.println();
		int w = dao.carPaymentUpdate(car_number);
		if(w == -1) { System.out.println("업데이트 문제발생"); }
		return fee;
	}
	
	public static boolean zonze(int arr[],int w) { 
		boolean flag = false;
		for (int i = 0; i < 30; i++) {
			if(arr[i] == w) {
				flag = true;
				break;
			}else {
				flag = false;
			}
		}
		return flag;
	}
	
	public static void loading() {
		System.out.println(" 처리중 입니다");
		for (int i = 0; i < 30; i++) {
			try {
				Thread.sleep(55);
				System.out.print(">");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println();
	}
	
	public static String carSizeCheck(String ch,int w) {
		CarDto cDto = null;
		CustomerDto dto = null;
		cDto = dao.getCarTable(ch, w);
		String car_number = cDto.getCar_number();
		dto = dao.getCustomer(car_number);
		return dto.getCar_size();
	}
	
	public static void credit() {
		try {
			System.out.println();
			System.out.println("신용카드를 넣어주세요.");
			Thread.sleep(1200);
			System.out.println();
			System.out.print("결제 처리중");
			for (int i = 0; i < 3; i++) {
				System.out.print(">");
				Thread.sleep(700);
			}
			System.out.println();
			System.out.println();
			System.out.println(" 선결제가 완료 되었습니다. 10분안에 출차 해주십시오.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void cash() {
		try {
			System.out.println();
			System.out.println("현금을 넣어주세요.");
			Thread.sleep(2000);
			System.out.println();
			System.out.println();
			System.out.println(" 선결제가 완료 되었습니다. 10분안에 출차 해주십시오.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void tMoney() {
		try {
			System.out.println();
			System.out.println("교통카드를 찍어주세요.");
			Thread.sleep(500);
			System.out.println();
			System.out.println();
			System.out.println(" 선결제가 완료 되었습니다. 10분안에 출차 해주십시오.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int paymentCheck(String car_number) {
		String payment = dao.getEntranceTime(car_number).getPayment();
		if(payment == null) {
			payment = "미결제";
		}
		if(payment.equals("선결제")) {
			return 1;
		}else {
			return -1;
		}
	}
	
	public static void paymentOk() {
		
	}
}
