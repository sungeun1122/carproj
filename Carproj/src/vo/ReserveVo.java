package vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ReserveVo {
	private int res_no;
	private String custom_id;
	private String car_id;
	private Date res_date;
	private Date vis_date;
	
	
	//인자 3개인 커스텀생성자(custom_no,car_id,vis_date) - insertRes()메소드를 위해
	public ReserveVo(String custom_id, String car_id, Date vis_date) {
		super();
		this.custom_id = custom_id;
		this.car_id = car_id;
		this.vis_date = vis_date;
	}

	//인자 2개인 커스텀 생성자 - 데이터베이스에서 Reserve테이블 로우 가져오기
			public ReserveVo(String custom_id, Date vis_date) {
				super();
				this.custom_id = custom_id;
				this.vis_date = vis_date;
			}
	
	
	
}
