let timer = setTimeout(() => _, 2000);

function creerTaupe(image)
{
    let taupe = document.createElement("img");
    document.body.appendChild(taupe);
    taupe.src = image;
    taupe.style.position = "absolute";
    
    let taille = Math.min(window.innerWidth, window.innerHeight) / 8;
    taupe.style.width = taille + "px";
    taupe.style.height = taille + "px";

    return taupe;
}

function placerTaupe(taupe)
{
  let taille = Math.min(window.innerWidth, window.innerHeight) / 8

  let maxLeft = window.innerWidth  - taille;
  let maxTop  = window.innerHeight - taille;

  taupe.style.left = Math.random() * maxLeft + "px";
  taupe.style.top  = Math.random() * maxTop  + "px";
}

function chevauchement(t1, t2)
{
  let hitbox_taupe_1 = t1.getBoundingClientRect();
  let hitbox_taupe_2 = t2.getBoundingClientRect();
  return !(hitbox_taupe_1.right  < hitbox_taupe_2.left  || hitbox_taupe_2.right  < hitbox_taupe_1.left  || hitbox_taupe_1.bottom < hitbox_taupe_2.top   || hitbox_taupe_2.bottom < hitbox_taupe_1.top);
}

function placerSansChevaucher(taupe, tab_taupe)
{
  placerTaupe(taupe);
  let i = 0;
  while (i < tab_taupe.length) {
    if (chevauchement(taupe, tab_taupe[i]))
    {
      placerTaupe(taupe);
      i = 0;
    }
    else
    {
      i++;
    }
  }
}

let bonne_taupe;
let mauvaises_taupes = [];

function clicBonneTaupe()
{
    placerSansChevaucher(bonne_taupe, []);
    let placees = [bonne_taupe];
    for (let t of mauvaises_taupes)
    {
        placerSansChevaucher(t, placees);
        placees.push(t);
    }

    let mauvaise_taupe = creerTaupe("mauvaise_taupe.png");
    placerSansChevaucher(mauvaise_taupe, placees);
    mauvaises_taupes.push(mauvaise_taupe);
    mauvaise_taupe.addEventListener("click", clicMauvaiseTaupe);
    horloge();
}

function clicMauvaiseTaupe()
{
    mauvaises_taupes.forEach(t => t.remove());
    mauvaises_taupes = [];
    placerSansChevaucher(bonne_taupe, []);
}

function horloge()
{
    clearTimeout(timer);
    timer = setTimeout(reset, 2000);
}

function reset()
{
    mauvaises_taupes.forEach(t => t.remove()); //on remove l'element html 
    mauvaises_taupes = [];
    placerSansChevaucher(bonne_taupe, []);
}


bonne_taupe = creerTaupe("bonne_taupe.png");
placerTaupe(bonne_taupe);
bonne_taupe.addEventListener("click", clicBonneTaupe);
