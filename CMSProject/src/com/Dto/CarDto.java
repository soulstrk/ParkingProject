package com.Dto;

import java.sql.Date;

public class CarDto {
	private String car_number;
	private String entrance; 
	private String area;
	private int area_num;
	private String payment;
	public CarDto() {}
	public CarDto(String car_number,String entrance,String area,int area_num) {
		this.car_number = car_number;
		this.entrance = entrance;
		this.area = area;
		this.area_num = area_num;
	}
	public CarDto(String car_number,String entrance,String area,int area_num,String payment) {
		this.car_number = car_number;
		this.entrance = entrance;
		this.area = area;
		this.area_num = area_num;
		this.payment = payment;
	}
	
	
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public int getArea_num() {
		return area_num;
	}
	public void setArea_num(int area_num) {
		this.area_num = area_num;
	}
	public String getCar_number() {
		return car_number;
	}
	public void setCar_number(String car_number) {
		this.car_number = car_number;
	}
	public String getEntrance() {
		return entrance;
	}
	public void setEntrance(String entrance) {
		this.entrance = entrance;
	}
	
}
