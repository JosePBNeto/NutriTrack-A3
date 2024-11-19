-- Criar banco de dados
CREATE DATABASE IF NOT EXISTS nutritrack;
USE nutritrack;

-- Tabela de macronutrientes
CREATE TABLE macronutrients (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    proteins DOUBLE NOT NULL,
    carbohydrates DOUBLE NOT NULL,
    fats DOUBLE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabela de alimentos
CREATE TABLE food (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    macronutrients_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (macronutrients_id) REFERENCES macronutrients(id)
);

-- Tabela de micronutrientes
CREATE TABLE micronutrient (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    amount DOUBLE NOT NULL,
    unit VARCHAR(10) NOT NULL,
    food_id BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (food_id) REFERENCES food(id)
);

-- Tabela de ingestão diária
CREATE TABLE daily_intake (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabela de relacionamento entre ingestão diária e alimentos
CREATE TABLE daily_intake_foods (
    daily_intake_id BIGINT,
    food_id BIGINT,
    FOREIGN KEY (daily_intake_id) REFERENCES daily_intake(id),
    FOREIGN KEY (food_id) REFERENCES food(id),
    PRIMARY KEY (daily_intake_id, food_id)
);

-- Índices para otimização
CREATE INDEX idx_food_name ON food(name);
CREATE INDEX idx_daily_intake_date ON daily_intake(date);

-- Inserir dados de exemplo
-- Macronutrientes para Arroz branco cozido (100g)
INSERT INTO macronutrients (proteins, carbohydrates, fats) VALUES
(2.5, 28.2, 0.2);

-- Arroz branco cozido
INSERT INTO food (name, description, macronutrients_id) VALUES
('Arroz Branco Cozido', 'Arroz branco tipo 1 cozido (porção de 100g)', 1);

-- Micronutrientes do Arroz
INSERT INTO micronutrient (name, amount, unit, food_id) VALUES
('Ferro', 0.1, 'mg', 1),
('Sódio', 1.0, 'mg', 1),
('Potássio', 26.0, 'mg', 1),
('Vitamina B6', 0.05, 'mg', 1);

-- Macronutrientes para Feijão preto cozido (100g)
INSERT INTO macronutrients (proteins, carbohydrates, fats) VALUES
(4.8, 14.0, 0.5);

-- Feijão preto cozido
INSERT INTO food (name, description, macronutrients_id) VALUES
('Feijão Preto Cozido', 'Feijão preto cozido (porção de 100g)', 2);

-- Micronutrientes do Feijão
INSERT INTO micronutrient (name, amount, unit, food_id) VALUES
('Ferro', 1.5, 'mg', 2),
('Cálcio', 27.0, 'mg', 2),
('Potássio', 256.0, 'mg', 2),
('Magnésio', 42.0, 'mg', 2);

-- Macronutrientes para Frango grelhado (100g)
INSERT INTO macronutrients (proteins, carbohydrates, fats) VALUES
(31.0, 0.0, 3.6);

-- Frango grelhado
INSERT INTO food (name, description, macronutrients_id) VALUES
('Peito de Frango Grelhado', 'Peito de frango grelhado sem pele (porção de 100g)', 3);

-- Micronutrientes do Frango
INSERT INTO micronutrient (name, amount, unit, food_id) VALUES
('Ferro', 0.9, 'mg', 3),
('Zinco', 1.0, 'mg', 3),
('Vitamina B6', 0.6, 'mg', 3),
('Vitamina B12', 0.3, 'mcg', 3);

-- Inserir exemplo de ingestão diária
INSERT INTO daily_intake (date) VALUES
('2024-01-05'),
('2024-01-05');

-- Relacionar alimentos com a ingestão diária
INSERT INTO daily_intake_foods (daily_intake_id, food_id) VALUES
(1, 1), -- Arroz no primeiro registro
(1, 2), -- Feijão no primeiro registro
(2, 3); -- Frango no segundo registro

-- Criar views úteis
CREATE VIEW v_food_complete AS
SELECT
    f.id,
    f.name,
    f.description,
    m.proteins,
    m.carbohydrates,
    m.fats,
    (m.proteins * 4 + m.carbohydrates * 4 + m.fats * 9) as calories
FROM food f
JOIN macronutrients m ON f.macronutrients_id = m.id;

CREATE VIEW v_daily_nutrition AS
SELECT
    di.date,
    SUM(m.proteins) as total_proteins,
    SUM(m.carbohydrates) as total_carbs,
    SUM(m.fats) as total_fats,
    SUM(m.proteins * 4 + m.carbohydrates * 4 + m.fats * 9) as total_calories
FROM daily_intake di
JOIN daily_intake_foods dif ON di.id = dif.daily_intake_id
JOIN food f ON dif.food_id = f.id
JOIN macronutrients m ON f.macronutrients_id = m.id
GROUP BY di.date;

-- Criar usuário para a aplicação
CREATE USER IF NOT EXISTS 'nutritrack_user'@'localhost' IDENTIFIED BY 'nutritrack_password';
GRANT SELECT, INSERT, UPDATE, DELETE ON nutritrack.* TO 'nutritrack_user'@'localhost';
FLUSH PRIVILEGES;