class Champignon
{
    constructor(nom, poids, comestibles)
    {
        this.nom = nom;
        this.poids = poids;
        this.comestibles = comestibles;
    }

    toString()
    {
        return `Nom du champignon : ${this.nom}  || Il pese ${this.poids} || Est-il comestibles ? :  ${this.comestibles}`
    }
}

class Morille extends Champignon
{
    constructor(poids)
    {
        super("morille", poids, true);
    }
}

class Amanites extends Champignon
{
    constructor(nom, poids, comestibles, stries)
    {
        super(nom, poids, comestibles);
        this.stries = stries;
    }
    toString()
    {
        return super.toString() + `Possede-t-il des stries ? : ${this.stries}`
    }
}

class Golmotte extends Amanites
{
    constructor(poids)
    {
        super("golmotte", poids, true, false);
    }
}

class Panthere extends Amanites
{
    constructor(poids)
    {
        super("panthere", poids, false, true);
    }
}

function selectionFor(panier)
{
    let resultat = [];

    for(let i = 0; i < panier.length; i++)
    {
        if (panier[i].comestibles == true)
        {
            resultat.push(panier[i]);
        }
    }
    return resultat
}

function selectionForOf(panier)
{
    let resultat = [];

    for (champi of panier)
    {
        if (champi.comestibles == true)
        {
            resultat.push(champi);
        }
    }
    return resultat
}

let selection = (panier) => panier.filter(champi => champi.comestibles);

function danger_golmottes(panier)
{
    let max_poids = 0;
    for(champi in panier)
    {
        if (champi.nom == "golmotte")
        {
            max_poids += champi.poids;
        }
    }
    if (max_poids >= 1000)
    {
        return true
    }
    return false
}

// let danger_golmottes = (panier) => panier.reduce((acc, champi) => acc + (champi instanceof Golmotte ? champi.poids : 0));

let protoad = 
{
    toString()
    {
       return `Nom du champignon : ${this.nom}  || Il pese ${this.poids} || Est-il comestibles ? :  ${this.comestibles}` 
    }
}

// les toads
function usine_toad(nom, poids, comestibles, stries)
{
    let obj = Object.create(protoad);
    obj.nom = nom;
    obj.poids = poids;
    obj.comestibles = comestibles;
    return obj;
}

const morille_proto = Object.create(protoad);
function usine_morille(poids) {
  const obj = Object.create(morille_proto);
  obj.nom = "Morille";
  obj.poids = poids;
  obj.comestibles = true;
  return obj;
}

// amanit
const amanite_protoad = Object.create(protoad);
amanite_protoad.toString = function()
{
  return protoad.toString.call(this) + ` || stries: ${this.stries}`;
};
function usine_amanite(nom, poids, comestibles, stries)
{
  const obj = Object.create(amanite_protoad);
  obj.nom = nom;
  obj.poids = poids;
  obj.comestibles = comestibles;
  obj.stries = stries;
  return obj;
}

// golmotte
const golmotte_protoad = Object.create(amanite_protoad);

function usine_golmotte(poids)
{
  const obj = Object.create(golmotte_protoad);
  obj.nom = "Golmotte";
  obj.poids = poids;
  obj.comestibles = true;
  obj.stries = false;
  return obj;
}

// panthere
const panthere_protoad = Object.create(amanite_protoad);

function usine_panthere(poids)
{
  const obj = Object.create(panthere_protoad);
  obj.nom = "Amanite Panthère";
  obj.poids = poids;
  obj.comestibles = false;
  obj.stries = true;
  return obj;
}

//Testo
let m = new Morille(150);
let g = new Golmotte(200);
let p = new Panthere(180);
console.log(m.toString());
console.log(g.toString());
console.log(p.toString());

let up = usine_panthere(500);
let ug = usine_golmotte(300);
console.log(up.toString());
console.log(ug.toString());
