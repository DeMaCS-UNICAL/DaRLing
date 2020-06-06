package query.constructs;

public class IRI extends Term {
	
	public IRI() {
		super();
	}
	
	public IRI(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof IRI) {
			IRI iri = (IRI) obj;
			return iri.name.equals(this.name);
		}
		return false;
	}

	
	
}
