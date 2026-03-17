"use strict";

console.log("hello depuis script.js");

//alert("toto");
// seulement dans un navigateur

let a = 42;
a = "foo";
const b = 42;
//b = "bar";

console.log(12/5); // pas de type int, seulement des numbers
console.log(Math.floor(13/5)); //juste la partie entiere

console.log("true"==1); //false
console.log(true==1); //true donc true = 1
console.log(true==2); //false donc false = autre nb
console.log(true=="true");
console.log(12=="12");
console.log(false==0);
console.log(false=="0");
console.log("0"==false);
// == ne vÃ©rifie pas que les types sont Ã©gaux
// s'ils le sont, alors on vÃ©rifie que les valeurs sont Ã©gales
// sinon, les deux valeurs sont converties en nombres, et on les compare

//"true" -> NaN
// NaN est la constante numÃ©rique signifiant "not a number"
console.log(NaN == NaN) // les NaN sont tous diffÃ©rents

console.log(false==="0");
// === vÃ©rifie que les types sont Ã©gaux

console.log(12-Infinity)
console.log(Infinity-Infinity)
console.log(0*Infinity)

a=Infinity
console.log(a-a)

console.log("string".slice(2,4))
console.log('test')
console.log(`a: ${a}`)

console.log(10<2)
console.log("10"<"2") // sur les strings, < dÃ©signe l'ordre lexicographique

if (3) {
    console.log("3 est vrai");
} else {
    console.log("3 est false");
}

for (let i=0; i<10; i++){
    console.log(i);
}

while(false){

}

let t = [1,2,3,"quatre"];

t.push(5);
console.log(t.pop())

for (let e of t) {
    console.log(e);
}
    
function f (x,y) {
    console.log(`premier argument ${x}, deuxieme argument ${y}`);
}

function g() {
    console.log("g");
}
f(5,6);
f(5,6,g());

f(5);

let h = function() {}

console.log([1,2,3].map( x=>2*x ));
console.log([1,2,3].forEach( x=>console.log(x) ));
console.log([1,2,3].filter( x=>x>1 ));

function somme1(t) {
    let acc = 0;
    for (let e of t) {
	acc = acc+e;
    }
    return acc;
}

let somme2 = t => t.reduce( (acc,e)=>acc+e , 0)
// deuxiÃ¨me argument de reduce : valeur de dÃ©part de l'accumulateur
// premier argument de reduce : fonction indiquant comment combiner la valeur de l'accumulateur et la valeur de l'Ã©lÃ©ment courant du tableau pour calculer la nouvelle valeur de l'accumulateur

console.log(somme2([1,2,3]));

let theo = {
    nom: "ThÃ©o",
    numetu: 123
}

console.log(theo.nom) // nom n'est pas Ã©valuÃ©
console.log(theo["nom"])
let nom = "numetu";
console.log(theo[nom]) // ici, nom est Ã©valuÃ©

console.log(theo);
theo.toString = function () { return `Mon nom est ${this.nom} et mon Ã¢ge est ${this.age} ans`;}
theo.age = 20;

console.log(theo.toString());

theo.toStringBis = () => `Mon nom est ${this.nom} et mon age est ${this.age} ans`

console.log(theo.toStringBis());

// !?
// la suite au prochain Ã©pisode



// ============================================================
// QUESTION 1 - Classes et héritage
// ============================================================

class Champignon {
  constructor(nom, poids, comestible) {
    this.nom = nom;
    this.poids = poids;
    this.comestible = comestible;
  }

  toString() {
    return `${this.nom} (${this.poids}g) - ${this.comestible ? "comestible" : "toxique"}`;
  }
}

class Morille extends Champignon {
  constructor(poids) {
    super("Morille", poids, true);
  }
}

class Amanite extends Champignon {
  constructor(nom, poids, comestible, stries) {
    super(nom, poids, comestible);
    this.stries = stries;
  }

  toString() {
    return super.toString() + ` - stries: ${this.stries}`;
  }
}

class Golmotte extends Amanite {
  constructor(poids) {
    super("Golmotte", poids, true, false);
  }
}

class Panthere extends Amanite {
  constructor(poids) {
    super("Amanite Panthère", poids, false, true);
  }
}

// Tests Q1
const m = new Morille(150);
const g = new Golmotte(200);
const p = new Panthere(180);
console.log(m.toString()); // Morille (150g) - comestible
console.log(g.toString()); // Golmotte (200g) - comestible - stries: false
console.log(p.toString()); // Amanite Panthère (180g) - toxique - stries: true


// ============================================================
// QUESTION 2 - Filtrage du panier (3 versions)
// ============================================================

// Version 1 : boucle for classique
function selection_for(panier) {
  const resultat = [];
  for (let i = 0; i < panier.length; i++) {
    if (panier[i].comestible) resultat.push(panier[i]);
  }
  return resultat;
}

// Version 2 : for..of
function selection_forof(panier) {
  const resultat = [];
  for (const champignon of panier) {
    if (champignon.comestible) resultat.push(champignon);
  }
  return resultat;
}

// Version 3 : filter + lambda (une ligne)
const selection = (panier) => panier.filter(c => c.comestible);

// Tests Q2
const panier = [new Morille(100), new Golmotte(300), new Panthere(200), new Morille(50)];
console.log(selection(panier).map(c => c.nom)); // ['Morille', 'Golmotte', 'Morille']


// ============================================================
// QUESTION 3 - Danger golmottes (2 versions)
// ============================================================

// Version 1 : for..of
function danger_golmottes_forof(panier) {
  let total = 0;
  for (const c of panier) {
    if (c instanceof Golmotte) total += c.poids;
  }
  return total > 1000;
}

// Version 2 : reduce
const danger_golmottes = (panier) =>
  panier.reduce((acc, c) => acc + (c instanceof Golmotte ? c.poids : 0), 0) > 1000;

// Tests Q3
const panier2 = [new Golmotte(600), new Golmotte(500), new Morille(200)];
console.log(danger_golmottes(panier2)); // true (1100g de golmottes)
console.log(danger_golmottes(panier));  // false (300g seulement)


// ============================================================
// QUESTION 4 - Prototypes (sans class)
// ============================================================

// Prototype Champignon
function usine_champignon(nom, poids, comestible) {
  const obj = Object.create(champignon_proto);
  obj.nom = nom;
  obj.poids = poids;
  obj.comestible = comestible;
  return obj;
}
const champignon_proto = {
  toString() {
    return `${this.nom} (${this.poids}g) - ${this.comestible ? "comestible" : "toxique"}`;
  }
};

// Prototype Morille
const morille_proto = Object.create(champignon_proto);
function usine_morille(poids) {
  const obj = Object.create(morille_proto);
  obj.nom = "Morille";
  obj.poids = poids;
  obj.comestible = true;
  return obj;
}

// Prototype Amanite
const amanite_proto = Object.create(champignon_proto);
amanite_proto.toString = function() {
  return champignon_proto.toString.call(this) + ` - stries: ${this.stries}`;
};
function usine_amanite(nom, poids, comestible, stries) {
  const obj = Object.create(amanite_proto);
  obj.nom = nom;
  obj.poids = poids;
  obj.comestible = comestible;
  obj.stries = stries;
  return obj;
}

// Prototype Golmotte
const golmotte_proto = Object.create(amanite_proto);
function usine_golmotte(poids) {
  const obj = Object.create(golmotte_proto);
  obj.nom = "Golmotte";
  obj.poids = poids;
  obj.comestible = true;
  obj.stries = false;
  return obj;
}

// Prototype Panthère
const panthere_proto = Object.create(amanite_proto);
function usine_panthere(poids) {
  const obj = Object.create(panthere_proto);
  obj.nom = "Amanite Panthère";
  obj.poids = poids;
  obj.comestible = false;
  obj.stries = true;
  return obj;
}

// Tests Q4
const m2 = usine_morille(150);
const g2 = usine_golmotte(200);
const p2 = usine_panthere(180);
console.log(m2.toString()); // Morille (150g) - comestible
console.log(g2.toString()); // Golmotte (200g) - comestible - stries: false
console.log(p2.toString()); // Amanite Panthère (180g) - toxique - stries: true