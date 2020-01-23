package datalog.costructs;

import java.util.HashSet;
import java.util.Set;

public class Program {
	
	private Set<Rule> rules;

	public Program() {
		rules = new HashSet<Rule>();
	}
	
	public Program(Set<Rule> rules) {
		this.rules = rules;
	}

	public Set<Rule> getRules() {
		return rules;
	}

	public void setRules(Set<Rule> rules) {
		this.rules = rules;
	}
	
	@Override
	public String toString() {
		String program = "";
		for(Rule rule : rules) {
			program += rule +"\n";
		}
		return program;
	}
	
}
