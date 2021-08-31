package vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//기본생성자 , 커스텀생성자, getter,setter, toString 재정의
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@Data // -- toString 포함된다. 몰랐다,,기억하자 
public class CustomVo {
	//필드생성
    private int custom_no;
    private String custom_id;
    private String c_name;
    private String c_phone;
    private String c_birth;
    private String c_sex;
    
    //custom_id 없는 생성자 - insert() 할 떄 사용 
	public CustomVo(String custom_id, String c_name, String c_phone, String c_birth, String c_sex) {
		super();
		this.custom_id = custom_id;
		this.c_name = c_name;
		this.c_phone = c_phone;
		this.c_birth = c_birth;
		this.c_sex = c_sex;
	}
	// 00 회원님 반값습니다. 
    // 고객님의 회원정보 : 아이디, 이름, 전화번호, 생일 , 성별 
     @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	 return "<<고객님의 회원 정보>>\n"+" [아이디 = "+custom_id+"]"+" [성명 = "+c_name+"]"+" [전화번호 = "
    	   +c_phone+"]"+" [생년월일 = "+c_birth+"]"+" [성별 = "+c_sex+"]";
    }
    
	
}
