%{

open Ast

%}

/* Déclaration des terminaux */
%token <int> INT
%token PLUS
%token MINUS
%token LPAR RPAR
%token TERM
%token TIME
%token <string> ID


/* Précédences (priorité + associativité) des terminaux */
%left PLUS MINUS
%left TIME
%nonassoc NEG

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
  | INT                     { ECst $1 }
  | ID                      { EId $1 }
  | expr PLUS expr          { EAdd ($1, $3) }
  | expr TIME expr          { EMult ($1, $3) }
  | expr MINUS expr         { EAdd ( $1, ENeg($3))}
  | MINUS expr %prec NEG    { ENeg($2)}
  | LPAR expr RPAR          { $2 }
;


