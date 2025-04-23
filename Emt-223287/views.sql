
DROP MATERIALIZED VIEW IF EXISTS books_by_author;

CREATE MATERIALIZED VIEW books_by_author AS
SELECT a.id AS author_id,
       a.name || ' ' || a.surname AS author_name,
       COUNT(b.id) AS num_books
FROM author a
         LEFT JOIN book b ON a.id = b.author_id
GROUP BY a.id, a.name, a.surname;

CREATE UNIQUE INDEX idx_books_by_author_unique ON books_by_author (author_id);



DROP MATERIALIZED VIEW IF EXISTS authors_by_country;

CREATE MATERIALIZED VIEW authors_by_country AS
SELECT c.name AS country_name,
       COUNT(a.id) AS num_authors
FROM author a
         JOIN country c ON a.country_id = c.id
GROUP BY c.name;

CREATE UNIQUE INDEX idx_authors_by_country_unique ON authors_by_country (country_name);
