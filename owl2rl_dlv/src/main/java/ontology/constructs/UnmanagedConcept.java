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

public class UnmanagedConcept extends Concept {

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UnmanagedConcept)
			return true;
		return false;
	}

	@Override
	public String toString() {
		return "unmanagedConcept";
	}

	@Override
	public boolean isAtomic() {
		return false;
	}

	@Override
	public Concept getFreshAtomicConcept() {
		return null;
	}

	@Override
	public Concept getStructuralTransformation() {
		return null;
	}

}
