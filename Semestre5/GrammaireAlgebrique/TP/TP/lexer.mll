{
open Parser
exception Eof
}


(* Déclaration du dictionnaire (regexp -> terminal/token) *)

rule anlex = parse
  | [' ' '\t' '\n' '\r']     { anlex lexbuf (* Oubli des espacements et passages à la ligne *) }
  | ['0'-'9']+ as lxm        { INT(int_of_string lxm) }
  | ['a'- 'z' 'A'-'z'] ['0' - '9' 'a'- 'z' 'A'-'z' '_']* as ident { ID ident }
  | "/*"                      {comment lexbuf}
  | '+'                      { PLUS }
  | '-'                      { MINUS }
  | '('                      { LPAR }
  | ')'                      { RPAR }
  | ";;"                     { TERM }
  | "*"                      { TIME }
  | eof                      { raise Eof }
  | _  as lxm                { (* Pour tout autre caractère : message sur la sortie erreur + oubli *)
                               Printf.eprintf "Unknown character '%c': ignored\n" lxm; flush stderr;
                               anlex lexbuf
                             }
and comment = parse
| "*/"      {anlex lexbuf}
| _         {comment lexbuf}