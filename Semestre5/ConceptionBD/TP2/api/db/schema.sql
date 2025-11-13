PRAGMA foreign_keys = ON;
PRAGMA recursive_triggers = ON;

-- -----------------------------
-- Table Médecin
-- -----------------------------
CREATE TABLE Medecin (
    adeli CHAR(9) NOT NULL,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    adresse VARCHAR(255) NOT NULL,
    telephone VARCHAR(20) NOT NULL,
    PRIMARY KEY (adeli)
);

-- -----------------------------
-- Table Patient
-- -----------------------------
CREATE TABLE Patient (
    nir CHAR(15) NOT NULL,
    date_naissance DATE NOT NULL,
    sexe CHAR(1) NOT NULL CHECK(sexe IN ('M','F')),
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    adresse VARCHAR(255) NOT NULL,
    telephone VARCHAR(20) NOT NULL,
    PRIMARY KEY (nir)
);

-- -----------------------------
-- Vue Personne
-- -----------------------------
CREATE VIEW Personne(id, nom, prenom, adresse, telephone) AS
    SELECT 
        CONCAT('med-', adeli) AS id,
        nom,
        prenom,
        adresse,
        telephone
    FROM Medecin
    UNION ALL
    SELECT
        CONCAT('pat-', nir) AS id,
        nom,
        prenom,
        adresse,
        telephone
    FROM Patient;

CREATE TRIGGER update_personne_patient_trigger
INSTEAD OF UPDATE ON Personne
WHEN NEW.id LIKE 'pat-%'
BEGIN
    UPDATE Patient SET 
        nom = NEW.nom,
        prenom = NEW.prenom,
        adresse = NEW.adresse,
        telephone = NEW.telephone
    WHERE nir = SUBSTR(OLD.id, 5);
END;

CREATE TRIGGER update_personne_medecin_trigger
INSTEAD OF UPDATE ON Personne
WHEN NEW.id LIKE 'med-%'
BEGIN
    UPDATE Medecin SET 
        nom = NEW.nom,
        prenom = NEW.prenom,
        adresse = NEW.adresse,
        telephone = NEW.telephone
    WHERE adeli = SUBSTR(OLD.id, 5);
END;

CREATE TRIGGER delete_personne_patient_trigger
INSTEAD OF DELETE ON Personne
WHEN OLD.id LIKE 'pat-%'
BEGIN
    DELETE FROM Patient 
    WHERE nir = SUBSTR(OLD.id, 5);
END;

CREATE TRIGGER delete_personne_medecin_trigger
INSTEAD OF DELETE ON Personne
WHEN OLD.id LIKE 'med-%'
BEGIN
    DELETE FROM Medecin
    WHERE adeli = SUBSTR(OLD.id, 5);
END;



-- -----------------------------
-- Table Spécialité
-- -----------------------------
CREATE TABLE Specialite (
    nom_specialite VARCHAR(100) NOT NULL,
    PRIMARY KEY (nom_specialite)
);

-- -----------------------------
-- Médecin Spécialisé
-- -----------------------------
CREATE TABLE Specialiste (
    adeli CHAR(9) NOT NULL,
    nom_specialite VARCHAR(100) NOT NULL,
    PRIMARY KEY (adeli, nom_specialite),
    CONSTRAINT fk_specialiste_medecin
        FOREIGN KEY (adeli) REFERENCES Medecin(adeli)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT fk_specialiste_specialite
        FOREIGN KEY (nom_specialite) REFERENCES Specialite(nom_specialite)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

-- -----------------------------
-- Suivi
-- -----------------------------
CREATE TABLE Suivi (
    nir CHAR(15) NOT NULL,
    adeli CHAR(9) NOT NULL,
    nom_specialite VARCHAR(100) NOT NULL,
    PRIMARY KEY (nir, adeli, nom_specialite),
    CONSTRAINT fk_suivi_specialiste
        FOREIGN KEY (adeli, nom_specialite) REFERENCES Specialiste(adeli, nom_specialite)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    CONSTRAINT fk_suivi_patient
        FOREIGN KEY (nir) REFERENCES Patient(nir)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);


-- -----------------------------
-- Intervention
-- -----------------------------
CREATE TABLE Intervention (
    nir CHAR(15) NOT NULL,
    adeli CHAR(9) NOT NULL,
    nom_specialite VARCHAR(100) NOT NULL,
    date TIMESTAMP NOT NULL,
    date_fin TIMESTAMP NOT NULL CHECK(date < date_fin),
    tarif DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (nir, adeli, nom_specialite, date),
    CONSTRAINT fk_intervention_suivi
        FOREIGN KEY (nir, adeli, nom_specialite) REFERENCES Suivi(nir, adeli, nom_specialite)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

-- -----------------------------
-- Consultation
-- -----------------------------
CREATE TABLE Consultation (
    nir CHAR(15) NOT NULL,
    adeli CHAR(9) NOT NULL,
    nom_specialite VARCHAR(100) NOT NULL,
    date TIMESTAMP NOT NULL,
    descriptif VARCHAR(255) NOT NULL,
    tarif DECIMAL(10,2) NOT NULL,
    last_ordonnance_id INT NOT NULL DEFAULT 0,
    PRIMARY KEY (nir, adeli, nom_specialite, date),
    CONSTRAINT fk_consultation_suivi
        FOREIGN KEY (nir, adeli, nom_specialite) REFERENCES Suivi(nir, adeli, nom_specialite)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

-- -- -----------------------------
-- -- Acte
-- -- -----------------------------
CREATE VIEW Acte(nir, adeli, nom_specialite, date, tarif) AS 
    SELECT nir, adeli, nom_specialite, date, tarif FROM Intervention
    UNION ALL
    SELECT nir, adeli, nom_specialite, date, tarif FROM Consultation
;

CREATE TRIGGER intervention_insert_check_trigger
BEFORE INSERT ON Intervention
FOR EACH ROW
WHEN EXISTS (
    SELECT 1 FROM Acte a
    WHERE a.nir = NEW.nir AND a.adeli = NEW.adeli AND a.nom_specialite = NEW.nom_specialite AND a.date = NEW.date
)
BEGIN
    SELECT RAISE(ABORT, 'intervention_insert_check_trigger');
END;

CREATE TRIGGER intervention_update_check_trigger
BEFORE UPDATE ON Intervention
FOR EACH ROW
WHEN EXISTS (
    SELECT 1 FROM Acte a
    WHERE OLD.date <> NEW.date
      AND a.nir = NEW.nir AND a.adeli = NEW.adeli AND a.nom_specialite = NEW.nom_specialite AND a.date = NEW.date
)
BEGIN
    SELECT RAISE(ABORT, 'intervention_update_check_trigger');
END;

CREATE TRIGGER consultation_insert_check_trigger
BEFORE INSERT ON Consultation
FOR EACH ROW
WHEN EXISTS (
    SELECT 1 FROM Acte a
    WHERE a.nir = NEW.nir AND a.adeli = NEW.adeli AND a.nom_specialite = NEW.nom_specialite AND a.date = NEW.date
)
BEGIN
    SELECT RAISE(ABORT, 'consultation_insert_check_trigger');
END;

CREATE TRIGGER consultation_update_check_trigger
BEFORE UPDATE ON Consultation
FOR EACH ROW
WHEN EXISTS (
    SELECT 1 FROM Acte a
    WHERE OLD.date <> NEW.date
      AND a.nir = NEW.nir AND a.adeli = NEW.adeli AND a.nom_specialite = NEW.nom_specialite AND a.date = NEW.date
)
BEGIN
    SELECT RAISE(ABORT, 'consultation_update_check_trigger');
END;

CREATE TRIGGER update_acte_trigger
INSTEAD OF UPDATE ON Acte
FOR EACH ROW
BEGIN
    UPDATE Consultation SET 
        tarif = NEW.tarif,
        date = NEW.date
    WHERE nir = OLD.nir
      AND adeli = OLD.adeli
      AND nom_specialite = OLD.nom_specialite
      AND date = OLD.date;
    UPDATE Intervention SET 
        tarif = NEW.tarif,
        date = NEW.date
    WHERE nir = OLD.nir
      AND adeli = OLD.adeli
      AND nom_specialite = OLD.nom_specialite
      AND date = OLD.date;
END;

CREATE TRIGGER delete_acte_trigger
INSTEAD OF DELETE ON Acte
FOR EACH ROW
BEGIN
    DELETE FROM Consultation
    WHERE nir = OLD.nir
      AND adeli = OLD.adeli
      AND nom_specialite = OLD.nom_specialite
      AND date = OLD.date;
    DELETE FROM Intervention
    WHERE nir = OLD.nir
      AND adeli = OLD.adeli
      AND nom_specialite = OLD.nom_specialite
      AND date = OLD.date;
END;

-- -----------------------------
-- Ordonnance
-- -----------------------------
CREATE TABLE Ordonnance (
    nir CHAR(15) NOT NULL,
    adeli CHAR(9) NOT NULL,
    nom_specialite VARCHAR(100) NOT NULL,
    date TIMESTAMP NOT NULL,
    num_ordonnance INT DEFAULT NULL,
    text VARCHAR(255) NOT NULL,
    PRIMARY KEY (nir, adeli, nom_specialite, date, num_ordonnance),
    CONSTRAINT fk_ordonnance_consultation
        FOREIGN KEY (nir, adeli, nom_specialite, date) REFERENCES Consultation(nir, adeli, nom_specialite, date)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TRIGGER ordonnance_numero_trigger
AFTER INSERT ON Ordonnance
FOR EACH ROW
BEGIN
    UPDATE Consultation SET last_ordonnance_id = (
        SELECT last_ordonnance_id + 1 FROM Consultation
        WHERE nir = NEW.nir
        AND   adeli = NEW.adeli
        AND   nom_specialite = NEW.nom_specialite
        AND   date = NEW.date
    )   WHERE nir = NEW.nir
        AND   adeli = NEW.adeli
        AND   nom_specialite = NEW.nom_specialite
        AND   date = NEW.date;
    UPDATE Ordonnance SET num_ordonnance = (
        SELECT last_ordonnance_id FROM Consultation
        WHERE nir = NEW.nir
        AND   adeli = NEW.adeli
        AND   nom_specialite = NEW.nom_specialite
        AND   date = NEW.date
    )   WHERE nir = NEW.nir
        AND   adeli = NEW.adeli
        AND   nom_specialite = NEW.nom_specialite
        AND   date = NEW.date
        AND   num_ordonnance IS NULL;
END;



INSERT INTO Patient(nir, nom, prenom, adresse, telephone, date_naissance, sexe)
VALUES
    ('284056789012345', 'Martin', 'Sophie', '12 rue des Lilas, 75014 Paris', '0612345678', '1984-05-14', 'F'),
    ('196078901234567', 'Dupont', 'Jean', '8 avenue de la République, 69003 Lyon', '0623456789', '1960-07-23', 'M'),
    ('200112345678901 ', 'Nguyen', 'Lucas', '5 rue des Acacias, 31000 Toulouse', '0634567890', '2001-01-05', 'M');

UPDATE Personne SET nom = 'Dupond' WHERE id = 'pat-196078901234567';

INSERT INTO Medecin (adeli, nom, prenom, adresse, telephone)
VALUES
    ('123456789', 'Durand', 'Claire', '14 rue Victor Hugo, 75015 Paris', '0145236789'),
    ('987654321', 'Bernard', 'Thomas', '22 avenue Jean Jaurès, 69007 Lyon', '0478123456'),
    ('456789123', 'Moreau', 'Isabelle', '5 place du Capitole, 31000 Toulouse', '0561782345');

INSERT INTO Specialite(nom_specialite)
VALUES
    ('cardiologie'),
    ('radiologie'),
    ('médecine générale'),
    ('dermatologie'),
    ('pédiatrie'),
    ('gynécologie-obstétrique');

INSERT INTO Specialiste(adeli, nom_specialite)
VALUES
    ('123456789', 'cardiologie'),
    ('123456789', 'pédiatrie'),
    ('987654321', 'gynécologie-obstétrique');

INSERT INTO Suivi(nir, adeli, nom_specialite)
VALUES
    ('284056789012345', '123456789', 'cardiologie');

INSERT INTO Intervention(nir, adeli, nom_specialite, date, date_fin, tarif)
VALUES
    ('284056789012345', '123456789', 'cardiologie', '2025-11-01 15:30:00', '2025-11-02 10:30:00', '400.00');


INSERT INTO Consultation(nir, adeli, nom_specialite, date, descriptif, tarif)
VALUES
    ('284056789012345', '123456789', 'cardiologie', '2025-10-27 16:40:00', 'Résultats d''analyse', '80.00'),
    ('284056789012345', '123456789', 'cardiologie', '2025-12-09 11:10:00', 'Bilan post-op', '70.00');

INSERT INTO Ordonnance(nir, adeli, nom_specialite, date, text)
VALUES
    ('284056789012345', '123456789', 'cardiologie', '2025-12-09 11:10:00', 'Echographie'),
    ('284056789012345', '123456789', 'cardiologie', '2025-12-09 11:10:00', 'Aspirine');

UPDATE Acte SET tarif = '80.00', date = '2025-12-19 11:10:00' WHERE nir = '284056789012345' AND adeli = '123456789' AND nom_specialite = 'cardiologie' AND date = '2025-12-09 11:10:00';
