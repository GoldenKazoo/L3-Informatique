PRAGMA foreign_keys = ON;

--Create table


CREATE TABLE Medecin (
    adeli CHAR(9) PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    adresse VARCHAR(255) NOT NULL,
    telephone VARCHAR(20) NOT NULL
);

CREATE TABLE Patient (
    nir CHAR(15) PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    adresse VARCHAR(255) NOT NULL,
    telephone VARCHAR(20) NOT NULL,
    sexe CHAR(1) CHECK (sexe IN ('M', 'F')),
    date_naissance DATE NOT NULL
);

CREATE TABLE Specialite (
    nom_specialite VARCHAR(100) PRIMARY KEY
);

CREATE TABLE Specialiste (
    adeli CHAR(9) NOT NULL,
    nom_specialite VARCHAR(100) NOT NULL,
    PRIMARY KEY (adeli, nom_specialite),
    FOREIGN KEY (adeli) REFERENCES Medecin(adeli)
        ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY (nom_specialite) REFERENCES Specialite(nom_specialite)
        ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE Suivi (
    nir CHAR(15) NOT NULL,
    adeli CHAR(9) NOT NULL,
    nom_specialite VARCHAR(100) NOT NULL,
    PRIMARY KEY (nir, adeli, nom_specialite),
    FOREIGN KEY (nir) REFERENCES Patient(nir)
        ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (adeli, nom_specialite) REFERENCES Specialiste(adeli, nom_specialite)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Consultation (
    nir CHAR(15),
    adeli CHAR(9),
    nom_specialite VARCHAR(100),
    date DATETIME,
    descriptif TEXT,
    tarif REAL,
    PRIMARY KEY (nir, adeli, nom_specialite, date),
    FOREIGN KEY (nir, adeli, nom_specialite) REFERENCES Suivi(nir, adeli, nom_specialite)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Intervention (
    nir CHAR(15),
    adeli CHAR(9),
    nom_specialite VARCHAR(100),
    date DATETIME,
    date_fin DATETIME,
    tarif REAL,
    PRIMARY KEY (nir, adeli, nom_specialite, date),
    FOREIGN KEY (nir, adeli, nom_specialite) REFERENCES Suivi(nir, adeli, nom_specialite)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Ordonnance (
    num_ordonnance INTEGER,
    nir CHAR(15),
    adeli CHAR(9),
    nom_specialite VARCHAR(100),
    date DATETIME,
    texte TEXT,
    PRIMARY KEY (num_ordonnance, nir, adeli, nom_specialite, date),
    FOREIGN KEY (nir, adeli, nom_specialite, date) REFERENCES Consultation(nir, adeli, nom_specialite, date)
        ON UPDATE CASCADE ON DELETE CASCADE
);

--Insert des donnees

INSERT INTO Medecin VALUES ('123456789', 'Dubreuil', 'Christian', 'Moulinsart', '0254314159');

INSERT INTO Specialite VALUES ('dermatologie'), ('cardiologie'), ('medecine generale');


INSERT INTO Patient VALUES ('214119912345633', 'Ducitron', 'Clémentine', 'Paris', '0610203040', 'F', '1914-11-25');

/*INSERT INTO Patient (nir, nom, prenom, adresse, date_de_naissance, telephone)
VALUES (214119912345633, 'Polnaref', 'Ducitron', 'Paris', 25/11/1914, 0610203040);
Erreur car on a 2 NIR similaire, du coup pour la selection on se sait pas aupres de qui on doit s'adresser, Clementine ou Polnaref ?*/

INSERT INTO Specialiste VALUES 
('123456789', 'cardiologie'),
('123456789', 'medecine generale');

INSERT INTO Suivi VALUES ('214119912345633', '123456789', 'cardiologie');

-- Create view

CREATE VIEW Acte AS
SELECT nir, adeli, nom_specialite, date, tarif FROM Intervention
UNION ALL
SELECT nir, adeli, nom_specialite, date, tarif FROM Consultation;

CREATE VIEW Personne AS
SELECT
    'med-' || adeli AS id,
    nom, prenom, adresse, telephone
FROM Medecin
UNION ALL
SELECT
    'pat-' || nir AS id,
    nom, prenom, adresse, telephone
FROM Patient;


-- Intervention / Consultation disjoints
CREATE TRIGGER intervention_check_trigger
BEFORE INSERT ON Intervention
FOR EACH ROW
WHEN EXISTS (
    SELECT 1 FROM Consultation
    WHERE nir = NEW.nir AND adeli = NEW.adeli
    AND nom_specialite = NEW.nom_specialite AND date = NEW.date
)
BEGIN
    SELECT RAISE(ABORT, 'intervention_existe_deja');
END;

CREATE TRIGGER consultation_check_trigger
BEFORE INSERT ON Consultation
FOR EACH ROW
WHEN EXISTS (
    SELECT 1 FROM Intervention
    WHERE nir = NEW.nir AND adeli = NEW.adeli
    AND nom_specialite = NEW.nom_specialite AND date = NEW.date
)
BEGIN
    SELECT RAISE(ABORT, 'consultation_existe_deja');
END;

-- Incr auto du num d'ordonnance
CREATE TRIGGER ordonnance_auto_increment
BEFORE INSERT ON Ordonnance
FOR EACH ROW
WHEN NEW.num_ordonnance IS NULL
BEGIN
    SELECT COALESCE(MAX(num_ordonnance), 0) + 1
    INTO NEW.num_ordonnance
    FROM Ordonnance
    WHERE nir = NEW.nir
      AND adeli = NEW.adeli
      AND nom_specialite = NEW.nom_specialite
      AND date = NEW.date;
END;

-- Check au - 1 medecin a une spe
CREATE TRIGGER medecin_check_specialite
AFTER INSERT ON Medecin
FOR EACH ROW
WHEN NOT EXISTS (SELECT 1 FROM Specialiste WHERE adeli = NEW.adeli)
BEGIN
    SELECT RAISE(ABORT, 'medecin_sans_specialite');
END;
