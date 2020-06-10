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
	if($l=~/TBox written in: (.*\.asp)/){
		if($sameas!=0)
		{
			print(qx(perl sameAs_Manager.pl $1));
			qx(rm $1);
		}
	}
	if($l=~/Query \d+ written in: (.*\.asp)/){
		if($sameas!=0)
		{
			print(qx(perl sameAs_Manager.pl $1\n));
			qx(rm $1);
		}
	}
}
