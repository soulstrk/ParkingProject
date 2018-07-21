package com.Dto;

public class CustomerDto {
	private String car_number; //차랑번호
	private String name; //회원이름
	private String car_name; //차종
	private String car_size; //소중대
	private String discount; //할인조건
	private String area; //A,B,C 구역
	public CustomerDto() {}
	public CustomerDto(String car_number,String name,String car_name,String car_size,String area,String discount) {
		this.car_number = car_number;
		this.name = name;
		this.car_name = car_name;
		this.car_size = car_size;
		this.discount = discount;
		this.area = area;
	}
	
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getCar_number() {
		return car_number;
	}
	public void setCar_number(String car_number) {
		this.car_number = car_number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCar_name() {
		return car_name;
	}
	public void setCar_name(String car_name) {
		this.car_name = car_name;
	}
	public String getCar_size() {
		return car_size;
	}
	public void setCar_size(String car_size) {
		this.car_size = car_size;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	@Override
	public String toString() {
		String str = car_number+"\t"+name+"\t"+car_name+"\t"+car_size+"\t"+discount;
		return str;
	}
	
}
