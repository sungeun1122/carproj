package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import dbc.OracleConnectionUtil;
import vo.ReserveVo;

//insertRes(reserveVo vo)메소드-reserve
public class ReserveDao {
	private static ReserveDao dao = new ReserveDao();
	private ReserveDao() {;}
	public static ReserveDao getInstance() {return dao;}
	
	//insertRes(reserveVo vo)
	//res_no	custom_id	car_id	res_date	vis_date
	//시퀀스		변수			입력		sysdate		입력
	
	public void insertRes(ReserveVo vo) {
		//
		Connection conn = OracleConnectionUtil.connect();
		String sql = "INSERT INTO RESERVE(res_no,custom_id,car_id,vis_date) "
				+ "VALUES(seq_reserve.nextval,?,?,?)";
		//CUSTOM_ID 컬럼 : 무결성 PK 제약조건 검사합니다. -> 중복된값 X, null X
		//
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);	
			
			pstmt.setString(1, vo.getCustom_id());	
			pstmt.setString(2, vo.getCar_id());
			pstmt.setDate(3, vo.getVis_date());
			
			pstmt.execute();			
			pstmt.close();				
		} catch (SQLException e) {
			System.out.println("SQL 실행에 오류가 발생했습니다. : " + e.getMessage());
		} finally {
			OracleConnectionUtil.close(conn); 	// 연결 종료
		}
	}
	
	
	// checkingDate() 메소드 : 날자 입력 조건 잡는 메소드
	public boolean checkingDate(String vis_date) {
		try {
			LocalDate today1 = LocalDate.now();	//현재날짜 생성
			Date vis_date2 = Date.valueOf(vis_date);	// 입력날짜 Date로 캐스팅
			Date today2 = Date.valueOf(today1);	//현재날짜 Date로 캐스팅
			if(today2.before(vis_date2)) return true;	//입력날짜가 오늘 이후이다.
			else {System.out.println("잘못된 날짜입니다. 다시 입력해주세요.");return false;}	//입력날짜가 오늘 이전이다. 다시입력.
		//년도 ,월 ,일 범위제한
		} catch (Exception e) {
			System.out.println("잘못된 날짜 형식입니다. 다시 입력해주세요.");
			return false;	//날짜 형식이 잘못됨 (없는 날짜)
//			e.printStackTrace();
		}
	}
	
	
	// checkingDate2() 메소드 : 만약에 vis_date랑 id가 둘다 일치하는 로우가 있어? 그럼 vis_date리턴해 아니면 null 리턴
	public Date checkingDate2(String custom_id, Date vis_date) {
		//
		Connection conn = OracleConnectionUtil.connect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql =  "SELECT * FROM RESERVE WHERE CUSTOM_ID = ? AND VIS_DATE = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, custom_id);
			pstmt.setDate(2, vis_date);
			rs = pstmt.executeQuery();
			if(rs.next()) return vis_date;	//중복일때 false;
			else return null;
			
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
		return null;
	}
	
	//deleteReserve(id)메소드-customDao
			public void deleteReserve(String custom_id) {
				//
				Connection conn = OracleConnectionUtil.connect();
				String sql = "DELETE FROM RESERVE WHERE CUSTOM_ID = ?";
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
