-- Seed locations
INSERT INTO locations (name, address, max_headcount_per_shift, is_active)
VALUES ('Head Office',    '1 Main Street, Accra',      20, TRUE),
       ('Branch North',   '45 North Road, Kumasi',     15, TRUE),
       ('Branch South',   '12 South Avenue, Takoradi', 10, TRUE);

-- Seed departments (location_id references the locations above)
INSERT INTO departments (name, location_id)
VALUES ('Human Resources',      1),
       ('Engineering',          1),
       ('Finance',              1),
       ('Operations - North',   2),
       ('Operations - South',   3),
       ('Customer Service',     1);

