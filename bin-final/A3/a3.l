%{
#include<stdio.h>
#include<stdlib.h>
extern int yylval;
extern FILE* yyin;
%}

%%
[\n] {yylineno++;}
"#include<"[a-zA-Z]+".h>" {printf("%d\t%s\tHeaders\n",yylineno,yytext);}
main|return|if|else|while|switch {printf("%d\t%s\tKEYWORD\n",yylineno,yytext);}
int|float|char|double {printf("%d\t%s\tKEYWORD-DATATYPE\n",yylineno,yytext);}
"{" {printf("%d\t%s\tSTART BRACE\n",yylineno,yytext);}
"}" {printf("%d\t%s\tCLOSE BRACE\n",yylineno,yytext);}
"(" {printf("%d\t%s\tOPEN BRACKET\n",yylineno,yytext);}
")" {printf("%d\t%s\tEND BRACKET\n",yylineno,yytext);}
";" {printf("%d\t%s\tSEMI COLON\n",yylineno,yytext);}
"=" {printf("%d\t%s\tASSIGNMENT\n",yylineno,yytext);}
[a-zA-Z][a-zA-Z0-9]* {printf("%d\t%s\tIDENTIFIER\n",yylineno,yytext);}
[0-9] {printf("%d\t%s\tDIGIT\n",yylineno,yytext);}
[\t] {;}
" "* {;}
. {printf("%d\t%s\tError\n",yylineno,yytext);}
%%
int yywrap()
{
	return 1;
}

int main()
{
	yyin=fopen("sample.c","r");
	yylex();
	fclose(yyin);
	return 0;
}	
