%{
#include<stdio.h>
#include<stdlib.h>
#include"y.tab.h"
extern FILE* yyin;
void yyerror(char*s);
int yylex();
%}

%token HEADER MAIN OPB CLB OPC CLC ENDS EQ DT DIGIT ID OP

%%
S: Preprocessor MAIN {printf("main block\n");} OPC Statement CLC {printf("main block terminated\n");};

Preprocessor: HEADER Preprocessor | HEADER {printf("Preprocessor Statement\n");};

Statement: stmt | Statement stmt | Statement exp;

stmt: DT ID ENDS {printf("Statement\n");}; | assignment;

assignment: DT ID EQ DIGIT ENDS {printf("assignment statement\n");};

exp: ID EQ ID OP ID ENDS {printf("expression\n");};

%%
void yyerror(char*s)
{
	printf("Error %s",s);
}

int main()
{
	yyin = fopen("sample.c","r");
	yyparse();
	return 0;
} 	 
