package com.Dto;

public class parkingDto {
	private int a_empty;
	private int b_empty;
	private int c_empty;
	private int sum_empty;
	private int s_car; // 소형
	private int ml_car; // 중대형
	public parkingDto() {}
	public parkingDto(int a_empty,int b_empty,int c_empty,int sum_empty,int s_car,int ml_car) {
		this.a_empty = a_empty;
		this.b_empty = b_empty;
		this.c_empty = c_empty;
		this.sum_empty = sum_empty;
		this.s_car = s_car;
		this.ml_car = ml_car;
	}
	public int getA_empty() {
		return a_empty;
	}
	public void setA_empty(int a_empty) {
		this.a_empty = a_empty;
	}
	public int getB_empty() {
		return b_empty;
	}
	public void setB_empty(int b_empty) {
		this.b_empty = b_empty;
	}
	public int getC_empty() {
		return c_empty;
	}
	public void setC_empty(int c_empty) {
		this.c_empty = c_empty;
	}
	public int getSum_empty() {
		return sum_empty;
	}
	public void setSum_empty(int sum_empty) {
		this.sum_empty = sum_empty;
	}
	public int getS_car() {
		return s_car;
	}
	public void setS_car(int s_car) {
		this.s_car = s_car;
	}
	public int getMl_car() {
		return ml_car;
	}
	public void setMl_car(int ml_car) {
		this.ml_car = ml_car;
	}
	@Override
	public String toString() {
		return "parkingDto [a_empty=" + a_empty + ", b_empty=" + b_empty + ", c_empty=" + c_empty + ", sum_empty="
				+ sum_empty + ", s_car=" + s_car + ", ml_car=" + ml_car + "]";
	}
	
}
