-- 1. Tabela za korisnika
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    datum_rodjenja DATE -- ovde se čuva npr. 1995-10-25
);

-- 2. Tabela za postove
CREATE TABLE posts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    naslov VARCHAR(100) NOT NULL,
    tekst TEXT NOT NULL,
    datum_objave DATETIME DEFAULT CURRENT_TIMESTAMP, -- automatski beleži vreme
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);