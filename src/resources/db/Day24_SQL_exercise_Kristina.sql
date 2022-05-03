--TODO
--CREATE YOUR OWN genre of music
INSERT INTO genres (Name)
VALUES ('Tralialia');

--CREATE artist Yourself or someone real or imagined
INSERT INTO artists (Name)
VALUES ('Kristina Kudriašova');

--CREATE album
INSERT INTO albums (Title, ArtistId)
--VALUES ('Kristina''s Solo Album', 276);
VALUES ('Kristina''s Solo Album',
SELECT ArtistId FROM artists WHERE artists.Name = 'Kristina Kudriašova');

--CREATE 2 tracks from that album that use your own genre of music
INSERT INTO tracks
(Name, AlbumId, MediaTypeId, 
GenreId, Composer, Milliseconds,
Bytes, UnitPrice)
VALUES
('My awesome tune',348, 1, 26, 'Kristina Kud.', 178000, 1400000, 6.99),
('My even more awesome tune',348, 1, 26, 'Kristina Kud.', 189000, 1500000, 7.99);

--UPDATE one of the tracks to be opera genre
UPDATE tracks 
--SET GenreId = 25
SET GenreId = (SELECT GenreId FROM genres WHERE Name = 'Opera')
WHERE Name = 'My awesome tune';

SELECT * FROM tracks t 
WHERE Composer GLOB '*Kud.*';

--DELETE the opera track 
DELETE FROM tracks 
WHERE TrackId = 3504;

--SELECT show your track joining it together with genre, album and artist
SELECT t.TrackId, a2.Name AS 'Artist name', t.Name AS 'Track title', a.Title AS 'Album title', g.Name AS 'Genre', t.Composer, t.UnitPrice 
FROM tracks t 
JOIN genres g 
ON g.GenreId = t.GenreId
JOIN albums a 
ON a.AlbumId = t.AlbumId 
JOIN artists a2 
ON a2.ArtistId = a.ArtistId
WHERE a2.Name GLOB '*Kristina Ku*'
ORDER BY TrackId ;


--like we did in a previous lecture