%{
#include <stdio.h>
#include "y.tab.h"
extern int yylval;
%}

%%
"#include<"[a-zA-Z]+".h>"[\n]* {return HEADER;}
"main()"[\n]* {return MAIN;}
"(" {return OPB;}
")" {return CLB;}
"{" {return OPC;}
"}" {return CLC;}
";" {return ENDS;}
int|float|char|double {return DT;}
[a-zA-Z][a-zA-Z0-9]* {return ID;}
"=" {return EQ;}
[+-/*] {return OP;}
[0-9] {yylval=atoi(yytext); return DIGIT;}
[\t]+ ;
%%

int yywrap()
{
	return 1;
}
