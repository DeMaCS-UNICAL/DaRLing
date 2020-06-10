###############################################################################
 # Copyright 2020 Alessio Fiorentino and Marco Manna
 # 
 # Licensed under the Apache License, Version 2.0 (the "License"); you may not
 # use this file except in compliance with the License.  You may obtain a copy
 # of the License at
 # 
 #   http://www.apache.org/licenses/LICENSE-2.0
 # 
 # Unless required by applicable law or agreed to in writing, software
 # distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 # WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 # License for the specific language governing permissions and limitations under
 # the License.
 ##############################################################################

 #!/usr/bin/perl

use Getopt::Long;
$sameas=0;
@abox=();
@tbox=();
@query=();
$help=0;
GetOptions ("sameas|s"  => \$sameas,
		"abox|a=s"  => \@abox,
		"tbox|t=s"  => \@tbox,
		"query|q=s"  => \@query,
		"help|h"  => \$help);
$options = "";
if($#query >= 0){
	$options .= " -q @query";
}
if($#tbox >= 0){
	$options .= " -t @tbox";
}
if($#abox >=0){
	$options .= " -a @abox";
}
if($help!=0){
	$options .= " -h ";
}
$out=(qx(java -jar darling.jar $options));
@outArray=split("\n",$out);
for $l (@outArray){
	if($l=~/TBox written in: (.#\.asp)/){
		if($sameas!=0)
		{
			print(qx(perl sameAs_Manager.pl $1));
			qx(rm $1);
		}
	}
	if($l=~/Query \d+ written in: (.#\.asp)/){
		if($sameas!=0)
		{
			print(qx(perl sameAs_Manager.pl $1\n));
			qx(rm $1);
		}
	}
}
