
public class Attribute {
	private String name;
	private String type;
	
	public Attribute(){
		this.name = null;
		this.type = null;
	}
	public Attribute (String name,int indicator){
		this.name = name;
		this.type = getTypeFromIndicator(indicator);
	}
	
	private String getTypeFromIndicator(int indicator) {
		switch (indicator) {
			case 0 :
				return "data";
			case 1 :
				return "hour";
			case 2 :
				return "eventContext";
			case 3 :
				return "component";
			case 4 :
				return "eventName";
			case 5 :
				return "description";
			case 6 :
				return "origin";
			case 7 :
				return "ip address";
			default:
				return "unknown";
		}
	}
	public String getName () {
		return this.name;
	}
	
	public String getType() {
		return this.type;
	}

}
