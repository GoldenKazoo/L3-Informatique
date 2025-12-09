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
  | EAnd of expr * expr
  | EOr  of expr * expr
  | ENot of expr

(* Conversion en chaîne de caractères pour affichage *)
let rec string_of_value v = match v with
  | VInt i -> string_of_int i
  | VFloat f -> string_of_float f
  | VBool b -> string_of_bool b
  | VStr s -> Printf.sprintf "\"%s\"" s

and string_of_expr e = match e with
  | ECst v -> string_of_value v
  | EAdd (e1, e2) -> Printf.sprintf "(%s) + (%s)" (string_of_expr e1) (string_of_expr e2)
  | EMul (e1, e2) -> Printf.sprintf "(%s) * (%s)" (string_of_expr e1) (string_of_expr e2)
  | ENeg e -> Printf.sprintf "-(%s)" (string_of_expr e)
  | EAnd (e1, e2) -> Printf.sprintf "(%s) && (%s)" (string_of_expr e1) (string_of_expr e2)
  | EOr  (e1, e2) -> Printf.sprintf "(%s) || (%s)" (string_of_expr e1) (string_of_expr e2)
  | ENot e -> Printf.sprintf "!(%s)" (string_of_expr e)

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

let rec repeat_string n s =
  if n <= 0 then "" else s ^ (repeat_string (n - 1) s)

let mul_value v1 v2 = match (v1, v2) with
  | VInt i1, VInt i2 -> VInt (i1 * i2)
  | VInt i, VFloat f -> VFloat (float_of_int i *. f)
  | VFloat f, VInt i -> VFloat (f *. float_of_int i)
  | VFloat f1, VFloat f2 -> VFloat (f1 *. f2)
  | VStr s, VInt n -> VStr (repeat_string n s)
  | VInt n, VStr s -> VStr (repeat_string n s)
  | VStr s1, VStr s2 -> VStr (s1 ^ s2)
  | _, _ -> failwith (Printf.sprintf "Type mismatch in mul_value: '%s' * '%s'" (string_of_value v1) (string_of_value v2))

let neg_value v = match v with
  | VInt i -> VInt (-i)
  | VFloat f -> VFloat (-.f)
  | _ -> failwith (Printf.sprintf "Type mismatch: cannot neg '%s'" (string_of_value v))

let and_value v1 v2 = match (v1, v2) with
  | VBool b1, VBool b2 -> VBool (b1 && b2)
  | _ -> failwith "Type mismatch: and expects two booleans"

let or_value v1 v2 = match (v1, v2) with
  | VBool b1, VBool b2 -> VBool (b1 || b2)
  | _ -> failwith "Type mismatch: or expects two booleans"

let not_value v = match v with
  | VBool b -> VBool (not b)
  | _ -> failwith "Type mismatch: not expects a boolean"

(* Evaluation des expressions *)
let rec eval_expr e = match e with
  | ECst v -> v
  | EAdd (e1, e2) ->
      let v1 = eval_expr e1 in
      let v2 = eval_expr e2 in
      add_value v1 v2
  | EMul (e1, e2) ->
      let v1 = eval_expr e1 in
      let v2 = eval_expr e2 in
      mul_value v1 v2
  | ENeg e ->
      let v = eval_expr e in
      neg_value v
  | EAnd (e1, e2) ->
      let v1 = eval_expr e1 in
      let v2 = eval_expr e2 in
      and_value v1 v2
  | EOr (e1, e2) ->
      let v1 = eval_expr e1 in
      let v2 = eval_expr e2 in
      or_value v1 v2
  | ENot e ->
      let v = eval_expr e in
      not_value v
