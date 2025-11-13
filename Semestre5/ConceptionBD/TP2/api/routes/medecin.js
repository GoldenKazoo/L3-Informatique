const express = require('express');
const db = require('../db/db.js');
const domains = require('./domains');

// Création du router
const router = express.Router();

// Configuration du router
router.get("/", (req, res) => {
  try {
    const ressources = db.prepare("SELECT adeli FROM Medecin").all();
    const links = ressources.map(r => `${req.originalUrl}${encodeURIComponent(r.adeli)}`);
    res.status(200).json(links);
  } catch (err) {
    console.error("SQL Error: ", err);
    res.status(500).send();
  }
});

router.get("/:adeli", (req, res) => {
  const { adeli } = req.params;
  try {
    const ressource = db.prepare("SELECT adeli, nom, prenom, adresse, telephone FROM Medecin WHERE adeli = ?").get(adeli);
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

router.delete("/:adeli", (req, res) => {
  const { adeli } = req.params;
  try {
    const result = db.prepare("DELETE FROM Medecin WHERE adeli = ?").run(adeli);
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
    adeli = null,
    nom = null,
    prenom = null,
    adresse = null,
    telephone = null,
  } = req.body || {};
  if (!adeli || !domains.isValidADELI(adeli)) {
    return res.status(412).send();
  };
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
      INSERT INTO Medecin (adeli, nom, prenom, adresse, telephone)
      VALUES (?, ?, ?, ?, ?)
    `).run(adeli, nom, prenom, adresse, telephone);
    res.location(`${req.baseUrl}/${encodeURIComponent(adeli)}`);
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

router.put("/:adeli", (req, res) => {
  const { adeli } = req.params;
  const {
    adeli: adeli_ = null,
    nom = null,
    prenom = null,
    adresse = null,
    telephone = null,
  } = req.body || {};
  if (adeli_ && !domains.isValidADELI(adeli_)) {
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
        SELECT * FROM Medecin WHERE adeli = ?
      `).get(adeli);
      if (!exists) return false;
      const result = db.prepare(`
        UPDATE Medecin
        SET adeli = COALESCE(?, adeli),
            nom = COALESCE(?, nom),
            prenom = COALESCE(?, prenom),
            adresse = COALESCE(?, adresse),
            telephone = COALESCE(?, telephone)
        WHERE adeli = ?
      `).run(adeli_, nom, prenom, adresse, telephone, adeli);
      return true;
    })();
    if (!updated) {
      console.error(`SQL Error: Unknown ressource ${req.originalUrl}`);
      return res.status(404).send();
    }
    if (adeli_ && adeli !== adeli_) {
      res.location(`${req.baseUrl}/${encodeURIComponent(adeli_)}`);
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

