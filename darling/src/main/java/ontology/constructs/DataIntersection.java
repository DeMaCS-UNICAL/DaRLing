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

import java.util.*;

public class DataIntersection extends DataRange {
	
	private List<DataRange> dataRanges;
	
	public DataIntersection() {
		dataRanges = new LinkedList<DataRange>();
	}

	public DataIntersection(List<DataRange> dataRanges) {
		super();
		this.dataRanges = dataRanges;
	}

	public List<DataRange> getDataRanges() {
		return dataRanges;
	}

	public void setDataRanges(List<DataRange> dataRanges) {
		this.dataRanges = dataRanges;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof DataIntersection) {
			DataIntersection dataIntersection = (DataIntersection) obj;
			Set<DataRange> ranges = new HashSet<DataRange>();
			Set<DataRange> thisRanges = new HashSet<DataRange>();
			for (DataRange range : dataIntersection.getDataRanges()) {
				ranges.add(range);
			}
			for (DataRange range : dataRanges) {
				thisRanges.add(range);
			}
			return thisRanges.equals(ranges);
		}
		return false;
	}

	@Override
	public String toString() {
		String res = "dataIntersectionOf(";
		for (DataRange range : dataRanges) {
			res += range.toString();
		}
		res += ")";
		return res;
	}
	
	

}
