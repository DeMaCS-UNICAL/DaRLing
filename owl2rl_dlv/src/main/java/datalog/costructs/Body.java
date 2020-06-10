/*******************************************************************************
 * Copyright 2020 Alessio Fiorentino and Marco Manna
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
 ******************************************************************************/
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
