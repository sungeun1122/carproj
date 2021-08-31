package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbc.OracleConnectionUtil;
import vo.CustomVo;
/*
getName()메소드 - customDao 구현
idcheck메소드-customDao
insert(vo)메소드-customDao
getModelList()메소드 - CarDao
insertRes(reserveVo vo)메소드-reserve
*/
public class CustomDao {
	private static CustomDao dao = new CustomDao();
	private CustomDao() {;}
	public static CustomDao getInstance() {return dao;}
	
	//getName()메소드 
	public CustomVo getName(String id) {		//custom_id 컬럼값으로 조회
		//
		Connection conn = OracleConnectionUtil.connect();
		
		String sql = "SELECT * FROM custom WHERE CUSTOM_ID = ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//		String id=null;
		String c_name,c_sex,c_phone,c_birth;
		int custom_no;
		CustomVo vo=null;	//선언만.
				
		try {
			pstmt = conn.prepareStatement(sql);		//sql 쿼리 전달
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();		
			
			if(rs.next()) {		//where 검색컬럼이 PK,unique 일때 -> while x , if 문으로
				//테이블 컬럼과 CustomVo 타입 객체와 매핑 -> 자동으로 할 때가 옵니다....~!
				custom_no = rs.getInt("CUSTOM_NO");
				id = rs.getString("CUSTOM_ID");
				c_name = rs.getString("C_NAME");
				c_phone = rs.getString("C_PHONE");
				c_birth = rs.getString("C_BIRTH");
				c_sex = rs.getString("C_SEX");		
				vo = new CustomVo(custom_no, id, c_name, c_phone, c_birth, c_sex);	//객체 생성하고 초기화
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
	
	//idcheck메소드-customDao
	public boolean idcheck(String id) {
		//
		Connection conn = OracleConnectionUtil.connect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql =  "SELECT * FROM CUSTOM WHERE CUSTOM_ID = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) return false;	//중복일때 false;
			else return true;
			
		} catch (SQLException e) {
			System.out.println("SQL 실행에 오류가 발생했습니다. : " + e.getMessage());
//			e.printStackTrace();
		} finally {
			 try {
				 rs.close();	// 꼭 닫기!! 
				 pstmt.close();		// 꼭 닫기!!
			} catch (SQLException e) {
				System.out.println("close 오류 : " + e.getMessage());
//				e.printStackTrace();
			}
		}
		//오직 exception 생겼을때 아래문장 실행
		return false;
	}
	
	//insert(vo)메소드-customDao
	public void insert(CustomVo vo) {
		//
		Connection conn = OracleConnectionUtil.connect();
		String sql = "INSERT INTO CUSTOM(custom_no,CUSTOM_ID,c_name,c_phone,c_birth,c_sex) "
				+ "VALUES(seq_custom.nextval,?,?,?,?,?)";
		//CUSTOM_ID 컬럼 : 무결성 PK 제약조건 검사합니다. -> 중복된값 X, null X
		//
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);	
			
			pstmt.setString(1, vo.getCustom_id());	
			pstmt.setString(2, vo.getC_name());
			pstmt.setString(3, vo.getC_phone());
			pstmt.setString(4, vo.getC_birth());			
			pstmt.setString(5, vo.getC_sex());
			
			pstmt.execute();			
			pstmt.close();				
			System.out.println("회원가입 완료!!");	// 나중에 수정
		} catch (SQLException e) {
			System.out.println("SQL 실행에 오류가 발생했습니다. : " + e.getMessage());
		} finally {
			OracleConnectionUtil.close(conn); 	// 연결 종료
		}
	}
	
	
	//phoneCheck()메소드-customDao
		public boolean phoneCheck(String phone) {
			//
			Connection conn = OracleConnectionUtil.connect();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql =  "SELECT * FROM CUSTOM WHERE C_PHONE = ?";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, phone);
				rs = pstmt.executeQuery();
				if(rs.next()) return false;	//중복일때 false;
				else return true;
				
			} catch (SQLException e) {
				System.out.println("SQL 실행에 오류가 발생했습니다. : " + e.getMessage());
//				e.printStackTrace();
			} finally {
				 try {
					 rs.close();	// 꼭 닫기!! 
					 pstmt.close();		// 꼭 닫기!!
				} catch (SQLException e) {
					System.out.println("close 오류 : " + e.getMessage());
//					e.printStackTrace();
				}
			}
			//오직 exception 생겼을때 아래문장 실행
			return false;
		}
	
		
		//updatePhone() 메소드 : 연락처 update 해주는 메소드 -- 5.회원정보 수정
		public void updatePhone(String c_phone, String custom_id) {
			Connection conn = OracleConnectionUtil.connect();
			String sql = "UPDATE CUSTOM SET C_PHONE = ? WHERE CUSTOM_ID = ?";		
			//custom_id 를 조건으로 하여 email을 수정할 수 있도록 합니다. (+) 추가 reg_date 도 지금시간으로 변경
			
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, c_phone);
				pstmt.setString(2, custom_id);
				pstmt.execute();
				pstmt.close();
			} catch (SQLException e) {
				System.out.println("SQL 실행에 오류가 발생했습니다. : " + e.getMessage());
			}finally {
				OracleConnectionUtil.close(conn);
			}
		}
	
		//getID()메소드   - 연락처 입력받아서 row vo로 리턴
		public CustomVo getID(String c_phone) {		//custom_id 컬럼값으로 조회
			//
			Connection conn = OracleConnectionUtil.connect();
			
			String sql = "SELECT * FROM custom WHERE C_PHONE = ?";
			PreparedStatement pstmt = null;
			ResultSet rs = null;
//			String id=null;
			String c_name,c_sex,custom_id,c_birth;
			int custom_no;
			CustomVo vo=null;	//선언만.
					
			try {
				pstmt = conn.prepareStatement(sql);		//sql 쿼리 전달
				pstmt.setString(1, c_phone);
				rs = pstmt.executeQuery();		
				
				if(rs.next()) {		//where 검색컬럼이 PK,unique 일때 -> while x , if 문으로
					//테이블 컬럼과 CustomVo 타입 객체와 매핑 -> 자동으로 할 때가 옵니다....~!
					custom_no = rs.getInt("CUSTOM_NO");
					custom_id = rs.getString("CUSTOM_ID");
					c_name = rs.getString("C_NAME");
					c_phone = rs.getString("C_PHONE");
					c_birth = rs.getString("C_BIRTH");
					c_sex = rs.getString("C_SEX");		
					vo = new CustomVo(custom_no, custom_id, c_name, c_phone, c_birth, c_sex);	//객체 생성하고 초기화
				}
				// 위에서 rs.next() 로 조회된 row와 자바의 클래스를 mapping시킵니다.
				//				ㄴ Custom 클래스를 만들고 테이블의 컬럼과 mapping되는 변수들을 선언합니다.  -> 데이터를 저장 VO(Value Object)
				
//				System.out.println(vo);		//vo는 null 초기화 필요
				
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
	
		
		//deleteCustom(id)메소드-customDao
		public void deleteCustom(String custom_id) {
			//
			Connection conn = OracleConnectionUtil.connect();
			String sql = "DELETE FROM CUSTOM WHERE CUSTOM_ID = ?";
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql);	
				
				pstmt.setString(1, custom_id);	
				pstmt.execute();			
				pstmt.close();				
			} catch (SQLException e) {
				System.out.println("SQL 실행에 오류가 발생했습니다. : " + e.getMessage());
			} finally {
				OracleConnectionUtil.close(conn); 	// 연결 종료
			}
		}
		
		
	
	
	
}
