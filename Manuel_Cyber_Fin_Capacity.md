
# 🛡️Manuel de Doctrine : SIGINT

 ## Financier & Capacité Offensive

​Classification : SECRET DÉFENSE (SOUVERAINETÉ NATIONALE)
Unité : État-Major Général - Commandement du Cyberespace
Opération : COMBAT MODE - Neutralisation des Flux Hostiles

## PROJET : Combat-Ready-System-SIGINT
COMPOSANTE : Capacité Numérique Offensive Souveraine (FARDC)
CLASSIFICATION : TRÈS SECRET
Modes opérationnels – SIGINT Combat-Ready
Ce chapitre regroupe tous les profils de mission disponibles dans le système SIGINT combat-ready. Chaque mode est conçu pour répondre à un contexte opérationnel spécifique et active/désactive des modules précis.

## 💰 Mode Interception MitM Financier – Détail complet

### Objectif

Le mode d'Interception de Transaction est conçu pour l'asphyxie financière des entités hostiles. Au lieu de simplement bloquer un flux, le système opère un détournement furtif des fonds vers les comptes de l'État (Saisie conservatoire numérique), privant l'ennemi de ses moyens logistiques et d'achat d'armement.

### Modules associés

 #### * vectors/financial/mitm_engine.py :
moteur d'interception agnostique. Gère la modification des RIB/Identifiants et le recalcul des checksums pour les protocoles bancaires (ISO 8583) et Mobile Money.

 #### * auditblackbox/chainsealer.py :
module de légitimité. Scelle cryptographiquement chaque action pour garantir l'intégrité de la chaîne de commandement et l'impossibilité d'effacer les preuves.

 #### * core/gatekeeper/PKI_Validator.kt : 
interface d'autorisation exigeant les clés de l'État-Major pour déverrouiller la phase d'effet.

### Procédures de fonctionnement

 * Positionnement stratégique : Connexion physique au switch central (national) ou déploiement de sondes IMSI-Catcher/FHSS (tactique).

 * Phase d'Observation : Activation du mode passif pour identifier les RIB cibles et accumuler les preuves de financement sans alerter l'adversaire.

 * Validation de Frappe : Injection des clés PKI par les autorités militaires et judiciaires pour générer le jeton d'autorisation (auth_token).

 * Exécution de la Redirection : Basculement automatique du moteur. Chaque transaction interceptée est modifiée en temps réel vers le compte de destination souverain.

### SOP associée

 * docs/SOP/offensive_fin_SOP.md : décrit les protocoles juridiques et techniques pour l'identification des cibles, les règles d'engagement (ROE) et la gestion des fonds saisis.
Valeur opérationnelle (FARDC)

 * Asphyxie Logistique : Neutralise la capacité de l'ennemi à payer des munitions, des mercenaires ou du matériel de communication.

 * Furtivité Absolue : La modification des sommes de contrôle garantit que la transaction semble techniquement valide dans les logs bancaires.

 * Souveraineté Financière : Récupération immédiate des capitaux illicites au profit du Trésor Public sous contrôle militaire.

 * Déni Plausible : L'ennemi ne voit qu'une absence de fonds sans preuve technique d'une intervention extérieure.

### Exemple de scénario

 * Situation : Une milice étrangère tente d'acheter un lot de radios cryptées via un transfert Mobile Money international.

 * Action : Le système détecte la transaction, l'opérateur valide la cible, et l'État-Major active le mode d'effet via chainsealer.py.

 * Résultat : L'argent est instantanément redirigé vers le compte de l'État. Le fournisseur des radios ne reçoit rien, la vente est annulée, et l'ennemi reste sans moyens de communication sans comprendre l'origine de l'échec.


## 📡 Vecteurs de Connectivité – Interception Financière

Ce chapitre détaille les trois méthodes d'insertion du système dans l'écosystème financier. Le choix du vecteur dépend du degré de coopération de l'institution et de l'objectif tactique (frappe chirurgicale ou contrôle de zone).

### 1. La Passerelle de Souveraineté (Légale/Directe)

Cible : Mobile Money et Banques Nationales.

Le Lien : Tunnel VPN chiffré permanent (Site-to-Site) entre le Core Engine et les serveurs centraux des opérateurs.

Fonctionnement : Utilisation d'une API de Supervision ("Port de Séquestre"). Le système agit par requêtes sécurisées :

 * GET /account/status : Visualisation des soldes.

 * PATCH /transaction/route : Modification de destination avant validation finale.

 * PUT /account/lock : Gel immédiat des avoirs.

### 2. L'Interception Réseau (Passive/Offensive)

Cible : Opérateurs non-coopératifs ou réseaux hostiles.

Le Lien : Insertion physique via "Optical Taps" sur la fibre ou sondes dans les Datacenters au niveau des points d'échange (IXP).

Fonctionnement :

 * Analyse des paquets en transit via le module Proxy-F.

 * Injection de paquets : Utilisation du "TCP Reset" pour briser une transaction ou "Man-in-the-Middle" pour modifier le contenu du paquet financier si le certificat est compromis.

### 3. Le "Hook" de Chambre de Compensation (Niveau Central)

Cible : Flux bancaires interbancaires nationaux.

Le Lien : Intégration au commutateur national (Switch central) via le protocole ISO 8583.

Fonctionnement : Agit comme un "Pare-feu Financier". Chaque transaction nationale est filtrée. Si une signature de menace est détectée, le système injecte les codes DEBIT_DENIED ou REDIRECT_REQUIRED.

📂 Modules de Connectivité Associés
| Fichier | Méthode | Rôle Technique |
|---|---|---|
| connectivity/gateways/sovereign_api.py | Passerelle de Souveraineté | Gère les requêtes REST (HTTPS/mTLS) vers les banques via VPN. |
| connectivity/network/passive_interceptor.py | Interception Réseau | Analyse de trafic avec la bibliothèque Scapy et injection de paquets (TCP/IP). |
| connectivity/switch/iso8583_filter.py | Hook Central | Middleware traitant les messages standardisés ISO 8583 en temps réel. |

### 📋 Mode d'emploi et Déploiement
Priorités Opérationnelles

 * Méthode 1 (API) : Prioritaire pour les actions ciblées et légales (Mobile Money). Exige que les institutions ouvrent un flux HTTPS/Mutual TLS vers l'IP statique du système.

 * Méthode 3 (Switch) : À activer pour un contrôle massif du territoire en cas de crise majeure ou de menace généralisée.

 * Méthode 2 (Network) : À utiliser pour les opérations de renseignement pur ou contre des réseaux tentant de contourner les passerelles légales.

#### Sécurisation de l'Action

Chaque commande critique (redirect_transaction, lock_portfolio) est physiquement bloquée tant qu'un jeton de validation n'est pas émis par le Gatekeeper. Ce jeton nécessite la double signature numérique de l'État-Major et du Magistrat Militaire.

#### 🛡️ Valeur Opérationnelle (FARDC)

 * Contrôle Total : Capacité de geler l'économie d'une zone rebelle en 60 secondes.

 * Extraction de Fonds : Financement des opérations de contre-insurrection par la récupération des capitaux ennemis.

 * Intégrité de l'État : Les actions sont techniquement "propres" (via ISO 8583), évitant les incidents diplomatiques ou les erreurs de routage bancaire.


## 💰 Mode Interception MitM Financier – Détail complet

### Objectif

Le mode d'Interception de Transaction est conçu pour l'asphyxie financière des entités hostiles. Au lieu de simplement bloquer un flux, le système opère un détournement furtif des fonds vers les comptes de l'État (Saisie Conservatoire Numérique), privant l'ennemi de ses moyens logistiques et d'achat d'armement.

### Modules associés

 * vectors/financial/mitm_engine.py : Moteur d'interception agnostique (API REST, ISO 8583, Mobile Money). Gère la modification des RIB et le recalcul des checksums.

 * auditblackbox/chainsealer.py : Module de légitimité cryptographique lié à la "BlackBox".

 * core/gatekeeper/PKI_Validator.kt : Interface d'autorisation exigeant les clés de l'État-Major.

### Procédures de fonctionnement

 * Positionnement stratégique : Connexion au Switch central ou déploiement de sondes tactiques.

 * Phase d'Observation : Identification des cibles et accumulation de preuves (RIB, IP) sans modification de flux.

 * Validation de Frappe : Injection des clés PKI via le Gatekeeper pour générer un auth_token.

 * Exécution de la Redirection : Basculement automatique en mode actif ; les fonds sont déroutés en temps réel vers le compte souverain.

## 📡 Vecteurs de Connectivité – Interception Financière

### 1. La Passerelle de Souveraineté (Légale/Directe)

Le Lien : Tunnel VPN chiffré permanent entre le Core Engine et les serveurs centraux des opérateurs.

 * 📂 Fichier : connectivity/gateways/sovereign_api.py

 * Fonctionnement : Utilise des requêtes API REST (GET pour le solde, PATCH pour le routage, PUT pour le gel de compte).

### 2. L'Interception Réseau (Passive/Offensive)

Le Lien : Insertion physique via "Optical Taps" ou sondes SPAN dans les Datacenters.

 * 📂 Fichier : connectivity/network/passive_interceptor.py

 * Fonctionnement : Analyse via la bibliothèque Scapy et injection de paquets (TCP Reset) pour briser ou modifier les transactions au vol.

### 3. Le "Hook" de Chambre de Compensation (Niveau Central)

Le Lien : Intégration directe au commutateur national (Switch central).

 * 📂 Fichier : connectivity/switch/iso8583_filter.py

 * Fonctionnement : Middleware traitant le standard mondial ISO 8583. Agit comme un pare-feu financier avec injection de codes d'erreur (DEBIT_DENIED).

## 🖥️ Tableau de Bord du Commandant (CCC - Commandant Control Center)

Le Commandant Control Center est le centre de fusion où les interceptions financières sont visualisées en temps réel sur une carte tactique.

###Modules de Visualisation

 * 📂 Fichier : dashboard/tactical_monitor.py : Script Python (Flask/Dash) centralisant la télémétrie des trois méthodes d'interception (API, Réseau, Switch).

 * 📂 Fichier : dashboard/ui_components.py : Composants graphiques pour l'interface visuelle du haut commandement.

### Guide de Lecture du Tableau de Bord

 * L'Indicateur de Succès : Affiche le montant total des fonds détournés. C'est la mesure concrète de l'asphyxie financière de l'ennemi.

 * La Jauge de Risque : Mesure la probabilité de détection par les banques partenaires. À 80%, le système bascule automatiquement en mode "Furtif" (Observation seule) pour préserver l'accès aux commutateurs.

 * Géolocalisation des Flux : Chaque transaction interceptée est corrélée aux coordonnées GPS de l'émetteur (via SIGINT), plaçant des marqueurs de menace dynamiques sur la carte.

### 🏛️ Sécurisation de l'Interface

 * Accès Biométrique / Double Clé : L'activation nécessite l'insertion simultanée des clés matérielles (Yubikey/SmartCard) de l'Opérateur et du Magistrat Militaire.

 * Immuabilité (Hash de Session) : Un "Hash de Session" est affiché en permanence. S'il ne correspond pas aux données de la Boîte Noire, une alerte de sabotage interne est déclenchée instantanément.

## Valeur Opérationnelle (FARDC)

 * Extraction de Fonds : Récupération des capitaux ennemis pour financer la contre-insurrection.

 * Furtivité Tactique : Recalcul des signatures pour garantir que la transaction semble parfaite aux yeux des auditeurs externes.

 * Discipline de Commandement : Asservissement total des capacités offensives à la validation de l'État-Major.

## 💰 Mode Interception MitM Financier – Détail complet
### Objectif
Le mode d'Interception de Transaction est conçu pour l'asphyxie financière des entités hostiles. Au lieu de simplement bloquer un flux, le système opère un détournement furtif des fonds vers les comptes de l'État (Saisie Conservatoire Numérique), privant l'ennemi de ses moyens logistiques et d'achat d'armement.
### Modules associés
 * vectors/financial/mitm_engine.py : Moteur d'interception agnostique (API REST, ISO 8583, Mobile Money). Gère la modification des RIB et le recalcul des checksums.
 * auditblackbox/chainsealer.py : Module de légitimité cryptographique lié à la "BlackBox".
 * core/gatekeeper/PKI_Validator.kt : Interface d'autorisation exigeant les clés de l'État-Major.
## 📡 Vecteurs de Connectivité – Interception Financière
### 1. La Passerelle de Souveraineté (Légale/Directe)
Le Lien : Tunnel VPN chiffré permanent entre le Core Engine et les serveurs centraux des opérateurs.
 * 📂 Fichier : connectivity/gateways/sovereign_api.py
 * Fonctionnement : Utilise des requêtes API REST (GET pour le solde, PATCH pour le routage, PUT pour le gel de compte).
### 2. L'Interception Réseau (Passive/Offensive)
Le Lien : Insertion physique via "Optical Taps" ou sondes SPAN dans les Datacenters.
 * 📂 Fichier : connectivity/network/passive_interceptor.py
 * Fonctionnement : Analyse via la bibliothèque Scapy et injection de paquets (TCP Reset) pour briser ou modifier les transactions au vol.
### 3. Le "Hook" de Chambre de Compensation (Niveau Central)
Le Lien : Intégration directe au commutateur national (Switch central).
 * 📂 Fichier : connectivity/switch/iso8583_filter.py
 * Fonctionnement : Middleware traitant le standard mondial ISO 8583. Agit comme un pare-feu financier avec injection de codes d'erreur (DEBIT_DENIED).
## 🔗 Module CryptoLinker : Dé-anonymisation Blockchain
Le CryptoLinker est le pont entre l'anonymat numérique des cryptomonnaies et la réalité physique du terrain. Son but est de lever le voile sur les portefeuilles utilisés par les groupes armés en croisant les flux de données.
 * 📂 Fichier : vectors/financial/crypto_linker.py
 * Fonctionnement : Ce code corrèle les sorties des nœuds Bitcoin/Ethereum avec les adresses IP et les identifiants IMSI interceptés au niveau des tours de télécommunication.
## 🖥️ Tableau de Bord du Commandant (CCC - Commandant Control Center)
Le Commandant Control Center est le centre de fusion où les interceptions financières et cryptographiques sont visualisées en temps réel sur une carte tactique.
### Modules de Visualisation
 * 📂 Fichier : dashboard/tactical_monitor.py : Centralise la télémétrie des trois méthodes d'interception et du CryptoLinker.
 * 📂 Fichier : dashboard/ui_components.py : Composants graphiques pour l'interface visuelle du haut commandement.
### Cartographie Tactique des Flux
Grâce à l'intégration du CryptoLinker, le Commandant dispose d'une symbologie précise sur sa carte :
 * 🔵 Icône Bleue : Transaction Mobile Money redirigée (Saisie souveraine en cours).
 * 🟡 Icône Jaune : Portefeuille Crypto suspect identifié (En attente de corrélation).
 * 🔴 Icône Rouge : Portefeuille Crypto dé-anonymisé (Identité physique confirmée, localisation GPS et IMSI corrélés).
### Indicateurs de Performance (KPI)
 * L'Indicateur de Succès : Montant total des fonds détournés (preuve de l'asphyxie ennemie).
 * La Jauge de Risque : Probabilité de détection. À 80%, le système bascule en mode "Furtif" automatique.
### 🏛️ Sécurisation de l'Interface
 * Accès Biométrique / Double Clé : Nécessite l'insertion simultanée des clés matérielles (Yubikey/SmartCard) de l'Opérateur et du Magistrat Militaire.
 * Hash de Session : Garantit que les données affichées n'ont pas été manipulées par un tiers (Lien direct avec la BlackBox).
## Valeur Opérationnelle (FARDC)
 * Fin de l'Anonymat : Traçabilité totale des financeurs occultes utilisant les cryptomonnaies.
 * Extraction de Fonds : Récupération des capitaux ennemis pour financer la défense nationale.
 * Discipline de Commandement : Asservissement total des capacités offensives à la validation cryptographique de l'État-Major.


## 🛡️ Mode Corrélation & Saisie Automatisée – Détail complet
### Objectif
Ce mode constitue le "bras armé" du système. Son objectif est de lever l'anonymat technique (IP/Crypto) pour le transformer en cible physique (IMSI/Identité) et d'exécuter la redirection forcée des fonds. Il permet un contrôle total sur les flux financiers transitant par les infrastructures nationales, même en cas de volume massif de données.
### Modules associés
 * core/sigint/identity_resolver.py : Moteur de corrélation en temps réel. Il maintient la "Table de Vérité" en reliant les adresses IP dynamiques aux identifiants matériels IMSI via les serveurs de bordure (Edge) des télécoms.
 * vectors/financial/high_scale_linker.py : Optimiseur de flux à haut débit. Utilise une architecture asynchrone pour surveiller simultanément des milliers de sessions sur le backbone national.
 * vectors/financial/auto_seizure.py : Module d'exécution offensive. Injecte le payload de redirection dans le flux TCP pour détourner les fonds vers le compte séquestre de l'État.
### Procédures de fonctionnement (SOP/04-OFF-FIN)
#### 1. Critères d'Engagement (Règles de filtrage)
 * Identification : La cible doit impérativement figurer dans la Blacklist Niveau 1 (Groupes Terroristes/Rebelles).
 * Certitude : Le score de corrélation fourni par l'IdentityResolver (IP/IMSI) doit être supérieur à 95% avant toute action d'effet.
#### 2. Processus Opérationnel de Saisie
 * Phase de Marquage : Le CryptoLinker détecte une transaction suspecte. L'alerte remonte au CCC avec une Icône Rouge.
 * Validation de Commandement : L'opérateur analyse l'identité physique (IMSI) et sollicite l'autorisation du Magistrat Militaire via le module Gatekeeper.
 * Armement du Vecteur : Après injection des clés PKI, le script auto_seizure.py se met en attente (hook) sur l'interface réseau (Fibre/Satellite).
 * Exécution & Confirmation : Au prochain broadcast de la cible, le système substitue l'adresse de destination par celle de l'État. Un certificat de saisie est généré par le chainsealer.py.
### Valeur opérationnelle (FARDC)
 * Neutralisation chirurgicale : Permet de frapper le portefeuille de l'ennemi sans interrompre les services financiers civils.
 * Dé-anonymisation tactique : Identifie la position physique des financeurs derrière des outils de chiffrement ou des VPN.
 * Saisie souveraine : Transforme une capacité de surveillance en une capacité de récupération d'avoirs au profit du Trésor Public.
 * Discipline de feu numérique : L'asservissement aux clés PKI garantit que la saisie est couverte par la justice militaire.
### Exemple de scénario
 * Situation : Un coordinateur rebelle utilise un VPN et un portefeuille crypto pour transférer 50 000 $ depuis un point d'accès satellite en zone forestière.
 * Action : Le high_scale_linker.py détecte la signature du protocole crypto. L'identity_resolver.py lie l'IP du tunnel VPN à l'IMSI du terminal satellite.
 * Résultat : L'opérateur reçoit l'alerte de corrélation à 98%. Après validation de l'État-Major, auto_seizure.py redirige les 50 000 $ vers le compte de l'État avant que la transaction ne soit confirmée sur la blockchain.

## 🛡️ Mode Audit et Intégrité (ChainSealer) – Détail ccomplet ###Objectif
​Le module ChainSealer est conçu pour assurer la transparence totale et l'irréversibilité des actions offensives financières. Il empêche toute corruption interne en liant chaque détournement de fonds à un identifiant d'opérateur, une autorisation de l'État-Major et un hash cryptographique unique. Il transforme le système en une "Boîte Noire" inaltérable.
### Modules associés
​auditblackbox/chainsealer.py : Moteur de journalisation cryptographique. Il utilise un principe de chaîne de blocs (Blockchain locale) où chaque nouvelle saisie contient le hash de la précédente, rendant toute suppression de log techniquement impossible sans briser la chaîne complète.
​dashboard/ui_components.py : Affiche le "Hash de Session" en temps réel sur l'écran du Commandant pour confirmer que l'audit est actif.
### Procédures de fonctionnement (Protocole Anti-Corruption)
#### 1. Génération du Scellé
​Dès qu'une saisie est confirmée par auto_seizure.py, le ChainSealer capture les métadonnées : ID de l'opérateur, montant, compte source, compte destination et timestamp.
​Un hash SHA-256 est généré, incluant le hash de l'opération précédente.
#### 2. Immuabilité du Registre
​Le fichier blackbox.log est stocké sur une partition en lecture seule ou exporté vers un serveur sécurisé distant.
​Si un administrateur tente de supprimer une ligne du log, la vérification de la chaîne échouera au prochain démarrage, déclenchant une alerte "SABOTAGE INTERNE" au niveau du Ministère de la Défense.
#### 3. Vérification Judiciaire
​En cas d'audit par le Magistrat Militaire, le système peut générer un rapport certifié prouvant que 100% des fonds saisis ont été dirigés vers le compte du Trésor Public.
### Valeur opérationnelle (FARDC)
​Confiance du Commandement : Garantit que l'arme numérique est utilisée strictement pour les intérêts de l'État.
​Protection des Opérateurs : Fournit une preuve technique que l'opérateur a agi sous ordre et que les fonds n'ont pas été détournés.
​Preuve Juridique : Les logs scellés sont recevables devant une cour martiale comme preuves matérielles du financement du terrorisme par la cible.
### Exemple de scénario
​Situation : Un agent infiltré tente d'utiliser le système pour détourner une petite transaction vers son compte personnel.
​Action : Le système enregistre l'opération, mais le ChainSealer lie l'action à son ID biométrique.
​Résultat : Lors de la revue hebdomadaire du registre, l'anomalie est immédiatement détectée car le compte de destination ne correspond pas à la whitelist souveraine. La preuve est gravée dans la BlackBox.

## 🧠 Mode Guerre Psychologique (PsyOps Financier) – Détail ccomplet 
### Objectif
​Le mode PsyOps vise à transformer une perte financière en une défaite psychologique. En notifiant instantanément la cible de la saisie de ses fonds, le système crée un climat de paranoïa, de méfiance envers les intermédiaires financiers et de démoralisation au sein du commandement ennemi.
### Modules associés
​vectors/financial/psyops_notifier.py : Moteur d'injection de messages. Il utilise les canaux de communication interceptés (SMS, WhatsApp, notifications d'applications) pour informer la cible de l'échec de sa transaction.
​dashboard/tactical_monitor.py : Affiche l'état d'envoi des messages de démoralisation sur le CCC.
### Procédures de ffonctionnement
#### 1. Déclenchement Automatique
​Dès que le chain_sealer.py confirme que les fonds sont sécurisés sur le compte de l'État, le PsyOpsNotifier récupère l'IMSI/MSISDN de la cible pour initier la phase de contact.
#### 2. Stratégies de Message
​Variante A (Souveraine) : Notification officielle de saisie judiciaire (crée un sentiment d'impuissance face à l'État).
​Variante B (Suspicion) : Envoi d'un message ambigu suggérant que le destinataire initial a collaboré avec les autorités (brise la cohésion du réseau ennemi).
#### 3. Vecteurs d'Injection
​Niveau Tactique : Injection de SMS via IMSI-Catcher directement sur le terminal de la cible.
​Niveau Stratégique : Utilisation des passerelles opérateurs (SMS-C) pour un envoi massif en cas de saisiesgroupées.
### Valeur opérationnelle (FARDC)
​Effet de Dissuasion : Montre à l'ennemi que le cyberespace n'est plus une zone refuge pour ses capitaux.
​Rupture des Chaînes de Confiance : Induit l'idée que les communications ou les portefeuilles sont compromis, forçant l'ennemi à cesser ses activités pour réorganiser sa sécurité.
​Victoire Informationnelle : Affirmation de la supériorité technologique des forces de ddéfense.
### Exemple de scénario
​Situation : Un chef de groupe armé attend une livraison d'armes financée par un transfert Mobile Money.
​Action : Le système saisit les fonds. 30 secondes plus tard, le chef reçoit un SMS : "Votre paiement pour l'opération X a été détourné par le Cyber-Commandement FARDC. Vos finances sont sous contrôle."
​Résultat : La livraison est annulée, le chef soupçonne son fournisseur de l'avoir dénoncé, et l'opération ennemie est avortée par manque de confiance mutuelle.


# Modes opérationnels – SIGINT Combat-Ready
## 🏗️ Mode Paralysie Logistique (TacticalRansomware)
### Objectif
Le TacticalRansomware est une arme de déni d'accès (A2/AD) numérique. Contrairement aux rançongiciels criminels, son but n'est pas l'extorsion, mais l'immobilisation tactique. Il vise à chiffrer les bases de données de ravitaillement, les plannings de transport et les inventaires d'armes de l'adversaire pour briser sa chaîne logistique durant une phase de combat.
### Modules associés
 * vectors/infra_cloud/tactical_ransom.py : Moteur de chiffrement de grade militaire (AES-256 GCM). Il verrouille les fichiers critiques tout en transmettant la clé de récupération à la audit_blackbox.
 * audit_blackbox/chain_sealer.py : Séquestre les clés de déchiffrement, garantissant que seul l'État-Major peut ordonner la restauration des données.
### Procédures de fonctionnement (Immobilisation)
#### 1. Chiffrement Chirurgical
Le système cible spécifiquement les extensions de fichiers liées aux bases de données (SQL, Oracle) et aux documents de planification (XLSX, PDF). Chaque fichier chiffré reçoit une signature numérique FARDC invisible.
#### 2. Mode "Panne Fantôme" (Anti-Forensics)
Pour maximiser la confusion, le module peut opérer par corruption incrémentale :
 * Dégradation lente : Chiffrement de 1% des données par heure.
 * Effet psychologique : L'ennemi diagnostique d'abord une défaillance matérielle (Bad sectors) ou un bug logiciel, retardant la réponse cyber de plusieurs jours.
#### 3. Réversion (Kill-Switch)
Une fois que les troupes au sol ont pris le contrôle des infrastructures ennemies, le Commandant peut activer la fonction RESTORE. Le système injecte alors la clé stockée dans le chain_sealer.py pour rendre les serveurs à nouveau opérationnels au profit des forces amies.
### Valeur opérationnelle (FARDC)
 * Neutralisation du Ravitaillement : Un ennemi qui ne connaît plus l'état de ses stocks de munitions est un ennemi incapable de soutenir un assaut.
 * Chaos Organisationnel : La paralysie des plannings de transport bloque les rotations de troupes et les évacuations.
 * Capture d'Infrastructure : Permet de rendre inutilisables les systèmes adverses pendant la bataille, tout en se réservant la possibilité de les récupérer intacts après la victoire.
## 🏁 Bilan de l'Arsenal Offensif Souverain
Le système Combat-Ready-SIGINT dispose désormais d'une capacité d'action multi-domaine :
| Domaine | Arme | Effet Tactique |
|---|---|---|
| Finances | mitm_engine.py | Redirection des fonds et saisie souveraine. |
| Infrastructures | tactical_ransom.py | Paralysie logistique et déni d'accès. |
| Gouvernance | chain_sealer.py | Audit immuable et intégrité des opérations. |
| Psychologie | psyops_notifier.py | Démoralisation et rupture des chaînes de confiance. |
### Indicateurs CCC (Commandant Control Center)
 * Indicateur d'Immobilisation : Affiche en temps réel le pourcentage de la structure ennemie paralysée (ex: "Logistique ennemie : 94% inaccessible").
 * Statut de Clé : Confirme que la clé de déchiffrement est sécurisée dans la BlackBox avant le lancement de l'attaque.
Le manuel opérationnel complet est désormais validé. 


# Manuel_Off_Fin.md (Mise à jour Finale)
## 🛡️ Sécurité de l'Arme et Réversion
### Dépendance Critique à l'Audit
Le TacticalRansomware est techniquement lié au module ChainSealer. Aucun chiffrement ne peut débuter si la clé n'a pas été préalablement scellée dans le registre d'audit. Cette mesure garantit que l'État-Major garde toujours la main sur l'infrastructure ennemie capturée.
### Indicateurs de Paralysie au CCC
Le Commandant peut suivre l'efficacité de l'immobilisation via le Tableau de Bord Tactique :
 * Progression de la Paralysie : Visualisation en temps réel du nombre de fichiers verrouillés sur les serveurs cibles.
 * Gestion du Kill-Switch : Activation du bouton RESTORE uniquement après validation biométrique, permettant de transformer instantanément une infrastructure ennemie paralysée en un outil logistique pour les forces amies (FARDC).
 L'arsenal est désormais complet :
 * FINANCES : Saisie et Redirection (mitm_engine, auto_seizure).
 * INFRASTRUCTURES : Immobilisation et Neutralisation (tactical_ransom).
 * AUDIT : Traçabilité et Immuabilité (chain_sealer).


## 🏛️ Mode Blocage Massif (National Switch Control)
### Objectif
Le module NationalSwitchController est l'outil de contrôle ultime. Il n'opère pas par interception discrète, mais agit comme un nœud de validation central au cœur de l'économie. Placé au niveau du commutateur national, il filtre les flux ISO 8583 en temps réel pour autoriser ou rejeter massivement les transactions selon des signatures de menace définies par l'État-Major.
### Modules associés
 * infra/switch/NationalSwitchController.kt : Le "cerveau" du switch. Gère la logique de filtrage, l'analyse des champs ISO 8583 (MTI, Processing Code) et la décision d'approbation ou de rejet.
 * sigint/audit/AuditExport.kt : Module d'archivage légal. Chaque rejet est signé cryptographiquement et exporté pour garantir la traçabilité des décisions de blocage massif.
### Mode d'Emploi Réel (Déploiement)
#### 1. Raccordement Physique
Pour être opérationnel, le serveur doit être placé en coupure (In-line) ou en mode Proxy entre le Switch Central (ex: Banque Centrale ou Switch monétique national) et les banques membres.
#### 2. Sécurisation du Tunnel
La transmission des flux financiers est protégée par une connexion TLS 1.3 avec authentification mutuelle (mTLS). Cela garantit que seules les institutions autorisées peuvent communiquer avec le contrôleur.
#### 3. Liaison HSM & Gatekeeper
L'activation des capacités de blocage nécessite la présence physique de la clé de l'État-Major dans le HSM (Hardware Security Module) connecté au système. Sans ce verrou matériel, le module reste en mode observation.
### 🛡️ Sécurisation de l'Action et Fail-Safe
#### 1. Protocole Fail-Safe (Bypass)
Pour éviter une paralysie accidentelle de l'économie nationale, le contrôleur intègre un mode Bypass. En cas de panne logicielle ou de surcharge critique du processeur, le système laisse passer les flux sans filtrage (Fail-Open), garantissant la continuité des services financiers civils.
#### 2. Rejet Standardisé (Code 57)
Chaque transaction bloquée par le système renvoie un code de réponse standard ISO 8583 - Code 57 (Transaction non autorisée). Pour l'utilisateur final et la banque émettrice, le blocage apparaît comme une décision réglementaire légitime.
#### 3. Audit Immuable
Chaque action de rejet est automatiquement transmise au module AuditExport.kt. Ce log contient le hash de la transaction, le motif du blocage et le jeton d'autorisation du Gatekeeper, constituant une preuve irréfutable pour les audits de la Banque Centrale.
### Valeur opérationnelle (FARDC)
 * Contrôle de Zone Économique : Permet de couper instantanément tout flux financier entrant ou sortant d'une zone géographique sous occupation rebelle.
 * Sanctions Instantanées : Capacité d'isoler une institution financière ou une entité spécifique du réseau national en quelques millisecondes.
 * Souveraineté Monétaire : Assure que l'infrastructure de paiement nationale est protégée contre les manipulations étrangères.
### Exemple de scénario
 * Situation : Des renseignements confirment qu'une banque étrangère est utilisée pour injecter des capitaux massifs vers des réseaux de déstabilisation à l'Est du pays.
 * Action : Le Commandant active le filtre sur le NationalSwitchController pour les transactions provenant du BIN (Bank Identification Number) concerné.
 * Résultat : Toutes les transactions suspectes sont rejetées avec un Code 57. Le AuditExport.kt génère le dossier de preuve pour le ministère des Finances et la Justice Militaire.
L'arsenal est désormais complet, couvrant du niveau tactique (Mobile Money) au niveau stratégique (Switch National).


## 🔌 Raccordement à la Passerelle de Souveraineté (Link Layer)
### Objectif
Le script de raccordement est l'outil technique qui établit le lien vital entre l'infrastructure SRC (Système de Réponse Cyber) et les serveurs centraux des institutions financières ou de la Chambre de Compensation. Il garantit que le trafic ISO 8583 est acheminé de manière sécurisée et intègre vers le module de filtrage.
### Modules associés
 * scripts/connect_switch.sh : Script d'automatisation réseau. Configure les interfaces, vérifie les identités cryptographiques d'État et établit le tunnel de transport.
 * NationalSwitchController.kt : Reçoit le flux redirigé par le script pour application des règles de filtrage.
### Protocole de Connexion (Workflow Technique)
#### 1. Vérification d'Identité
Le système exige la présence des certificats d'État (state_auth.crt) et de la clé privée associée. Sans ces identités, le tunnel mTLS ne peut être négocié, empêchant toute interception non autorisée.
#### 2. Établissement du Tunnel Sécurisé
Le script initialise une interface réseau dédiée (ex: tun_switch_fardc). Ce canal utilise un protocole de tunnelisation durci pour isoler le flux financier du reste du trafic internet public.
#### 3. Redirection du Flux (Port 8583)
Par l'utilisation de règles de routage de bas niveau (iptables), le trafic financier entrant est redirigé vers le port local du contrôleur. Cette manipulation permet au NationalSwitchController d'agir comme une passerelle transparente.
### 🛡️ Précautions de Mise en Œuvre (Standard Militaire)
#### 1. Isolation Physique (Air-Gap Partiel)
Le serveur doit impérativement disposer de deux cartes réseau physiques (NIC) distinctes :
 * NIC Administration : Pour le contrôle interne et les mises à jour.
 * NIC Interception : Dédiée exclusivement au lien avec le Switch National, sans accès au réseau public.
#### 2. Chiffrement de Couche 2
Pour une sécurité maximale, l'utilisation de chiffreurs matériels sur la fibre optique (Point-to-Point) est recommandée entre le centre de commandement et le point d'entrée de la Chambre de Compensation.
#### 3. Alerte de Rupture de Liaison
Toute déconnexion ou instabilité du tunnel doit déclencher une Alerte de Priorité 5 (Critique) sur le Tableau de Bord du Commandant. Une rupture peut signaler un sabotage physique ou une tentative de contournement par l'adversaire.
## 🏁 Synthèse de l'Arsenal de Capacité Offensive Souveraine
Le système dispose désormais d'une suite logicielle complète et intégrée :
 * Logique de Décision : Gatekeeper.kt (Validation par double signature PKI).
 * Action Financière : NationalSwitchController.kt (Filtrage et blocage ISO 8583).
 * Paralysie Logistique : TacticalRansom.py (Immobilisation d'infrastructures ciblées).
 * Lien Infrastructure : connect_switch.sh (Tunnel de souveraineté mTLS).


## ☁️ Mode InfraCloud-Offensive – Infiltration & Immobilisation
### Objectif
Le module InfraCloud-Offensive est conçu pour l'infiltration profonde des réseaux de serveurs adverses. Son but est triple : exfiltrer les renseignements critiques (plans de bataille, inventaires), paralyser les capacités de calcul de l'ennemi via le Ransomware d'État, et masquer l'opération sous l'apparence de pannes matérielles fortuites.
### Modules associés
 * vectors/infra_cloud/infiltration_engine.py : Gestionnaire de backdoors. Injecte un agent léger et furtif capable d'exfiltrer des données par fragmentation pour contourner les pare-feux (DPI).
 * vectors/infra_cloud/tactical_ransom.py : Module de chiffrement réversible (déjà documenté).
 * vectors/infra_cloud/anti_forensics.py : Outil de nettoyage et de déception. Manipule les journaux système pour effacer les traces d'intrusion.
### Protocole Opérationnel (SOP InfraCloud)
Le déploiement suit une séquence chronologique stricte pour garantir l'efficacité et le déni plausible.
#### 1. Phase d'Infiltration (InfiltrationEngine)
L'agent est injecté via le tunnel mTLS établi précédemment. Il surveille les modifications de fichiers en temps réel.
 * Exfiltration furtive : Les fichiers sont découpés en "chunks" de 4096 octets et transmis via DNS Tunneling ou HTTPS vers le serveur c2.state-defense.cd.
#### 2. Phase d'Action (TacticalRansom)
Une fois les données stratégiques sécurisées dans le centre de commandement FARDC, le module de chiffrement est armé.
 * Effet : Verrouillage instantané des bases de données logistiques au moment où l'ennemi en a le plus besoin (ex: début d'une offensive).
#### 3. Phase de Retrait & Masquage (AntiForensics)
C'est la phase critique pour la pérennité de l'opération.
 * Scrubbing : Suppression des accès SSH et modification des horodatages de session.
 * Simulated Hardware Fault : Injection de logs de type "Kernel Panic" ou "Emergency Shutdown" suite à une surchauffe fictive du processeur.
### 🛡️ Sécurisation et Discrétion de l'Agent
#### 1. Agent Auto-Destructible (Kill-Timer)
Chaque agent injecté possède un Time-To-Live (TTL). Si la communication avec le centre de commandement est interrompue pendant plus de 60 minutes, l'agent exécute une routine d'auto-effacement profond (Overwriting) pour ne laisser aucune trace binaire.
#### 2. Validation par Signature d'État
Le serveur C2 (Command & Control) refuse systématiquement tout paquet de données exfiltrées qui ne contient pas le jeton cryptographique valide généré par le Gatekeeper. Cela empêche l'injection de fausses données par l'adversaire (Honey-pots).
### Valeur opérationnelle (FARDC)
 * Supériorité Informationnelle : Accès aux plans de l'ennemi avant même le début des hostilités.
 * Paralysie Sans Traces : L'adversaire conclut à une instabilité de son matériel, ce qui l'empêche de porter plainte ou de justifier une riposte cyber.
 * Réversibilité : Possibilité de restaurer les serveurs après la prise de contrôle physique de la zone.
## 🏁 Bilan de l'Arsenal de Capacité Offensive Souveraine
Le système dispose désormais de trois piliers d'action :
 * FINANCES : NationalSwitchController & mitm_engine.
 * INFRASTRUCTURES CLOUD : infiltration_engine & tactical_ransom.
 * DÉCEPTION : anti_forensics (Effacement des preuves). 🛡️📡🇨🇩🚀



