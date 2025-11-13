const express = require('express');
const db = require('../db/db.js');
const domains = require('./domains');

// Création du router
const router = express.Router();

// Configuration du router
router.get("/", (req, res) => {
  try {
    const ressources = db.prepare("SELECT nir FROM Patient").all();
    const links = ressources.map(r => `${req.originalUrl}${encodeURIComponent(r.nir)}`);
    res.status(200).json(links);
  } catch (err) {
    console.error("SQL Error: ", err);
    res.status(500).send();
  }
});

router.get("/:nir", (req, res) => {
  const { nir } = req.params;
  try {
    const ressource = db.prepare("SELECT nir, nom, prenom, adresse, telephone, date_naissance, sexe FROM Patient WHERE nir = ?").get(nir);
    if (!ressource) {
      console.error(`SQL Error: Unknown ressource ${req.originalUrl}`);
      return res.status(404).send();
    }
    ressource._links = {
      self: { href: `${req.originalUrl}` },
    };
    res.status(200).json(ressource);
  } catch (err) {
    console.error("SQL Error: ", err);
    res.status(500).send();
  }
});

router.delete("/:nir", (req, res) => {
  const { nir } = req.params;
  try {
    const result = db.prepare("DELETE FROM Patient WHERE nir = ?").run(nir);
    if (result.changes === 0) {
      console.error(`SQL Error: Unknown ressource ${req.originalUrl}`);
      return res.status(404).send();
    }
    res.status(204).send();
  } catch (err) {
    console.error("SQL Error:", err);
    res.status(500).send();
  }
});

router.post("/", (req, res) => {
  const {
    nir = null,
    nom = null,
    prenom = null,
    adresse = null,
    telephone = null,
    date_naissance = null,
    sexe = null
  } = req.body || {};
  if (!nir || !domains.isValidNIR(nir)) {
    return res.status(412).send();
  };
  if (!sexe || !domains.isValidSex(sexe)) {
    return res.status(412).send();
  }
  if (!date_naissance || !domains.isValidDate(date_naissance)) {
    return res.status(412).send();
  }
  if (!telephone || !domains.isValidPhone(telephone)) {
    return res.status(412).send();
  }
  if (!nom || !domains.isNotEmptyText(nom)) {
    return res.status(412).send();
  }
  if (!prenom || !domains.isNotEmptyText(prenom)) {
    return res.status(412).send();
  }
  if (!adresse || !domains.isNotEmptyText(adresse)) {
    return res.status(412).send();
  }
  try {
    db.prepare(`
      INSERT INTO Patient (nir, nom, prenom, adresse, telephone, date_naissance, sexe)
      VALUES (?, ?, ?, ?, ?, ?, ?)
    `).run(nir, nom, prenom, adresse, telephone, date_naissance, sexe);
    res.location(`${req.baseUrl}/${encodeURIComponent(nir)}`);
    res.status(201).send();
  } catch (err) {
    console.error("SQL Error:", err);
    switch (err.code) {
      case 'SQLITE_CONSTRAINT_PRIMARYKEY':
      case 'SQLITE_CONSTRAINT_FOREIGNKEY':
      case 'SQLITE_CONSTRAINT_DATATYPE':
      case 'SQLITE_CONSTRAINT_CHECK':
      case 'SQLITE_CONSTRAINT_NOTNULL':
      case 'SQLITE_CONSTRAINT_TRIGGER':
      case 'SQLITE_CONSTRAINT_UNIQUE':
        return res.status(409).send();
      default:
        return res.status(500).send();
    }
  }
});

router.put("/:nir", (req, res) => {
  const { nir } = req.params;
  const {
    nir: nir_ = null,
    nom = null,
    prenom = null,
    adresse = null,
    telephone = null,
    date_naissance = null,
    sexe = null
  } = req.body || {};
  if (nir_ && !domains.isValidNIR(nir_)) {
    return res.status(412).send();
  }
  if (sexe && !domains.isValidSex(sexe)) {
    return res.status(412).send();
  }
  if (date_naissance && !domains.isValidDate(date_naissance)) {
    return res.status(412).send();
  }
  if (telephone && !domains.isValidPhone(telephone)) {
    return res.status(412).send();
  }
  if (nom && !domains.isNotEmptyText(nom)) {
    return res.status(412).send();
  }
  if (prenom && !domains.isNotEmptyText(prenom)) {
    return res.status(412).send();
  }
  if (adresse && !domains.isNotEmptyText(adresse)) {
    return res.status(412).send();
  }
  try {
    const updated = db.transaction(() => {
      const exists = db.prepare(`
        SELECT * FROM Patient WHERE nir = ?
      `).get(nir);
      if (!exists) return false;
      const result = db.prepare(`
        UPDATE Patient
        SET nir = COALESCE(?, nir),
            nom = COALESCE(?, nom),
            prenom = COALESCE(?, prenom),
            adresse = COALESCE(?, adresse),
            telephone = COALESCE(?, telephone),
            date_naissance = COALESCE(?, date_naissance),
            sexe = COALESCE(?, sexe)
        WHERE nir = ?
      `).run(nir_, nom, prenom, adresse, telephone, date_naissance, sexe, nir);
      return true;
    })();
    if (!updated) {
      console.error(`SQL Error: Unknown ressource ${req.originalUrl}`);
      return res.status(404).send();
    }
    if (nir_ && nir !== nir_) {
      res.location(`${req.baseUrl}/${encodeURIComponent(nir_)}`);
    } 
    return res.status(204).send();
  } catch (err) {
    console.error("SQL Error:", err);
    switch (err.code) {
      case 'SQLITE_CONSTRAINT_PRIMARYKEY':
      case 'SQLITE_CONSTRAINT_FOREIGNKEY':
      case 'SQLITE_CONSTRAINT_DATATYPE':
      case 'SQLITE_CONSTRAINT_CHECK':
      case 'SQLITE_CONSTRAINT_NOTNULL':
      case 'SQLITE_CONSTRAINT_TRIGGER':
      case 'SQLITE_CONSTRAINT_UNIQUE':
        return res.status(409).send();
      default:
        return res.status(500).send();
    }
  }
});

// Export du router
module.exports = router;

