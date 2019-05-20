INSERT INTO pet_types (name) VALUES
    ('Hamster'),
    ('Maus'),
    ('Fisch'),
    ('Hund'),
    ('Katze'),
    ('Vogel');

insert into pets (name, type, birth_date, price) values
('Klaus', 'Hamster', to_date('13.04.2019', 'dd.mm.yyyy'), 20);
insert into pets (name, type, birth_date, price) values
('Rubert', 'Hund', to_date('18.09.2018', 'dd.mm.yyyy'), 550);
insert into pets (name, type, birth_date, price) values
('Blacky', 'Katze', to_date('12.12.2018', 'dd.mm.yyyy'), 350);
