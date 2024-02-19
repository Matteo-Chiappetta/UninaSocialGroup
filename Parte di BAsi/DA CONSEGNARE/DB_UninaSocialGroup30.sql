/*Creazione tabelle*/

CREATE TYPE tipo_post AS ENUM ('post_semplice','post_con_media');

create table Utente(
	username varchar(20) not NULL,
	email varchar(60) Primary key,
	password varchar(60) Not NULL,
	img_profilo varchar(100),
	descrizione varchar(300)
);


create table seguiti(
	email_utente_1 varchar(60),
	email_utente_2 varchar(60),
	constraint fk_utente_1 Foreign key (email_utente_1)references Utente(email) ON DELETE CASCADE,
	constraint fk_utente_2 Foreign key (email_utente_2)references Utente(email) ON DELETE CASCADE,
	constraint segui_unico UNIQUE (email_utente_1, email_utente_2)
);


create table Gruppo(
	tag varchar(20) Primary key,
	tema varchar(20) not null,
	email_creatore varchar(60)not null,
	constraint fk_creatore foreign key(email_creatore) references Utente (email)
);

create table Partecipanti_al_gruppo(
	email_membro varchar(60),
	tag_gruppo varchar(20),
	constraint fk_email foreign key (email_membro) references Utente(email) on delete cascade,
	constraint fk_tag foreign key (tag_gruppo) references gruppo(tag) on delete cascade
);

create table Post(
	id_post SERIAL PRIMARY KEY,		
	data_ora_post timestamp default current_timestamp,
	didascalia varchar(300) NOT NULL ,
	percorso_file varchar(100),
	tipo_file tipo_post,
	email_creatore_post varchar(60),
	tag_gruppo varchar(20),
	constraint fk_tag foreign key (tag_gruppo) references gruppo(tag),
	constraint fk_email_creatore_post foreign key (email_creatore_post) references utente(email)
);



create table Notifica(
	id_notifica SERIAL primary key ,
	data_ora_notifica timestamp default current_timestamp,
	id_post_pubblicato integer,
	constraint fk_post foreign key (id_post_pubblicato) references post(id_post)
);



create table Raggiunge(
	email_utente varchar(60),
	id_notifica INTEGER,
	constraint fk_email foreign key (email_utente) references utente(email),
	constraint fk_id_notifica foreign key (id_notifica) references notifica(id_notifica),
	Constraint notifica_unica unique (email_utente,id_notifica)
);



create table Commento(
	id_commento SERIAL primary key,
	data_ora_commento timestamp default current_timestamp ,
	commento varchar(300)not null,
	email_utente varchar(60),
	id_post integer,
	constraint fk_email foreign key (email_utente) references Utente(email),
	constraint fk_post foreign key (id_post) references post(id_post)
);



create table Mi_piace(
	id_mi_piace SERIAL primary key,
	data_ora_mi_piace timestamp default current_timestamp,
	email_utente varchar(60),
	id_post integer,
	constraint fk_email foreign key (email_utente) references Utente(email),
	constraint fk_post foreign key (id_post) references post(id_post),
	Constraint mi_piace_unica unique (email_utente, id_post)
);


create table richiesta(
	accettato BOOLEAN,
	data_ora_richiesta timestamp default current_timestamp,
	email_utente varchar(60),
	tag_gruppo varchar(20),
	constraint fk_tag foreign key(tag_gruppo) references Gruppo (tag),
	constraint fk_utente foreign key(email_utente) references Utente (email)
)




/*Vari alter table*/

ALTER TABLE Utente
ADD CONSTRAINT check_email_valida 
		CHECK (email ~'^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$');



ALTER TABLE Utente
ADD CONSTRAINT check_password_valida 
CHECK (
    password ~ '[0-9]' AND
    password ~ '[A-Z]' AND
    password ~ '[a-z]' AND
    password ~ '[!?@#%&$]' AND
    LENGTH(password) >= 8
);


ALTER TABLE Notifica
ADD CONSTRAINT  notifica_per_post UNIQUE (id_notifica, id_post_pubblicato);




ALTER TABLE Partecipanti_al_gruppo
	ADD CONSTRAINT partecipa_una_volta UNIQUE(email_membro, tag_gruppo);



ALTER TABLE Richiesta
ADD CONSTRAINT accettato_una_volta UNIQUE(




/*Trigger e funzioni*/


create or replace function filePresenteInPost()
returns trigger 
language plpgsql
as $filePresenteInPost$
BEGIN
    IF NEW.percorso_file IS NULL THEN
        NEW.tipo_file = 'post_semplice';
    ELSE
        NEW.tipo_file = 'post_con_media';
    END IF;
	return new;
END;
$filePresenteInPost$;



CREATE TRIGGER filePresenteInPost 
BEFORE INSERT ON Post
FOR EACH ROW EXECUTE FUNCTION filePresenteInPost();

/* */

CREATE OR REPLACE FUNCTION accettaNuovoMembro()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $accettaNuovoMembro$
BEGIN
    IF OLD.accettato IS NULL AND NEW.accettato = true THEN
        INSERT INTO Partecipanti_al_gruppo (email_membro, tag_gruppo)
        VALUES (NEW.email_utente, NEW.tag_gruppo);
    END IF;
    
    RETURN NEW;
END;
$accettaNuovoMembro$;

CREATE TRIGGER accettaNuovoMembro
AFTER UPDATE ON Richiesta
FOR EACH ROW
EXECUTE FUNCTION accettaNuovoMembro();




/* */

CREATE OR REPLACE FUNCTION fileValido()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $fileValido$
DECLARE
    valid INTEGER;
    lunghezza INTEGER;
    finale TEXT;
BEGIN
	IF (NEW.percorso_file IS NULL) THEN
        RETURN NEW;
    END IF;
    valid := 0;
    lunghezza := LENGTH(NEW.percorso_file); 
    finale := SUBSTR(NEW.percorso_file, lunghezza-3, lunghezza);

    IF (finale = '.png' OR finale = '.jpg') THEN 
        valid := 1; 
    END IF;

    IF (valid <> 1) THEN 
        RAISE EXCEPTION 'il file deve essere png o jpg'; 
    END IF;

    RETURN NEW;
END;
$fileValido$;



CREATE TRIGGER fileValido
BEFORE INSERT ON Post
FOR EACH ROW
EXECUTE FUNCTION fileValido();


/* */

CREATE OR REPLACE FUNCTION ImgProfiloValido()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $ImgProfiloValido$
DECLARE
    valid INTEGER;
    lunghezza INTEGER;
    finale TEXT;
BEGIN
    IF (NEW.img_profilo IS NULL) THEN
        RETURN NEW;
    END IF;

    valid := 0;
    lunghezza := LENGTH(NEW.img_profilo); 
    finale := SUBSTR(NEW.img_profilo, lunghezza-3, lunghezza);

    IF (finale = '.png' OR finale = '.jpg') THEN 
        valid := 1; 
    END IF;

    IF (valid <> 1) THEN 
        RAISE EXCEPTION 'il file per l immagine del profilo deve essere png o jpg'; 
    END IF;

    RETURN NEW;
END;
$ImgProfiloValido$;



CREATE TRIGGER ImgProfiloValido
BEFORE INSERT ON utente
FOR EACH ROW
EXECUTE FUNCTION ImgProfiloValido();



/* */


CREATE OR REPLACE FUNCTION notificaRaggiungeUtenti()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $notificaRaggiungeUtenti$
DECLARE 
    recupera_utenti CURSOR FOR
        SELECT PG.email_membro
        FROM Post P
        JOIN Gruppo G ON P.tag_gruppo = G.tag
        JOIN Partecipanti_al_gruppo PG ON G.tag = PG.tag_gruppo
        WHERE P.id_post = NEW.id_post_pubblicato;

    email Utente.email%TYPE;
BEGIN
    OPEN recupera_utenti;

    LOOP
        FETCH recupera_utenti INTO email;
        EXIT WHEN NOT FOUND; -- Corretto NOT FOUND
        INSERT INTO Raggiunge VALUES (email, NEW.id_notifica);
    END LOOP;

    CLOSE recupera_utenti;
RETURN NEW;
END;
$notificaRaggiungeUtenti$;
 

CREATE TRIGGER notificaRaggiungeUtenti
AFTER INSERT ON Notifica
FOR EACH ROW EXECUTE FUNCTION notificaRaggiungeUtenti();




/* */


CREATE OR REPLACE FUNCTION NuovaNotifica()
RETURNS TRIGGER
language plpgsql
AS $NuovaNotifica$
DECLARE
BEGIN
	insert into notifica(id_post_pubblicato) values (NEW.id_post);
RETURN NEW;
END;
$NuovaNotifica$;


TRIGGER:
CREATE TRIGGER NuovaNotifica
AFTER INSERT ON post
FOR EACH ROW EXECUTE FUNCTION NuovaNotifica();


/* */



CREATE OR REPLACE FUNCTION postGruppoValido()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $postGruppoValido$
DECLARE
    valid INTEGER := 0;
BEGIN
  	SELECT COUNT(*) INTO valid
	FROM Partecipanti_al_gruppo as PG
	WHERE PG.tag_gruppo = NEW.tag_gruppo AND PG.email_membro = NEW.email_creatore_post;

    IF (valid <> 1) THEN
        RAISE EXCEPTION 'L''utente non può pubblicare in questo gruppo';
    END IF;
    
    RETURN NEW;
END;
$postGruppoValido$;




CREATE TRIGGER postGruppoValido
BEFORE INSERT ON Post
FOR EACH ROW
EXECUTE FUNCTION postGruppoValido();



/* */


CREATE OR REPLACE FUNCTION primoPartecipante()
RETURNS TRIGGER
language plpgsql
AS $primoPartecipante$
BEGIN

	INSERT INTO Partecipanti_al_gruppo 
		VALUES(NEW.email_creatore, NEW.tag);
return new;

END;	
$primoPartecipante$;



CREATE TRIGGER primoPartecipante
AFTER INSERT ON Gruppo
FOR EACH ROW EXECUTE FUNCTION primoPartecipante();



/* */


CREATE OR REPLACE FUNCTION richiestaGruppoValida()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $richiestaGruppoValida$
DECLARE
    richieste_in_corso INT;
BEGIN
    SELECT COUNT(*) INTO richieste_in_corso
    FROM Richiesta AS R
    WHERE R.email_utente = NEW.email_utente 
        AND R.tag_gruppo = NEW.tag_gruppo
        AND R.accettato IS NULL;

    IF richieste_in_corso > 0 THEN
        RAISE EXCEPTION 'Richiesta inserita non valida: esiste già una richiesta ancora da considerare.';
    END IF;

    RETURN NEW;
END;
$richiestaGruppoValida$;



CREATE TRIGGER richiestaGruppoValida
BEFORE INSERT ON Richiesta
FOR EACH ROW
EXECUTE richiestaGruppoValida();



/* */


CREATE OR REPLACE FUNCTION utenteInGruppoValida()
RETURNS TRIGGER
language plpgsql
AS $utenteInGruppoValida$
DECLARE
	utente_in_gruppo INT;
BEGIN

	SELECT COUNT(*) INTO utente_in_gruppo
		FROM Partecipanti_al_gruppo AS P
		WHERE P.email_membro = NEW.email_utente 
				AND P.tag_gruppo = NEW.tag_gruppo;

	IF ( utente_in_gruppo > 0 ) THEN
		RAISE EXCEPTION 'Richiesta inserita non valida: l utente fa gia parte del gruppo';
	END IF;
	RETURN NEW;
END;
$utenteInGruppoValida$;




CREATE TRIGGER utenteInGruppoValida
BEFORE INSERT ON Richiesta
FOR EACH ROW
EXECUTE FUNCTION utenteInGruppoValida();



/* */



CREATE OR REPLACE FUNCTION MiPiaceGruppoValido()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $MiPiaceGruppoValido$
DECLARE
	gruppo post.tag_gruppo%TYPE;
	partecipazione INTEGER := 0;
    
    
BEGIN

    SELECT P.tag_gruppo INTO gruppo
	FROM POST as P
	WHERE P.id_post = NEW.id_post;
 
	
	SELECT COUNT(*) INTO partecipazione
	FROM Partecipanti_al_gruppo as PG
	WHERE PG.tag_gruppo = gruppo AND PG.email_membro = NEW.email_utente;

    IF (partecipazione <> 1) THEN
        RAISE EXCEPTION 'L''utente non può mettere mi piace a questo Post';
    END IF;
    
    RETURN NEW;
END;
$MiPiaceGruppoValido$;



CREATE TRIGGER MiPiaceGruppoValido
BEFORE INSERT ON Mi_Piace
FOR EACH ROW 
EXECUTE FUNCTION MiPiaceGruppoValido();


/* */


CREATE OR REPLACE FUNCTION CommentoGruppoValido()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $CommentoGruppoValido$
DECLARE
	gruppo post.tag_gruppo%TYPE;
	partecipazione INTEGER := 0;
    
    
BEGIN

    SELECT P.tag_gruppo INTO gruppo
	FROM POST as P
	WHERE P.id_post = NEW.id_post;
 
	
	SELECT COUNT(*) INTO partecipazione
	FROM Partecipanti_al_gruppo as PG
	WHERE PG.tag_gruppo = gruppo AND PG.email_membro = NEW.email_utente;

    IF (partecipazione <> 1) THEN
        RAISE EXCEPTION 'L''utente non può commentare sotto a questo Post';
    END IF;
    
    RETURN NEW;
END;
$CommentoGruppoValido$;


CREATE TRIGGER CommentoGruppoValido
BEFORE INSERT ON Commento
FOR EACH ROW 
EXECUTE FUNCTION CommentoGruppoValido();







/*Popolamento del DB*/
/*Popolamento tabella Utente*/

INSERT INTO Utente (username,email,password,descrizione) values ('Sabrina','Sabrina@email.it','Sab2004!','Ciao a tutti');

INSERT INTO Utente (username,email,password,descrizione,img_profilo) values ('Matteo','Matteo@email.it','Matt2002!','Ciao a tutti','foto.jpg');

INSERT INTO Utente (username,email,password,descrizione,img_profilo) values ('Francesco','Vitale@email.it','Viki2002!','Ciao a tutti','fotina.jpg');

INSERT INTO Utente (username,email,password,descrizione) values ('Alessandro','Ale@email.it','Aleandro2002!','Ciao a tutti sono Ale');

INSERT INTO Utente (username,email,password,descrizione) values ('Benedetta','benny@email.it','Benny2002!','Ciao a tutti sono Benedetta');

INSERT INTO Utente (username,email,password,descrizione) values ('OfficerK','Officer@email.it','OfficerK2049!','Sto solo facendo il mio lavoro');

INSERT INTO Utente (username,email,password,descrizione) values ('Mila','Mila@email.it','Mila1994!','Ciao a tutti sono una brava giocatrice');



/*Popolamento tabella Gruppo */

INSERT INTO Gruppo (tag, tema, email_creatore) VALUES ('Anime','AnimeUnina','Sabrina@email.it');

INSERT INTO Gruppo (tag, tema, email_creatore) VALUES ('VideoGame','VideoGameUnina','Matteo@email.it');

INSERT INTO Gruppo (tag, tema, email_creatore) VALUES ('WebTecnology','WebTecnology','Vitale@email.it');

INSERT INTO Gruppo (tag, tema, email_creatore) VALUES ('pallavolo','PallavoloUnina','Mila@email.it');


/* Popolamento tabella Richiesta. Nota: la tabella Partecipanti_al_gruppo viene popolata grazie ai progressivi UPDATE su Richiesta (vedi trigger accettaNuovoMembro)*/

INSERT INTO Richiesta (email_utente,tag_gruppo) VALUES ('Vitale@email.it','Anime');

INSERT INTO Richiesta (email_utente,tag_gruppo) VALUES ('Matteo@email.it','Anime');

INSERT INTO Richiesta (email_utente,tag_gruppo) VALUES ('benny@email.it','VideoGame');

UPDATE Richiesta
    SET accettato = true
    WHERE email_utente = 'Vitale@email.it' AND tag_gruppo = 'Anime' 
	AND data_ora_richiesta = (select MAX(data_ora_richiesta) 
								from richiesta where 
    							email_utente =  'Vitale@email.it' AND tag_gruppo = 'Anime' 
         						);
UPDATE Richiesta
    SET accettato = false
    WHERE email_utente = 'Matteo@email.it' AND tag_gruppo = 'Anime' 
	AND data_ora_richiesta = (select MAX(data_ora_richiesta) 
								from richiesta where 
    							email_utente =  'Matteo@email.it' AND tag_gruppo = 'Anime' 
         						);

UPDATE Richiesta
    SET accettato = true
    WHERE email_utente = 'benny@email.it' AND tag_gruppo = 'VideoGame' 
	AND data_ora_richiesta = (select MAX(data_ora_richiesta) 
								from richiesta where 
    							email_utente =  'benny@email.it' AND tag_gruppo = 'VideoGame' 
         						);

INSERT INTO Richiesta (email_utente,tag_gruppo) VALUES ('Ale@email.it','VideoGame');

UPDATE Richiesta
    SET accettato = false
    WHERE email_utente = 'Ale@email.it' AND tag_gruppo = 'VideoGame' 
	AND data_ora_richiesta = (select MAX(data_ora_richiesta) 
								from richiesta where 
    							email_utente =  'Ale@email.it' AND tag_gruppo = 'VideoGame' 
         						);

INSERT INTO Richiesta (email_utente,tag_gruppo) VALUES ('Ale@email.it','VideoGame');

INSERT INTO Richiesta (email_utente,tag_gruppo) VALUES ('Ale@email.it','WebTecnology');

INSERT INTO Richiesta (email_utente,tag_gruppo) VALUES ('benny@email.it','WebTecnology');

INSERT INTO Richiesta (email_utente,tag_gruppo) VALUES ('benny@email.it','Anime');


UPDATE Richiesta
    SET accettato = true
    WHERE email_utente = 'Ale@email.it' AND tag_gruppo = 'WebTecnology' 
	AND data_ora_richiesta = (select MAX(data_ora_richiesta) 
								from richiesta where 
    							email_utente =  'Ale@email.it' AND tag_gruppo = 'WebTecnology' 
         						);

UPDATE Richiesta
    SET accettato = true
    WHERE email_utente = 'Ale@email.it' AND tag_gruppo = 'VideoGame' 
	AND data_ora_richiesta = (select MAX(data_ora_richiesta) 
								from richiesta where 
    							email_utente =  'Ale@email.it' AND tag_gruppo = 'VideoGame' 
         						);

INSERT INTO Richiesta (email_utente,tag_gruppo) VALUES ('Matteo@email.it','WebTecnology');


UPDATE Richiesta
    SET accettato = true
    WHERE email_utente = 'Matteo@email.it' AND tag_gruppo = 'WebTecnology' 
	AND data_ora_richiesta = (select MAX(data_ora_richiesta) 
								from richiesta where 
    							email_utente =  'Matteo@email.it' AND tag_gruppo = 'WebTecnology' 
         						);


UPDATE Richiesta
    SET accettato = true
    WHERE email_utente = 'benny@email.it' AND tag_gruppo = 'WebTecnology' 
	AND data_ora_richiesta = (select MAX(data_ora_richiesta) 
								from richiesta where 
    							email_utente =  'benny@email.it' AND tag_gruppo = 'WebTecnology' 
         						);


INSERT INTO Richiesta (email_utente,tag_gruppo) VALUES ('Matteo@email.it','Anime');

UPDATE Richiesta
    SET accettato = true
    WHERE email_utente = 'Matteo@email.it' AND tag_gruppo = 'Anime' 
	AND data_ora_richiesta = (select MAX(data_ora_richiesta) 
								from richiesta where 
    							email_utente =  'Matteo@email.it' AND tag_gruppo = 'Anime' 
         						);


/*Popolamento tabella Post. Nota: Le tabelle Notifica e Raggiunge vengono popolate automaticamente dai trigger nuovaNotifica e notificaRaggiungeUtenti. */

INSERT INTO Post (didascalia,email_creatore_post,tag_gruppo,percorso_file) VALUES ('Ciao, il mio primo post su AnimeUnina.','Sabrina@email.it','Anime','Fotin.jpg');

INSERT INTO Post (didascalia,email_creatore_post,tag_gruppo) VALUES ('Ciao, il mio secondo post su AnimeUnina.','Sabrina@email.it','Anime');

INSERT INTO Post (didascalia,email_creatore_post,tag_gruppo,percorso_file) VALUES ('Ciao!','Matteo@email.it','VideoGame','Foto.jpg');

INSERT INTO Post (didascalia,email_creatore_post,tag_gruppo) VALUES ('Ciao, mi piace questo social.','Vitale@email.it','WebTecnology');


/*Popolamento tabella Commento */

INSERT INTO Commento (commento,email_utente,id_post) VALUES ('bello questo post','Vitale@email.it',6);

INSERT INTO Commento (commento,email_utente,id_post) VALUES ('che bella idea!','Matteo@email.it',6);

INSERT INTO Commento (commento,email_utente,id_post) VALUES ('bravo, bel post.','Ale@email.it',3);

INSERT INTO Commento (commento,email_utente,id_post) VALUES ('wow <3','benny@email.it',6);


/*Popolamento tabella Mi_piace */

INSERT INTO Mi_piace (email_utente,id_post) VALUES ('Vitale@email.it',6);

INSERT INTO Mi_piace (email_utente,id_post) VALUES ('Matteo@email.it',6);

INSERT INTO Mi_piace (email_utente,id_post) VALUES ('Sabrina@email.it',6);

INSERT INTO Mi_piace (email_utente,id_post) VALUES ('benny@email.it',3);

INSERT INTO Mi_piace (email_utente,id_post) VALUES ('Ale@email.it',3);

INSERT INTO Mi_piace (email_utente,id_post) VALUES ('Matteo@email.it',3);

INSERT INTO Mi_piace (email_utente,id_post) VALUES ('Officer@email.it',7);

INSERT INTO Mi_piace (email_utente,id_post) VALUES ('Mila@email.it',7);