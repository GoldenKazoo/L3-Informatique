{
open Parser
exception Eof
}


(* Déclaration du dictionnaire (regexp -> terminal/token) *)

rule anlex = parse
  | [' ' '\t' '\n' '\r']              { anlex lexbuf (* Oubli des espacements et passages à la ligne *) }
  | "/*"                              { comment 0 lexbuf }
  | '"'                               { str "" lexbuf }
  | "true"                            { TRUE }
  | "false"                           { FALSE }
  | ['0'-'9']+'.'['0'-'9']* as lxm    { FLOAT(float_of_string lxm) }
  | ['0'-'9']*'.'['0'-'9']+ as lxm    { FLOAT(float_of_string lxm) }
  | ['0'-'9']+ as lxm                 { INT(int_of_string lxm) }
  | '+'                               { PLUS }
  | '-'                               { MINUS }
  | '*'                               { TIMES }
  | '('                               { LPAR }
  | ')'                               { RPAR }
  | ";;"                              { TERM }
  | "and"                             { AND  }
  | "or"                              { OR   }
  | "not"                             { NOT  }
  | eof                               { raise Eof }
  | _  as lxm                         { (* Pour tout autre caractère : message sur la sortie erreur + oubli *)
                                        Printf.eprintf "Unknown character '%c': ignored\n" lxm; flush stderr;
                                        anlex lexbuf
                                      }

and comment n = parse
  | "*/"    { if n = 0 then anlex lexbuf else comment (n-1) lexbuf }
  | "/*"    { comment (n+1) lexbuf }
  | _       { comment n lexbuf }

and str s = parse
  | '"'       { STR s }
  | "\\n"     { str (Printf.sprintf "%s\n" s) lexbuf }
  | "\\t"     { str (Printf.sprintf "%s\t" s) lexbuf }
  | "\\r"     { str (Printf.sprintf "%s\r" s) lexbuf }
  | "\\\\"     { str (Printf.sprintf "%s\\" s) lexbuf }
  | _ as c    { str (Printf.sprintf "%s%c" s c) lexbuf }




