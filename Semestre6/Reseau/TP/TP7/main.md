Une fois la connexion établis le client envoie le message :
"GET / HTTP/1.1\nHost: www.example.com\n\n" sur la connexion.

Décrire entièrement la trame Ethernet de ce message ?

| MAC Dest | MAC Src | Type (ipv4) | Data | FCS

MAC Dest    : 81:FA:5E:FA:65:12 = 6
MAC Src     : 01:00:5E:7F:FF:FA = 6
Type        : 0x0800 = 2
Payload     :
    IP Header = 20
    TCP Header = 20
    Data = GET / HTTP/1.1\nHost: www.example.com\n\n
FCS         : 4 octets

