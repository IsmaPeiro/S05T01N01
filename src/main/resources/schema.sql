CREATE TABLE IF NOT EXISTS players (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nickname VARCHAR(255),
    card_games_won INT,
    card_games_lost INT,
    card_games_draw INT
);