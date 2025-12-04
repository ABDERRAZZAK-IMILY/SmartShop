#  SmartShop - API de Gestion Commerciale B2B

**SmartShop** est une application backend robuste développée avec **Spring Boot** pour *MicroTech Maroc*. Elle vise à gérer le processus commercial B2B, incluant la gestion des clients, des commandes complexes, un système de fidélité dynamique et le suivi des paiements fractionnés.

---

##  Contexte du Projet

Cette application est une **API REST** pure (sans interface graphique) conçue pour assurer :
* La traçabilité financière des commandes.
* La gestion automatique des niveaux de fidélité (Basic, Silver, Gold, Platinum).
* La validation stricte des règles métier (Stock, Plafond Cash, Solde restant).
* Une sécurité personnalisée sans frameworks lourds (Session Based Auth).

---

##  Fonctionnalités Clés

### 1. Gestion des Clients 
* CRUD complet (Création, Lecture, Mise à jour, Suppression).
* **Statistiques automatiques :** Total dépensé, Nombre de commandes.
* **Suivi temporel :** Date de la première et dernière commande.
* **Isolation des données :** Un client ne peut consulter que ses propres informations.

### 2. Gestion des Produits 
* Catalogue avec **Pagination** et **Recherche** (Filtres).
* **Soft Delete :** Les produits supprimés restent en base pour l'intégrité des factures mais sont marqués `deleted=true`.

### 3. Gestion des Commandes 
* Vérification du stock en temps réel.
* Calcul automatique : **Sous-total -> Remise (Fidélité + Code Promo) -> TVA (20%) -> Total TTC**.
* **Workflow d'état :** `PENDING` ➔ `CONFIRMED` (uniquement si payée à 100%) / `REJECTED` / `CANCELED`.
* Mise à jour automatique du **Niveau de Fidélité** après confirmation.

### 4. Système de Paiements 
* Support des paiements partiels (Acomptes).
* Types supportés : Espèces, Chèque, Virement.
* **Règle métier :** Paiement en espèces plafonné à **20,000 DH**.
* Mise à jour immédiate du `Reste à payer` sur la commande.

### 5. Sécurité & Technique 
* **Authentification :** Session HTTP (Login/Logout) avec `AuthInterceptor`.
* **Autorisation :** Rôles `ADMIN` (Accès total) vs `CLIENT` (Lecture seule sur ses données).
* **Gestion des Erreurs :** Réponses JSON uniformes (Codes 400, 404, 422, 500) via `@ControllerAdvice`.
* **Logging :** Suivi des opérations et temps d'exécution via **Spring AOP**.

---

##  Stack Technique

* **Langage :** Java 17
* **Framework :** Spring Boot 3.x (Web, Data JPA, Validation, AOP)
* **Base de données :** PostgreSQL
* **Outils :** Lombok, Maven
* **Tests :** JUnit 5, Mockito
* **Architecture :** N-Tier (Controller, Service, Repository, Domain, DTO)

---

##  Installation et Démarrage

### Prérequis
* Java 17 ou supérieur.
* PostgreSQL installé et lancé.
* Maven.



# class diagram:

![img_1.png](img_1.png)

### URL du diagramme en ligne :
https://app.diagrams.net/?src=about#G1vas-xPRROPmsjRD9kkpH1pj9zd6km91l#%7B%22pageId%22%3A%22uDjDEmsDGETxZd_or1DD%22%7D

# planification Jira Url :

https://azeimily2001-1757951624297.atlassian.net/jira/software/projects/SMAR/boards/73?atlOrigin=eyJpIjoiZTAzZmYzNzVlZDA5NDM1NDk2Nzc3ZDcxOGQ2NWJiN2UiLCJwIjoiaiJ9





### 1. Configuration de la Base de Données
Créez une base de données nommée `smartshop_db` ou configurez `src/main/resources/application.properties` :

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/smartshop_db
spring.datasource.username=postgres
spring.datasource.password=votre_mot_de_passe

```
### 2. Lancement de l'application

```
# Compiler et lancer les tests
./mvnw clean install

# Démarrer le serveur
./mvnw spring-boot:run

```

3. Compte Administrateur par défaut
   Au premier démarrage, l'application crée automatiquement un compte admin :

Username : admin

Password : admin123

Documentation API (Endpoints Principaux)
Vous pouvez tester l'API via Postman.

### Authentification
```
POST /auth/login : Connexion (Body: username, password).

POST /auth/logout : Déconnexion.
```
### Produits (Products)
```
GET /api/products?page=0&size=10 : Liste paginée.

GET /api/products/search?search=laptop : Recherche par nom.

POST /api/products : Créer un produit (Admin).

DELETE /api/products/{id} : Suppression logique (Admin).
```
### Clients (Clients)
```
POST /api/clients : Inscription.

GET /api/clients/{id} : Détails profil.

PUT /api/clients/{id} : Mise à jour infos.
```
### Commandes (Orders)
```
POST /api/orders : Créer une commande.

PATCH /api/orders/{id}/status?status=CONFIRMED : Valider une commande (Admin).

Note : Échouera si le montant restant > 0.
```
### Paiements (Payments)
```
POST /api/payments : Enregistrer un paiement.

PATCH /api/payments/{id}/validate : Valider un chèque (Admin).
```
# Tests Unitaires
Le projet inclut des tests unitaires pour valider la logique métier critique (Calcul des remises fidélité).

Pour lancer les tests :

```
./mvnw test
```

# Auteur

ABDERRAZZAK IMILY : Full Stack Java Developer



# Architecture respectée :
Clean Layered Architecture.

# Qualité :
Clean Code, SOLID principles, Defensive Programming.



