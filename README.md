# 🍽️ Application de Gestion de Restaurant
**Groupe 4 — Spring Boot + MySQL + Thymeleaf**

---

## 👥 Membres
- C26698 – Yahya Sidaty El Hachmy (MIAGE)
- C27186 – Mohamed Saiid Mohameden (DA2I)

---

## ⚙️ Technologies
- **Spring Boot 3.2** (Java 17)
- **Spring Security** — Rôles: MANAGER, SERVEUR, CUISINER
- **Spring Data JPA** — Hibernate
- **Thymeleaf** — Templates HTML
- **MySQL** — Base de données
- **Maven** — Build

---

## 🚀 Installation Pas à Pas (IntelliJ IDEA 2026.1.1)

### Étape 1 — Prérequis
- Java 17+ installé → vérifier: `java -version`
- MySQL Workbench installé et démarré
- IntelliJ IDEA 2026.1.1

### Étape 2 — Base de données MySQL
1. Ouvrir **MySQL Workbench**
2. Se connecter (root par défaut)
3. Exécuter le fichier `setup-database.sql`:
   ```sql
   CREATE DATABASE IF NOT EXISTS restaurant_db
       CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```
4. La base `restaurant_db` est créée ✅

### Étape 3 — Configurer les credentials MySQL
Ouvrir `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/restaurant_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root        # ← Votre username MySQL
spring.datasource.password=root        # ← Votre password MySQL
```
⚠️ Modifiez `username` et `password` selon votre configuration MySQL.

### Étape 4 — Ouvrir dans IntelliJ
1. **File → Open** → Sélectionner le dossier `restaurant/`
2. IntelliJ détecte automatiquement le projet Maven
3. Cliquer **"Load Maven Project"** si demandé
4. Attendre le téléchargement des dépendances (première fois ~2 minutes)

### Étape 5 — Lancer l'application
1. Ouvrir `src/main/java/com/restaurant/RestaurantApplication.java`
2. Cliquer le bouton ▶️ vert à côté de `main`
3. Ou: **Run → Run 'RestaurantApplication'**
4. Attendre le message dans la console:
   ```
   Started RestaurantApplication in X.XXX seconds
   ✅ Compte manager créé: username=manager / password=manager123
   ```

### Étape 6 — Accéder à l'application
Ouvrir un navigateur: **http://localhost:8080**

---

## 🔐 Comptes par défaut

| Rôle | Username | Password |
|------|----------|----------|
| **Manager** | `manager` | `manager123` |

> Le Manager crée ensuite les comptes Serveur et Cuisiner depuis **Paramètres → Gestion des Utilisateurs**.

---

## 📱 Fonctionnalités par Rôle

### 👔 Manager
| Page | Fonctionnalités |
|------|-----------------|
| **Commandes** | Tableau de bord avec stats, voir toutes les commandes (ID, Date, Catégorie, Nom, Statut, Table), ajouter, supprimer |
| **Menu** | Créer/supprimer des catégories, ajouter/supprimer des plats (nom, description, prix), barre de recherche |
| **Paramètres** | Changer la langue (FR/EN), modifier son mot de passe, créer/supprimer des comptes Serveur/Cuisiner |

### 🛎️ Serveur
| Page | Fonctionnalités |
|------|-----------------|
| **Commandes** | Voir ses commandes, créer une nouvelle commande (→ statut **En Attente** automatique), supprimer (si En Attente), marquer comme **Livré** (quand Done) |

### 👨‍🍳 Cuisiner
| Page | Fonctionnalités |
|------|-----------------|
| **Commandes** | Vue Kanban (En Attente / En Cours / Done+Livré), changer statut: En Attente→**En Cours**→**Done** |

---

## 🔄 Workflow Complet

```
Serveur crée commande → [EN_ATTENTE]
Cuisiner démarre → [EN_COURS]
Cuisiner termine → [DONE]
Serveur livre au client → [LIVRÉ]
```

---

## 🗃️ Structure du Projet

```
restaurant/
├── pom.xml                          # Dépendances Maven
├── setup-database.sql               # Script MySQL
└── src/main/
    ├── java/com/restaurant/
    │   ├── RestaurantApplication.java
    │   ├── config/
    │   │   ├── SecurityConfig.java  # Spring Security
    │   │   └── DataInitializer.java # Compte manager par défaut
    │   ├── model/                   # Entités JPA
    │   │   ├── User.java
    │   │   ├── Category.java
    │   │   ├── Plat.java
    │   │   ├── Commande.java
    │   │   ├── Role.java
    │   │   └── CommandeStatus.java
    │   ├── repository/              # Spring Data JPA
    │   ├── service/                 # Logique métier
    │   ├── controller/              # Contrôleurs MVC
    │   └── security/
    │       └── CustomUserDetailsService.java
    └── resources/
        ├── application.properties
        ├── templates/               # Thymeleaf HTML
        │   ├── auth/login.html
        │   ├── manager/ (commandes, menu, parametres)
        │   ├── serveur/ (commandes)
        │   └── cuisiner/ (commandes)
        └── static/
            ├── css/style.css
            └── js/main.js
```

---

## ❓ Problèmes Fréquents

**Erreur de connexion MySQL:**
→ Vérifiez que MySQL Workbench est démarré et que les credentials dans `application.properties` sont corrects.

**Port 8080 déjà utilisé:**
→ Ajoutez dans `application.properties`: `server.port=8081`

**Lombok non reconnu:**
→ IntelliJ: **File → Settings → Plugins** → Installer "Lombok" → Redémarrer.
→ Puis: **File → Settings → Build → Compiler → Annotation Processors** → Cocher "Enable annotation processing".

**Tables non créées:**
→ Vérifiez `spring.jpa.hibernate.ddl-auto=update` dans `application.properties`.
