#!/bin/bash

# --- Vérification des paramètres ---
if [ $# -ne 1 ]; then
    echo "Usage: $0 <nb_messages>"
    echo "Exemple: $0 5"
    exit 1
fi

NMSG="$1"
FIFO="/tmp/chan_test_$$"
LOG="server.log"

echo "[TEST] Nombre de messages à envoyer : $NMSG"
echo ""

echo "[TEST] Compilation..."
make
if [ $? -ne 0 ]; then
    echo "[ERREUR] Échec de la compilation. Abandon du test."
    exit 1
fi

echo "[TEST] Nettoyage de la FIFO précédente si besoin..."
rm -f "$FIFO"
rm -f "$LOG"

echo "[TEST] Lancement du serveur..."
./server "$FIFO" "$LOG" &
SERVER_PID=$!

sleep 1

echo "[TEST] Lancement du client ($NMSG fils, message 'bonjour')..."
sleep 0.5
echo "----------------------------------------"
./client "$FIFO" "$NMSG" "bonjour"

sleep 0.5
echo "----------------------------------------"
echo "[TEST] Attente de la fin du serveur..."
wait $SERVER_PID

sleep 1
echo "----------------------------------------"
echo "[TEST] Affichage du contenu du fichier log :"
sleep 1
echo "----------------------------------------"
cat "$LOG"
echo "----------------------------------------"

# --- Vérification de la présence de NMSG lignes "bonjour" ---
echo "[TEST] Vérification du résultat..."

NB_LINES=$(grep -c "bonjour" "$LOG")

if [ "$NB_LINES" -eq "$NMSG" ]; then
    echo ""
    echo "🎉🎉🎉 BRAVO ! Le test a fonctionné correctement 🎉🎉🎉"
    echo "Les $NMSG messages ont été reçus, déchiffrés et enregistrés."
    echo ""
else
    echo ""
    echo "❌ Le test n'a pas réussi."
    echo "Attendu : $NMSG lignes contenant 'bonjour'"
    echo "Reçu    : $NB_LINES"
    echo "Vérifiez votre client, votre serveur et votre decoder."
    echo ""
fi

sleep 1
echo "[TEST] Nettoyage..."
rm -f "$FIFO"

sleep 1
echo "[TEST] Terminé."
