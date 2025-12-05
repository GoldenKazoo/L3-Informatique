(* Syntaxe abstraite et évaluateur complet pour le TP2 *)

(* Valeurs *)
type value =
  | VInt of int
  | VFloat of float
  | VBool of bool
  | VStr of string
  | VClosure of string * expr * (string * value) list  (* fermeture : arg, corps, env *)

and expr =
  | ECst of value
  | EAdd of expr * expr
  | EMul of expr * expr
  | ENeg of expr
  (* logiques *)
  | EAnd of expr * expr
  | EOr of expr * expr
  | ENot of expr
  (* comparaisons *)
  | EEq of expr * expr
  | ENeq of expr * expr
  | ELt of expr * expr
  | ELe of expr * expr
  | EGt of expr * expr
  | EGe of expr * expr
  (* conditionnelle *)
  | EIf of expr * expr * expr
  (* identifiants / let *)
  | EId of string
  | ELet of string * expr * expr
  (* fonctions et application *)
  | EFun of string * expr
  | EApp of expr * expr

(* Conversion en chaîne de caractères pour affichage *)
let rec string_of_value v =
  match v with
  | VInt i -> string_of_int i
  | VFloat f -> string_of_float f
  | VBool b -> string_of_bool b
  | VStr s -> Printf.sprintf "\"%s\"" s  (* FIXME: échapper \n, \r, \t, etc. *)
  | VClosure (arg, body, _env) -> Printf.sprintf "<fun %s -> %s>" arg (string_of_expr_short body)

and string_of_expr_short e =
  (* version courte pour afficher les closures sans trop imbriquer *)
  match e with
  | ECst v -> string_of_value v
  | EId x -> x
  | EFun (x, _) -> Printf.sprintf "(fun %s -> ...)" x
  | _ -> "..."

and string_of_expr e =
  match e with
  | ECst v -> string_of_value v
  | EId x -> x
  | EAdd (e1, e2) -> Printf.sprintf "(%s) + (%s)" (string_of_expr e1) (string_of_expr e2)
  | EMul (e1, e2) -> Printf.sprintf "(%s) * (%s)" (string_of_expr e1) (string_of_expr e2)
  | ENeg e -> Printf.sprintf "-(%s)" (string_of_expr e)
  | EAnd (e1, e2) -> Printf.sprintf "(%s) and (%s)" (string_of_expr e1) (string_of_expr e2)
  | EOr (e1, e2) -> Printf.sprintf "(%s) or (%s)" (string_of_expr e1) (string_of_expr e2)
  | ENot e -> Printf.sprintf "not (%s)" (string_of_expr e)
  | EEq (e1, e2) -> Printf.sprintf "(%s) == (%s)" (string_of_expr e1) (string_of_expr e2)
  | ENeq (e1, e2) -> Printf.sprintf "(%s) != (%s)" (string_of_expr e1) (string_of_expr e2)
  | ELt (e1, e2) -> Printf.sprintf "(%s) < (%s)" (string_of_expr e1) (string_of_expr e2)
  | ELe (e1, e2) -> Printf.sprintf "(%s) <= (%s)" (string_of_expr e1) (string_of_expr e2)
  | EGt (e1, e2) -> Printf.sprintf "(%s) > (%s)" (string_of_expr e1) (string_of_expr e2)
  | EGe (e1, e2) -> Printf.sprintf "(%s) >= (%s)" (string_of_expr e1) (string_of_expr e2)
  | EIf (ec, e1, e2) -> Printf.sprintf "if %s then %s else %s" (string_of_expr ec) (string_of_expr e1) (string_of_expr e2)
  | ELet (x, e1, e2) -> Printf.sprintf "let %s = %s in %s" x (string_of_expr e1) (string_of_expr e2)
  | EFun (x, body) -> Printf.sprintf "(fun %s -> %s)" x (string_of_expr body)
  | EApp (e1, e2) -> Printf.sprintf "(%s)(%s)" (string_of_expr e1) (string_of_expr e2)

(* ---- opérations de bas niveau sur values ---- *)

let rec string_of_value_safe v =
  try string_of_value v
  with _ -> "<unknown>"

(* addition *)
let add_value v1 v2 = match (v1, v2) with
  | VInt i1, VInt i2 -> VInt (i1 + i2)
  | VInt i1, VFloat f2 -> VFloat (float_of_int i1 +. f2)
  | VFloat f1, VInt i2 -> VFloat (f1 +. float_of_int i2)
  | VFloat f1, VFloat f2 -> VFloat (f1 +. f2)
  | VStr s1, VStr s2 -> VStr (s1 ^ s2)
  | VStr s1, v2 -> VStr (s1 ^ (string_of_value_safe v2))
  | v1, VStr s2 -> VStr ((string_of_value_safe v1) ^ s2)
  | _, _ -> failwith (Printf.sprintf "Type mismatch: cannot add '%s' and '%s'"
                        (string_of_value_safe v1) (string_of_value_safe v2))

(* négation *)
let neg_value v = match v with
  | VInt i -> VInt (-i)
  | VFloat f -> VFloat (-.f)
  | _ -> failwith (Printf.sprintf "Type mismatch: cannot negate '%s'" (string_of_value_safe v))

(* multiplication *)
let rec mul_value v1 v2 = match (v1, v2) with
  | VInt i1, VInt i2 -> VInt (i1 * i2)
  | VInt i, VFloat f -> VFloat (float_of_int i *. f)
  | VFloat f, VInt i -> VFloat (f *. float_of_int i)
  | VFloat f1, VFloat f2 -> VFloat (f1 *. f2)
  (* répétition de chaîne : int * str ou str * int *)
  | VInt n, VStr s ->
      if n < 0 then failwith (Printf.sprintf "Negative repetition: cannot multiply '%s' and '%s'"
                                 (string_of_value_safe v1) (string_of_value_safe v2))
      else
        let rec repeat k =
          if k = 0 then VStr ""
          else add_value (VStr s) (repeat (k - 1))
        in repeat n
  | VStr s, VInt n -> mul_value (VInt n) (VStr s)  (* délégation *)
  | _, _ -> failwith (Printf.sprintf "Type mismatch: cannot multiply '%s' and '%s'"
                        (string_of_value_safe v1) (string_of_value_safe v2))

(* opérateurs logiques *)
let and_value v1 v2 = match (v1, v2) with
  | VBool b1, VBool b2 -> VBool (b1 && b2)
  | _, _ -> failwith (Printf.sprintf "Type mismatch: cannot and '%s' and '%s'"
                        (string_of_value_safe v1) (string_of_value_safe v2))

let or_value v1 v2 = match (v1, v2) with
  | VBool b1, VBool b2 -> VBool (b1 || b2)
  | _, _ -> failwith (Printf.sprintf "Type mismatch: cannot or '%s' and '%s'"
                        (string_of_value_safe v1) (string_of_value_safe v2))

let not_value v = match v with
  | VBool b -> VBool (not b)
  | _ -> failwith (Printf.sprintf "Type mismatch: cannot not '%s'" (string_of_value_safe v))

(* comparaisons d'égalité (même type seulement) *)
let eq_value v1 v2 = match (v1, v2) with
  | VBool b1, VBool b2 -> VBool (b1 = b2)
  | VInt i1, VInt i2 -> VBool (i1 = i2)
  | VFloat f1, VFloat f2 -> VBool (f1 = f2)
  | VStr s1, VStr s2 -> VBool (s1 = s2)
  | _, _ -> failwith (Printf.sprintf "Type mismatch: cannot eq '%s' and '%s'"
                        (string_of_value_safe v1) (string_of_value_safe v2))

(* strictement inférieur (même type seulement) *)
let lt_value v1 v2 = match (v1, v2) with
  | VInt i1, VInt i2 -> VBool (i1 < i2)
  | VFloat f1, VFloat f2 -> VBool (f1 < f2)
  | VStr s1, VStr s2 -> VBool (s1 < s2)
  | _, _ -> failwith (Printf.sprintf "Type mismatch: cannot compare '%s' and '%s'"
                        (string_of_value_safe v1) (string_of_value_safe v2))

(* ---- Évaluateur: prend un environnement gamma ---- *)
(* gamma : (string * value) list *)

let rec eval_expr gamma e =
  match e with
  | ECst v -> v

  | EId x ->
      (try List.assoc x gamma
       with Not_found -> failwith (Printf.sprintf "Unbound identifier '%s'" x))

  | EAdd (e1, e2) ->
      let v1 = eval_expr gamma e1 in
      let v2 = eval_expr gamma e2 in
      add_value v1 v2

  | EMul (e1, e2) ->
      let v1 = eval_expr gamma e1 in
      let v2 = eval_expr gamma e2 in
      mul_value v1 v2

  | ENeg e1 ->
      let v = eval_expr gamma e1 in
      neg_value v

  (* logiques *)
  | EAnd (e1, e2) ->
      let v1 = eval_expr gamma e1 in
      let v2 = eval_expr gamma e2 in
      and_value v1 v2

  | EOr (e1, e2) ->
      let v1 = eval_expr gamma e1 in
      let v2 = eval_expr gamma e2 in
      or_value v1 v2

  | ENot e1 ->
      let v = eval_expr gamma e1 in
      not_value v

  (* comparaisons *)
  | EEq (e1, e2) ->
      let v1 = eval_expr gamma e1 in
      let v2 = eval_expr gamma e2 in
      eq_value v1 v2

  | ENeq (e1, e2) ->
      (* sugar: not (e1 == e2) *)
      let eq = eval_expr gamma (EEq (e1, e2)) in
      (match eq with
       | VBool b -> VBool (not b)
       | _ -> failwith "Internal error: eq did not return bool")

  | ELt (e1, e2) ->
      let v1 = eval_expr gamma e1 in
      let v2 = eval_expr gamma e2 in
      lt_value v1 v2

  | ELe (e1, e2) ->
      (* e1 <= e2  ≡ (e1 < e2) or (e1 == e2) *)
      let left = eval_expr gamma (ELt (e1, e2)) in
      let eq = eval_expr gamma (EEq (e1, e2)) in
      (match left, eq with
       | VBool b1, VBool b2 -> VBool (b1 || b2)
       | _ -> failwith "Internal error: comparison did not return bool")

  | EGt (e1, e2) ->
      (* e1 > e2 ≡ not ((e1 < e2) or (e1 == e2)) *)
      let orv = eval_expr gamma (EOr (ELt (e1, e2), EEq (e1, e2))) in
      (match orv with
       | VBool b -> VBool (not b)
       | _ -> failwith "Internal error: comparison did not return bool")

  | EGe (e1, e2) ->
      (* e1 >= e2 ≡ not (e1 < e2) *)
      let lt = eval_expr gamma (ELt (e1, e2)) in
      (match lt with
       | VBool b -> VBool (not b)
       | _ -> failwith "Internal error: comparison did not return bool")

  (* conditionnelle : n'évalue qu'une des branches *)
  | EIf (ec, e1, e2) ->
      let vc = eval_expr gamma ec in
      (match vc with
       | VBool true -> eval_expr gamma e1
       | VBool false -> eval_expr gamma e2
       | _ -> failwith (Printf.sprintf "Type mismatch: condition '%s' is not boolean" (string_of_expr ec)))

  (* let x = e1 in e2 *)
  | ELet (x, e1, e2) ->
      let v1 = eval_expr gamma e1 in
      let gamma' = (x, v1) :: gamma in
      eval_expr gamma' e2

  (* fonctions et application *)
  | EFun (arg, body) ->
      VClosure (arg, body, gamma)  (* closured with current gamma *)

  | EApp (ef, ea) ->
      let vf = eval_expr gamma ef in
      let va = eval_expr gamma ea in
      (match vf with
       | VClosure (arg, body, env_closure) ->
           let env_call = (arg, va) :: env_closure in
           eval_expr env_call body
       | _ -> failwith (Printf.sprintf "Type mismatch: trying to call a non-function '%s'"
                          (string_of_value_safe vf)))

(* petit moteur de test si besoin (non utilisé par le reste) *)
let eval_top e = eval_expr [] e