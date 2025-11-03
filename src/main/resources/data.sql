-- data.sql

-- ==========================================
-- INSERT GAMES (50 games)
-- ==========================================

-- RPG Games
INSERT INTO gl_games (id, title, description, status) VALUES
      ('a1b2c3d4-e5f6-4a5b-8c9d-0e1f2a3b4c5d', 'The Witcher 3: Wild Hunt', 'An epic open-world RPG adventure following Geralt of Rivia', 'ACTIVE'),
      ('b2c3d4e5-f6a7-4b5c-9d0e-1f2a3b4c5d6e', 'Dark Souls III', 'A challenging action RPG with deep combat mechanics', 'ACTIVE'),
      ('c3d4e5f6-a7b8-4c5d-0e1f-2a3b4c5d6e7f', 'Final Fantasy XIV', 'A massively multiplayer online role-playing game', 'ACTIVE'),
      ('d4e5f6a7-b8c9-4d5e-1f2a-3b4c5d6e7f8a', 'Persona 5 Royal', 'A stylish JRPG about high school students with supernatural powers', 'ACTIVE'),
      ('e5f6a7b8-c9d0-4e5f-2a3b-4c5d6e7f8a9b', 'Skyrim: Special Edition', 'An open-world fantasy RPG in the land of Skyrim', 'ACTIVE'),

-- Action/Adventure Games
      ('f6a7b8c9-d0e1-4f5a-3b4c-5d6e7f8a9b0c', 'God of War', 'Follow Kratos and Atreus on their epic Norse mythology adventure', 'ACTIVE'),
      ('a7b8c9d0-e1f2-4a5b-4c5d-6e7f8a9b0c1d', 'The Last of Us Part II', 'A gripping post-apocalyptic action-adventure game', 'ACTIVE'),
      ('b8c9d0e1-f2a3-4b5c-5d6e-7f8a9b0c1d2e', 'Red Dead Redemption 2', 'An immersive Wild West open-world adventure', 'ACTIVE'),
      ('c9d0e1f2-a3b4-4c5d-6e7f-8a9b0c1d2e3f', 'Ghost of Tsushima', 'A beautiful samurai adventure set in feudal Japan', 'ACTIVE'),
      ('d0e1f2a3-b4c5-4d5e-7f8a-9b0c1d2e3f4a', 'Assassins Creed Valhalla', 'Viking raids and conquests in medieval England', 'ACTIVE'),

-- FPS/Shooter Games
      ('e1f2a3b4-c5d6-4e5f-8a9b-0c1d2e3f4a5b', 'Call of Duty: Modern Warfare', 'Fast-paced modern military shooter', 'ACTIVE'),
      ('f2a3b4c5-d6e7-4f5a-9b0c-1d2e3f4a5b6c', 'Valorant', 'Tactical 5v5 character-based shooter', 'ACTIVE'),
      ('a3b4c5d6-e7f8-4a5b-0c1d-2e3f4a5b6c7d', 'Apex Legends', 'Battle royale with unique character abilities', 'ACTIVE'),
      ('b4c5d6e7-f8a9-4b5c-1d2e-3f4a5b6c7d8e', 'Overwatch 2', 'Team-based multiplayer hero shooter', 'ACTIVE'),
      ('c5d6e7f8-a9b0-4c5d-2e3f-4a5b6c7d8e9f', 'Counter-Strike 2', 'The legendary competitive FPS game', 'ACTIVE'),

-- Strategy Games
      ('d6e7f8a9-b0c1-4d5e-3f4a-5b6c7d8e9f0a', 'Civilization VI', 'Build an empire to stand the test of time', 'ACTIVE'),
      ('e7f8a9b0-c1d2-4e5f-4a5b-6c7d8e9f0a1b', 'Stellaris', 'Grand strategy game of space exploration and empire building', 'ACTIVE'),
      ('f8a9b0c1-d2e3-4f5a-5b6c-7d8e9f0a1b2c', 'XCOM 2', 'Turn-based tactical combat against alien invaders', 'ACTIVE'),
      ('a9b0c1d2-e3f4-4a5b-6c7d-8e9f0a1b2c3d', 'Total War: Warhammer III', 'Epic fantasy strategy with massive battles', 'ACTIVE'),
      ('b0c1d2e3-f4a5-4b5c-7d8e-9f0a1b2c3d4e', 'Age of Empires IV', 'Classic real-time strategy brought to modern era', 'ACTIVE'),

-- Sports Games
      ('c1d2e3f4-a5b6-4c5d-8e9f-0a1b2c3d4e5f', 'FIFA 24', 'The ultimate football simulation experience', 'ACTIVE'),
      ('d2e3f4a5-b6c7-4d5e-9f0a-1b2c3d4e5f6a', 'NBA 2K24', 'Realistic basketball simulation', 'ACTIVE'),
      ('e3f4a5b6-c7d8-4e5f-0a1b-2c3d4e5f6a7b', 'Madden NFL 24', 'American football at its finest', 'ACTIVE'),
      ('f4a5b6c7-d8e9-4f5a-1b2c-3d4e5f6a7b8c', 'Gran Turismo 7', 'The ultimate racing simulator', 'ACTIVE'),
      ('a5b6c7d8-e9f0-4a5b-2c3d-4e5f6a7b8c9d', 'Tony Hawks Pro Skater 1+2', 'Classic skateboarding action', 'ACTIVE'),

-- Indie/Puzzle Games
      ('b6c7d8e9-f0a1-4b5c-3d4e-5f6a7b8c9d0e', 'Hades', 'Rogue-like dungeon crawler with Greek mythology', 'ACTIVE'),
      ('c7d8e9f0-a1b2-4c5d-4e5f-6a7b8c9d0e1f', 'Celeste', 'Challenging platformer about climbing a mountain', 'ACTIVE'),
      ('d8e9f0a1-b2c3-4d5e-5f6a-7b8c9d0e1f2a', 'Hollow Knight', 'Beautiful metroidvania with challenging combat', 'ACTIVE'),
      ('e9f0a1b2-c3d4-4e5f-6a7b-8c9d0e1f2a3b', 'Stardew Valley', 'Relaxing farming and life simulation', 'ACTIVE'),
      ('f0a1b2c3-d4e5-4f5a-7b8c-9d0e1f2a3b4c', 'Undertale', 'Unique RPG where you dont have to kill anyone', 'ACTIVE'),

-- Horror/Survival Games
      ('a1b2c3d4-e5f6-5a6b-8c9d-0e1f2a3b4c5e', 'Resident Evil Village', 'Survival horror in a mysterious European village', 'ACTIVE'),
      ('b2c3d4e5-f6a7-5b6c-9d0e-1f2a3b4c5d6f', 'Dead Space Remake', 'Terrifying space horror reimagined', 'ACTIVE'),
      ('c3d4e5f6-a7b8-5c6d-0e1f-2a3b4c5d6e7a', 'The Evil Within 2', 'Psychological survival horror masterpiece', 'ACTIVE'),
      ('d4e5f6a7-b8c9-5d6e-1f2a-3b4c5d6e7f8b', 'Outlast', 'First-person horror with no combat', 'ACTIVE'),
      ('e5f6a7b8-c9d0-5e6f-2a3b-4c5d6e7f8a9c', 'Alien: Isolation', 'Survive against a single unstoppable alien', 'ACTIVE'),

-- Simulation Games
      ('f6a7b8c9-d0e1-5f6a-3b4c-5d6e7f8a9b0d', 'Microsoft Flight Simulator', 'Ultra-realistic flight simulation', 'ACTIVE'),
      ('a7b8c9d0-e1f2-5a6b-4c5d-6e7f8a9b0c1e', 'Cities: Skylines', 'Complex city-building simulation', 'ACTIVE'),
      ('b8c9d0e1-f2a3-5b6c-5d6e-7f8a9b0c1d2f', 'The Sims 4', 'Create and control virtual lives', 'ACTIVE'),
      ('c9d0e1f2-a3b4-5c6d-6e7f-8a9b0c1d2e3a', 'Euro Truck Simulator 2', 'Realistic truck driving across Europe', 'ACTIVE'),
      ('d0e1f2a3-b4c5-5d6e-7f8a-9b0c1d2e3f4b', 'Planet Zoo', 'Build and manage your dream zoo', 'ACTIVE'),

-- MOBA/Multiplayer
      ('e1f2a3b4-c5d6-5e6f-8a9b-0c1d2e3f4a5c', 'League of Legends', 'The worlds most popular MOBA', 'ACTIVE'),
      ('f2a3b4c5-d6e7-5f6a-9b0c-1d2e3f4a5b6d', 'Dota 2', 'Complex and competitive MOBA gameplay', 'ACTIVE'),
      ('a3b4c5d6-e7f8-5a6b-0c1d-2e3f4a5b6c7e', 'Fortnite', 'Battle royale with building mechanics', 'ACTIVE'),
      ('b4c5d6e7-f8a9-5b6c-1d2e-3f4a5b6c7d8f', 'Rocket League', 'Soccer with rocket-powered cars', 'ACTIVE'),
      ('c5d6e7f8-a9b0-5c6d-2e3f-4a5b6c7d8e9a', 'Minecraft', 'Infinite sandbox creativity and survival', 'ACTIVE'),

-- Additional Games
      ('d6e7f8a9-b0c1-5d6e-3f4a-5b6c7d8e9f0b', 'Elden Ring', 'Massive open-world dark fantasy RPG', 'ACTIVE'),
      ('e7f8a9b0-c1d2-5e6f-4a5b-6c7d8e9f0a1c', 'Baldurs Gate 3', 'Epic Dungeons & Dragons adventure', 'ACTIVE'),
      ('f8a9b0c1-d2e3-5f6a-5b6c-7d8e9f0a1b2d', 'Cyberpunk 2077', 'Futuristic open-world RPG in Night City', 'ACTIVE'),
      ('a9b0c1d2-e3f4-5a6b-6c7d-8e9f0a1b2c3e', 'Spider-Man: Miles Morales', 'Swing through New York as Miles Morales', 'ACTIVE'),
      ('b0c1d2e3-f4a5-5b6c-7d8e-9f0a1b2c3d4f', 'Horizon Forbidden West', 'Hunt robotic creatures in a post-apocalyptic world', 'ACTIVE');

-- ==========================================
-- INSERT GAME GENRES
-- ==========================================

-- RPG Games Genres
INSERT INTO gl_game_genres (game_id, genre) VALUES
    ('a1b2c3d4-e5f6-4a5b-8c9d-0e1f2a3b4c5d', 'RPG'),
    ('a1b2c3d4-e5f6-4a5b-8c9d-0e1f2a3b4c5d', 'ADVENTURE'),
    ('b2c3d4e5-f6a7-4b5c-9d0e-1f2a3b4c5d6e', 'RPG'),
    ('b2c3d4e5-f6a7-4b5c-9d0e-1f2a3b4c5d6e', 'ACTION'),
    ('c3d4e5f6-a7b8-4c5d-0e1f-2a3b4c5d6e7f', 'RPG'),
    ('c3d4e5f6-a7b8-4c5d-0e1f-2a3b4c5d6e7f', 'MMO'),
    ('d4e5f6a7-b8c9-4d5e-1f2a-3b4c5d6e7f8a', 'RPG'),
    ('e5f6a7b8-c9d0-4e5f-2a3b-4c5d6e7f8a9b', 'RPG'),
    ('e5f6a7b8-c9d0-4e5f-2a3b-4c5d6e7f8a9b', 'ADVENTURE'),

-- Action/Adventure Genres
    ('f6a7b8c9-d0e1-4f5a-3b4c-5d6e7f8a9b0c', 'ACTION'),
    ('f6a7b8c9-d0e1-4f5a-3b4c-5d6e7f8a9b0c', 'ADVENTURE'),
    ('a7b8c9d0-e1f2-4a5b-4c5d-6e7f8a9b0c1d', 'ACTION'),
    ('a7b8c9d0-e1f2-4a5b-4c5d-6e7f8a9b0c1d', 'ADVENTURE'),
    ('b8c9d0e1-f2a3-4b5c-5d6e-7f8a9b0c1d2e', 'ACTION'),
    ('b8c9d0e1-f2a3-4b5c-5d6e-7f8a9b0c1d2e', 'ADVENTURE'),
    ('c9d0e1f2-a3b4-4c5d-6e7f-8a9b0c1d2e3f', 'ACTION'),
    ('c9d0e1f2-a3b4-4c5d-6e7f-8a9b0c1d2e3f', 'ADVENTURE'),
    ('d0e1f2a3-b4c5-4d5e-7f8a-9b0c1d2e3f4a', 'ACTION'),
    ('d0e1f2a3-b4c5-4d5e-7f8a-9b0c1d2e3f4a', 'RPG'),

-- FPS/Shooter Genres
    ('e1f2a3b4-c5d6-4e5f-8a9b-0c1d2e3f4a5b', 'SHOOTER'),
    ('e1f2a3b4-c5d6-4e5f-8a9b-0c1d2e3f4a5b', 'ACTION'),
    ('f2a3b4c5-d6e7-4f5a-9b0c-1d2e3f4a5b6c', 'SHOOTER'),
    ('a3b4c5d6-e7f8-4a5b-0c1d-2e3f4a5b6c7d', 'SHOOTER'),
    ('a3b4c5d6-e7f8-4a5b-0c1d-2e3f4a5b6c7d', 'BATTLE_ROYALE'),
    ('b4c5d6e7-f8a9-4b5c-1d2e-3f4a5b6c7d8e', 'SHOOTER'),
    ('c5d6e7f8-a9b0-4c5d-2e3f-4a5b6c7d8e9f', 'SHOOTER'),

-- Strategy Genres
    ('d6e7f8a9-b0c1-4d5e-3f4a-5b6c7d8e9f0a', 'STRATEGY'),
    ('e7f8a9b0-c1d2-4e5f-4a5b-6c7d8e9f0a1b', 'STRATEGY'),
    ('e7f8a9b0-c1d2-4e5f-4a5b-6c7d8e9f0a1b', 'SIMULATION'),
    ('f8a9b0c1-d2e3-4f5a-5b6c-7d8e9f0a1b2c', 'STRATEGY'),
    ('a9b0c1d2-e3f4-4a5b-6c7d-8e9f0a1b2c3d', 'STRATEGY'),
    ('b0c1d2e3-f4a5-4b5c-7d8e-9f0a1b2c3d4e', 'STRATEGY'),

-- Sports Genres
    ('c1d2e3f4-a5b6-4c5d-8e9f-0a1b2c3d4e5f', 'SPORTS'),
    ('d2e3f4a5-b6c7-4d5e-9f0a-1b2c3d4e5f6a', 'SPORTS'),
    ('e3f4a5b6-c7d8-4e5f-0a1b-2c3d4e5f6a7b', 'SPORTS'),
    ('f4a5b6c7-d8e9-4f5a-1b2c-3d4e5f6a7b8c', 'RACING'),
    ('f4a5b6c7-d8e9-4f5a-1b2c-3d4e5f6a7b8c', 'SIMULATION'),
    ('a5b6c7d8-e9f0-4a5b-2c3d-4e5f6a7b8c9d', 'SPORTS'),

-- Indie/Puzzle Genres
    ('b6c7d8e9-f0a1-4b5c-3d4e-5f6a7b8c9d0e', 'ROGUELIKE'),
    ('b6c7d8e9-f0a1-4b5c-3d4e-5f6a7b8c9d0e', 'ACTION'),
    ('c7d8e9f0-a1b2-4c5d-4e5f-6a7b8c9d0e1f', 'PLATFORMER'),
    ('d8e9f0a1-b2c3-4d5e-5f6a-7b8c9d0e1f2a', 'PLATFORMER'),
    ('d8e9f0a1-b2c3-4d5e-5f6a-7b8c9d0e1f2a', 'ADVENTURE'),
    ('e9f0a1b2-c3d4-4e5f-6a7b-8c9d0e1f2a3b', 'SIMULATION'),
    ('f0a1b2c3-d4e5-4f5a-7b8c-9d0e1f2a3b4c', 'RPG'),
    ('f0a1b2c3-d4e5-4f5a-7b8c-9d0e1f2a3b4c', 'INDIE'),

-- Horror/Survival Genres
    ('a1b2c3d4-e5f6-5a6b-8c9d-0e1f2a3b4c5e', 'HORROR'),
    ('a1b2c3d4-e5f6-5a6b-8c9d-0e1f2a3b4c5e', 'SURVIVAL'),
    ('b2c3d4e5-f6a7-5b6c-9d0e-1f2a3b4c5d6f', 'HORROR'),
    ('c3d4e5f6-a7b8-5c6d-0e1f-2a3b4c5d6e7a', 'HORROR'),
    ('d4e5f6a7-b8c9-5d6e-1f2a-3b4c5d6e7f8b', 'HORROR'),
    ('e5f6a7b8-c9d0-5e6f-2a3b-4c5d6e7f8a9c', 'HORROR'),
    ('e5f6a7b8-c9d0-5e6f-2a3b-4c5d6e7f8a9c', 'SURVIVAL'),

-- Simulation Genres
    ('f6a7b8c9-d0e1-5f6a-3b4c-5d6e7f8a9b0d', 'SIMULATION'),
    ('a7b8c9d0-e1f2-5a6b-4c5d-6e7f8a9b0c1e', 'SIMULATION'),
    ('a7b8c9d0-e1f2-5a6b-4c5d-6e7f8a9b0c1e', 'STRATEGY'),
    ('b8c9d0e1-f2a3-5b6c-5d6e-7f8a9b0c1d2f', 'SIMULATION'),
    ('c9d0e1f2-a3b4-5c6d-6e7f-8a9b0c1d2e3a', 'SIMULATION'),
    ('d0e1f2a3-b4c5-5d6e-7f8a-9b0c1d2e3f4b', 'SIMULATION'),

-- MOBA/Multiplayer Genres
    ('e1f2a3b4-c5d6-5e6f-8a9b-0c1d2e3f4a5c', 'MOBA'),
    ('f2a3b4c5-d6e7-5f6a-9b0c-1d2e3f4a5b6d', 'MOBA'),
    ('a3b4c5d6-e7f8-5a6b-0c1d-2e3f4a5b6c7e', 'BATTLE_ROYALE'),
    ('a3b4c5d6-e7f8-5a6b-0c1d-2e3f4a5b6c7e', 'SHOOTER'),
    ('b4c5d6e7-f8a9-5b6c-1d2e-3f4a5b6c7d8f', 'SPORTS'),
    ('c5d6e7f8-a9b0-5c6d-2e3f-4a5b6c7d8e9a', 'SANDBOX'),
    ('c5d6e7f8-a9b0-5c6d-2e3f-4a5b6c7d8e9a', 'SURVIVAL'),

-- Additional Games Genres
    ('d6e7f8a9-b0c1-5d6e-3f4a-5b6c7d8e9f0b', 'RPG'),
    ('d6e7f8a9-b0c1-5d6e-3f4a-5b6c7d8e9f0b', 'ACTION'),
    ('e7f8a9b0-c1d2-5e6f-4a5b-6c7d8e9f0a1c', 'RPG'),
    ('f8a9b0c1-d2e3-5f6a-5b6c-7d8e9f0a1b2d', 'RPG'),
    ('f8a9b0c1-d2e3-5f6a-5b6c-7d8e9f0a1b2d', 'ACTION'),
    ('a9b0c1d2-e3f4-5a6b-6c7d-8e9f0a1b2c3e', 'ACTION'),
    ('a9b0c1d2-e3f4-5a6b-6c7d-8e9f0a1b2c3e', 'ADVENTURE'),
    ('b0c1d2e3-f4a5-5b6c-7d8e-9f0a1b2c3d4f', 'ACTION'),
    ('b0c1d2e3-f4a5-5b6c-7d8e-9f0a1b2c3d4f', 'RPG');

-- ==========================================
-- INSERT PLAYERS (20 players)
-- ==========================================

INSERT INTO gl_players (id, username, first_name, last_name, status) VALUES
 ('11111111-1111-1111-1111-111111111111', 'GamerPro2024', 'John', 'Smith', 'ACTIVE'),
 ('22222222-2222-2222-2222-222222222222', 'DragonSlayer99', 'Emma', 'Johnson', 'ACTIVE'),
 ('33333333-3333-3333-3333-333333333333', 'NightHawk', 'Michael', 'Williams', 'ACTIVE'),
 ('44444444-4444-4444-4444-444444444444', 'PixelQueen', 'Sarah', 'Brown', 'ACTIVE'),
 ('55555555-5555-5555-5555-555555555555', 'ShadowNinja', 'David', 'Jones', 'ACTIVE'),
 ('66666666-6666-6666-6666-666666666666', 'PhoenixRising', 'Emily', 'Garcia', 'ACTIVE'),
 ('77777777-7777-7777-7777-777777777777', 'ThunderStrike', 'Daniel', 'Martinez', 'ACTIVE'),
 ('88888888-8888-8888-8888-888888888888', 'MysticMage', 'Jessica', 'Rodriguez', 'ACTIVE'),
 ('99999999-9999-9999-9999-999999999999', 'IronFist', 'Christopher', 'Hernandez', 'ACTIVE'),
 ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'CrystalSword', 'Ashley', 'Lopez', 'ACTIVE'),
 ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'BlazeRunner', 'Matthew', 'Gonzalez', 'ACTIVE'),
 ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'FrostByte', 'Amanda', 'Wilson', 'ACTIVE'),
 ('dddddddd-dddd-dddd-dddd-dddddddddddd', 'StormChaser', 'Joshua', 'Anderson', 'ACTIVE'),
 ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'LunarEclipse', 'Megan', 'Thomas', 'ACTIVE'),
 ('ffffffff-ffff-ffff-ffff-ffffffffffff', 'VenomStrike', 'Andrew', 'Taylor', 'ACTIVE'),
 ('10101010-1010-1010-1010-101010101010', 'SolarFlare', 'Samantha', 'Moore', 'ACTIVE'),
 ('20202020-2020-2020-2020-202020202020', 'CosmicWarrior', 'Ryan', 'Jackson', 'ACTIVE'),
 ('30303030-3030-3030-3030-303030303030', 'TitanForce', 'Lauren', 'Martin', 'ACTIVE'),
 ('40404040-4040-4040-4040-404040404040', 'VortexHunter', 'Brandon', 'Lee', 'ACTIVE'),
 ('50505050-5050-5050-5050-505050505050', 'NeonGhost', 'Olivia', 'Perez', 'ACTIVE');

-- ==========================================
-- INSERT PLAYER-GAME RELATIONSHIPS
-- (Players loving various games)
-- ==========================================

-- GamerPro2024 loves RPG and Action games
INSERT INTO gl_player_loved_games (player_id, game_id) VALUES
     ('11111111-1111-1111-1111-111111111111', 'a1b2c3d4-e5f6-4a5b-8c9d-0e1f2a3b4c5d'),
     ('11111111-1111-1111-1111-111111111111', 'b2c3d4e5-f6a7-4b5c-9d0e-1f2a3b4c5d6e'),
     ('11111111-1111-1111-1111-111111111111', 'e5f6a7b8-c9d0-4e5f-2a3b-4c5d6e7f8a9b'),
     ('11111111-1111-1111-1111-111111111111', 'd6e7f8a9-b0c1-5d6e-3f4a-5b6c7d8e9f0b'),
     ('11111111-1111-1111-1111-111111111111', 'f6a7b8c9-d0e1-4f5a-3b4c-5d6e7f8a9b0c');

-- DragonSlayer99 loves RPG games
INSERT INTO gl_player_loved_games (player_id, game_id) VALUES
    ('22222222-2222-2222-2222-222222222222', 'a1b2c3d4-e5f6-4a5b-8c9d-0e1f2a3b4c5d'),
    ('22222222-2222-2222-2222-222222222222', 'c3d4e5f6-a7b8-4c5d-0e1f-2a3b4c5d6e7f'),
    ('22222222-2222-2222-2222-222222222222', 'd4e5f6a7-b8c9-4d5e-1f2a-3b4c5d6e7f8a'),
    ('22222222-2222-2222-2222-222222222222', 'e7f8a9b0-c1d2-5e6f-4a5b-6c7d8e9f0a1c'),
    ('22222222-2222-2222-2222-222222222222', 'd6e7f8a9-b0c1-5d6e-3f4a-5b6c7d8e9f0b');

-- NightHawk loves FPS and shooters
INSERT INTO gl_player_loved_games (player_id, game_id) VALUES
    ('33333333-3333-3333-3333-333333333333', 'e1f2a3b4-c5d6-4e5f-8a9b-0c1d2e3f4a5b'),
    ('33333333-3333-3333-3333-333333333333', 'f2a3b4c5-d6e7-4f5a-9b0c-1d2e3f4a5b6c'),
    ('33333333-3333-3333-3333-333333333333', 'a3b4c5d6-e7f8-4a5b-0c1d-2e3f4a5b6c7d'),
    ('33333333-3333-3333-3333-333333333333', 'c5d6e7f8-a9b0-4c5d-2e3f-4a5b6c7d8e9f'),
    ('33333333-3333-3333-3333-333333333333', 'b4c5d6e7-f8a9-4b5c-1d2e-3f4a5b6c7d8e');

-- PixelQueen loves indie and puzzle games
INSERT INTO gl_player_loved_games (player_id, game_id) VALUES
    ('44444444-4444-4444-4444-444444444444', 'b6c7d8e9-f0a1-4b5c-3d4e-5f6a7b8c9d0e'),
    ('44444444-4444-4444-4444-444444444444', 'c7d8e9f0-a1b2-4c5d-4e5f-6a7b8c9d0e1f'),
    ('44444444-4444-4444-4444-444444444444', 'd8e9f0a1-b2c3-4d5e-5f6a-7b8c9d0e1f2a'),
    ('44444444-4444-4444-4444-444444444444', 'e9f0a1b2-c3d4-4e5f-6a7b-8c9d0e1f2a3b'),
    ('44444444-4444-4444-4444-444444444444', 'f0a1b2c3-d4e5-4f5a-7b8c-9d0e1f2a3b4c'),
    ('44444444-4444-4444-4444-444444444444', 'c5d6e7f8-a9b0-5c6d-2e3f-4a5b6c7d8e9a');

-- ShadowNinja loves action-adventure
INSERT INTO gl_player_loved_games (player_id, game_id) VALUES
    ('55555555-5555-5555-5555-555555555555', 'f6a7b8c9-d0e1-4f5a-3b4c-5d6e7f8a9b0c'),
    ('55555555-5555-5555-5555-555555555555', 'a7b8c9d0-e1f2-4a5b-4c5d-6e7f8a9b0c1d'),
    ('55555555-5555-5555-5555-555555555555', 'c9d0e1f2-a3b4-4c5d-6e7f-8a9b0c1d2e3f'),
    ('55555555-5555-5555-5555-555555555555', 'a9b0c1d2-e3f4-5a6b-6c7d-8e9f0a1b2c3e'),
    ('55555555-5555-5555-5555-555555555555', 'b0c1d2e3-f4a5-5b6c-7d8e-9f0a1b2c3d4f');

-- PhoenixRising loves strategy games
INSERT INTO gl_player_loved_games (player_id, game_id) VALUES
    ('66666666-6666-6666-6666-666666666666', 'd6e7f8a9-b0c1-4d5e-3f4a-5b6c7d8e9f0a'),
    ('66666666-6666-6666-6666-666666666666', 'e7f8a9b0-c1d2-4e5f-4a5b-6c7d8e9f0a1b'),
    ('66666666-6666-6666-6666-666666666666', 'f8a9b0c1-d2e3-4f5a-5b6c-7d8e9f0a1b2c'),
    ('66666666-6666-6666-6666-666666666666', 'a9b0c1d2-e3f4-4a5b-6c7d-8e9f0a1b2c3d'),
    ('66666666-6666-6666-6666-666666666666', 'b0c1d2e3-f4a5-4b5c-7d8e-9f0a1b2c3d4e');

-- ThunderStrike loves sports games
INSERT INTO gl_player_loved_games (player_id, game_id) VALUES
    ('77777777-7777-7777-7777-777777777777', 'c1d2e3f4-a5b6-4c5d-8e9f-0a1b2c3d4e5f'),
    ('77777777-7777-7777-7777-777777777777', 'd2e3f4a5-b6c7-4d5e-9f0a-1b2c3d4e5f6a'),
    ('77777777-7777-7777-7777-777777777777', 'e3f4a5b6-c7d8-4e5f-0a1b-2c3d4e5f6a7b'),
    ('77777777-7777-7777-7777-777777777777', 'f4a5b6c7-d8e9-4f5a-1b2c-3d4e5f6a7b8c'),
    ('77777777-7777-7777-7777-777777777777', 'b4c5d6e7-f8a9-5b6c-1d2e-3f4a5b6c7d8f');

-- MysticMage loves horror games
INSERT INTO gl_player_loved_games (player_id, game_id) VALUES
    ('88888888-8888-8888-8888-888888888888', 'a1b2c3d4-e5f6-5a6b-8c9d-0e1f2a3b4c5e'),
    ('88888888-8888-8888-8888-888888888888', 'b2c3d4e5-f6a7-5b6c-9d0e-1f2a3b4c5d6f'),
    ('88888888-8888-8888-8888-888888888888', 'c3d4e5f6-a7b8-5c6d-0e1f-2a3b4c5d6e7a'),
    ('88888888-8888-8888-8888-888888888888', 'd4e5f6a7-b8c9-5d6e-1f2a-3b4c5d6e7f8b'),
    ('88888888-8888-8888-8888-888888888888', 'e5f6a7b8-c9d0-5e6f-2a3b-4c5d6e7f8a9c');

-- IronFist loves all fighting/action games
INSERT INTO gl_player_loved_games (player_id, game_id) VALUES
    ('99999999-9999-9999-9999-999999999999', 'f6a7b8c9-d0e1-4f5a-3b4c-5d6e7f8a9b0c'),
    ('99999999-9999-9999-9999-999999999999', 'b2c3d4e5-f6a7-4b5c-9d0e-1f2a3b4c5d6e'),
    ('99999999-9999-9999-9999-999999999999', 'd6e7f8a9-b0c1-5d6e-3f4a-5b6c7d8e9f0b'),
    ('99999999-9999-9999-9999-999999999999', 'b8c9d0e1-f2a3-4b5c-5d6e-7f8a9b0c1d2e');

-- CrystalSword loves simulation games
INSERT INTO gl_player_loved_games (player_id, game_id) VALUES
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'e9f0a1b2-c3d4-4e5f-6a7b-8c9d0e1f2a3b'),
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'f6a7b8c9-d0e1-5f6a-3b4c-5d6e7f8a9b0d'),
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'a7b8c9d0-e1f2-5a6b-4c5d-6e7f8a9b0c1e'),
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'b8c9d0e1-f2a3-5b6c-5d6e-7f8a9b0c1d2f'),
    ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'd0e1f2a3-b4c5-5d6e-7f8a-9b0c1d2e3f4b');

-- BlazeRunner loves racing and action
INSERT INTO gl_player_loved_games (player_id, game_id) VALUES
    ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'f4a5b6c7-d8e9-4f5a-1b2c-3d4e5f6a7b8c'),
    ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'c9d0e1f2-a3b4-5c6d-6e7f-8a9b0c1d2e3a'),
    ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'b8c9d0e1-f2a3-4b5c-5d6e-7f8a9b0c1d2e');

-- FrostByte loves everything
INSERT INTO gl_player_loved_games (player_id, game_id) VALUES
    ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'a1b2c3d4-e5f6-4a5b-8c9d-0e1f2a3b4c5d'),
    ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'c5d6e7f8-a9b0-5c6d-2e3f-4a5b6c7d8e9a'),
    ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'e1f2a3b4-c5d6-5e6f-8a9b-0c1d2e3f4a5c'),
    ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'b6c7d8e9-f0a1-4b5c-3d4e-5f6a7b8c9d0e'),
    ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'd6e7f8a9-b0c1-4d5e-3f4a-5b6c7d8e9f0a'),
    ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'f6a7b8c9-d0e1-4f5a-3b4c-5d6e7f8a9b0c'),
    ('cccccccc-cccc-cccc-cccc-cccccccccccc', 'a3b4c5d6-e7f8-5a6b-0c1d-2e3f4a5b6c7e');

-- StormChaser loves multiplayer games
INSERT INTO gl_player_loved_games (player_id, game_id) VALUES
    ('dddddddd-dddd-dddd-dddd-dddddddddddd', 'e1f2a3b4-c5d6-5e6f-8a9b-0c1d2e3f4a5c'),
    ('dddddddd-dddd-dddd-dddd-dddddddddddd', 'f2a3b4c5-d6e7-5f6a-9b0c-1d2e3f4a5b6d'),
    ('dddddddd-dddd-dddd-dddd-dddddddddddd', 'a3b4c5d6-e7f8-5a6b-0c1d-2e3f4a5b6c7e'),
    ('dddddddd-dddd-dddd-dddd-dddddddddddd', 'c5d6e7f8-a9b0-5c6d-2e3f-4a5b6c7d8e9a'),
    ('dddddddd-dddd-dddd-dddd-dddddddddddd', 'b4c5d6e7-f8a9-5b6c-1d2e-3f4a5b6c7d8f');

-- LunarEclipse loves story-driven games
INSERT INTO gl_player_loved_games (player_id, game_id) VALUES
    ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'a7b8c9d0-e1f2-4a5b-4c5d-6e7f8a9b0c1d'),
    ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'e7f8a9b0-c1d2-5e6f-4a5b-6c7d8e9f0a1c'),
    ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'f0a1b2c3-d4e5-4f5a-7b8c-9d0e1f2a3b4c'),
    ('eeeeeeee-eeee-eeee-eeee-eeeeeeeeeeee', 'f8a9b0c1-d2e3-5f6a-5b6c-7d8e9f0a1b2d');

-- VenomStrike loves competitive games
INSERT INTO gl_player_loved_games (player_id, game_id) VALUES
    ('ffffffff-ffff-ffff-ffff-ffffffffffff', 'f2a3b4c5-d6e7-4f5a-9b0c-1d2e3f4a5b6c'),
    ('ffffffff-ffff-ffff-ffff-ffffffffffff', 'c5d6e7f8-a9b0-4c5d-2e3f-4a5b6c7d8e9f'),
    ('ffffffff-ffff-ffff-ffff-ffffffffffff', 'e1f2a3b4-c5d6-5e6f-8a9b-0c1d2e3f4a5c'),
    ('ffffffff-ffff-ffff-ffff-ffffffffffff', 'f2a3b4c5-d6e7-5f6a-9b0c-1d2e3f4a5b6d');

-- SolarFlare loves open-world games
INSERT INTO gl_player_loved_games (player_id, game_id) VALUES
    ('10101010-1010-1010-1010-101010101010', 'a1b2c3d4-e5f6-4a5b-8c9d-0e1f2a3b4c5d'),
    ('10101010-1010-1010-1010-101010101010', 'e5f6a7b8-c9d0-4e5f-2a3b-4c5d6e7f8a9b'),
    ('10101010-1010-1010-1010-101010101010', 'b8c9d0e1-f2a3-4b5c-5d6e-7f8a9b0c1d2e'),
    ('10101010-1010-1010-1010-101010101010', 'd6e7f8a9-b0c1-5d6e-3f4a-5b6c7d8e9f0b'),
    ('10101010-1010-1010-1010-101010101010', 'c5d6e7f8-a9b0-5c6d-2e3f-4a5b6c7d8e9a');

-- CosmicWarrior loves sci-fi games
INSERT INTO gl_player_loved_games (player_id, game_id) VALUES
    ('20202020-2020-2020-2020-202020202020', 'e7f8a9b0-c1d2-4e5f-4a5b-6c7d8e9f0a1b'),
    ('20202020-2020-2020-2020-202020202020', 'f8a9b0c1-d2e3-5f6a-5b6c-7d8e9f0a1b2d'),
    ('20202020-2020-2020-2020-202020202020', 'e5f6a7b8-c9d0-5e6f-2a3b-4c5d6e7f8a9c');

-- TitanForce loves everything popular
INSERT INTO gl_player_loved_games (player_id, game_id) VALUES
    ('30303030-3030-3030-3030-303030303030', 'a1b2c3d4-e5f6-4a5b-8c9d-0e1f2a3b4c5d'),
    ('30303030-3030-3030-3030-303030303030', 'e1f2a3b4-c5d6-5e6f-8a9b-0c1d2e3f4a5c'),
    ('30303030-3030-3030-3030-303030303030', 'c5d6e7f8-a9b0-5c6d-2e3f-4a5b6c7d8e9a'),
    ('30303030-3030-3030-3030-303030303030', 'd6e7f8a9-b0c1-5d6e-3f4a-5b6c7d8e9f0b'),
    ('30303030-3030-3030-3030-303030303030', 'f6a7b8c9-d0e1-4f5a-3b4c-5d6e7f8a9b0c'),
    ('30303030-3030-3030-3030-303030303030', 'b6c7d8e9-f0a1-4b5c-3d4e-5f6a7b8c9d0e');

-- VortexHunter loves battle royale
INSERT INTO gl_player_loved_games (player_id, game_id) VALUES
    ('40404040-4040-4040-4040-404040404040', 'a3b4c5d6-e7f8-4a5b-0c1d-2e3f4a5b6c7d'),
    ('40404040-4040-4040-4040-404040404040', 'a3b4c5d6-e7f8-5a6b-0c1d-2e3f4a5b6c7e');

-- NeonGhost loves indie games
INSERT INTO gl_player_loved_games (player_id, game_id) VALUES
    ('50505050-5050-5050-5050-505050505050', 'b6c7d8e9-f0a1-4b5c-3d4e-5f6a7b8c9d0e'),
    ('50505050-5050-5050-5050-505050505050', 'c7d8e9f0-a1b2-4c5d-4e5f-6a7b8c9d0e1f'),
    ('50505050-5050-5050-5050-505050505050', 'd8e9f0a1-b2c3-4d5e-5f6a-7b8c9d0e1f2a'),
    ('50505050-5050-5050-5050-505050505050', 'f0a1b2c3-d4e5-4f5a-7b8c-9d0e1f2a3b4c'),
    ('50505050-5050-5050-5050-505050505050', 'e9f0a1b2-c3d4-4e5f-6a7b-8c9d0e1f2a3b');