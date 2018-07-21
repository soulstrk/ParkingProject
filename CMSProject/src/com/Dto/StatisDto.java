package com.Dto;

public class StatisDto {
	private String car_number;
	private String exit_time;
	private int fee;
	private String car_name;
	public StatisDto() {}
	public StatisDto(String car_number,String exit_time, int fee,String car_name) {
		this.car_number=car_number;
		this.exit_time=exit_time;
		this.fee=fee;
		this.car_name=car_name;
	}
	
	@Override
	public String toString() {
		return "차량번호=" + car_number + " | 출차시간=" + exit_time + " | 지불요금=" + fee + "원 | 차종="
				+ car_name + "";
	}
	public String getCar_name() {
		return car_name;
	}
	public void setCar_name(String car_name) {
		this.car_name = car_name;
	}
	public String getCar_number() {
		return car_number;
	}
	public void setCar_number(String car_number) {
		this.car_number = car_number;
	}
	public String getExit_time() {
		return exit_time;
	}
	public void setExit_time(String exit_time) {
		this.exit_time = exit_time;
	}
	public int getFee() {
		return fee;
	}
	public void setFee(int fee) {
		this.fee = fee;
	}
	
}

