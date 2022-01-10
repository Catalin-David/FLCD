%{
#include <stdio.h>
#include <stdlib.h>
#define YYDEBUG 1

#define TIP_INT 1
#define TIP_CHR 2

double stiva[20];
int sp;

void push(double x)
{ stiva[sp++]=x; }

double pop()
{ return stiva[--sp]; }

%}

%union {
  	int l_val;
	char *p_val;
}

%token IF
%token THEN
%token BEGIN
%token END
%token PROCEDURE
%token CHAR
%token STRING
%token INT
%token LOOP
%token WRITE
%token STRUCT
%token ELSE
%token ELSIF
%token CONST
%token READ
%token VAR

%token ID
%token <p_val> CONST_INT
%token <p_val> CONST_CHR
%token AND
%token OR
%token CONST_SIR
%token DO
%token DIV
%token CONST_CAR
%token NOT
%token PROGRAM

%token ATRIB
%token WHILE
%token INTEGER
%token PRINT

%left '+' '-'
%left '%' '*' '/'
%left '||'
%left '&&'
%left '!'

%type <l_val> expr_stat factor_stat constanta
%%
prog_sursa:	PROGRAM ID ';' bloc '.'
		;
bloc:		sect_const sect_var instr_comp
		;
sect_const:	/* empty */
		| CONST lista_const
		;
lista_const:	decl_const
		| lista_const decl_const
		;
sect_var:	/* empty */
		| VAR lista_var
		;
lista_var:	decl_var
		| lista_var decl_var
		;
decl_const:	ID '=' {sp=0;} expr_stat ';'	{
		printf("*** %d %g ***\n", $4, pop());
					}
		;
decl_var:	lista_id ':' tip ';'
		;
lista_id:	ID
		| lista_id ',' ID
		;
tip:		tip_simplu
		;
tip_simplu:	INTEGER
		| CHAR
		;
expr_stat:	factor_stat
		| expr_stat '+' expr_stat	{
			if($1==TIP_CAR) $$=TIP_CAR;
				else $$=TIP_INT;
			push(pop()+pop());
						}
		| expr_stat '-' expr_stat	{
			if($1==TIP_CAR) $$=TIP_CAR;
				else $$=TIP_INT;
			push(-pop()+pop());
						}
		| expr_stat '*' expr_stat	{
			if($1==TIP_CAR) $$=TIP_CAR;
				else $$=TIP_INT;
			push(pop()*pop());
						}
		| expr_stat '/' expr_stat
		| expr_stat '%' expr_stat
		;
factor_stat:	ID		{}
		| constanta
		| '(' expr_stat ')'	{$$ = $2;}
		;
constanta:	CONST_INT	{
			$$ = TIP_INT;
				}
		| CONST_CAR	{
			$$ = TIP_CAR;
				}
		;
instr_comp:	BEGIN lista_instr END
		;
lista_instr:	instr
		| lista_instr ';' instr
		;
instr:		/* empty */
		| instr_atrib
		| instr_if
		| instr_while
		| instr_comp
		| instr_read
		| instr_print
		;
instr_atrib:	variabila ATRIB expresie
		;
variabila:	ID
		| ID '[' expresie ']'
		| ID '.' ID
		;
expresie:	factor
		| expresie '+' expresie
		| expresie '-' expresie
		| expresie '*' expresie
		| expresie '/' expresie
		| expresie DIV expresie
		| expresie '%' expresie
		;
factor:		ID
		| constanta {}
		| ID '(' lista_expr ')'
		| '(' expresie ')'
		| ID '[' expresie ']'
		| ID '.' ID
		;
lista_expr:	expresie
		| lista_expr ',' expresie
		;
instr_if:	IF conditie THEN instr ramura_else
		;
ramura_else:	/* empty */
		ELSE instr
		;
conditie:	expr_logica
		| expresie op_rel expresie
		;
expr_logica:	factor_logic
		| expr_logica AND expr_logica
		| expr_logica OR expr_logica
		;
factor_logic:	'(' conditie ')'
		| NOT factor_logic
		;
op_rel:		'='
		| '<'
		| '>'
		;
instr_while:	WHILE conditie DO instr
		;
instr_print:	PRINT '(' lista_elem ')'
		;
lista_elem:	element
		| lista_elem ',' element
		;
element:	expresie
		| CONST_SIR
		;
instr_read:	READ '(' lista_variab ')'
		;
lista_variab:	variabila
		| lista_variab ',' variabila
		;
%%

yyerror(char *s)
{
  printf("%s\n", s);
}

extern FILE *yyin;

main(int argc, char **argv)
{
  if(argc>1) yyin = fopen(argv[1], "r");
  if((argc>2)&&(!strcmp(argv[2],"-d"))) yydebug = 1;
  if(!yyparse()) fprintf(stderr,"\tO.K.\n");
}