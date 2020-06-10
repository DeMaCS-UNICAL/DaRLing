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

public class NotEqualToLiteral {
	
	private final static String KEYWORD = " != "; 
	
	private Variable leftHandSide;
	private Term rightHandSide;
	
	public NotEqualToLiteral() {}
	
	public NotEqualToLiteral(Variable leftHandSide, Term rightHandSide) {
		this.leftHandSide = leftHandSide;
		this.rightHandSide = rightHandSide;
	}

	public Variable getLeftHandSide() {
		return leftHandSide;
	}
	public void setLeftHandSide(Variable leftHandSide) {
		this.leftHandSide = leftHandSide;
	}
	public Term getRightHandSide() {
		return rightHandSide;
	}
	public void setRightHandSide(Term rightHandSide) {
		this.rightHandSide = rightHandSide;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof NotEqualToLiteral) {
			NotEqualToLiteral ineq = (NotEqualToLiteral) obj;
			return leftHandSide.equals(ineq.getLeftHandSide()) && rightHandSide.equals(ineq.getRightHandSide());
		}
		return false;
	}
	
	@Override
	public String toString() {
		return leftHandSide.toString() + KEYWORD + rightHandSide.toString();
	}

}
