TRUNCATE TABLE teas, brands RESTART IDENTITY CASCADE;

INSERT INTO brands (id, name, country) VALUES (1, 'Twinings', 'UK');
INSERT INTO brands (id, name, country) VALUES (2, 'Osulloc', 'Korea');
INSERT INTO brands (id, name, country) VALUES (3, 'Harney & Sons', 'USA');

INSERT INTO teas (id, name, type, brand_id, origin_country, caffeine, description, rating, created_at)
VALUES (1, 'English Breakfast', 'BLACK', 1, 'UK', true, 'Classic black breakfast tea.', 4.5, TIMESTAMP '2026-03-10 08:00:00');

INSERT INTO teas (id, name, type, brand_id, origin_country, caffeine, description, rating, created_at)
VALUES (2, 'Jeju Green Tea', 'GREEN', 2, 'Korea', true, 'Fresh green tea from Jeju.', 4.8, TIMESTAMP '2026-03-10 08:10:00');

INSERT INTO teas (id, name, type, brand_id, origin_country, caffeine, description, rating, created_at)
VALUES (3, 'Chamomile Bliss', 'HERBAL', 3, 'Egypt', false, 'Calming herbal infusion.', 4.3, TIMESTAMP '2026-03-10 08:20:00');

SELECT setval(pg_get_serial_sequence('brands', 'id'), COALESCE(MAX(id), 1), true) FROM brands;
SELECT setval(pg_get_serial_sequence('teas', 'id'), COALESCE(MAX(id), 1), true) FROM teas;

-- -----------------------------------------------------------------------------
-- Reference only: previous H2-based data.sql
-- -----------------------------------------------------------------------------
-- INSERT INTO brands (id, name, country) VALUES (1, 'Twinings', 'UK');
-- INSERT INTO brands (id, name, country) VALUES (2, 'Osulloc', 'Korea');
-- INSERT INTO brands (id, name, country) VALUES (3, 'Harney & Sons', 'USA');
--
-- INSERT INTO teas (id, name, type, brand_id, origin_country, caffeine, description, rating, created_at)
-- VALUES (1, 'English Breakfast', 'BLACK', 1, 'UK', true, 'Classic black breakfast tea.', 4.5, TIMESTAMP '2026-03-10 08:00:00');
--
-- INSERT INTO teas (id, name, type, brand_id, origin_country, caffeine, description, rating, created_at)
-- VALUES (2, 'Jeju Green Tea', 'GREEN', 2, 'Korea', true, 'Fresh green tea from Jeju.', 4.8, TIMESTAMP '2026-03-10 08:10:00');
--
-- INSERT INTO teas (id, name, type, brand_id, origin_country, caffeine, description, rating, created_at)
-- VALUES (3, 'Chamomile Bliss', 'HERBAL', 3, 'Egypt', false, 'Calming herbal infusion.', 4.3, TIMESTAMP '2026-03-10 08:20:00');
--
-- ALTER TABLE brands ALTER COLUMN id RESTART WITH 4;
-- ALTER TABLE teas ALTER COLUMN id RESTART WITH 4;
