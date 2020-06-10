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


package ontology.tbox;

public class UnmanagedAxiom {
	
	private String owlAxiom;
	
	public UnmanagedAxiom(String owlAxiom) {
		this.owlAxiom = owlAxiom;
	}

	public String getOwlAxiom() {
		return owlAxiom;
	}

	public void setOwlAxiom(String owlAxiom) {
		this.owlAxiom = owlAxiom;
	}
	
	@Override
	public String toString() {
		return owlAxiom;
	}

}
