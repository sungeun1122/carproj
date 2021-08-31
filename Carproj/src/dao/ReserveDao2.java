package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbc.OracleConnectionUtil;
import vo.CustomVo;
import vo.ReserveVo2;

//insertRes(reserveVo vo)메소드-reserve
public class ReserveDao2 {
	private static ReserveDao2 dao = new ReserveDao2();
	private ReserveDao2() {;}
	public static ReserveDao2 getInstance() {return dao;}
	
	
	// getResVo2() 메소드 생성
	public List<ReserveVo2> getResVo2(String custom_id, Date vis_date) {		//
		//
		Connection conn = OracleConnectionUtil.connect();
		
		String sql = "SELECT C_NAME,CUS.CUSTOM_ID,C.CAR_ID,C.MODEL,C.COLOR,RES_DATE,VIS_DATE " + 
				"FROM CAR c, RESERVE res, CUSTOM cus " + 
				"WHERE RES.CUSTOM_ID = CUS.CUSTOM_ID AND C.CAR_ID = RES.CAR_ID "
				+ "AND CUS.CUSTOM_ID = ? AND VIS_DATE = ?;";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//		String id=null;
		String c_name,car_id,model,color;
		Date res_date;
		ReserveVo2 vo=null;	//선언만.
		List<ReserveVo2> list = new ArrayList<ReserveVo2>();
		
		try {
			pstmt = conn.prepareStatement(sql);		//sql 쿼리 전달
			pstmt.setString(1, custom_id);
			pstmt.setDate(2, vis_date);
			rs = pstmt.executeQuery();		
			
			if(rs.next()) {		//where 검색컬럼이 PK,unique 일때 -> while x , if 문으로
				//테이블 컬럼과 CustomVo 타입 객체와 매핑 -> 자동으로 할 때가 옵니다....~!
				c_name = rs.getString(1);
				custom_id = rs.getString(2);
				car_id = rs.getString(3);
				model = rs.getString(4);
				color = rs.getString(5);
				res_date = rs.getDate(6);		
				vis_date = rs.getDate(7);		
                 vo = new ReserveVo2(c_name, custom_id, car_id, model, color, res_date, vis_date);
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
	
	
}
