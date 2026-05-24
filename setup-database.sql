-- ============================================================
-- GESTION RESTAURANT — MySQL Setup Script
-- Exécuter dans MySQL Workbench avant de lancer l'application
-- ============================================================

-- Créer la base de données
CREATE DATABASE IF NOT EXISTS restaurant_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE restaurant_db;

-- (Optionnel) Créer un utilisateur dédié
-- CREATE USER 'restaurant_user'@'localhost' IDENTIFIED BY 'restaurant_pass';
-- GRANT ALL PRIVILEGES ON restaurant_db.* TO 'restaurant_user'@'localhost';
-- FLUSH PRIVILEGES;

-- Les tables sont créées automatiquement par Spring Boot (JPA/Hibernate ddl-auto=update)
-- Ce script sert uniquement à créer la base de données.

SELECT 'Base de données restaurant_db créée avec succès !' AS message;
