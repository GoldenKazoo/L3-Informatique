(* Syntaxe abstraite *)
type value =
  | VInt of int
  | VFloat of float
  | VBool of bool
  | VStr of string

and expr =
  | ECst of value
  | EAdd of expr * expr
  | EMul of expr * expr
  | ENeg of expr

(* Conversion en chaîne de caractères pour affichage *)
let rec string_of_value v = match v with
  | VInt i -> string_of_int i
  | VFloat f -> string_of_float f
  | VBool b -> string_of_bool b
  | VStr s -> Printf.sprintf "\"%s\"" s (* FIXME: transformer les caractères spéciaux '\n', '\r' et '\t' *)

and string_of_expr e = match e with
  | ECst v -> string_of_value v
  | EAdd (e1, e2) -> Printf.sprintf "(%s) + (%s)" (string_of_expr e1) (string_of_expr e2)
  | EMul (e1, e2) -> Printf.sprintf "(%s) * (%s)" (string_of_expr e1) (string_of_expr e2)
  | ENeg e -> Printf.sprintf "- (%s)" (string_of_expr e)

(* Arithmétique du type value *)
let add_value v1 v2 = match (v1, v2) with
  | VInt i1, VInt i2 -> VInt (i1 + i2)
  | VInt i1, VFloat f2 -> VFloat (float_of_int i1 +. f2)
  | VFloat f1, VInt i2 -> VFloat (f1 +. float_of_int i2)
  | VFloat f1, VFloat f2 -> VFloat (f1 +. f2)
  | VStr s1, VStr s2 -> VStr (s1 ^ s2)
  | VStr s1, v2 -> VStr (s1 ^ (string_of_value v2))
  | v1, VStr s2 -> VStr ((string_of_value v1) ^ s2)
  | _, _ -> failwith (Printf.sprintf "Type mismatch: cannot add '%s' and '%s'" (string_of_value v1) (string_of_value v2))

(* Evaluation des expressions *)
let rec eval_expr e = match e with
  | ECst v -> v
  | EAdd (e1, e2) ->
    let v1 = eval_expr e1 in
    let v2 = eval_expr e2 in
    add_value v1 v2
  | _ -> failwith (Printf.sprintf "Evaluation of '%s' not yet implemented" (string_of_expr e))


let rec neg_value v1



