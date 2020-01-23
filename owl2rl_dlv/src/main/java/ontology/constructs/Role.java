package ontology.constructs;

public class Role {
	
	private String name;
	private boolean inverse;
	

	public Role() {	
		name = "";
		inverse = false;
	}
	
	public Role(String name) {
		this.name = name;
		inverse = false;
	}
	
	public Role(String name, boolean inverse) {
		this.name = name;
		this.inverse = inverse;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isInverse() {
		return inverse;
	}

	public void setInverse(boolean inverse) {
		this.inverse = inverse;
	}

	public Role getInverseRole() {
		Role r = new Role();
		r.setName(name);
		r.setInverse(!inverse);
		return r;
	}
	
	@Override
	public String toString() {
		return inverse ? name + "(-)" : name;
	}
	
	public String toDatalogString() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Role) {
			Role r = (Role) obj;
			return (name.contentEquals(r.getName()) && inverse == r.isInverse());
		}
		return false;
	}
	
}
