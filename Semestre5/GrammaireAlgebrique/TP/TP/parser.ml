type token =
  | INT of (
# 8 "parser.mly"
        int
# 6 "parser.ml"
)
  | PLUS
  | MINUS
  | LPAR
  | RPAR
  | TERM
  | TIME
  | ID of (
# 14 "parser.mly"
        string
# 17 "parser.ml"
)

open Parsing
let _ = parse_error;;
# 2 "parser.mly"

open Ast

# 26 "parser.ml"
let yytransl_const = [|
  258 (* PLUS *);
  259 (* MINUS *);
  260 (* LPAR *);
  261 (* RPAR *);
  262 (* TERM *);
  263 (* TIME *);
    0|]

let yytransl_block = [|
  257 (* INT *);
  264 (* ID *);
    0|]

let yylhs = "\255\255\
\001\000\001\000\002\000\002\000\002\000\002\000\002\000\002\000\
\002\000\000\000"

let yylen = "\002\000\
\002\000\002\000\001\000\001\000\003\000\003\000\003\000\002\000\
\003\000\002\000"

let yydefred = "\000\000\
\000\000\000\000\003\000\000\000\000\000\000\000\004\000\010\000\
\000\000\008\000\000\000\001\000\000\000\000\000\002\000\000\000\
\009\000\000\000\000\000\006\000"

let yydgoto = "\002\000\
\008\000\009\000"

let yysindex = "\003\000\
\010\255\000\000\000\000\016\255\016\255\010\255\000\000\000\000\
\000\255\000\000\020\255\000\000\016\255\016\255\000\000\016\255\
\000\000\254\254\254\254\000\000"

let yyrindex = "\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\000\000\026\255\031\255\000\000"

let yygindex = "\000\000\
\002\000\252\255"

let yytablesize = 37
let yytable = "\010\000\
\011\000\013\000\014\000\001\000\016\000\015\000\016\000\012\000\
\018\000\019\000\003\000\020\000\004\000\005\000\000\000\006\000\
\003\000\007\000\004\000\005\000\000\000\013\000\014\000\007\000\
\017\000\000\000\016\000\005\000\005\000\000\000\005\000\005\000\
\007\000\007\000\000\000\007\000\007\000"

let yycheck = "\004\000\
\005\000\002\001\003\001\001\000\007\001\006\001\007\001\006\000\
\013\000\014\000\001\001\016\000\003\001\004\001\255\255\006\001\
\001\001\008\001\003\001\004\001\255\255\002\001\003\001\008\001\
\005\001\255\255\007\001\002\001\003\001\255\255\005\001\006\001\
\002\001\003\001\255\255\005\001\006\001"

let yynames_const = "\
  PLUS\000\
  MINUS\000\
  LPAR\000\
  RPAR\000\
  TERM\000\
  TIME\000\
  "

let yynames_block = "\
  INT\000\
  ID\000\
  "

let yyact = [|
  (fun _ -> failwith "parser")
; (fun __caml_parser_env ->
    let _2 = (Parsing.peek_val __caml_parser_env 0 : Ast.expr) in
    Obj.repr(
# 31 "parser.mly"
                            ( _2 )
# 106 "parser.ml"
               : Ast.expr))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 1 : 'expr) in
    Obj.repr(
# 32 "parser.mly"
                            ( _1 )
# 113 "parser.ml"
               : Ast.expr))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 0 : int) in
    Obj.repr(
# 36 "parser.mly"
                            ( ECst _1 )
# 120 "parser.ml"
               : 'expr))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 0 : string) in
    Obj.repr(
# 37 "parser.mly"
                            ( EId _1 )
# 127 "parser.ml"
               : 'expr))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 2 : 'expr) in
    let _3 = (Parsing.peek_val __caml_parser_env 0 : 'expr) in
    Obj.repr(
# 38 "parser.mly"
                            ( EAdd (_1, _3) )
# 135 "parser.ml"
               : 'expr))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 2 : 'expr) in
    let _3 = (Parsing.peek_val __caml_parser_env 0 : 'expr) in
    Obj.repr(
# 39 "parser.mly"
                            ( EMult (_1, _3) )
# 143 "parser.ml"
               : 'expr))
; (fun __caml_parser_env ->
    let _1 = (Parsing.peek_val __caml_parser_env 2 : 'expr) in
    let _3 = (Parsing.peek_val __caml_parser_env 0 : 'expr) in
    Obj.repr(
# 40 "parser.mly"
                            ( EAdd ( _1, ENeg(_3)))
# 151 "parser.ml"
               : 'expr))
; (fun __caml_parser_env ->
    let _2 = (Parsing.peek_val __caml_parser_env 0 : 'expr) in
    Obj.repr(
# 41 "parser.mly"
                            ( ENeg(_2))
# 158 "parser.ml"
               : 'expr))
; (fun __caml_parser_env ->
    let _2 = (Parsing.peek_val __caml_parser_env 1 : 'expr) in
    Obj.repr(
# 42 "parser.mly"
                            ( _2 )
# 165 "parser.ml"
               : 'expr))
(* Entry ansyn *)
; (fun __caml_parser_env -> raise (Parsing.YYexit (Parsing.peek_val __caml_parser_env 0)))
|]
let yytables =
  { Parsing.actions=yyact;
    Parsing.transl_const=yytransl_const;
    Parsing.transl_block=yytransl_block;
    Parsing.lhs=yylhs;
    Parsing.len=yylen;
    Parsing.defred=yydefred;
    Parsing.dgoto=yydgoto;
    Parsing.sindex=yysindex;
    Parsing.rindex=yyrindex;
    Parsing.gindex=yygindex;
    Parsing.tablesize=yytablesize;
    Parsing.table=yytable;
    Parsing.check=yycheck;
    Parsing.error_function=parse_error;
    Parsing.names_const=yynames_const;
    Parsing.names_block=yynames_block }
let ansyn (lexfun : Lexing.lexbuf -> token) (lexbuf : Lexing.lexbuf) =
   (Parsing.yyparse yytables 1 lexfun lexbuf : Ast.expr)
