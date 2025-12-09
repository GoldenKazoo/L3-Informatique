%{

open Ast

%}

/* Déclaration des terminaux */
%token <int> INT
%token <float> FLOAT
%token <string> STR
%token TRUE FALSE
%token PLUS MINUS TIMES
%token LPAR RPAR
%token TERM
%token AND
%token OR
%token NOT

/* Précédences (priorité + associativité) des terminaux */
%left PLUS MINUS
%left TIMES
%left AND OR NOT
%nonassoc NEG NOT

/* Déclaration du non-terminal axiome (ici, ansyn) et du type de son attribut */
%type <Ast.expr> ansyn
%start ansyn

%%

/* Déclaration de la grammaire avec les actions sémantiques */

ansyn:
  | TERM ansyn              { $2 }
  | expr TERM               { $1 }  
;

expr:
  | value                   { ECst $1 }
  | expr PLUS expr          { EAdd ($1, $3) }
  | expr TIMES expr         { EMul ($1, $3) }
  | expr MINUS expr         { EAdd ($1, ENeg $3) }
  | MINUS expr  %prec NEG   { ENeg $2 }
  | LPAR expr RPAR          { $2 }
;

value:
  | INT                     { VInt $1 }
  | FLOAT                   { VFloat $1 }
  | TRUE                    { VBool true }
  | FALSE                   { VBool false }
  | STR                     { VStr $1 }
;