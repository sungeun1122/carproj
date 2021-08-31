package vo;

//KCarList()
//인자 x
//리턴타입 : String
public class CarVo {
    //필드 생성
	// car 테이블 : char, varchar2, varchar2, char, number, number, varchar2, varchar2, varchar2
	private String car_id;
	private String brand;
	private String model;
	private String car_year;
	private long price;
	private double km;
	private String fueltype;
	private String color;
	private String imported;
	private String inventory;
	
	//기본생성자
		public CarVo() {;} //컨트롤 스페이스
		
		//커스텀생성자 alt + shift + s + o
	    public CarVo(String car_id, String brand, String model, String car_year, long price, double km, String fueltype,
			String color, String imported,String inventory) {
		super();
		this.car_id = car_id;
		this.brand = brand;
		this.model = model;
		this.car_year = car_year;
		this.price = price;
		this.km = km;
		this.fueltype = fueltype;
		this.color = color;
		this.imported = imported;
		this.imported = inventory;
	}
	//getter setter  알트 쉬프트 s r ???? 
	/**
	 * @return the brand
	 */
	public String getBrand() {
		return brand;
	}
	/**
	 * @param brand the brand to set
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}
	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}
	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}
	/**
	 * @return the car_year
	 */
	public String getCar_year() {
		return car_year;
	}
	/**
	 * @param car_year the car_year to set
	 */
	public void setCar_year(String car_year) {
		this.car_year = car_year;
	}
	/**
	 * @return the price
	 */
	public long getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(long price) {
		this.price = price;
	}
	/**
	 * @return the km
	 */
	public double getKm() {
		return km;
	}
	/**
	 * @param km the km to set
	 */
	public void setKm(double km) {
		this.km = km;
	}
	/**
	 * @return the fueltype
	 */
	public String getFueltype() {
		return fueltype;
	}
	/**
	 * @param fueltype the fueltype to set
	 */
	public void setFueltype(String fueltype) {
		this.fueltype = fueltype;
	}
	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}
	/**
	 * @param color the color to set
	 */
	public void setColor(String color) {
		this.color = color;
	}
	/**
	 * @return the imported
	 */
	public String getImported() {
		return imported;
	}
	/**
	 * @param imported the imported to set
	 */
	public void setImported(String imported) {
		this.imported = imported;
	}
	/**
	 * @return the car_id
	 */
	public String getCar_id() {
		return car_id;
	}
	/**
	 * @param car_id the car_id to set
	 */
	public void setCar_id(String car_id) {
		this.car_id = car_id;
	}
	

	/**
	 * @return the inventory
	 */
	public String getInventory() {
		return inventory;
	}

	/**
	 * @param inventory the inventory to set
	 */
	public void setInventory(String inventory) {
		this.inventory = inventory;
	}
	
	//toString 재정의   알트 쉬프트 s s 
	@Override
	public String toString() {
		
		 return "[매물번호 : " + car_id + ", 브랜드 : " + brand + ", 모델명 : " + model + ", 출시일 : " + car_year
	              + ", 가격(won) : " + price + ", 주행거리 : " + km +"KM"+ ", 연료 타입 : " + fueltype + ", 색상 : " + color + ", 국산/수입 : "
	              + imported + "]";
	
	}

   //TabSpace() 메소드  - string.size() 에 따라 리턴값을 탭인지 공백인지 결정
	//public String TabSpace(String sr) {
		//if(sr.length()>=8)return sr+" "; //공백하나
		//else return sr+"\t"; //8글자 안되면 탭 연결
	//}

	
}
