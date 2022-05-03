SELECT * FROM artists a 
--WHERE Name LIKE '%V%"
WHERE Name GLOB '*Sau*'
LIMIT 20;

--CREATE (in CRUD terminology)
INSERT INTO artists (Name)
VALUES ('Valdis Saulespurēns');

UPDATE artists
SET name = 'Rūta Saulespurēna'
WHERE ArtistId = 277;

UPDATE artists 
SET name = 'Valdis Saulespurēns'
WHERE Name GLOB '*Sau*';
 -- so I skip my own name
 -- so limit and order options might not be enabled in your SQLite
 -- https://www.sqlite.org/lang_update.html

-- DELETE - Delete in CRUD
-- so check first with SELECT to see what you want to delete
-- Syntax is very similar
SELECT * FROM artists a 
WHERE Name GLOB '*Sau*';




DELETE FROM artists 
WHERE ArtistId = 277;


SELECT * FROM albums a2 
ORDER BY AlbumId DESC 
LIMIT 5;
--Insert INTO albums
INSERT INTO albums 
(Title, ArtistId)
VALUES ('My amazing Xmas album', 276);

SELECT * FROM tracks t 
ORDER BY TrackId DESC 
LIMIT 10;

INSERT INTO tracks 
(Name, AlbumId, MediaTypeId, 
GenreId, Composer, Milliseconds,
Bytes, UnitPrice)
VALUES ('Sitting under a tree',348,1,
22, 'Valdis S',181000,1359000, 2.99);

INSERT INTO tracks 
(Name, AlbumId, MediaTypeId, 
GenreId, Composer, Milliseconds,
Bytes, UnitPrice)
VALUES ('Sitting on a tree',358,1,
22, 'Valdis S',181300,1359000, 4.99);

UPDATE tracks 
SET AlbumId = 348
WHERE TrackId = 3505;

--TODO
--CREATE YOUR OWN genre of music

--CREATE artist Yourself or someone real or imagined
--CREATE album
--CREATE 2 tracks from that album that use your own genre of music

--UPDATE one of the tracks to be opera genre
--DELETE the opera track 

--SELECT show your track joining it together with genre, album and artist
--like we did in a previous lecture