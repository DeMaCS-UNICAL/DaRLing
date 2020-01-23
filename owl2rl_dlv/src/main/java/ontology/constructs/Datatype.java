package ontology.constructs;

public class Datatype extends DataRange {
	
	public static final String TYPE_STRING = "xsd:string";
	public static final String TYPE_INTEGER = "xsd:integer";
	
	private String type;
	
	public Datatype(String type) {
		this.type = type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Datatype) {
			Datatype datatype = (Datatype) obj;
			return this.type.equals(datatype.getType());
		}
		return false;
	}
	
	@Override
	public String toString() {
		return type;
	}

}
