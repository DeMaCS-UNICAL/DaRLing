package ontology.constructs;

public class Individual {

	private String name;
	
	public Individual() {
		name = "";
	}
	
	public Individual(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName (String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Individual) {
			Individual i = (Individual) obj;
			return name.equals(i.getName());
		}
		return false;
	}

}
