use travelGame;
INSERT INTO Continent (name)
VALUES ('Africa'),
       ('Antarctica'),
       ('Asia'),
       ('Europe'),
       ('North America'),
       ('Oceania'),
       ('South America');

INSERT INTO Country (name, continent_id)
VALUES ('Afghanistan', 3),
       ('Albania', 4),
       ('Algeria', 1),
       ('Andorra', 4),
       ('Angola', 1),
       ('Antigua and Barbuda', 5),
       ('Argentina', 7),
       ('Armenia', 3),
       ('Australia', 6),
       ('Austria', 4),
       ('Azerbaijan', 3),
       ('Bahamas', 5),
       ('Bahrain', 3),
       ('Bangladesh', 3),
       ('Barbados', 5),
       ('Belarus', 4),
       ('Belgium', 4),
       ('Belize', 5),
       ('Benin', 1),
       ('Bhutan', 3),
       ('Bolivia', 7),
       ('Bosnia and Herzegovina', 4),
       ('Botswana', 1),
       ('Brazil', 7),
       ('Brunei', 3),
       ('Bulgaria', 4),
       ('Burkina Faso', 1),
       ('Burundi', 1),
       ('Côte d''Ivoire', 1),
       ('Cabo Verde', 1),
       ('Cambodia', 3),
       ('Cameroon', 1),
       ('Canada', 5),
       ('Central African Republic', 1),
       ('Chad', 1),
       ('Chile', 7),
       ('China', 3),
       ('Colombia', 7),
       ('Comoros', 1),
       ('Costa Rica', 5),
       ('Croatia', 4),
       ('Cuba', 5),
       ('Cyprus', 4),
       ('Czech Republic', 4),
       ('Democratic Republic of the Congo', 1),
       ('Denmark', 4),
       ('Djibouti', 1),
       ('Dominica', 5),
       ('Dominican Republic', 5),
       ('Ecuador', 7),
       ('Egypt', 1),
       ('El Salvador', 5),
       ('Equatorial Guinea', 1),
       ('Eritrea', 1),
       ('Estonia', 4),
       ('Eswatini', 1),
       ('Ethiopia', 1),
       ('Fiji', 6),
       ('Finland', 4),
       ('France', 4),
       ('Gabon', 1),
       ('Gambia', 1),
       ('Georgia', 3),
       ('Germany', 4),
       ('Ghana', 1),
       ('Greece', 4),
       ('Grenada', 7),
       ('Guatemala', 5),
       ('Guinea', 1),
       ('Guinea-Bissau', 1),
       ('Guyana', 7),
       ('Haiti', 5),
       ('Honduras', 5),
       ('Hungary', 4),
       ('Iceland', 4),
       ('India', 3),
       ('Indonesia', 3),
       ('Iran', 3),
       ('Iraq', 3),
       ('Ireland', 4),
       ('Israel', 3),
       ('Italy', 4),
       ('Jamaica', 5),
       ('Japan', 3),
       ('Jordan', 3),
       ('Kazakhstan', 3),
       ('Kenya', 1),
       ('Kiribati', 6),
       ('Kuwait', 3),
       ('Kyrgyzstan', 3),
       ('Laos', 3),
       ('Latvia', 4),
       ('Lebanon', 3),
       ('Lesotho', 1),
       ('Liberia', 1),
       ('Libya', 1),
       ('Liechtenstein', 4),
       ('Lithuania', 4),
       ('Luxembourg', 4),
       ('Madagascar', 1),
       ('Malawi', 1),
       ('Malaysia', 3),
       ('Maldives', 3),
       ('Mali', 1),
       ('Malta', 4),
       ('Marshall Islands', 6),
       ('Mauritania', 1),
       ('Mauritius', 1),
       ('Mexico', 5),
       ('Micronesia', 6),
       ('Moldova', 4),
       ('Monaco', 4),
       ('Mongolia', 3),
       ('Montenegro', 4),
       ('Morocco', 1),
       ('Mozambique', 1),
       ('Myanmar', 3),
       ('Namibia', 1),
       ('Nauru', 6),
       ('Nepal', 3),
       ('Netherlands', 4),
       ('New Zealand', 6),
       ('Nicaragua', 5),
       ('Niger', 1),
       ('Nigeria', 1),
       ('North Korea', 3),
       ('North Macedonia', 4),
       ('Norway', 4),
       ('Oman', 3),
       ('Pakistan', 3),
       ('Palau', 6),
       ('Panama', 5),
       ('Papua New Guinea', 6),
       ('Paraguay', 7),
       ('Peru', 7),
       ('Philippines', 3),
       ('Poland', 4),
       ('Portugal', 4),
       ('Qatar', 3),
       ('Romania', 4),
       ('Russia', 4),
       ('Rwanda', 1),
       ('Saint Kitts and Nevis', 5),
       ('Saint Lucia', 5),
       ('Saint Vincent and the Grenadines', 5),
       ('Samoa', 6),
       ('San Marino', 4),
       ('Sao Tome and Principe', 1),
       ('Saudi Arabia', 3),
       ('Senegal', 1),
       ('Serbia', 4),
       ('Seychelles', 1),
       ('Sierra Leone', 1),
       ('Singapore', 3),
       ('Slovakia', 4),
       ('Slovenia', 4),
       ('Solomon Islands', 6),
       ('Somalia', 1),
       ('South Africa', 1),
       ('South Korea', 3),
       ('South Sudan', 1),
       ('Spain', 4),
       ('Sri Lanka', 3),
       ('Sudan', 1),
       ('Suriname', 7),
       ('Sweden', 4),
       ('Switzerland', 4),
       ('Syria', 3),
       ('Taiwan', 3),
       ('Tajikistan', 3),
       ('Tanzania', 1),
       ('Thailand', 3),
       ('Timor-Leste', 3),
       ('Togo', 1),
       ('Tonga', 6),
       ('Trinidad and Tobago', 5),
       ('Tunisia', 1),
       ('Turkey', 3),
       ('Turkmenistan', 3),
       ('Tuvalu', 6),
       ('Uganda', 1),
       ('Ukraine', 4),
       ('United Arab Emirates', 3),
       ('United Kingdom', 4),
       ('United States of America', 5),
       ('Uruguay', 7),
       ('Uzbekistan', 3),
       ('Vanuatu', 6),
       ('Vatican City', 4),
       ('Venezuela', 7),
       ('Vietnam', 3),
       ('Yemen', 3),
       ('Zambia', 1),
       ('Zimbabwe', 1);

INSERT INTO Location (name, type, x, y, country_id)
VALUES ('Algiers', 'CAPITAL', 25, 29, 3),
       ('Luanda', 'CAPITAL', 26, 16, 5),
       ('Buenos Aires', 'CAPITAL', 16, 9, 7),
       ('Córdoba', 'CITY', 16, 10, 7),
       ('Perito Moreno Glacier', 'SITE', 15, 4, 7),
       ('Sydney', 'CITY', 45, 9, 9),
       ('Perth', 'CITY', 41, 10, 9),
       ('Uluru', 'SITE', 43, 12, 9),
       ('Vienna', 'CAPITAL', 27, 32, 10),
       ('Baku City Circuit', 'SITE', 31, 30, 11),
       ('Dhaka', 'CAPITAL', 37, 26, 14),
       ('Minsk', 'CAPITAL', 28, 34, 16),
       ('Mogilev', 'CITY', 29, 34, 16),
       ('Brussels', 'CAPITAL', 25, 33, 17),
       ('Belize City', 'CITY', 13, 22, 18),
       ('La Paz', 'CAPITAL', 16, 14, 21),
       ('Lake Titicaca', 'SITE', 15, 14, 21),
       ('Brasília', 'CAPITAL', 18, 15, 24),
       ('São Paulo', 'CITY', 18, 12, 24),
       ('Rio de Janeiro', 'CITY', 19, 12, 24),
       ('Manaus', 'CITY', 16, 18, 24),
       ('Salvador', 'CITY', 19, 15, 24),
       ('Recife', 'CITY', 20, 16, 24),
       ('São Luís', 'CITY', 18, 18, 24),
       ('Christ the Redeemer', 'SITE', 19, 13, 24),
       ('Amazonas', 'SITE', 16, 17, 24),
       ('Iguazu Falls', 'SITE', 17, 12, 24),
       ('Bandar Seri Begawan', 'CAPITAL', 41, 20, 25),
       ('Sofia', 'CAPITAL', 28, 31, 26),
       ('Abidjan', 'CITY', 24, 20, 29),
       ('Angkor Wat', 'SITE', 39, 22, 31),
       ('Ottawa', 'CAPITAL', 14, 31, 33),
       ('Edmonton', 'CITY', 9, 35, 33),
       ('Toronto', 'CITY', 13, 31, 33),
       ('Bangui', 'CAPITAL', 27, 20, 34),
       ('N''Djamena', 'CAPITAL', 27, 22, 35),
       ('Santiago', 'CAPITAL', 15, 9, 36),
       ('Valparaíso', 'CITY', 14, 9, 36),
       ('Easter Island', 'SITE', 9, 12, 36),
       ('Torres del Paine', 'SITE', 14, 4, 36),
       ('Atacama Desert', 'SITE', 15, 12, 36),
       ('Beijing', 'CAPITAL', 41, 29, 37),
       ('Shanghai', 'CITY', 41, 27, 37),
       ('Guangzhou', 'CITY', 40, 25, 37),
       ('Wuhan', 'CITY', 40, 27, 37),
       ('Hong Kong', 'CITY', 40, 24, 37),
       ('Chongqing', 'CITY', 39, 27, 37),
       ('Qingdao', 'CITY', 41, 28, 37),
       ('Zhengzhou', 'CITY', 40, 28, 37),
       ('The Great Wall of China', 'SITE', 41, 30, 37),
       ('Terracotta Army', 'SITE', 39, 28, 37),
       ('Yungang Grottoes', 'SITE', 40, 29, 37),
       ('Yandang Mountain', 'SITE', 41, 26, 37),
       ('Changsha', 'CITY', 40, 26, 37),
       ('Bogotá', 'CAPITAL', 14, 20, 38),
       ('Zagreb', 'CAPITAL', 27, 31, 41),
       ('Havana', 'CAPITAL', 13, 24, 42),
       ('Ayia Napa', 'CITY', 29, 28, 43),
       ('Kinshasa', 'CAPITAL', 27, 17, 45),
       ('Copenhagen', 'CAPITAL', 26, 35, 46),
       ('Nuuk', 'CITY', 17, 38, 46),
       ('Djibouti', 'CAPITAL', 30, 22, 47),
       ('Santo Domingo', 'CAPITAL', 15, 23, 49),
       ('Cairo', 'CAPITAL', 29, 27, 51),
       ('Pyramids of Giza', 'SITE', 29, 26, 51),
       ('Alexandria', 'CITY', 28, 27, 51),
       ('Quito', 'CAPITAL', 14, 18, 50),
       ('Galápagos Islands', 'SITE', 12, 18, 50),
       ('Asmara', 'CAPITAL', 30, 23, 54),
       ('Tallinn', 'CAPITAL', 28, 36, 55),
       ('Addis Ababa', 'CAPITAL', 30, 21, 57),
       ('Helsinki', 'CAPITAL', 28, 37, 59),
       ('Vasa', 'CITY', 28, 38, 59),
       ('Paris', 'CAPITAL', 25, 32, 60),
       ('Marseille', 'CITY', 25, 31, 60),
       ('Bordeaux', 'CITY', 24, 31, 60),
       ('Brest Castle', 'SITE', 24, 32, 60),
       ('Tbilisi', 'CAPITAL', 30, 30, 63),
       ('Berlin', 'CAPITAL', 27, 34, 64),
       ('Frankfurt', 'CITY', 26, 33, 64),
       ('Neuschwanstein', 'SITE', 26, 32, 64),
       ('Athens', 'CAPITAL', 28, 29, 66),
       ('Knossos', 'SITE', 28, 28, 66),
       ('Guatemala City', 'CAPITAL', 12, 22, 68),
       ('Conakry', 'CAPITAL', 22, 21, 69),
       ('Port-au-Prince', 'CAPITAL', 14, 24, 72),
       ('Reykjavík', 'CAPITAL', 21, 38, 75),
       ('New Delhi', 'CAPITAL', 35, 26, 76),
       ('Mumbai', 'CITY', 35, 24, 76),
       ('Chennai', 'CITY', 36, 22, 76),
       ('Kolkata', 'CITY', 37, 25, 76),
       ('Bengaluru', 'CITY', 35, 22, 76),
       ('Visakhapatnam', 'CITY', 36, 23, 76),
       ('Taj Mahal', 'SITE', 35, 25, 76),
       ('Goa', 'SITE', 35, 23, 76),
       ('Mahabodhi Temple', 'SITE', 36, 25, 76),
       ('Jakarta', 'CAPITAL', 39, 17, 77),
       ('Surabaya', 'CITY', 40, 17, 77),
       ('Tehran', 'CAPITAL', 32, 28, 78),
       ('Baghdad', 'CAPITAL', 31, 28, 79),
       ('Cork', 'CITY', 23, 33, 80),
       ('Giant''s Causeway', 'SITE', 23, 35, 80),
       ('Cliffs of Moher', 'SITE', 23, 34, 80),
       ('Jerusalem', 'CAPITAL', 30, 28, 81),
       ('Rome', 'CAPITAL', 26, 30, 82),
       ('Palermo', 'CITY', 26, 29, 82),
       ('Naples', 'CITY', 27, 30, 82),
       ('Pollino National Park', 'SITE', 27, 29, 82),
       ('Leaning Tower of Pisa', 'SITE', 26, 31, 82),
       ('Kingston', 'CAPITAL', 14, 23, 83),
       ('Tokyo', 'CAPITAL', 44, 28, 84),
       ('Sapporo', 'CITY', 44, 31, 84),
       ('Sendai', 'CITY', 44, 29, 84),
       ('Nagasaki', 'CITY', 43, 27, 84),
       ('Wajima', 'CITY', 43, 29, 84),
       ('Himeji Castle', 'SITE', 43, 28, 84),
       ('Mount Hakodate', 'SITE', 44, 30, 84),
       ('Petra', 'SITE', 30, 27, 85),
       ('Lake Balkhash', 'SITE', 35, 32, 86),
       ('Monrovia', 'CAPITAL', 23, 20, 95),
       ('Tripoli', 'CAPITAL', 27, 27, 96),
       ('Riga', 'CAPITAL', 28, 35, 98),
       ('Antananarivo', 'CAPITAL', 31, 13, 100),
       ('Bamako', 'CAPITAL', 24, 22, 104),
       ('Kuala Lumpur', 'CAPITAL', 39, 19, 102),
       ('Mexico City', 'CAPITAL', 11, 24, 109),
       ('Chichén Itza', 'SITE', 12, 24, 109),
       ('Ulaanbaatar', 'CAPITAL', 39, 32, 113),
       ('Rabat', 'CAPITAL', 23, 28, 115),
       ('Maputo', 'CAPITAL', 29, 11, 116),
       ('Windhoek', 'CAPITAL', 27, 12, 118),
       ('Mount Everest', 'SITE', 36, 36, 120),
       ('Amsterdam', 'CAPITAL', 25, 34, 121),
       ('Auckland', 'CITY', 49, 8, 122),
       ('Managua', 'CAPITAL', 13, 22, 123),
       ('Lagos', 'CITY', 25, 20, 125),
       ('Pyongyang', 'CAPITAL', 42, 30, 126),
       ('Oslo', 'CAPITAL', 26, 37, 128),
       ('Trondheim', 'CITY', 26, 38, 128),
       ('Trolltunga', 'SITE', 25, 37, 128),
       ('Stavanger', 'CITY', 25, 36, 128),
       ('Mo i Rana', 'CITY', 26, 39, 128),
       ('Three-Country Cairn', 'SITE', 27, 41, 128),
       ('Kirkenes', 'CITY', 29, 41, 128),
       ('Stabbursdalen National Park', 'SITE', 28, 41, 128),
       ('Karachi', 'CITY', 34, 25, 130),
       ('Lahore', 'CITY', 35, 27, 130),
       ('Hyderabad', 'CITY', 34, 26, 130),
       ('K2', 'SITE', 35, 28, 130),
       ('Panama Canal', 'SITE', 13, 21, 132),
       ('Port Moresby', 'CAPITAL', 45, 16, 133),
       ('Asunción', 'CAPITAL', 16, 12, 134),
       ('Lima', 'CAPITAL', 14, 15, 135),
       ('Machu Picchu', 'SITE', 15, 15, 135),
       ('Nazca Lines', 'SITE', 14, 14, 135),
       ('Manila', 'CAPITAL', 41, 23, 136),
       ('Davao', 'CITY', 42, 20, 136),
       ('Cagayan De Oro', 'CITY', 42, 21, 136),
       ('Warsaw', 'CAPITAL', 27, 34, 137),
       ('Wieliczka Salt Mine', 'SITE', 27, 33, 137),
       ('Lisbon', 'CAPITAL', 23, 29, 138),
       ('Porto', 'CITY', 23, 30, 138),
       ('Torre de Hércules', 'SITE', 23, 31, 138),
       ('Bran Castle', 'SITE', 28, 32, 140),
       ('Moscow', 'CAPITAL', 30, 35, 141),
       ('Saint Petersburg', 'CITY', 29, 37, 141),
       ('Yekaterinburg', 'CITY', 33, 35, 141),
       ('Novgorod', 'CITY', 29, 36, 141),
       ('Pskov', 'CITY', 29, 35, 141),
       ('Murmansk', 'CITY', 29, 40, 141),
       ('Oymyakon', 'CITY', 44, 38, 141),
       ('Lake Baikal', 'SITE', 39, 34, 141),
       ('Putorana Plateau', 'SITE', 38, 40, 141),
       ('Dakar', 'CAPITAL', 22, 22, 150),
       ('Freetown', 'CAPITAL', 23, 21, 153),
       ('Singapore', 'CAPITAL', 39, 18, 154),
       ('Mogadishu', 'CAPITAL', 31, 19, 158),
       ('Pretoria', 'CAPITAL', 28, 12, 159),
       ('Cape Town', 'CITY', 27, 9, 159),
       ('Johannesburg', 'CITY', 28, 11, 159),
       ('Seoul', 'CAPITAL', 42, 29, 160),
       ('Busan', 'CITY', 42, 28, 160),
       ('Madrid', 'CAPITAL', 24, 30, 162),
       ('Sagrada Família', 'SITE', 25, 30, 162),
       ('Alhambra', 'SITE', 24, 29, 162),
       ('Colombo', 'CAPITAL', 36, 20, 163),
       ('Khartoum', 'CAPITAL', 29, 23, 164),
       ('Gothenburg', 'CITY', 26, 36, 166),
       ('Stockholm', 'CAPITAL', 27, 36, 166),
       ('Kalmar', 'CITY', 27, 35, 166),
       ('Sundsvall', 'CITY', 27, 38, 166),
       ('Haparanda', 'CITY', 28, 39, 166),
       ('Skellefteå', 'CITY', 27, 39, 166),
       ('Ice Hotel (Jukkasjärvi)', 'SITE', 27, 40, 166),
       ('Gävle Goat', 'SITE', 27, 37, 166),
       ('Pajala', 'CITY', 28, 40, 166),
       ('Taipei', 'CAPITAL', 41, 25, 169),
       ('Serengeti National Park', 'SITE', 29, 18, 171),
       ('Mount Kilimanjaro', 'SITE', 30, 17, 171),
       ('Tunis', 'CAPITAL', 26, 28, 177),
       ('Ankara', 'CAPITAL', 29, 29, 178),
       ('Istanbul', 'CITY', 29, 30, 178),
       ('Batman', 'CITY', 30, 29, 178),
       ('Hagia Sophia', 'SITE', 28, 30, 178),
       ('Lake Victoria', 'SITE', 29, 19, 181),
       ('Kyiv', 'CAPITAL', 29, 33, 182),
       ('Lviv', 'CITY', 28, 33, 182),
       ('Sevastopol', 'CITY', 29, 31, 182),
       ('Mariupol', 'CITY', 30, 31, 182),
       ('Donetsk', 'CITY', 30, 32, 182),
       ('Odesa', 'CITY', 29, 32, 182),
       ('London', 'CAPITAL', 24, 34, 184),
       ('Edinburgh', 'CITY', 24, 35, 184),
       ('Loch Ness', 'SITE', 24, 36, 184),
       ('Stonehenge', 'SITE', 24, 33, 184),
       ('New York', 'CITY', 14, 30, 185),
       ('Los Angeles', 'CITY', 8, 28, 185),
       ('Chicago', 'CITY', 12, 30, 185),
       ('Seattle', 'CITY', 7, 32, 185),
       ('Boston', 'CITY', 15, 30, 185),
       ('Philadelphia', 'CITY', 14, 29, 185),
       ('Denver', 'CITY', 10, 30, 185),
       ('Phoenix', 'CITY', 9, 28, 185),
       ('San Diego', 'CITY', 8, 27, 185),
       ('San Francisco', 'CITY', 7, 29, 185),
       ('Las Vegas', 'CITY', 8, 29, 185),
       ('Detroit', 'CITY', 13, 30, 185),
       ('Portland', 'CITY', 7, 31, 185),
       ('Miami', 'CITY', 13, 25, 185),
       ('Salt Lake City', 'CITY', 9, 30, 185),
       ('Tucson', 'CITY', 9, 27, 185),
       ('El Paso', 'CITY', 10, 27, 185),
       ('Virginia Beach', 'CITY', 14, 28, 185),
       ('Honolulu', 'CITY', 3, 24, 185),
       ('Albuquerque', 'CITY', 10, 28, 185),
       ('Santa Fe', 'CITY', 10, 29, 185),
       ('Golden Gate Bridge', 'SITE', 7, 30, 185),
       ('Yosemite National Park', 'SITE', 8, 30, 185),
       ('Disneyland', 'SITE', 13, 26, 185),
       ('Grand Canyon', 'SITE', 9, 29, 185),
       ('Big Bend National Park', 'SITE', 10, 26, 185),
       ('Mount McKinley', 'SITE', 3, 38, 185),
       ('The White House', 'SITE', 13, 28, 185),
       ('Montevideo', 'CAPITAL', 17, 9, 186),
       ('Caracas', 'CAPITAL', 15, 21, 190),
       ('Lusaka', 'CAPITAL', 29, 14, 193),
       ('Livingstone', 'CITY', 28, 14, 193),
       ('Victoria Falls', 'SITE', 28, 13, 194);

INSERT INTO Transport (type, dice_count, dice_sides,cost_per_move)
VALUES ('BUSS',1,6,250),
       ('TRAIN',2,6,500),
       ('AIRPLANE',3,6,1000);

-- Stockholm -> Berlin
INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT
    from_loc.id,
    to_loc.id,
    10
FROM Location from_loc
         JOIN Location to_loc
WHERE from_loc.name = 'Stockholm'
  AND to_loc.name = 'Berlin'
  AND NOT EXISTS (
    SELECT 1 FROM LocationLink ll
    WHERE ll.from_location_id = from_loc.id
      AND ll.to_location_id = to_loc.id
);

-- Berlin -> Stockholm (returresa)
INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT
    from_loc.id,
    to_loc.id,
    10
FROM Location from_loc
         JOIN Location to_loc
WHERE from_loc.name = 'Berlin'
  AND to_loc.name   = 'Stockholm'
  AND NOT EXISTS (
    SELECT 1 FROM LocationLink ll
    WHERE ll.from_location_id = from_loc.id
      AND ll.to_location_id   = to_loc.id
);

-- Transportmedel Berlin -> Stockholm
INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll
         JOIN Transport t
WHERE ll.from_location_id = (SELECT id FROM Location WHERE name = 'Stockholm')
  AND ll.to_location_id   = (SELECT id FROM Location WHERE name = 'Berlin')
  AND t.type IN ('BUSS', 'TRAIN', 'AIRPLANE')
  AND NOT EXISTS (
    SELECT 1 FROM TransportLink tl
    WHERE tl.location_link_id = ll.id
      AND tl.transport_id     = t.id
);

-- Tillåtna transportmedel Berlin -> Stockholm
INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll
         JOIN Transport t
WHERE ll.from_location_id = (SELECT id FROM Location WHERE name = 'Berlin')
  AND ll.to_location_id   = (SELECT id FROM Location WHERE name = 'Stockholm')
  AND t.type IN ('BUSS', 'TRAIN', 'AIRPLANE')
  AND NOT EXISTS (
    SELECT 1 FROM TransportLink tl
    WHERE tl.location_link_id = ll.id
      AND tl.transport_id     = t.id
);



INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT
    from_loc.id,
    to_loc.id,
    25
FROM Location from_loc
         JOIN Location to_loc
WHERE from_loc.name = 'Stockholm'
  AND to_loc.name = 'Paris'
  AND NOT EXISTS (
    SELECT 1 FROM LocationLink ll
    WHERE ll.from_location_id = from_loc.id
      AND ll.to_location_id = to_loc.id
);

INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT
    from_loc.id,
    to_loc.id,
    12
FROM Location from_loc
         JOIN Location to_loc
WHERE from_loc.name = 'Berlin'
  AND to_loc.name = 'Paris'
  AND NOT EXISTS (
    SELECT 1 FROM LocationLink ll
    WHERE ll.from_location_id = from_loc.id
      AND ll.to_location_id = to_loc.id
);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll
         JOIN Transport t
WHERE ll.from_location_id = (SELECT id FROM Location WHERE name = 'Stockholm')
  AND ll.to_location_id   = (SELECT id FROM Location WHERE name = 'Berlin')
  AND t.type IN ('BUSS', 'TRAIN')
  AND NOT EXISTS (
    SELECT 1 FROM TransportLink tl
    WHERE tl.location_link_id = ll.id
      AND tl.transport_id = t.id
);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll
         JOIN Transport t
WHERE ll.from_location_id = (SELECT id FROM Location WHERE name = 'Berlin')
  AND ll.to_location_id   = (SELECT id FROM Location WHERE name = 'Paris')
  AND t.type IN ('TRAIN', 'AIRPLANE')
  AND NOT EXISTS (
    SELECT 1 FROM TransportLink tl
    WHERE tl.location_link_id = ll.id
      AND tl.transport_id = t.id
);

-- Berlin -> Paris
INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT from_loc.id, to_loc.id, 12
FROM Location from_loc
         JOIN Location to_loc
WHERE from_loc.name = 'Berlin'
  AND to_loc.name   = 'Paris'
  AND NOT EXISTS (
    SELECT 1 FROM LocationLink ll
    WHERE ll.from_location_id = from_loc.id
      AND ll.to_location_id   = to_loc.id
);

-- Paris -> Berlin
INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT from_loc.id, to_loc.id, 12
FROM Location from_loc
         JOIN Location to_loc
WHERE from_loc.name = 'Paris'
  AND to_loc.name   = 'Berlin'
  AND NOT EXISTS (
    SELECT 1 FROM LocationLink ll
    WHERE ll.from_location_id = from_loc.id
      AND ll.to_location_id   = to_loc.id
);

-- Tillåtna transportmedel Berlin -> Paris
INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll
         JOIN Transport t
WHERE ll.from_location_id = (SELECT id FROM Location WHERE name = 'Berlin')
  AND ll.to_location_id   = (SELECT id FROM Location WHERE name = 'Paris')
  AND t.type IN ('TRAIN', 'AIRPLANE')
  AND NOT EXISTS (
    SELECT 1 FROM TransportLink tl
    WHERE tl.location_link_id = ll.id
      AND tl.transport_id     = t.id
);

-- Tillåtna transportmedel Paris -> Berlin
INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll
         JOIN Transport t
WHERE ll.from_location_id = (SELECT id FROM Location WHERE name = 'Paris')
  AND ll.to_location_id   = (SELECT id FROM Location WHERE name = 'Berlin')
  AND t.type IN ('TRAIN')
  AND NOT EXISTS (
    SELECT 1 FROM TransportLink tl
    WHERE tl.location_link_id = ll.id
      AND tl.transport_id     = t.id
);

-- London -> New York
INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT from_loc.id, to_loc.id, 50
FROM Location from_loc
         JOIN Location to_loc
WHERE from_loc.name = 'London'
  AND to_loc.name   = 'New York'
  AND NOT EXISTS (
    SELECT 1 FROM LocationLink ll
    WHERE ll.from_location_id = from_loc.id
      AND ll.to_location_id   = to_loc.id
);

-- New York -> London
INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT from_loc.id, to_loc.id, 50
FROM Location from_loc
         JOIN Location to_loc
WHERE from_loc.name = 'New York'
  AND to_loc.name   = 'London'
  AND NOT EXISTS (
    SELECT 1 FROM LocationLink ll
    WHERE ll.from_location_id = from_loc.id
      AND ll.to_location_id   = to_loc.id
);

-- Tillåtna transportmedel London -> New York
INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll
         JOIN Transport t
WHERE ll.from_location_id = (SELECT id FROM Location WHERE name = 'London')
  AND ll.to_location_id   = (SELECT id FROM Location WHERE name = 'New York')
  AND t.type IN ('AIRPLANE')
  AND NOT EXISTS (
    SELECT 1 FROM TransportLink tl
    WHERE tl.location_link_id = ll.id
      AND tl.transport_id     = t.id
);

-- Tillåtna transportmedel New York -> London
INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll
         JOIN Transport t
WHERE ll.from_location_id = (SELECT id FROM Location WHERE name = 'New York')
  AND ll.to_location_id   = (SELECT id FROM Location WHERE name = 'London')
  AND t.type IN ('AIRPLANE')
  AND NOT EXISTS (
    SELECT 1 FROM TransportLink tl
    WHERE tl.location_link_id = ll.id
      AND tl.transport_id     = t.id
);

-- =========================================================
-- provspela-nät: europa-kedja + usa + transatlantiskt
-- =========================================================

-- stockholm <-> copenhagen (kort: buss + tåg)
-- dist 6 (buss 1d6 kan klara på 1 tur, annars 2)
INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT a.id, b.id, 6
FROM Location a JOIN Location b
WHERE a.name='Stockholm' AND b.name='Copenhagen'
  AND NOT EXISTS (SELECT 1 FROM LocationLink ll WHERE ll.from_location_id=a.id AND ll.to_location_id=b.id);

INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT b.id, a.id, 6
FROM Location a JOIN Location b
WHERE a.name='Stockholm' AND b.name='Copenhagen'
  AND NOT EXISTS (SELECT 1 FROM LocationLink ll WHERE ll.from_location_id=b.id AND ll.to_location_id=a.id);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll JOIN Transport t
WHERE ll.from_location_id=(SELECT id FROM Location WHERE name='Stockholm')
  AND ll.to_location_id  =(SELECT id FROM Location WHERE name='Copenhagen')
  AND t.type IN ('BUSS','TRAIN')
  AND NOT EXISTS (SELECT 1 FROM TransportLink tl WHERE tl.location_link_id=ll.id AND tl.transport_id=t.id);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll JOIN Transport t
WHERE ll.from_location_id=(SELECT id FROM Location WHERE name='Copenhagen')
  AND ll.to_location_id  =(SELECT id FROM Location WHERE name='Stockholm')
  AND t.type IN ('BUSS','TRAIN')
  AND NOT EXISTS (SELECT 1 FROM TransportLink tl WHERE tl.location_link_id=ll.id AND tl.transport_id=t.id);


-- stockholm <-> oslo (kort: buss + tåg)
-- dist 6
INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT a.id, b.id, 6
FROM Location a JOIN Location b
WHERE a.name='Stockholm' AND b.name='Oslo'
  AND NOT EXISTS (SELECT 1 FROM LocationLink ll WHERE ll.from_location_id=a.id AND ll.to_location_id=b.id);

INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT b.id, a.id, 6
FROM Location a JOIN Location b
WHERE a.name='Stockholm' AND b.name='Oslo'
  AND NOT EXISTS (SELECT 1 FROM LocationLink ll WHERE ll.from_location_id=b.id AND ll.to_location_id=a.id);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll JOIN Transport t
WHERE ll.from_location_id=(SELECT id FROM Location WHERE name='Stockholm')
  AND ll.to_location_id  =(SELECT id FROM Location WHERE name='Oslo')
  AND t.type IN ('BUSS','TRAIN')
  AND NOT EXISTS (SELECT 1 FROM TransportLink tl WHERE tl.location_link_id=ll.id AND tl.transport_id=t.id);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll JOIN Transport t
WHERE ll.from_location_id=(SELECT id FROM Location WHERE name='Oslo')
  AND ll.to_location_id  =(SELECT id FROM Location WHERE name='Stockholm')
  AND t.type IN ('BUSS','TRAIN')
  AND NOT EXISTS (SELECT 1 FROM TransportLink tl WHERE tl.location_link_id=ll.id AND tl.transport_id=t.id);


-- stockholm <-> helsinki (medel: tåg + flyg)
-- dist 10 (tåg 2d6 ofta 1–2 turer, flyg 3d6 ofta 1 tur)
INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT a.id, b.id, 10
FROM Location a JOIN Location b
WHERE a.name='Stockholm' AND b.name='Helsinki'
  AND NOT EXISTS (SELECT 1 FROM LocationLink ll WHERE ll.from_location_id=a.id AND ll.to_location_id=b.id);

INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT b.id, a.id, 10
FROM Location a JOIN Location b
WHERE a.name='Stockholm' AND b.name='Helsinki'
  AND NOT EXISTS (SELECT 1 FROM LocationLink ll WHERE ll.from_location_id=b.id AND ll.to_location_id=a.id);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll JOIN Transport t
WHERE ll.from_location_id=(SELECT id FROM Location WHERE name='Stockholm')
  AND ll.to_location_id  =(SELECT id FROM Location WHERE name='Helsinki')
  AND t.type IN ('TRAIN','AIRPLANE')
  AND NOT EXISTS (SELECT 1 FROM TransportLink tl WHERE tl.location_link_id=ll.id AND tl.transport_id=t.id);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll JOIN Transport t
WHERE ll.from_location_id=(SELECT id FROM Location WHERE name='Helsinki')
  AND ll.to_location_id  =(SELECT id FROM Location WHERE name='Stockholm')
  AND t.type IN ('TRAIN','AIRPLANE')
  AND NOT EXISTS (SELECT 1 FROM TransportLink tl WHERE tl.location_link_id=ll.id AND tl.transport_id=t.id);


-- copenhagen <-> berlin (medel: tåg + flyg)
-- dist 12
INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT a.id, b.id, 12
FROM Location a JOIN Location b
WHERE a.name='Copenhagen' AND b.name='Berlin'
  AND NOT EXISTS (SELECT 1 FROM LocationLink ll WHERE ll.from_location_id=a.id AND ll.to_location_id=b.id);

INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT b.id, a.id, 12
FROM Location a JOIN Location b
WHERE a.name='Copenhagen' AND b.name='Berlin'
  AND NOT EXISTS (SELECT 1 FROM LocationLink ll WHERE ll.from_location_id=b.id AND ll.to_location_id=a.id);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll JOIN Transport t
WHERE ll.from_location_id=(SELECT id FROM Location WHERE name='Copenhagen')
  AND ll.to_location_id  =(SELECT id FROM Location WHERE name='Berlin')
  AND t.type IN ('TRAIN','AIRPLANE')
  AND NOT EXISTS (SELECT 1 FROM TransportLink tl WHERE tl.location_link_id=ll.id AND tl.transport_id=t.id);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll JOIN Transport t
WHERE ll.from_location_id=(SELECT id FROM Location WHERE name='Berlin')
  AND ll.to_location_id  =(SELECT id FROM Location WHERE name='Copenhagen')
  AND t.type IN ('TRAIN','AIRPLANE')
  AND NOT EXISTS (SELECT 1 FROM TransportLink tl WHERE tl.location_link_id=ll.id AND tl.transport_id=t.id);


-- berlin <-> warsaw (medel: buss + tåg)
-- dist 9
INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT a.id, b.id, 9
FROM Location a JOIN Location b
WHERE a.name='Berlin' AND b.name='Warsaw'
  AND NOT EXISTS (SELECT 1 FROM LocationLink ll WHERE ll.from_location_id=a.id AND ll.to_location_id=b.id);

INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT b.id, a.id, 9
FROM Location a JOIN Location b
WHERE a.name='Berlin' AND b.name='Warsaw'
  AND NOT EXISTS (SELECT 1 FROM LocationLink ll WHERE ll.from_location_id=b.id AND ll.to_location_id=a.id);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll JOIN Transport t
WHERE ll.from_location_id=(SELECT id FROM Location WHERE name='Berlin')
  AND ll.to_location_id  =(SELECT id FROM Location WHERE name='Warsaw')
  AND t.type IN ('BUSS','TRAIN')
  AND NOT EXISTS (SELECT 1 FROM TransportLink tl WHERE tl.location_link_id=ll.id AND tl.transport_id=t.id);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll JOIN Transport t
WHERE ll.from_location_id=(SELECT id FROM Location WHERE name='Warsaw')
  AND ll.to_location_id  =(SELECT id FROM Location WHERE name='Berlin')
  AND t.type IN ('BUSS','TRAIN')
  AND NOT EXISTS (SELECT 1 FROM TransportLink tl WHERE tl.location_link_id=ll.id AND tl.transport_id=t.id);


-- berlin <-> amsterdam (kort/medel: buss + tåg)
-- dist 7
INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT a.id, b.id, 7
FROM Location a JOIN Location b
WHERE a.name='Berlin' AND b.name='Amsterdam'
  AND NOT EXISTS (SELECT 1 FROM LocationLink ll WHERE ll.from_location_id=a.id AND ll.to_location_id=b.id);

INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT b.id, a.id, 7
FROM Location a JOIN Location b
WHERE a.name='Berlin' AND b.name='Amsterdam'
  AND NOT EXISTS (SELECT 1 FROM LocationLink ll WHERE ll.from_location_id=b.id AND ll.to_location_id=a.id);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll JOIN Transport t
WHERE ll.from_location_id=(SELECT id FROM Location WHERE name='Berlin')
  AND ll.to_location_id  =(SELECT id FROM Location WHERE name='Amsterdam')
  AND t.type IN ('BUSS','TRAIN')
  AND NOT EXISTS (SELECT 1 FROM TransportLink tl WHERE tl.location_link_id=ll.id AND tl.transport_id=t.id);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll JOIN Transport t
WHERE ll.from_location_id=(SELECT id FROM Location WHERE name='Amsterdam')
  AND ll.to_location_id  =(SELECT id FROM Location WHERE name='Berlin')
  AND t.type IN ('BUSS','TRAIN')
  AND NOT EXISTS (SELECT 1 FROM TransportLink tl WHERE tl.location_link_id=ll.id AND tl.transport_id=t.id);


-- amsterdam <-> paris (kort/medel: tåg)
-- dist 8
INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT a.id, b.id, 8
FROM Location a JOIN Location b
WHERE a.name='Amsterdam' AND b.name='Paris'
  AND NOT EXISTS (SELECT 1 FROM LocationLink ll WHERE ll.from_location_id=a.id AND ll.to_location_id=b.id);

INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT b.id, a.id, 8
FROM Location a JOIN Location b
WHERE a.name='Amsterdam' AND b.name='Paris'
  AND NOT EXISTS (SELECT 1 FROM LocationLink ll WHERE ll.from_location_id=b.id AND ll.to_location_id=a.id);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll JOIN Transport t
WHERE ll.from_location_id=(SELECT id FROM Location WHERE name='Amsterdam')
  AND ll.to_location_id  =(SELECT id FROM Location WHERE name='Paris')
  AND t.type IN ('TRAIN')
  AND NOT EXISTS (SELECT 1 FROM TransportLink tl WHERE tl.location_link_id=ll.id AND tl.transport_id=t.id);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll JOIN Transport t
WHERE ll.from_location_id=(SELECT id FROM Location WHERE name='Paris')
  AND ll.to_location_id  =(SELECT id FROM Location WHERE name='Amsterdam')
  AND t.type IN ('TRAIN')
  AND NOT EXISTS (SELECT 1 FROM TransportLink tl WHERE tl.location_link_id=ll.id AND tl.transport_id=t.id);


-- paris <-> london (kort: tåg + flyg)
-- dist 7
INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT a.id, b.id, 7
FROM Location a JOIN Location b
WHERE a.name='Paris' AND b.name='London'
  AND NOT EXISTS (SELECT 1 FROM LocationLink ll WHERE ll.from_location_id=a.id AND ll.to_location_id=b.id);

INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT b.id, a.id, 7
FROM Location a JOIN Location b
WHERE a.name='Paris' AND b.name='London'
  AND NOT EXISTS (SELECT 1 FROM LocationLink ll WHERE ll.from_location_id=b.id AND ll.to_location_id=a.id);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll JOIN Transport t
WHERE ll.from_location_id=(SELECT id FROM Location WHERE name='Paris')
  AND ll.to_location_id  =(SELECT id FROM Location WHERE name='London')
  AND t.type IN ('TRAIN','AIRPLANE')
  AND NOT EXISTS (SELECT 1 FROM TransportLink tl WHERE tl.location_link_id=ll.id AND tl.transport_id=t.id);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll JOIN Transport t
WHERE ll.from_location_id=(SELECT id FROM Location WHERE name='London')
  AND ll.to_location_id  =(SELECT id FROM Location WHERE name='Paris')
  AND t.type IN ('TRAIN','AIRPLANE')
  AND NOT EXISTS (SELECT 1 FROM TransportLink tl WHERE tl.location_link_id=ll.id AND tl.transport_id=t.id);


-- paris <-> madrid (lång: tåg + flyg)
-- dist 18 (tåg ofta 2 turer, flyg 1–2 turer)
INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT a.id, b.id, 18
FROM Location a JOIN Location b
WHERE a.name='Paris' AND b.name='Madrid'
  AND NOT EXISTS (SELECT 1 FROM LocationLink ll WHERE ll.from_location_id=a.id AND ll.to_location_id=b.id);

INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT b.id, a.id, 18
FROM Location a JOIN Location b
WHERE a.name='Paris' AND b.name='Madrid'
  AND NOT EXISTS (SELECT 1 FROM LocationLink ll WHERE ll.from_location_id=b.id AND ll.to_location_id=a.id);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll JOIN Transport t
WHERE ll.from_location_id=(SELECT id FROM Location WHERE name='Paris')
  AND ll.to_location_id  =(SELECT id FROM Location WHERE name='Madrid')
  AND t.type IN ('TRAIN','AIRPLANE')
  AND NOT EXISTS (SELECT 1 FROM TransportLink tl WHERE tl.location_link_id=ll.id AND tl.transport_id=t.id);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll JOIN Transport t
WHERE ll.from_location_id=(SELECT id FROM Location WHERE name='Madrid')
  AND ll.to_location_id  =(SELECT id FROM Location WHERE name='Paris')
  AND t.type IN ('TRAIN','AIRPLANE')
  AND NOT EXISTS (SELECT 1 FROM TransportLink tl WHERE tl.location_link_id=ll.id AND tl.transport_id=t.id);


-- madrid <-> lisbon (kort: buss + tåg)
-- dist 7
INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT a.id, b.id, 7
FROM Location a JOIN Location b
WHERE a.name='Madrid' AND b.name='Lisbon'
  AND NOT EXISTS (SELECT 1 FROM LocationLink ll WHERE ll.from_location_id=a.id AND ll.to_location_id=b.id);

INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT b.id, a.id, 7
FROM Location a JOIN Location b
WHERE a.name='Madrid' AND b.name='Lisbon'
  AND NOT EXISTS (SELECT 1 FROM LocationLink ll WHERE ll.from_location_id=b.id AND ll.to_location_id=a.id);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll JOIN Transport t
WHERE ll.from_location_id=(SELECT id FROM Location WHERE name='Madrid')
  AND ll.to_location_id  =(SELECT id FROM Location WHERE name='Lisbon')
  AND t.type IN ('BUSS','TRAIN')
  AND NOT EXISTS (SELECT 1 FROM TransportLink tl WHERE tl.location_link_id=ll.id AND tl.transport_id=t.id);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll JOIN Transport t
WHERE ll.from_location_id=(SELECT id FROM Location WHERE name='Lisbon')
  AND ll.to_location_id  =(SELECT id FROM Location WHERE name='Madrid')
  AND t.type IN ('BUSS','TRAIN')
  AND NOT EXISTS (SELECT 1 FROM TransportLink tl WHERE tl.location_link_id=ll.id AND tl.transport_id=t.id);


-- vienna <-> rome (medel/lång: tåg + flyg)
-- dist 14
INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT a.id, b.id, 14
FROM Location a JOIN Location b
WHERE a.name='Vienna' AND b.name='Rome'
  AND NOT EXISTS (SELECT 1 FROM LocationLink ll WHERE ll.from_location_id=a.id AND ll.to_location_id=b.id);

INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT b.id, a.id, 14
FROM Location a JOIN Location b
WHERE a.name='Vienna' AND b.name='Rome'
  AND NOT EXISTS (SELECT 1 FROM LocationLink ll WHERE ll.from_location_id=b.id AND ll.to_location_id=a.id);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll JOIN Transport t
WHERE ll.from_location_id=(SELECT id FROM Location WHERE name='Vienna')
  AND ll.to_location_id  =(SELECT id FROM Location WHERE name='Rome')
  AND t.type IN ('TRAIN','AIRPLANE')
  AND NOT EXISTS (SELECT 1 FROM TransportLink tl WHERE tl.location_link_id=ll.id AND tl.transport_id=t.id);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll JOIN Transport t
WHERE ll.from_location_id=(SELECT id FROM Location WHERE name='Rome')
  AND ll.to_location_id  =(SELECT id FROM Location WHERE name='Vienna')
  AND t.type IN ('TRAIN','AIRPLANE')
  AND NOT EXISTS (SELECT 1 FROM TransportLink tl WHERE tl.location_link_id=ll.id AND tl.transport_id=t.id);


-- rome <-> paris (lång: tåg + flyg)
-- dist 16
INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT a.id, b.id, 16
FROM Location a JOIN Location b
WHERE a.name='Rome' AND b.name='Paris'
  AND NOT EXISTS (SELECT 1 FROM LocationLink ll WHERE ll.from_location_id=a.id AND ll.to_location_id=b.id);

INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT b.id, a.id, 16
FROM Location a JOIN Location b
WHERE a.name='Rome' AND b.name='Paris'
  AND NOT EXISTS (SELECT 1 FROM LocationLink ll WHERE ll.from_location_id=b.id AND ll.to_location_id=a.id);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll JOIN Transport t
WHERE ll.from_location_id=(SELECT id FROM Location WHERE name='Rome')
  AND ll.to_location_id  =(SELECT id FROM Location WHERE name='Paris')
  AND t.type IN ('TRAIN','AIRPLANE')
  AND NOT EXISTS (SELECT 1 FROM TransportLink tl WHERE tl.location_link_id=ll.id AND tl.transport_id=t.id);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll JOIN Transport t
WHERE ll.from_location_id=(SELECT id FROM Location WHERE name='Paris')
  AND ll.to_location_id  =(SELECT id FROM Location WHERE name='Rome')
  AND t.type IN ('TRAIN','AIRPLANE')
  AND NOT EXISTS (SELECT 1 FROM TransportLink tl WHERE tl.location_link_id=ll.id AND tl.transport_id=t.id);


-- london <-> new york (extra lång: flyg only)
-- dist 35 (flyg 3d6 tar oftast 2–4 turer)
INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT a.id, b.id, 35
FROM Location a JOIN Location b
WHERE a.name='London' AND b.name='New York'
  AND NOT EXISTS (SELECT 1 FROM LocationLink ll WHERE ll.from_location_id=a.id AND ll.to_location_id=b.id);

INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT b.id, a.id, 35
FROM Location a JOIN Location b
WHERE a.name='London' AND b.name='New York'
  AND NOT EXISTS (SELECT 1 FROM LocationLink ll WHERE ll.from_location_id=b.id AND ll.to_location_id=a.id);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll JOIN Transport t
WHERE ll.from_location_id=(SELECT id FROM Location WHERE name='London')
  AND ll.to_location_id  =(SELECT id FROM Location WHERE name='New York')
  AND t.type IN ('AIRPLANE')
  AND NOT EXISTS (SELECT 1 FROM TransportLink tl WHERE tl.location_link_id=ll.id AND tl.transport_id=t.id);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll JOIN Transport t
WHERE ll.from_location_id=(SELECT id FROM Location WHERE name='New York')
  AND ll.to_location_id  =(SELECT id FROM Location WHERE name='London')
  AND t.type IN ('AIRPLANE')
  AND NOT EXISTS (SELECT 1 FROM TransportLink tl WHERE tl.location_link_id=ll.id AND tl.transport_id=t.id);


-- new york <-> boston (kort: buss + tåg)
-- dist 6
INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT a.id, b.id, 6
FROM Location a JOIN Location b
WHERE a.name='New York' AND b.name='Boston'
  AND NOT EXISTS (SELECT 1 FROM LocationLink ll WHERE ll.from_location_id=a.id AND ll.to_location_id=b.id);

INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT b.id, a.id, 6
FROM Location a JOIN Location b
WHERE a.name='New York' AND b.name='Boston'
  AND NOT EXISTS (SELECT 1 FROM LocationLink ll WHERE ll.from_location_id=b.id AND ll.to_location_id=a.id);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll JOIN Transport t
WHERE ll.from_location_id=(SELECT id FROM Location WHERE name='New York')
  AND ll.to_location_id  =(SELECT id FROM Location WHERE name='Boston')
  AND t.type IN ('BUSS','TRAIN')
  AND NOT EXISTS (SELECT 1 FROM TransportLink tl WHERE tl.location_link_id=ll.id AND tl.transport_id=t.id);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll JOIN Transport t
WHERE ll.from_location_id=(SELECT id FROM Location WHERE name='Boston')
  AND ll.to_location_id  =(SELECT id FROM Location WHERE name='New York')
  AND t.type IN ('BUSS','TRAIN')
  AND NOT EXISTS (SELECT 1 FROM TransportLink tl WHERE tl.location_link_id=ll.id AND tl.transport_id=t.id);


-- new york <-> los angeles (väldigt lång: flyg only)
-- dist 32 (flyg 3d6 tar oftast 2–4 turer)
INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT a.id, b.id, 32
FROM Location a JOIN Location b
WHERE a.name='New York' AND b.name='Los Angeles'
  AND NOT EXISTS (SELECT 1 FROM LocationLink ll WHERE ll.from_location_id=a.id AND ll.to_location_id=b.id);

INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT b.id, a.id, 32
FROM Location a JOIN Location b
WHERE a.name='New York' AND b.name='Los Angeles'
  AND NOT EXISTS (SELECT 1 FROM LocationLink ll WHERE ll.from_location_id=b.id AND ll.to_location_id=a.id);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll JOIN Transport t
WHERE ll.from_location_id=(SELECT id FROM Location WHERE name='New York')
  AND ll.to_location_id  =(SELECT id FROM Location WHERE name='Los Angeles')
  AND t.type IN ('AIRPLANE')
  AND NOT EXISTS (SELECT 1 FROM TransportLink tl WHERE tl.location_link_id=ll.id AND tl.transport_id=t.id);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll JOIN Transport t
WHERE ll.from_location_id=(SELECT id FROM Location WHERE name='Los Angeles')
  AND ll.to_location_id  =(SELECT id FROM Location WHERE name='New York')
  AND t.type IN ('AIRPLANE')
  AND NOT EXISTS (SELECT 1 FROM TransportLink tl WHERE tl.location_link_id=ll.id AND tl.transport_id=t.id);


-- los angeles <-> san francisco (kort/medel: buss + tåg)
-- dist 8
INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT a.id, b.id, 8
FROM Location a JOIN Location b
WHERE a.name='Los Angeles' AND b.name='San Francisco'
  AND NOT EXISTS (SELECT 1 FROM LocationLink ll WHERE ll.from_location_id=a.id AND ll.to_location_id=b.id);

INSERT INTO LocationLink (from_location_id, to_location_id, distance)
SELECT b.id, a.id, 8
FROM Location a JOIN Location b
WHERE a.name='Los Angeles' AND b.name='San Francisco'
  AND NOT EXISTS (SELECT 1 FROM LocationLink ll WHERE ll.from_location_id=b.id AND ll.to_location_id=a.id);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll JOIN Transport t
WHERE ll.from_location_id=(SELECT id FROM Location WHERE name='Los Angeles')
  AND ll.to_location_id  =(SELECT id FROM Location WHERE name='San Francisco')
  AND t.type IN ('BUSS','TRAIN')
  AND NOT EXISTS (SELECT 1 FROM TransportLink tl WHERE tl.location_link_id=ll.id AND tl.transport_id=t.id);

INSERT INTO TransportLink (location_link_id, transport_id)
SELECT ll.id, t.id
FROM LocationLink ll JOIN Transport t
WHERE ll.from_location_id=(SELECT id FROM Location WHERE name='San Francisco')
  AND ll.to_location_id  =(SELECT id FROM Location WHERE name='Los Angeles')
  AND t.type IN ('BUSS','TRAIN')
  AND NOT EXISTS (SELECT 1 FROM TransportLink tl WHERE tl.location_link_id=ll.id AND tl.transport_id=t.id);








