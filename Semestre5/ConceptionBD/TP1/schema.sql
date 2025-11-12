PRAGMA foreign_keys = ON;

CREATE TABLE Medecin
(
adeli CHAR(9) NOT NULL,
nom VARCHAR(100) NOT NULL,
prenom VARCHAR(100) NOT NULL,
adresse VARCHAR(255) NOT NULL,
telephone VARCHAR(20) NOT NULL,
PRIMARY KEY (adeli)
);

CREATE TABLE Patient
(
    nir         CHAR (9) NOT NULL,
    nom         VARCHAR(100) NOT NULL,
    prenom      VARCHAR(100) NOT NULL,
    adresse     VARCHAR(255) NOT NULL,
    telephone   VARCHAR(20) NOT NULL,
    sexe        CHAR(1) CHECK (sexe in ('F' , 'M')),
    date_de_naissance DATE(1) NOT NULL,
    PRIMARY KEY (nir)
);

CREATE TABLE Specialite
(
    nom_specialite VARCHAR(50),
    PRIMARY KEY (nom_specialite)
);

CREATE TABLE Specialiste
(
    adeli CHAR(9) NOT NULL,
    nom_specialite VARCHAR(50),
    FOREIGN KEY (adeli) REFERENCES Medecin(adeli)
    FOREIGN KEY (nom_specialite) REFERENCES Specialite(nom_specialite)
);

CREATE TABLE Intervention
(
    adeli       CHAR(9) NOT NULL,
    nom_specialite VARCHAR(50),
    nir         CHAR (9) NOT NULL,
    date        DATE(1) NOT NULL,
    date_fin    DATE(1) NOT NULL,
    tarif       INT(100) NOT NULL,
    FOREIGN KEY (adeli, nom_specialite, nir) REFERENCES Suivi(adeli, nom_specialite, nir),
    PRIMARY KEY (date)
);

CREATE TABLE Consultation
(
    adeli           CHAR(9) NOT NULL,
    nom_specialite  VARCHAR(50),
    nir             CHAR (9) NOT NULL,
    date            DATE(1) NOT NULL,
    descriptif      VARCHAR(10000) NOT NULL,
    tarif           INT(100) NOT NULL,
    FOREIGN KEY (adeli, nom_specialite, nir) REFERENCES Suivi (adeli, nom_specialite, nir)
    PRIMARY KEY (date)
);

CREATE TABLE Ordonnance
(
    adeli               CHAR(9) NOT NULL,
    nom_specialite      VARCHAR(50),
    nir                 CHAR (9) NOT NULL,
    date                DATE(1) NOT NULL,
    num_ordonnance      VARCHAR(10000) NOT NULL,
    texte               VARCHAR(100000000) NOT NULL,
    FOREIGN KEY (adeli, nom_specialite, nir, date) REFERENCES Consultation (adeli, nom_specialite, nir, date),
    PRIMARY KEY (num_ordonnance)
);

CREATE TABLE Suivi
(
    adeli       CHAR(9) NOT NULL,
    nom_specialite VARCHAR(50),
    nir         CHAR (9) NOT NULL,
    FOREIGN KEY (nir) REFERENCES Patient (nir),
    PRIMARY KEY (adeli, nom_specialite)
);

INSERT INTO Medecin (adeli, nom, prenom, adresse, telephone)
VALUES
('123456789','Dubreuil','Christian','Moulinsart',0254314159);

INSERT INTO Patient (nir, nom, prenom, adresse, date_de_naissance, telephone)
VALUES (214119912345633, 'Clémentine', 'Ducitron', 'Paris', 25/11/1914, 0610203040);

/*INSERT INTO Patient (nir, nom, prenom, adresse, date_de_naissance, telephone)
VALUES (214119912345633, 'Polnaref', 'Ducitron', 'Paris', 25/11/1914, 0610203040);
Erreur car on a 2 NIR similaire, du coup pour la selection on se sait pas aupres de qui on doit s'adresser, Clementine ou Polnaref ?*/

SELECT * FROM Medecin;