Requete :

1 | 0x0800 | 6 | 4 | 1 |  D8:3B:8D:C1:BF:C9 | 192.168.1.101/24 | NIL = 00:00:00:00:00:00 | 192.168.1.102/24
-----------------------------------------------------------------------------------------------------------
1 | 0x0800 | 6 | 4 | 1 |  01:00:5E:7F:FF:FA | 192.168.1.102/24 | NIL = 00:00:00:00:00:00 | 192.168.2.101/24
-----------------------------------------------------------------------------------------------------------
1 | 0x0800 | 6 | 4 | 2 |  01:00:5E:7F:AE:15 | 192.168.2.101/24 | 01:00:5E:7F:AE:15 | 192.168.1.102/24






Tete de la requete :  

| Champ         | Valeur            |
| ------------- | ----------------- |
| Hardware type | 1 (Ethernet)      |
| Protocol type | 0x0800 (IPv4)     |
| Hardware size | 6                 |
| Protocol size | 4                 |
| Opcode        | 1 (request)       |
| Sender MAC    | MAC de M1         |
| Sender IP     | IP de M1          |
| Target MAC    | 00:00:00:00:00:00 |
| Target IP     | IP de M2          |
_____________________________________


-----------ETHERNET -------------
| Champ     | Hex               |
| --------- | ----------------- |
| Dest MAC  | ff ff ff ff ff ff |           
| Src MAC   | aa aa aa aa aa aa |
| EtherType | 08 06             |
_________________________________

-------------- ARP -------------------
| Champ                      | Hex   |
| -------------------------- | ----- |
| Hardware type (Ethernet=1) | 00 01 |
| Protocol type (IPv4)       | 08 00 |
| Hardware size              | 06    |
| Protocol size              | 04    |
| Opcode (request=1)         | 00 01 |
______________________________________


Sender MAC | aa aa aa aa aa aa

Sender IP 192.168.1.10 | c0 a8 01 0a

Target MAC | 00 00 00 00 00 00

Target IP 192.168.1.25 | c0 a8 01 19


                            REQUEST 1
----------------------------------------------------------------------

1 | 0x0800 | 6 | 4 | 1 |  D8:3B:8D:C1:BF:C9 | 192.168.1.101/24 | NIL = 00:00:00:00:00:00 | 192.168.1.102/24

Etape A : Ecrire Ethernet

MAC dest = 00:00:00:00:00:00
MAC src = D8:3B:8D:C1:BF:C9
Type = 0806

Etape B : Ecrire ARP

Hardware type = 0001
Protocol type = 0800
Hardware taille = 06
Protocol taille = 04
Opcode = 00 01
MAC sender : D8:3B:8D:C1:BF:C9
IP sender = c0 a8 01 65
MAC target = 00:00:00:00:00:00
IP target = c0 a8 01 66

                            REQUEST 2
----------------------------------------------------------------------
1 | 0x0800 | 6 | 4 | 1 |  01:00:5E:7F:FF:FA | 192.168.1.102/24 | NIL = 00:00:00:00:00:00 | 192.168.2.101/24

Etape A : Ecrire Ethernet

MAC dest = 00:00:00:00:00:00
MAC src = 01:00:5E:7F:FF:FA
Type = 0806


Etape B : Ecrire ARP

Hardware type = 0001
Protocol type = 0800
Hardware taille = 06
Protocol taille = 04
Opcode = 00 01
MAC sender = 01:00:5E:7F:FF:FA
IP sender = c0 a8 01 66
MAC target = 00:00:00:00:00:00
IP target = c0 a8 02 65


                            REQUEST 3
----------------------------------------------------------------------

1 | 0x0800 | 6 | 4 | 2 |  01:00:5E:7F:AE:15 | 192.168.2.101/24 | 01:00:5E:7F:AE:15 | 192.168.1.102/24

Etape A : Ecrire Ethernet

MAC dest = 01:00:5E:7F:AE:15
MAC src = 01:00:5E:7F:AE:15
Type = 0806


Etape B : Ecrire ARP

Hardware type = 0001
Protocol type = 0800
Hardware taille = 06
Protocol taille = 04
Opcode = 00 02
MAC sender = 01:00:5E:7F:AE:15
IP sender = c0 a8 01 65
MAC target = 01:00:5E:7F:AE:15
IP target = c0 a8 02 66







