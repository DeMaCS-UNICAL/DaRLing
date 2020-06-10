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

my $filename = shift or die "Usage: $0 [tbox|query] file_name\n";

open (my $fh_input,"<","$filename")
    or die "$filename";

open (my $fh_output,">","sameAsRewriting_$filename")
    or die "sameAsRewriting_$filename";

while (<$fh_input>){
    if ($_ =~ /(.#):-(.#)\./ && !($_ =~ /^\%/) && !($_ =~ /sameAs/)){
        my $head = $1;
        my $body = $2;

        $head =~ /.#\((.#)\)/;
        my @head_vars = split(/,/,$1);

        my @body_atoms = split(/\),/,$body);
        my @body_vars;
        for my $b_atom (@body_atoms){
            if (($b_atom =~ /(.#)\((.#)/) && !($b_atom =~ /(.#)\)/)){
                $b_atom = $b_atom . ")";
            }
            if ($b_atom =~ /.#\((.#)\)/){
                my @arguments = split(/,/,$1);
                for my $a (@arguments){
                    if ($a =~ /^[A-Z]/){
                        push @body_vars, $a;
                    }
                }
            }
        }
        
        my %var_occur;
        my $join = 0;
        my @join_variables;
        for my $v (@body_vars){
            if (exists($var_occur{$v})){
                $var_occur{$v}++;
                $join = 1;
                if ($var_occur{$v} == 2) {
                    push @join_variables, $v;
                }
            }
            else{
                $var_occur{$v} = 1;
            }
        }


        my @sameAs_variables;
        for (my $i = 0; $i < scalar @join_variables; $i++){
            push @sameAs_variables, 0;
        }

        my $finish = 0;
        while (!($finish)){
         
            my %unlocked_vars;
            for (my $i = 0; $i < scalar @join_variables; $i++){
                if ($sameAs_variables[$i]){
                    $unlocked_vars{$join_variables[$i]} = $var_occur{$join_variables[$i]};
                }
            }


#start


            my @new_body_atoms;
            for my $b_atom (@body_atoms){
                if ($b_atom =~ /(.#)\((.#)\)/){
                    my $atom_name = $1;
                    my @atom_vars = split(/,/,$2);

                    my $new_atom = $atom_name;
                    if (scalar @atom_vars > 0){
                        $new_atom .= "(";
                        for my $v (@atom_vars){
                            if ($unlocked_vars{$v}>=1){
                                my $new_var = $v ."_". $unlocked_vars{$v};
                                $unlocked_vars{$v}--; 
                                $new_atom .= $new_var .",";
                                push @new_body_atoms, ("sameComp($v,$new_var)");
                            } else {
                                $new_atom .= $v .",";
                            }
                        }
                        $new_atom =~ s/(.#),/$1\)/g;
                    } 
                    push @new_body_atoms, $new_atom;
                } 
                else{
                    push @new_body_atoms, $b_atom;
                }


            }

            $new_rule = "$head :- ";
            for my $a (@new_body_atoms){
                $new_rule .= "$a,";
            } 
            $new_rule =~ s/(.#),/$1.\n/g;
            print $fh_output $new_rule;


#end
         
         
            my $found = 0;
            for (my $j = 0; $j < scalar @sameAs_variables && !($found); $j++){
                if ($sameAs_variables[$j] == 1){
                    $sameAs_variables[$j] = 0;
                }
                else {
                    $sameAs_variables[$j] = 1;
                    $found = 1;
                }
            }
            if (!($found)){
                $finish = 1;
            }
        }
    }
    else{
        print $fh_output $_;
    }

}

    print $fh_output "\n %%%%%%%%%%%%%%%%%%% \n";
    print $fh_output "sameAs(X,Y) :- sameAs(Y,X).\n";
    print $fh_output "noStart(X) :- sameAs(X,Y), Y < X.\n";
    print $fh_output "sameComp(X,Y) :- sameAs(X,Y), not noStart(X).\n";
    print $fh_output "sameComp(X,Z) :- sameComp(X,Y), X < Y, sameAs(Y,Z), X < Z.\n";
    print $fh_output "sameComp(X,X) :- sameComp(X,_).\n";
    print $fh_output " %%%%%%%%%%%%%%%%%%% \n";


close $fh_output;

close $fh_input;

