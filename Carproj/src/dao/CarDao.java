package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbc.OracleConnectionUtil;
import vo.CarVo;

//getModelList(String model)메소드 - CarDao
	//인자타입 : String model
	//리턴타입 : List<CarVo>
public class CarDao {
	private static CarDao dao = new CarDao();
	private CarDao() {;}
	public static CarDao getInstance() {return dao;}
	
	
	//getModelList(String model)
	public List<CarVo> getModelList(String model) {		//medel 컬럼값으로 조회
		//
		Connection conn = OracleConnectionUtil.connect();
		
		String sql = "SELECT * FROM CAR WHERE MODEL = ? AND INVENTORY = 'O'";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//		String id=null;
		String car_id,brand,car_year,fueltype,color,imported,inventory;
		long price;
		double km;
		CarVo vo=null;	//선언만.
		List<CarVo> list = new ArrayList<CarVo>();
				
		try {
			pstmt = conn.prepareStatement(sql);		//sql 쿼리 전달
			pstmt.setString(1, model);
			rs = pstmt.executeQuery();		
			
			while(rs.next()) {		//where 검색컬럼이 PK,unique 일때 -> while x , if 문으로
				//테이블 컬럼과 CustomVo 타입 객체와 매핑 -> 자동으로 할 때가 옵니다....~!
				car_id = rs.getString(1);
				brand = rs.getString(2);
				model = rs.getString(3);
				car_year = rs.getString(4);
				price = rs.getLong(5);
				km = rs.getDouble(6);
				fueltype = rs.getString(7);
				color = rs.getString(8);
				imported = rs.getString(9);
				inventory = rs.getString(10);
				
				vo = new CarVo(car_id, brand, model, car_year, 
						price, km, fueltype, color, imported, inventory);	//객체 생성하고 초기화
				
				list.add(vo);
			}
			// 위에서 rs.next() 로 조회된 row와 자바의 클래스를 mapping시킵니다.
			//				ㄴ Custom 클래스를 만들고 테이블의 컬럼과 mapping되는 변수들을 선언합니다.  -> 데이터를 저장 VO(Value Object)
			
//			System.out.println(vo);		//vo는 null 초기화 필요
			
		} catch (SQLException e) {
			System.out.println("SQL 실행에 오류가 발생했습니다. : " + e.getMessage());
			//e.printStackTrace();
		} finally{
			try {
				rs.close(); pstmt.close();
			} catch (SQLException e) {
			}
			OracleConnectionUtil.close(conn);
		}
		
		return list;
	}
	
	//changeInven() 메소드 추가   //예약된 차량은 inventory 'X'로 바꿔주는 메소드 추가
	public void updateInven(String car_id) {
		Connection conn = OracleConnectionUtil.connect();
		String sql = "UPDATE CAR SET INVENTORY = 'X' WHERE CAR_ID = ?";		
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, car_id);
			pstmt.execute();
			pstmt.close();
		} catch (SQLException e) {
			System.out.println("SQL 실행에 오류가 발생했습니다. : " + e.getMessage());
		}finally {
			OracleConnectionUtil.close(conn);
		}
	}
	
	
	//getChoiceCar() 메소드 추가 // car_id 로 car 정보 row 가져오기
	public CarVo getChoiceCar(String car_id) {
		Connection conn = OracleConnectionUtil.connect();
		
		String sql = "SELECT * FROM CAR WHERE CAR_ID = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String brand,car_year,fueltype,color,imported,inventory,model;
		long price;
		double km;
		CarVo vo=null;	//선언만.
				
		try {
			pstmt = conn.prepareStatement(sql);		//sql 쿼리 전달
			pstmt.setString(1, car_id);
			rs = pstmt.executeQuery();		
			
			if(rs.next()) {		//where 검색컬럼이 PK,unique 일때 -> while x , if 문으로
				//테이블 컬럼과 CustomVo 타입 객체와 매핑 -> 자동으로 할 때가 옵니다....~!
				car_id = rs.getString(1);
				brand = rs.getString(2);
				model = rs.getString(3);
				car_year = rs.getString(4);
				price = rs.getLong(5);
				km = rs.getDouble(6);
				fueltype = rs.getString(7);
				color = rs.getString(8);
				imported = rs.getString(9);
				inventory = rs.getString(10);
				
				vo = new CarVo(car_id, brand, model, car_year, 
						price, km, fueltype, color, imported, inventory);
			}
			// 위에서 rs.next() 로 조회된 row와 자바의 클래스를 mapping시킵니다.
			//				ㄴ Custom 클래스를 만들고 테이블의 컬럼과 mapping되는 변수들을 선언합니다.  -> 데이터를 저장 VO(Value Object)
			
//			System.out.println(vo);		//vo는 null 초기화 필요
			
		} catch (SQLException e) {
			System.out.println("SQL 실행에 오류가 발생했습니다. : " + e.getMessage());
			//e.printStackTrace();
		} finally{
			try {
				rs.close(); pstmt.close();
			} catch (SQLException e) {
			}
			OracleConnectionUtil.close(conn);
		}
		
		return vo;
	}
	
	
	
	
	
	
	
}
	
	










