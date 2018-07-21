package com.Dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.Dto.CarDto;
import com.Dto.CustomerDto;
import com.Dto.StatisDto;
import com.Dto.parkingDto;

public class Dao {
	private Connection conn = null;
	private String url = "jdbc:oracle:thin:@localhost:1521:orcl";

	public Dao() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			conn = DriverManager.getConnection(url, "scott", "soul4310");
			conn.setAutoCommit(false);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("오류1");
		}
	}

	public void commit() {
		try {
			conn.commit();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public void rollback() {
		try {
			conn.rollback();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public int customerInput(CustomerDto cDto) { // customers 테이블 입력
		PreparedStatement pstmt = null;
		try {
			String sql = "insert into customers values(?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, cDto.getCar_number());
			pstmt.setString(2, cDto.getName());
			pstmt.setString(3, cDto.getCar_name());
			pstmt.setString(4, cDto.getCar_size());
			pstmt.setString(5, cDto.getArea());
			pstmt.setString(6, cDto.getDiscount());
			int w = pstmt.executeUpdate();
			return w;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return -1;
		} finally {
			try {
				pstmt.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
	}

	public int carInput(String car_number) {// car테이블 입력
		PreparedStatement pstmt = null;
		try {
			String sql = "insert into car values(?,TO_CHAR(sysdate,'YY/MM/DD HH24\"시\"MI\"분\"'),null,null)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, car_number);
			int w = pstmt.executeUpdate();
			return w;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return -1;
		} finally {
			try {
				pstmt.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
	}
	
	public int carUpdate(String car_number,String area,int area_num) {// car테이블 업데이트
		PreparedStatement pstmt = null;
		try {
			String sql = "update car set area = ?, area_num = ? where car_number = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, area);
			pstmt.setInt(2, area_num);
			pstmt.setString(3, car_number);
			int w = pstmt.executeUpdate();
			return w;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return -1;
		} finally {
			try {
				pstmt.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
	}

	public int vacant(char ch) { // parking 테이블 구역 빈자리 리턴메소드
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "select " + ch + "_empty from parking where index_num = 1";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int w = rs.getInt(1);
				return w;
			} else
				return -1;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return -1;
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
	}

	public int areaNum(char ch,String car_number,int ranNum) { //구역별 30개의 번호 중 하나 넣기
		PreparedStatement pstmt = null;
		while(true) {
		try {
			String sql = "insert into "+ch+"_AREA values(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ranNum);
			pstmt.setString(2, car_number);
			pstmt.executeUpdate();
			return ranNum;
		} catch (Exception e) {
			System.out.println(".....");
			System.out.println("입력오류");
			System.exit(0);
		} finally {
			try {
				pstmt.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
		}
	}
	
	public CustomerDto getCustomer(String car_number){ // 손님 전체정보 긁어오기
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CustomerDto dto = null;
		try {
			String sql = "select * from customers where car_number = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, car_number);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String name = rs.getString("name");
				String car_name = rs.getString("car_name");
				String car_size = rs.getString("car_size");
				String discount = rs.getString("discount");
				String area = rs.getString("area");
				dto = new CustomerDto(car_number, name, car_name, car_size, area, discount);
			}
			return dto;
		} catch (Exception e) {
			System.out.println("여긴가");
			System.out.println(e.getMessage());
			return null;
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
	}
	
	public ArrayList<CustomerDto> getCustomerAll(int i){ // 손님 전체정보 긁어오기
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CustomerDto dto = null;
		ArrayList<CustomerDto> list = new ArrayList<>();
		String sql = "";
		try {
			if(i == 5) {
			 sql = "select * from customers a, car b where a.car_number = b.car_number order by a.area,b.area_num";
			}else if(i == 1) {
				sql = "select * from customers order by car_number";
			}else if(i == 2) {
				sql = "select * from customers order by name";
			}else if(i == 3) {
				sql = "select * from customers order by discount";
			}
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String car_number = rs.getString("car_number");
				String name = rs.getString("name");
				String car_name = rs.getString("car_name");
				String car_size = rs.getString("car_size");
				String discount = rs.getString("discount");
				String area = rs.getString("area");
				dto = new CustomerDto(car_number, name, car_name, car_size, area, discount);
				list.add(dto);
			}
			return list;
		} catch (Exception e) {
			System.out.println("여긴가");
			System.out.println(e.getMessage());
			return null;
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
	}
	
	
	public void close() {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public CarDto getEntranceTime(String car_number){ // car테이블 긁어오기
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CarDto dto = null;
		try {
			String sql = "select * from car where car_number = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, car_number);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String entranceTime = rs.getString("entrancetime");
				String area = rs.getString("area");
				int area_num = rs.getInt("area_num");
				dto = new CarDto(car_number, entranceTime, area, area_num);
			}
			return dto;
		} catch (Exception e) {
			System.out.println("여긴가??");
			System.out.println(e.getMessage());
			return null;
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
	}
	public int deleteAll(String car_number){ // 출차시 데이터삭제
		PreparedStatement pstmt = null;
		String sql = "delete from customers where car_number = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, car_number);
			int w = pstmt.executeUpdate();
			return w;
		} catch (Exception e) {
			System.out.println("딜리트 여기");
			System.out.println(e.getMessage());
			return -1;
		} finally {
			try {
				pstmt.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
	}
	
	public int insertStatis(String car_number,String exit_time,int fee,String car_name) {
		PreparedStatement pstmt = null;
		try {
			String sql = "insert into statis values(?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, car_number);
			pstmt.setString(2, exit_time);
			pstmt.setInt(3, fee);
			pstmt.setString(4, car_name);
			int w = pstmt.executeUpdate();
			return w;
		} catch (Exception e) {
			System.out.println("인서트 여기");
			System.out.println(e.getMessage());
			return -1;
		} finally {
			try {
				pstmt.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
	}
	public ArrayList<StatisDto> getStatis(){ // car테이블 긁어오기
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StatisDto dto = null;
		ArrayList<StatisDto> list = new ArrayList<>();
		try {
			String sql = "select * from statis";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String car_number = rs.getString("car_number");
				String exit_time = rs.getString("exit_time");
				int fee = rs.getInt("fee");
				String car_name = rs.getString("car_name");
				dto = new StatisDto(car_number, exit_time, fee, car_name);
				list.add(dto);
			}
			return list;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
	}
	
	public int[] getA_area(){ // car테이블 긁어오기
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int[] arr = new int[30];
		int i = 0;
		try {
			String sql = "select * from a_area";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				arr[i] = rs.getInt("num");
				i++;
			}
			return arr;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
	}
	
	public int[] getB_area(){ // car테이블 긁어오기
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int[] arr = new int[30];
		int i = 0;
		try {
			String sql = "select * from b_area";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				arr[i] = rs.getInt("num");
				i++;
			}
			return arr;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
	}
	
	public int[] getC_area(){ // c_area테이블 긁어오기
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int[] arr = new int[30];
		int i = 0;
		try {
			String sql = "select * from c_area";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				arr[i] = rs.getInt("num");
				i++;
			}
			return arr;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
	}
	
	public int get_area(char ch, int ranNum){ // c_area테이블 긁어오기
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String sql = "select * from "+ch+"_area where num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ranNum);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return 1; // 이미 존재
			}else {
				return -1; // 존재x
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return -2;
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
	}
	
	public parkingDto getParking() { // parking 정보
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		parkingDto dto = null; 
		try {
			String sql = "select * from parking";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int a_num = rs.getInt("a_empty");
				int b_num = rs.getInt("b_empty");
				int c_num = rs.getInt("c_empty");
				int sum_num = rs.getInt("sum_empty");
				int s_car = rs.getInt("s_car");
				int ml_car = rs.getInt("ml_car");
				dto = new parkingDto(a_num, b_num, c_num, sum_num, s_car, ml_car);
				return dto;
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println("getparking오류");
			System.out.println(e.getMessage());
			return null;
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
	}
	
	public CarDto getCarTable(String ch,int w){ // car테이블 긁어오기
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CarDto dto = null;
		try {
			String sql = "select * from car where area = ? and area_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ch);
			pstmt.setInt(2, w);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String car_number = rs.getString("car_number");
				String entranceTime = rs.getString("entrancetime");
				String area = rs.getString("area");
				int area_num = rs.getInt("area_num");
				dto = new CarDto(car_number, entranceTime, area, area_num);
			}
			return dto;
		} catch (Exception e) {
			System.out.println("여긴가??");
			System.out.println(e.getMessage());
			return null;
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}
		}
	}
}
