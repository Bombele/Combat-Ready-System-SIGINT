# Description du module UI

Le module `ui/` regroupe les interfaces utilisateur du système SIGINT.  
Il couvre les environnements **mobile**, **web** et **desktop**, ainsi que les ressources graphiques partagées.  
Ce module garantit la cohérence visuelle, l’accessibilité et la conformité institutionnelle des interfaces.

---

## 📂 Structure

### mobile/
- **screens/** → écrans principaux (capture, analyse, audit, settings).
- **components/** → composants réutilisables (signal_chart, emitter_card, compliance_badge).
- **mobile_index.md** → documentation mobile.

### web/
- **pages/** → pages principales (dashboard, capture, analysis, audit).
- **components/** → composants interactifs réutilisables (signal_chart.js, emitter_card.js, compliance_badge.js).
- **web_index.md** → documentation web.

### desktop/
- **windows_ui/** → interfaces Windows (dashboard.xaml, capture.xaml).
- **linux_ui/** → interfaces Linux (dashboard.ui, capture.ui).
- **desktop_index.md** → documentation desktop.

### assets/
- **icons/** → bibliothèque d’icônes institutionnelles :
  - **system/** → icônes système (add, delete, edit, settings).
  - **signals/** → icônes SIGINT (radar, voice, telemetry, spectrum).
  - **compliance/** → icônes conformité (audit, badge, report).
  - **icons_index.md** → documentation indexée des icônes.
- **styles/** → feuilles de style et thèmes :
  - **themes/** → thèmes institutionnels (light.css, dark.css, audit.css).
  - **components/** → styles pour composants UI (signal_chart.css, emitter_card.css, compliance_badge.css).
  - **layouts/** → styles pour mise en page (dashboard.css, capture.css, analysis.css).
  - **styles_index.md** → documentation indexée des styles.
- **assets_index.md** → documentation des ressources.

### ui_index.md
- Documentation globale du module.

---

## 🎯 Description des sous‑modules

- **mobile/** → interfaces Android/iOS avec écrans et composants réutilisables.  
- **web/** → interfaces web avec pages et composants interactifs.  
- **desktop/** → interfaces desktop pour Windows/Linux.  
- **assets/** → ressources graphiques, icônes et styles institutionnels.  
- **ui_index.md** → documentation globale du module UI.  

---

## 🏛️ Valeur institutionnelle

- **Accessibilité** : interfaces cohérentes sur mobile, web et desktop.  
- **Transmission** : documentation claire pour adoption continentale.  
- **Auditabilité** : intégration des badges et écrans de conformité.  
- **Homogénéité visuelle** : styles et icônes centralisés dans `assets/`.  

---

✅ Avec ce module `ui/`, ton système SIGINT dispose d’une **interface utilisateur complète et institutionnelle**, garantissant cohérence, accessibilité et conformité.