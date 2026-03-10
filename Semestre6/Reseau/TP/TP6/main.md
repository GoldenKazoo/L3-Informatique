Combien de séquences UDP sont envoyées ?

1 car UDP n'effectue pas de segmentation, tampis si des sequences sont perdus
On peut calculer la taille des donnees UDP total en prenant le nombre d'octets a envoye + l'en tete UDP
taille UDP = 2000 + 8 = 2008 octets

Combien de paquet IP décrivez le contenu de chaque paquet ?

On determine d'abord si IP doit fragmenter, pour ca on compare (taille UDP + en-tete IP) avec MTU:
taille UDP + en-tete IP = 2008 + 20 = 2028
MTU = 1500 sur Ethernet

2028 > MTU donc on fragmente

Calculons les fragments:

Chaque fragments contient <= 1500 dont 20 octets d'en-tete
Donc donnee max par segment = 1500 - 20 = 1480
On en a 2008 a transporter donc :
Fragment 1 = 1480
Fragment 2 = 2008 - 1480 = 528
On a donc send 2 paquets IP

Decrivons le contenu de chacun d'eux:

Datagramme UDP

IP Dest     : 64.190.63.222
Port Dest   : 35
Port Src    : Port Client
Data        : 2000 octets


Paquet IP 1

IP Src          : IP client
IP Dest         : 64.190.63.222
Protocole       : UDP
Fragment offset : 0
More Fragment   : 1
Data            : 1480 octets du dataframe UDP
Contenu         :  en-tete + debut des datas

Paquet IP 2

IP Src          : IP client
IP Dest         : 64.190.63.222
Protocole       : UDP
Fragment offset : 1480
More Fragment   : 0
Data            : 528 octets du dataframe UDP
Contenu         : fin des datas UDP


Que se passe-t-il si un paquet IP est perdu ?

Comme UDP lache tout sans aucune fiabilite

Si un seul fragment IP est perdu :
    - datagrame UDP peut pas etre reconstruit
    - Le systeme jette donc tout le datagramme
    - Pas de retransmission automatique
On perds donc nos 2000 octets 
L'app doit gerer les potentielles pertes


--------------------------------------------------------------------------------------------------------

Écrire la trame Ethernet complète d’un message de la question précédente ?

Structure dd'une trame Ethernet:

| MAC Dest | MAC Src | Type IP | Data (Payload) | FCS |


Trame Fragment 1

MAC Dest    : MAC routeur
MAC Src     : MAC client
Type        : 0x0800 (IPv4)
Payload     :
    IP header
    UDP header
    1480 - 8 = 1472

données max UDP = MTU − IP header − UDP header
Donc ici 1500 − 20 − 8 = 1472 octets


Trame Fragment 2

MAC Dest    : MAC routeur
MAC Src     : MAC client
Type        : 0x0800 (IPv4)
Payload     :
    IP header
    528 donnees UPD restantes

-------------------------------------------------------------------------------------------------------

Combien de séquences TCP ?

Step 1 : Etablissement de la co

TCP Utilise le three-way handshake (Slide 5)
    SYN
    SYN-ACK
    ACK
On a donc 3 segments TCP

Step 2 : Transmission des datas

MTU = 1500 (Ethernet)
Data max TCP = 1500 - 20 IP - 20 TCP = 1460 octets

Donc 2000 / 1460

On aura 2 segments TCP
    Segment 1: 1460
    Segment 2: 540

Step 3: Fermeture de la co

Fermeture TCP:
    FIN
    ACK
    FIN
    ACK
On a donc 4 segments TCP

Total sans perte:

Handshake   3
Data        2
Fermeture   4

Chaque segment = 1 paquet donc 9

Mais si on perds 1 paquet sur 2 ?

Consequence:
    Retransmission frequentes
    Nombre de segments environ double de sans perte
Pourquoi environ ? Car ca depends du timing de retransmission


--------------------------------------------------------------------------------------------------------

Écrire la trame Ethernet complète d’un message de la question précédente ?

| MAC Dest | MAC Src | type IP | IP header | TCP header | data TCP | FCS


MAC Dest    : 6
MAC Src     : 6
Type        : 2
IP Header   : 20
TCP Header  : 20
Data        : 1460 ou 540 suivant le segment
FCS         : 4 (Resultat du Cyclic Redundancy Check)


