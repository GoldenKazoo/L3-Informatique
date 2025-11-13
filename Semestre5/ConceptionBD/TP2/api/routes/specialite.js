const express = require('express');
const db = require('../db/db.js');
const domains = require('./domains');

// Création du router
const router = express.Router();

// Configuration du router
router.get("/", (req, res) => {
  try {
    const ressources = db.prepare("SELECT nom_specialite FROM Specialite").all();
    const links = ressources.map(r => `${req.originalUrl}${encodeURIComponent(r.nom_specialite)}`);
    res.status(200).json(links);
  } catch (err) {
    console.error("SQL Error: ", err);
    res.status(500).send();
  }
});

router.get("/:nom_specialite", (req, res) => {
  const { nom_specialite } = req.params;
  try {
    const ressource = db.prepare("SELECT nom_specialite FROM Specialite WHERE nom_specialite = ?").get(nom_specialite);
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

router.delete("/:nom_specialite", (req, res) => {
  const { nom_specialite } = req.params;
  try {
    const result = db.prepare("DELETE FROM Specialite WHERE nom_specialite = ?").run(nom_specialite);
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
    nom_specialite = null,
  } = req.body || {};
  if (!nom_specialite || !domains.isNotEmptyText(nom_specialite)) {
    return res.status(412).send();
  }
  try {
    db.prepare(`
      INSERT INTO Specialite (nom_specialite)
      VALUES (?)
    `).run(nom_specialite);
    res.location(`${req.baseUrl}/${encodeURIComponent(nom_specialite)}`);
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

router.put("/:nom_specialite", (req, res) => {
  const { nom_specialite } = req.params;
  const {
    nom_specialite: nom_specialite_ = null,
  } = req.body || {};
  if (nom_specialite_ && !domains.isNotEmptyText(nom_specialite_)) {
    return res.status(412).send();
  }
  try {
    const updated = db.transaction(() => {
      const exists = db.prepare(`
        SELECT * FROM Specialite WHERE nom_specialite = ?
      `).get(nom_specialite);
      if (!exists) return false;
      const result = db.prepare(`
        UPDATE Specialite
        SET nom_specialite = COALESCE(?, nom_specialite)
        WHERE nom_specialite = ?
      `).run(nom_specialite_, nom_specialite);
      return true;
    })();
    if (!updated) {
      console.error(`SQL Error: Unknown ressource ${req.originalUrl}`);
      return res.status(404).send();
    }
    if (nom_specialite_ && nom_specialite !== nom_specialite_) {
      res.location(`${req.baseUrl}/${encodeURIComponent(nom_specialite_)}`);
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

