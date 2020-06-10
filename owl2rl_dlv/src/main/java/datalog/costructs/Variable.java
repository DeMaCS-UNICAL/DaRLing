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


package datalog.costructs;

public class Variable extends Term {
	
	public Variable() {
		super();
	}

	public Variable(String name) {
		super(name);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Variable) {
			Variable var = (Variable) obj;
			return this.getName().equals(var.getName());
		}
		return false;
	}

	@Override
	public String toString() {
		return this.getName();
	}

}
