package vo;

// JOIN 한 컬럼들 매핑시킬 VO클래스

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class ReserveVo2 {
   private String c_name;
   private String custom_id;
   private String car_id;
   private String model;
   private String color;
   private Date res_date;
   private Date vis_date;
   
 
}
