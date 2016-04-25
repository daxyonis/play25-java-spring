-- INSERT INTO POST(zetime, useremail, title, zetext)
-- VALUES (clock_timestamp(),
-- 	'emaciejko@yahoo.com', 
-- 	'Une niaiserie',
-- 	'My first blog post won''t be about anything special. In fact, it won''t be about anything at all.');
-- INSERT INTO POST(zetime, useremail, title, zetext)	
-- VALUES	(clock_timestamp(),
-- 	'emaciejko@yahoo.com',
-- 	'Une niaiserie de plus',
-- 	'Nitz albeit alles');
-- INSERT INTO POST(zetime, useremail, title, zetext)	
-- VALUES	(clock_timestamp(),
-- 	'emaciejko@yahoo.com',
-- 	'Sans blagues !',
-- 	'Nous sommes bel et bien lundi, snif.');	

INSERT INTO POST(zetime, useremail, title, zetext)
VALUES (clock_timestamp(),
	'emaciejko@yahoo.com', 
	'Le thé vert serait bon pour le cerveau',
	'Vive le thé Sencha-Matcha du Costco.');
INSERT INTO POST(zetime, useremail, title, zetext)	
VALUES	(clock_timestamp(),
	'emaciejko@yahoo.com',
	'Le chocolat me fait du bien au moral...',
	'Eh oui, je suis une fille !');

SELECT * FROM Post;