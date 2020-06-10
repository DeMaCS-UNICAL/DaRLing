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
