package main;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import dao.CarDao;
import dao.CustomDao;
import dao.ReserveDao;
import dao.ReserveDao2;
import dbc.OracleConnectionUtil;
import vo.CarVo;
import vo.CustomVo;
import vo.ReserveVo;
import vo.ReserveVo2;

public class CarMain {

	public static void main(String[] args) {
		Connection conn = OracleConnectionUtil.connect();
		System.out.println(conn);
		String id = null,name,sex,choiceModel=null,mainchoice,phone=null,
				birth=null,choiceCarId,choiceBrand,choicModelNo,newPhone=null,vs;
		int cnt=0;
		boolean phoneCheck = true,birthCheck=true,idCheck=true,mainCheck=true,carSearchCheck=false,
				dateCheck=true,dateCheck2=true;
		Date vis_date = null;
		// Dao 가져오기
		CarDao cardao = CarDao.getInstance();
		CustomDao cusdao = CustomDao.getInstance();
		ReserveDao resdao = ReserveDao.getInstance();
		ReserveDao2 resdao2 = ReserveDao2.getInstance();
		
		// Vo 선언
		CustomVo cusvo;	//선언만
		ReserveVo resvo;
		
		// List
		List<CarVo> modelList,reservedList= new ArrayList<CarVo>();
		List<ReserveVo> insertResList;
		List<ReserveVo2> showResList;
		
		
		Scanner sc = new Scanner(System.in);
		while(mainCheck) {	//main while문  -- 차량조회 전까지 반복
		System.out.println("환영합니다!!!!!!!!!");
		System.out.println("-------------[[메인 메뉴]]----------------");
		System.out.println("1.기존회원 로그인  2.신규가입  3.ID 찾기  4.회원정보수정  5.예약내역조회  6.계정삭제  7.종료");
		//0827 , 1600 . switch문으로  1. 기존고객 로그인  2. 신규가입  3. ID찾기  4. 종료  +(0827 1900 5번 넣어서 연락처 변경 - 1번 복사)
		mainchoice = sc.nextLine();	
		// 차량조회메뉴로 이동하는 경우
		// 1.기존회원 로그인 완료, 2.신규가입 정상완료	//switch,mainCheckwhile문 탈출 해야함!!
		// 3.ID 찾기 완료시 1번으로 회귀	// 다시 메인메뉴로  -> 알아서  1번 선택
			switch (mainchoice) {
			case "1":			//1. 기존회원 로그인
				cnt=0;
				System.out.println("--------------------[기존회원 로그인 화면입니다.]-------------------");
				while(cnt!=3) {	// 1 2 3
				// ID,연락처 한번에 입력받기
				System.out.print("ID >>> ");
				id = sc.nextLine();
				System.out.print("연락처('-'제외하고 숫자만 입력) >>> ");	// phoneCheck() 메소드 만들기
				phone = sc.nextLine();
				if(!cusdao.idcheck(id)) {	// 기존에 가입한 ID 있으면 if 실행
					if(cusdao.getName(id).getC_phone().equals(phone)) { 	//기존에 가입한 연락처 있으면 if 실행
					// 고객 정보 출력
					cusvo = cusdao.getName(id);
					System.out.println("반갑습니다 \"" + cusvo.getC_name() + "\" 고객님!!!" );	//getName()메소드 - customDao 구현
					System.out.println(cusvo);	//개인정보 다 출력
					System.out.println("차량조회메뉴로 이동합니다.");
					mainCheck=false; //메인 while문 탈출 위함
					carSearchCheck=true;	//차량조회 if문 true로 실행 가능
					break;	//switch문 탈출
					}else {	//기존에 가입한 연락처 없으면 else 실행
						System.out.println("입력하신 ID와 연락처가 일치하지 않습니다."); cnt++;
						System.out.println("입력 오류 " + cnt + "(회)");
						}	
				}else {	// 기존에 가입한 ID 없으면 else 실행
					System.out.println("입력하신 ID와 연락처가 일치하지 않습니다.");
					cnt++;
					System.out.println("입력 오류 " + cnt + "(회)");
				}
				}// 기존고객 while end
				if(cnt==3) {
					System.out.println("입력하신 ID는 가입되지 않은 ID입니다.");
					System.out.println("메인메뉴로  되돌아갑니다.");
					}
				break;	//case 1 break
				
			case "2" : 		// 2. 신규 가입 
				System.out.println("--------------------[신규가입 화면입니다.]-------------------");
				System.out.println("신규 고객이시군요!! 상담예약을 위해 정보를 입력해주세요!!");
				//여기에 중복체크(idcheck메소드-customDao) 넣기
				while(idCheck) {
					//ID 만드는 조건
					System.out.print("ID >>> ");
					id = sc.nextLine();
					if(cusdao.idcheck(id)) {	//1. 중복체크
						if(id.length()>=3 && id.length()<=15){	//2.자릿수 => length() 로 구현
							char temp;  //ex) dlgustn!123
							for(int i=0;i<id.length();i++) {	//3.영어 소문자, 숫자, 특수문자 x => 아스키코드로 구현
								temp = id.charAt(i);
								if((temp>=97 && temp<=122) || (temp>=48 && temp<=57)) idCheck=false; //for문 탈출
								else {System.out.println("영어 소문자와 숫자만 사용 가능합니다. 다시 입력해주세요.");
									idCheck = true;	break;} 
							}
						}else System.out.println("3~15자리로 다시 입력해주세요");
					}
					else System.out.println("중복된 ID입니다. 다시 입력해주세요.");
				}
				System.out.print("이름 >>> ");
				name = sc.nextLine();
				while(phoneCheck) {	// false 이면 탈출
				System.out.print("연락처('-'제외하고 숫자만 입력) >>> ");	
				phone = sc.nextLine();
				//1. 11자리 이상 입력했을 때 length()메소드?
				if(phone.length()!=11) {System.out.println("잘못된 번호입니다. 다시 입력해주세요."); continue;}
				//2. 숫자 이외에 다른 문자가 들어갔을 시 contains() 메소드?
				else {	//11자리 잘 입력했을때
					for(int i=0;i<phone.length();i++) {
						//48~57 에 해당됨
						// 예시  010123456!8
						if(phone.charAt(i)>=48 && phone.charAt(i)<=57) {		//charAt() 사용 
							phoneCheck = false;	// while문 탈출 가능
						}else {phoneCheck = true;break;}	//for문 탈출(오류일때만)	
					} // for end
					if(phoneCheck) {	// 하나라도 숫자가 아니면 다시입력!!!
						System.out.println("잘못된 형식입니다. 숫자만 입력해주세요.");
						continue;
					} // if end
				}	//esle end
				break;	// while문 탈출
				} //while end - 전화번호
				
				while(birthCheck) {	// false 이면 탈출
				System.out.print("생년월일(ex.960220) >>> ");		//1. 6자리로 입력받기
				birth = sc.nextLine();
				//1. 6자리 이상 입력했을 때 length()메소드?
				if(birth.length()!=6) {System.out.println("잘못된 생년월일입니다. 다시 입력해주세요."); continue;}
				//2. 숫자 이외에 다른 문자가 들어갔을 시 contains() 메소드?
				else {	//6자리 잘 입력했을때
					for(int i=0;i<birth.length();i++) {
						//48~57 에 해당됨
						// 예시  96!@25
						if(birth.charAt(i)>=48 && birth.charAt(i)<=57) {		//charAt() 사용 
							birthCheck = false;	// while문 탈출 가능
						}else {birthCheck = true;break;}	//for문 탈출(오류일때만)	
					} // for end
					if(birthCheck) {	// 하나라도 숫자가 아니면 다시입력!!!
						System.out.println("잘못된 형식입니다. 숫자만 입력해주세요.");
						continue;
					} // if end
				}	//esle end
				break;	// while문 탈출
				} //while end - 생일
				
				while(true) {
				System.out.print("성별(남/여) >>> ");		// check 값이므로 남,여 두가지만 입력하도록 하기 equals("남") 메소드
				sex = sc.nextLine();
				//'남','여' 값만 입력받기
				if(sex.equals("남") || sex.equals("여")) break;	//while문 탈출
				else System.out.println("잘못 입력하셨습니다.");
				} // while end - sex
				
				//vo로 데이터 넣기
				cusvo = new CustomVo(id, name, phone, birth, sex);
				//insert(vo)메소드-customDao 구현
				cusdao.insert(cusvo);
				System.out.println("소중한 정보 감사합니다" + name + "고객님!!!" );	
				// 고객 정보 출력하기
				System.out.println("\n-----신규 가입 고객 정보-----");
				System.out.println(cusvo);
				System.out.println("차량조회메뉴로 이동합니다.");
				mainCheck=false; //메인 while문 탈출 위함
				carSearchCheck=true;	//차량조회 if문 true로 실행 가능
				break;  // case 2 end
			case "3" :	// 3.ID 찾기
				System.out.println("--------------------[ID찾기 화면입니다.]-------------------");
				while(true) {
				System.out.print("ID를 받을 연락처를 입력해주세요('-'제외하고 숫자만 입력)\n >>> ");
				phone = sc.nextLine();
				//형식 필터도 어차피 phoneCheck() 메소드에서 다같이 걸러짐
				if(!cusdao.phoneCheck(phone)) {	//중복이면 false
					System.out.println("입력하신 연락처로 ID를 전송하였습니다.");
					System.out.println("\n------------------[문자메세지]--------------------");
					String idMsg = "회원님의 ID는 " + cusdao.getID(phone).getCustom_id() + "입니다.";
					System.out.println(idMsg);
					System.out.println("------------------------------------------------");
					System.out.println("메인화면으로 돌아갑니다. 로그인 해주세요.");
					break;
				}else {
					System.out.println("입력하신 연락처는 등록되지 않은 연락처입니다.\n"
							+ "형식에 맞게 다시입력해주세요.");
				}// else end
				}// while end - case 3
				break;  // case 3 end	-- 메인으로 이동
			case "4" :	// 4. 회원정보수정
				cnt=0;
				System.out.println("--------------------[회원정보수정 화면입니다.]-------------------");
				while(cnt!=3) {	// 1 2 3
				// 로그인
				System.out.print("ID >>> ");
				id = sc.nextLine();
				System.out.print("연락처('-'제외하고 숫자만 입력) >>> ");	// phoneCheck() 메소드 만들기
				phone = sc.nextLine();
				if(!cusdao.idcheck(id)) {	// 기존에 가입한 ID 있으면 if 실행
					if(!cusdao.phoneCheck(phone)) { 	//기존에 가입한 연락처 있으면 if 실행
					// 고객 정보 출력
					cusvo = cusdao.getName(id);
					System.out.println("반갑습니다 \"" + cusvo.getC_name() + "\" 고객님!!!" );	//getName()메소드 - customDao 구현
					System.out.println(cusvo);	//개인정보 다 출력
					
					//연락처 수정
					phoneCheck=true;	//case 2번 재사용이므로 true로 초기화.
					while(phoneCheck) {	// false 이면 탈출
						System.out.print("새로운 연락처를 입력해주세요('-'제외하고 숫자만 입력) >>> ");
						newPhone = sc.nextLine();
					
						//1. 11자리 이상 입력했을 때 length()메소드?
						if(newPhone.length()!=11) {System.out.println("[갯수 에러]  잘못된 번호입니다. 다시 입력해주세요."); continue;}
						//2. 숫자 이외에 다른 문자가 들어갔을 시 contains() 메소드?
						else {	//11자리 잘 입력했을때
							for(int i=0;i<newPhone.length();i++) {
								//48~57 에 해당됨
								// 예시  010123456!8
								if(newPhone.charAt(i)>=48 && newPhone.charAt(i)<=57) {		//charAt() 사용 
									phoneCheck = false;	// while문 탈출 가능
								}else {phoneCheck = true;break;}	//for문 탈출(오류일때만)	
							} // for end
							if(phoneCheck) {	// 하나라도 숫자가 아니면 다시입력!!!
								System.out.println("[형식 에러]  잘못된 형식입니다. 숫자만 입력해주세요.");
								continue;
							} // if end
						}	//esle end
						break;	// while문 탈출 -- 전화번호
						} //while end - 전화번호
					
					//updatePhone() 메소드 사용
					if(!phoneCheck) {	//false일때만 실행하도록		//오류수정
					cusdao.updatePhone(newPhone, id);
					System.out.println("연락처 변경이 완료되었습니다.");
					System.out.println("변경 전 : " + phone);
					System.out.println("변경 후 : " + newPhone);
					//메인메뉴로 회귀
					System.out.println("\n메인메뉴로 이동합니다.\n변경된 연락처로 다시 로그인해주세요.");
					break;	//while문 탈출
					}else {
						System.out.println("");
					}
					}else {	//기존에 가입한 연락처 없으면 else 실행
						System.out.println("입력하신 ID와 연락처가 일치하지 않습니다."); cnt++;
						System.out.println("입력 오류 " + cnt + "(회)");
						}	
				}else {	// 기존에 가입한 ID 없으면 else 실행
					System.out.println("입력하신 ID와 연락처가 일치하지 않습니다.");
					cnt++;
					System.out.println("입력 오류 " + cnt + "(회)");
				}
				}// 기존고객 while end
				if(cnt==3) {
					System.out.println("입력하신 ID는 가입되지 않은 ID입니다.");
					System.out.println("메인메뉴로  되돌아갑니다.");
					}
				
				break;	// case 4 end
			case "5" :	// 예약내역조회
				cnt=0;
				while(cnt!=3) {	// 1 2 3
				// 로그인
				System.out.println("--------------------[예약내역조회 화면입니다.]-------------------");
				System.out.print("ID >>> ");
				id = sc.nextLine();
				System.out.print("연락처('-'제외하고 숫자만 입력) >>> ");	// phoneCheck() 메소드 만들기
				phone = sc.nextLine();
				if(!cusdao.idcheck(id)) {	// 기존에 가입한 ID 있으면 if 실행
					if(cusdao.getName(id).getC_phone().equals(phone)) { 	//기존에 가입한 연락처 있으면 if 실행
					// 고객 정보 출력
					cusvo = cusdao.getName(id);
					System.out.println("반갑습니다 \"" + cusvo.getC_name() + "\" 고객님!!!" );	//getName()메소드 - customDao 구현
					System.out.println(cusvo);	//개인정보 다 출력
					// 예약정보조회
					while(true) {
						System.out.print("상담예약하신 날짜를 입력해주세요.(ex.2021-08-31)\n >>> ");
						String vis_date2 = sc.nextLine();
						if(resdao.checkingDate(vis_date2)) {
							vis_date = Date.valueOf(vis_date2);
							// 만약에 vis_date랑 id가 둘다 일치하는 로우가 있어? 그럼 vis_date리턴해 아니면 null 리턴
							if(resdao.checkingDate2(id, vis_date)!=null) {
								vis_date = resdao.checkingDate2(id, vis_date);
								break;
							}
							else {System.out.println("해당날짜에는 예약된 정보가 없습니다. 다시 입력해주세요.");continue;}	// null 이면 다시 입력받자!
						}else continue;
						}// while end
					//VeserveVo로 만들어서 db에 있는지 검사
					//있으면 가져와서 리스트로 만듦
					//없으면 다시 입력받자
					System.out.println("----------------------[예약 내역 확인]----------------------");
					// ReserveVo2 객체와 매핑해서 각각-
					// getResVo2() 메소드 -- DB reserve테이블에서 예약 내용 ArrayList로 가져오기
					System.out.println(cusdao.getName(id).getC_name() + "님의 예약내역입니다.");
					showResList = resdao2.getResVo2(id, vis_date);
					int cnt2=1;
					for(ReserveVo2 vo2 : showResList) {
						System.out.println("<<예약 차량 " + cnt2 + "번>>");
						System.out.println("ID : " + vo2.getCustom_id());
						System.out.println("매물번호 : " + vo2.getCar_id());
						System.out.println("모델명 : " + vo2.getModel());
						System.out.println("색상 : " + vo2.getColor());
						System.out.println("예약 날짜 : " + vo2.getRes_date());
						System.out.println("방문 날짜 : " + vo2.getVis_date());
						cnt2++;
					}
					break;	// case 5 - while문 탈출
					}else {	//기존에 가입한 연락처 없으면 else 실행
						System.out.println("입력하신 ID와 연락처가 일치하지 않습니다."); cnt++;
						System.out.println("입력 오류 " + cnt + "(회)");
						}	
				}else {	// 기존에 가입한 ID 없으면 else 실행
					System.out.println("입력하신 ID와 연락처가 일치하지 않습니다.");
					cnt++;
					System.out.println("입력 오류 " + cnt + "(회)");
				}
				}// 기존고객 while end
				if(cnt==3) {
					System.out.println("입력하신 ID는 가입되지 않은 ID입니다.");
					System.out.println("메인메뉴로  되돌아갑니다.");
					}
				break;
			case "6" :	// 계정 삭제
				cnt=0;
				System.out.println("--------------------[계정삭제 화면입니다.]-------------------");
				while(cnt!=3) {	// 1 2 3
				// 로그인
				System.out.print("ID >>> ");
				id = sc.nextLine();
				System.out.print("연락처('-'제외하고 숫자만 입력) >>> ");	// phoneCheck() 메소드 만들기
				phone = sc.nextLine();
				if(!cusdao.idcheck(id)) {	// 기존에 가입한 ID 있으면 if 실행
					if(!cusdao.phoneCheck(phone)) { 	//기존에 가입한 연락처 있으면 if 실행
						System.out.println("정말로 계정을 삭제하시겠습니까?(y/n)\n(기존의 회원정보과 예약기록이 모두 삭제됩니다.)");
						if(sc.nextLine().toLowerCase().equals("y")) {
							resdao.deleteReserve(id);
							cusdao.deleteCustom(id);
							System.out.println("[계정이 삭제 완료되었습니다. 그동안 이용해주셔서 감사합니다.]");
							System.out.println("-----------메인메뉴로 돌아갑니다.-----------");
							break;	//break;
						}else {
							System.out.println("계정삭제가 취소되었습니다. 메인메뉴로 돌아갑니다.");
							break;	//while문 탈출
						}
					}else {	//기존에 가입한 연락처 없으면 else 실행
						System.out.println("입력하신 ID와 연락처가 일치하지 않습니다."); cnt++;
						System.out.println("입력 오류 " + cnt + "(회)");
						}	
				}else {	// 기존에 가입한 ID 없으면 else 실행
					System.out.println("입력하신 ID와 연락처가 일치하지 않습니다.");
					cnt++;
					System.out.println("입력 오류 " + cnt + "(회)");
				}
				}// 기존고객 while end
				if(cnt==3) {
					System.out.println("입력하신 ID는 가입되지 않은 ID입니다.");
					System.out.println("메인메뉴로  되돌아갑니다.");
					}
				break;	// case 6 end
			case "7" :	// 종료
				mainCheck = false;  
				System.out.println("---------------이용해주셔서 감사합니다.----------------");
				break;
			default :
				System.out.println("잘못 입력하셨습니다.");
						
		} // switch end
		}// main while end
		
		
		// 차량조회메뉴 구현
		if(carSearchCheck) {	// 1.기존고객 정상로그인, 2.신규고객 정상 가입시 
		System.out.println("-----------------------------------------------------");
		System.out.println("차량 조회 메뉴입니다.");
		while(true) {
		System.out.println("원하시는 브랜드를 선택해주세요");
		System.out.println("1.VOLKSWAGEN  2.TOYOTA  3.BMW  4.AUDI  5.HYUNDAI");
		System.out.print("브랜드 번호 입력 >>> ");		//switch 문으로 각 브랜드별 모델리스트 출력
		choiceBrand = sc.nextLine();
		int cbnum;
		try {
			cbnum = Integer.parseInt(choiceBrand);
			if(cbnum>=1||cbnum<=5)
				System.out.println("원하시는 모델명을 선택해주세요.");
		} catch (NumberFormatException e1) {
//			e1.printStackTrace();
		}
		switch(choiceBrand) {
		case "1": while(true) {
				System.out.println("1.T-Roc  2.Golf");
				System.out.print("모델 번호 입력 >>> ");
				choicModelNo = sc.nextLine();
				if(choicModelNo.equals("1")) {choiceModel="T-Roc"; break;}
				else if(choicModelNo.equals("2")) {choiceModel="Golf"; break;}
				else System.out.println("없는 모델번호입니다. 다시 입력해주세요.");
				}
			break;
		case "2": while(true) {
				System.out.println("1.GT86  2.Corolla  3.RAV4");
				System.out.print("모델 번호 입력 >>> ");
				choicModelNo = sc.nextLine();
				if(choicModelNo.equals("1")) {choiceModel="GT86"; break;}
				else if(choicModelNo.equals("2")) {choiceModel="Corolla"; break;}
				else if(choicModelNo.equals("3")) {choiceModel="RAV4"; break;}
				else System.out.println("없는 모델번호입니다. 다시 입력해주세요.");
				}
			break;
		case "3": while(true) {
				System.out.println("1. 1 Series  2. 2 Series 3. 3 Series");
				System.out.print("모델 번호 입력 >>> ");
				choicModelNo = sc.nextLine();
				if(choicModelNo.equals("1")) {choiceModel="1 Series"; break;}
				else if(choicModelNo.equals("2")) {choiceModel="2 Series"; break;}
				else if(choicModelNo.equals("3")) {choiceModel="3 Series"; break;}
				else System.out.println("없는 모델번호입니다. 다시 입력해주세요.");
				}
			break;
		case "4": while(true) {
				System.out.println("1.A1  2.A3  3.A4  4.A5  5.A6");
				System.out.print("모델 번호 입력 >>> ");
				choicModelNo = sc.nextLine();
				if(choicModelNo.equals("1")) {choiceModel="A1"; break;}
				else if(choicModelNo.equals("2")) {choiceModel="A3"; break;}
				else if(choicModelNo.equals("3")) {choiceModel="A4"; break;}
				else if(choicModelNo.equals("4")) {choiceModel="A5"; break;}
				else if(choicModelNo.equals("5")) {choiceModel="A6"; break;}
				else System.out.println("없는 모델번호입니다. 다시 입력해주세요.");
				}
			break;
		case "5": while(true) {
				System.out.println("1.Veloster  2.Tucson  3.Terracan  4.Santa Fe  5.Kona  6.IX35  7.IX20  "
						+ "8.Ioniq\n9.I800  10.I40  11.I30  12.I20  13.I10  14.Getz  15.Amica  16.Accent");
				System.out.print("모델 번호 입력 >>> ");
				choicModelNo = sc.nextLine();
				if(choicModelNo.equals("1")) {choiceModel="Veloster"; break;}
				else if(choicModelNo.equals("2")) {choiceModel="Tucson"; break;}
				else if(choicModelNo.equals("3")) {choiceModel="Terracan"; break;}
				else if(choicModelNo.equals("4")) {choiceModel="Santa Fe"; break;}
				else if(choicModelNo.equals("5")) {choiceModel="Kona"; break;}
				else if(choicModelNo.equals("6")) {choiceModel="IX35"; break;}
				else if(choicModelNo.equals("7")) {choiceModel="IX20"; break;}
				else if(choicModelNo.equals("8")) {choiceModel="Ioniq"; break;}
				else if(choicModelNo.equals("9")) {choiceModel="I800"; break;}
				else if(choicModelNo.equals("10")) {choiceModel="I40"; break;}
				else if(choicModelNo.equals("11")) {choiceModel="I30"; break;}
				else if(choicModelNo.equals("12")) {choiceModel="I20"; break;}
				else if(choicModelNo.equals("13")) {choiceModel="I10"; break;}
				else if(choicModelNo.equals("14")) {choiceModel="Getz"; break;}
				else if(choicModelNo.equals("15")) {choiceModel="Amica"; break;}
				else if(choicModelNo.equals("16")) {choiceModel="Accent"; break;}
				else System.out.println("없는 모델번호입니다. 다시 입력해주세요.");
				}
			break;
		default : System.out.println("잘못된 번호입니다. 다시 입력해주세요"); continue;
		}
		modelList = cardao.getModelList(choiceModel);
		System.out.println("----------------------[[차량 목록]]----------------------");
		for(CarVo carvo : modelList)
			System.out.println(carvo);
		//모델 리스트입니다. 쭉~~~~출력됨
		System.out.println("-------------------------------------------------------");
		System.out.println("상담을 원하시는 차량의 매물번호를 입력해주세요.");	//복수 선택 여부 추가
		while(true) {
			System.out.print("매물번호 입력 >>> ");
			choiceCarId = sc.nextLine();
			int carIdChoice;
			try {
				carIdChoice = Integer.parseInt(choiceCarId);
			} catch (NumberFormatException e) {
				System.out.println("잘못된 형식입니다. 숫자만 입력해주세요."); continue;
//				e.printStackTrace();
			}
			//매물번호 범위 조건
			//추가할수있는 조건 -> 브랜드선택, 모델선택 조건도 추가해서 챠량목록 리스트에 있는 번호만 입력받을 수 있게 함
			// 브랜드 1번, 모델 1번 : 폭스바겐 T-roc : 1001 ~ 1020  1002번
			// 예시 1. 0123   => 아얘 없는 번호		--> 걸러짐 , 매물번호 다시 입력
			// 예시 2. 3010	=> 다른 브랜드 다른 모델   --> 입력됨, 모델리스드 아래서 출력됨, 선택하신 매물이 맞습니까? n
			// 예시 3. 1003	=> 같은 브랜드 같은 모델 but 실수로 다른줄 선택
			if(carIdChoice>=1001 && carIdChoice<=1040) break;	//폭스바겐
			else if(carIdChoice>=2001 && carIdChoice<=2049) break;	//TOYOTA
				else if(carIdChoice>=3001 && carIdChoice<=3035) break;	//BMW
					else if(carIdChoice>=4001 && carIdChoice<=4102) break;	//AUDI
						else if(carIdChoice>=5001 && carIdChoice<=5245) break;	//HYUNDAI
							else System.out.println("없는 매물번호입니다. 다시 입력해주세요.");
		}//while end
		
		// choiceCarId 로 Car테이블에서 차량정보 가져오는 메소드 구현 -CarDao  
		// getChoiceCar()메소드 -리턴 : CarVo vo
		CarVo vo = cardao.getChoiceCar(choiceCarId);
		reservedList.add(vo);	// 리스트에 선택차량정보 누적담기 -- 여러 대 예약 원할경우
		System.out.println("차량 매물번호 : "+choiceCarId + " 이(가) 선택되었습니다.");		// 나중에 모델명 등 추가정보 같이 출력하기 1!!
		System.out.println("----------------<선택 차량 목록>-------------------");
		for(CarVo vo2 : reservedList)	
			System.out.println(vo2);
		
//		System.out.println(cardao.getChoiceCar(choiceCarId));
		System.out.println("선택하신 차량이 맞으신가요?(y/n)");
		if(sc.nextLine().toLowerCase().equals("y")) {
			cardao.updateInven(choiceCarId);	// 입력받은 car_id에 해당하는 모델의 inventory 컬럼 --> 'X' 로 update
			System.out.println("차량이 정상적으로 선택되었습니다.");
		}
		else {
			System.out.println ("차량 매물번호 : "+choiceCarId + " 이(가) 선택취소 되었습니다.");
			reservedList.remove(vo);	// list 에서 제거
			System.out.println("----------------<선택 차량 목록>-------------------");
			for(CarVo vo2 : reservedList)	
				System.out.println(vo2);
		}
		System.out.println("추가로 선택 하시겠습니까?(y/n)");
		if(sc.nextLine().toLowerCase().equals("y")) System.out.println("차량조회 화면으로 이동합니다.");
		else {System.out.println("예약화면으로 이동합니다.");  break;}
		}	//while end - 차량조회 while
		
		//예약하기
		System.out.println("<<차량 예약 화면입니다>>");
		//예약차량 정보 간략히 출력  (id,brand,model,color) -- foreach문으로 reservedList가져와 출력
		while(true) {
		System.out.println("상담을 원하시는 날짜를 입력해주세요(ex.2021-08-31)");
		System.out.print("방문 날짜는 예약하시는 날 다음날부터 선택 가능합니다.\n >>> ");
		String vis_date2 = sc.nextLine();
		if(resdao.checkingDate(vis_date2)) {
			vis_date = Date.valueOf(vis_date2); break;
		}else continue;
		}// while end
		insertResList = new ArrayList<ReserveVo>();	// AarrayList 생성
		for(CarVo vo : reservedList) {
			resvo = new ReserveVo(id, vo.getCar_id(), vis_date);	//ReserveVo 객체에 담음
			insertResList.add(resvo);		//List<ReserveVo> 에 각 객체 add()
		}
		//insertRes() 메소드 사용해서 reserve테이블에 예약 추가!!
		for(ReserveVo vo : insertResList)
			resdao.insertRes(vo);		//한줄씩 reserve 테이블에 insert
		System.out.println("예약 완료되었습니다.");
		System.out.println("---------------------[예약 내역]----------------------");
		// ReserveVo2 객체와 매핑해서 각각 
		// getResVo2() 메소드 -- DB reserve테이블에서 예약 내용 ArrayList로 가져오기
		showResList = resdao2.getResVo2(id, vis_date);
		for(ReserveVo2 vo2 : showResList) {
			System.out.println(vo2.getC_name() + "님 예약 완료되셨습니다.");
			System.out.println("ID : " + vo2.getCustom_id());
			System.out.println("매물번호 : " + vo2.getCar_id());
			System.out.println("모델명 : " + vo2.getModel());
			System.out.println("색상 : " + vo2.getColor());
			System.out.println("예약 날짜 : " + vo2.getRes_date());
			System.out.println("방문 날짜 : " + vo2.getVis_date());
		}
//		 이현수님 예약 완료되셨습니다.
//		 ID :
//		 매물번호 :
//		 모델명 :
//		 색상
//		 예약 날짜 :
//		 방문 날짜 :
		//		ㄴ 인자타입 : (검색 조건들) => 2개 이상 : String custom_id, Date vis_date(캐스팅 합시다.) 
		//		ㄴ 리턴타입 : ReserveVo2 객체
		//for문 서서 한줄씩 출력
		
		
		}// 차량조회 end 
	}// main end

}// class end
