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

public class Constant extends Term{

	private boolean isInteger;

	public Constant() {
		super();
		isInteger = false;
	}
	
	public Constant(String name) {
		super(name);
		isInteger = false;
	}
	
	public Constant(String name, boolean isInteger) {
		super(name);
		this.isInteger = isInteger;
	}
	
	public boolean isInteger() {
		return isInteger;
	}

	public void setInteger(boolean isInteger) {
		this.isInteger = isInteger;
	}

	@Override
	public String toString() {
		if (!this.isInteger) {
			return "\"" + this.getName() + "\"";
		} else {
			return this.getName();
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Constant) {
			Constant con = (Constant) obj;
			return this.getName().equals(con.getName());
		}
		return false;
	}
	
}
