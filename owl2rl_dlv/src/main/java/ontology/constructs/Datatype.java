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
