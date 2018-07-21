package com.parking;

public class test {

	public static void main(String[] args) {
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
}