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

public class Rule {
	
	private final static String KEYWORD = ":-";
	private Head head;
	private Body body;

	public Rule() {
		head = new Head();
		body = new Body();
	}
	
	public Rule(Head head, Body body) {
		this.head = head;
		this.body = body;
	}

	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}
	
	@Override
	public String toString() {
		String headAtom = head.toString();
		String bodyLiterals = body.toString();
		return (bodyLiterals.equals("")) ? headAtom + "." : headAtom + " " + KEYWORD + " " + bodyLiterals; 
	}

}
