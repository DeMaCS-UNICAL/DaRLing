package datalog.costructs;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Body {
	
	private Set<Literal> bodyLiterals;
	private Set<NotEqualToLiteral> inequalities;
	
	public Body() {
		bodyLiterals = new HashSet<Literal>();
		inequalities = new HashSet<NotEqualToLiteral>();
	}

	public Body(Set<Literal> bodyLiterals, Set<NotEqualToLiteral> inequalities) {
		super();
		this.bodyLiterals = bodyLiterals;
		this.inequalities = inequalities;
	}

	public Set<Literal> getBodyLiterals() {
		return bodyLiterals;
	}

	public void setBodyLiterals(Set<Literal> bodyLiterals) {
		this.bodyLiterals = bodyLiterals;
	}
	
	public Set<NotEqualToLiteral> getInequalities() {
		return inequalities;
	}

	public void setInequalities(Set<NotEqualToLiteral> inequalities) {
		this.inequalities = inequalities;
	}

	@Override
	public String toString() {
		String body = "";
		for(Iterator<Literal> iterator = bodyLiterals.iterator(); iterator.hasNext();) {
			body += iterator.next();
			if (iterator.hasNext()) {
				body += ",";
			}
		}
		for(Iterator<NotEqualToLiteral> iterator = inequalities.iterator(); iterator.hasNext();) {
			if (iterator.hasNext()) {
				body += ",";
			}
			body += iterator.next();
		}
		return (body.equals("")) ? body : body + ".";
	}

}
