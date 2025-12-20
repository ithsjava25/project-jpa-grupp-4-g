INSERT INTO Continent (name)
VALUES ('Africa'),
       ('Antarctica'),
       ('Asia'),
       ('Europe'),
       ('North America'),
       ('Oceania'),
       ('South America');

INSERT INTO Country (name, continent_id)
VALUES ('Argentina', 7),
       ('Australia', 6),
       ('Brazil', 7),
       ('Canada', 5),
       ('China', 3),
       ('Egypt', 1),
       ('France', 4),
       ('Germany', 4),
       ('India', 3),
       ('Japan', 3),
       ('Mexico', 5),
       ('South Korea', 3),
       ('Sweden', 4),
       ('United Kingdom', 4),
       ('United States of America', 5);

INSERT INTO Location (name, type, country_id)
VALUES ('Buenos Aires', 'CAPITAL', 7),
       ('Canberra', 'CAPITAL', 9),
       ('Brasília', 'CAPITAL', 24),
       ('Ottawa', 'CAPITAL', 33),
       ('Beijing', 'CAPITAL', 37),
       ('Cairo', 'CAPITAL', 51),
       ('Paris', 'CAPITAL', 60),
       ('Berlin', 'CAPITAL', 64),
       ('New Delhi', 'CAPITAL', 76),
       ('Tokyo', 'CAPITAL', 84),
       ('Mexico City', 'CAPITAL', 109),
       ('Seoul', 'CAPITAL', 126),
       ('Gothenburg', 'CAPITAL', 165),
       ('London', 'CAPITAL', 183),
       ('Washington, D.C.', 'CAPITAL', 184);

INSERT INTO Transport (type)
VALUES ('AIRPLANE'),
       ('BUSS'),
       ('TRAIN');
