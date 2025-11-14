(* Syntaxe abstraite *)

type value =
| VInt of int
| VBool of bool
| VFloat of float
| VString of string


type expr =
  | ECst of int
  | EAdd of expr * expr
  | ENeg of expr
  | EMult of expr * expr
  | EId of string

(* Conversion en chaîne de caractères pour affichage *)
let rec string_of_expr e = match e with
  | ECst i -> string_of_int i
  | EAdd (e1, e2) -> Printf.sprintf "(%s) + (%s)" (string_of_expr e1) (string_of_expr e2)
  | EMult (e1, e2) -> Printf.sprintf "(%s) * (%s)" (string_of_expr e1) (string_of_expr e2)
  | ENeg (e2 ) -> Printf.sprintf "(- %s)" (string_of_expr e2)
  | EId s -> Printf.sprintf "ID: %s" s


let rec string_of_value e = match e with
  | VInt i -> string_of_int(i)
  | VFloat f -> string_of_float(f)
  | VString s -> s
  | VBool i -> match i with
    | true -> Printf.sprintf("True")
    | false -> Printf.sprintf("False")