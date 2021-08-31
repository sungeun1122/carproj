package main;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import dao.CarDao;
import dao.CustomDao;
import dao.ReserveDao;
import vo.CarVo;

public class Test {

	public static void main(String[] args) {
		// id
		String id = "bin";
		CustomDao cdao = CustomDao.getInstance();
		ReserveDao rdao = ReserveDao.getInstance();
		CarDao cardao = CarDao.getInstance();
		// getName() 메소드 사용 연습
		Scanner sc = new Scanner(System.in);
//		id = sc.nextLine();
//		CustomVo vo = cdao.getName(id);
//		System.out.println(CustomDao.getInstance().getName(id));
//		System.out.println(vo);
//		System.out.println(vo.getC_name());
		
		// idCheck() 메소드 사용연습 - custom
//		if(cdao.idcheck(id)) //idchek가 false시 기존 아이디 있음
//			System.out.println("신규회원이십니다.");
//		else
//			System.out.println("기존회원이십니다.");
		
		// insert() 메소드 사용연습 - custom
//		System.out.println("신규 고객이시군요!! 상담예약을 위해 정보를 입력해주세요!!");
//		System.out.print("ID >>> ");
//		id = sc.nextLine();
//		System.out.print("이름 >>> ");
//		//여기에 중복체크(idcheck메소드-customDao) 넣기
//		String name = sc.nextLine();
//		System.out.print("연락처('-'제외하고 숫자만 입력) >>> ");
//		String phone = sc.nextLine();
//		System.out.print("생년월일(ex.960220) >>> ");
//		String birth = sc.nextLine();
//		System.out.print("성별(남/여) >>> ");
//		String sex = sc.nextLine();
//		//vo로 데이터 넣기
//		CustomVo vo2 = new CustomVo(id, name, phone, birth, sex);
//		//insert(vo)메소드-customDao 구현
//		cdao.insert(vo2);
//		System.out.println("소중한 정보 감사합니다 " + name + " 고객님!!!" );	
		
		// insertRes() 테스트
//		String custom_id = "soo";
//		String car_id = "2005";
//		Date vis_date= Date.valueOf("2021-09-01");
//		ReserveVo rvo = new ReserveVo(custom_id, car_id, vis_date);
//		rdao.insertRes(rvo);
		
		// getModelList() 테스트
//		String model = "Golf";
//		List<CarVo> list = cardao.getModelList(model);	// Golf로 검색한 list 리턴받음
//		for(CarVo cv : list)
//			System.out.println(cv);
		
		// changeInven() 테스트
//		String car_id = "1001";
//		cardao.updateInven(car_id);
		
		//날짜 대소비교
		String vis_date = "2021-08-31";		// 내일
		String vis_date2 = "2021-08-29";	// 어제
		String vis_date3 = "2021-08-35";	// 없는거
		
		System.out.println(rdao.checkingDate(vis_date));	// true
		System.out.println(rdao.checkingDate(vis_date2));	// false
		System.out.println(rdao.checkingDate(vis_date3));	// false
		
		
		
		
		
//		Date vis_date = Date.valueOf(date);
//		System.out.println(vis_date);
//		int m = Integer.parseInt(srArray[2]);
//		int d = Integer.parseInt(srArray[3]);
//		if(Integer.parseInt(srArray[1])>=2021)	//년도
//			if(m>=1 && m<=12){// 월
//				if(m==1||m==3||m==5||m==7||m==8||m==10||m==12)
//					if(d>=1 && d<=31) 
//					if(d>=1 && d<=30) 
//						if(d>=1 && d<=29) 
//			}
			
		
		
		
		
		
		
	}
	
}









