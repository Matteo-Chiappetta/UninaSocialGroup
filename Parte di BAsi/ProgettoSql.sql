PGDMP  &    9                |            Progetto    16.2    16.2 \    b           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            c           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            d           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            e           1262    16576    Progetto    DATABASE     }   CREATE DATABASE "Progetto" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Italian_Italy.1252';
    DROP DATABASE "Progetto";
                postgres    false            j           1247    16623 	   tipo_post    TYPE     T   CREATE TYPE public.tipo_post AS ENUM (
    'post_semplice',
    'post_con_media'
);
    DROP TYPE public.tipo_post;
       public          postgres    false            �            1255    16722    accettanuovomembro()    FUNCTION     ?  CREATE FUNCTION public.accettanuovomembro() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF OLD.accettato IS NULL AND NEW.accettato = true THEN
        INSERT INTO Partecipanti_al_gruppo (email_membro, tag_gruppo)
        VALUES (NEW.email_utente, NEW.tag_gruppo);
    END IF;
    
    RETURN NEW;
END;
$$;
 +   DROP FUNCTION public.accettanuovomembro();
       public          postgres    false            �            1255    16758    commentogruppovalido()    FUNCTION     2  CREATE FUNCTION public.commentogruppovalido() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
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
        RAISE EXCEPTION 'L''utente non può mettere commento a questo Post';
    END IF;
    
    RETURN NEW;
END;
$$;
 -   DROP FUNCTION public.commentogruppovalido();
       public          postgres    false            �            1255    16720    filepresenteinpost()    FUNCTION       CREATE FUNCTION public.filepresenteinpost() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF NEW.percorso_file IS NULL THEN
        NEW.tipo_file = 'post_semplice';
    ELSE
        NEW.tipo_file = 'post_con_media';
    END IF;
	return new;
END;
$$;
 +   DROP FUNCTION public.filepresenteinpost();
       public          postgres    false            �            1255    16724    filevalido()    FUNCTION     .  CREATE FUNCTION public.filevalido() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
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
$$;
 #   DROP FUNCTION public.filevalido();
       public          postgres    false            �            1255    16726    imgprofilovalido()    FUNCTION     M  CREATE FUNCTION public.imgprofilovalido() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
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
$$;
 )   DROP FUNCTION public.imgprofilovalido();
       public          postgres    false            �            1255    16756    mipiacegruppovalido()    FUNCTION     1  CREATE FUNCTION public.mipiacegruppovalido() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
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
$$;
 ,   DROP FUNCTION public.mipiacegruppovalido();
       public          postgres    false            �            1255    16728    notificaraggiungeutenti()    FUNCTION     ~  CREATE FUNCTION public.notificaraggiungeutenti() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
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
$$;
 0   DROP FUNCTION public.notificaraggiungeutenti();
       public          postgres    false            �            1255    16730    nuovanotifica()    FUNCTION     �   CREATE FUNCTION public.nuovanotifica() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
BEGIN
	insert into notifica(id_post_pubblicato) values (NEW.id_post);
RETURN NEW;
END;
$$;
 &   DROP FUNCTION public.nuovanotifica();
       public          postgres    false            �            1255    16732    postgruppovalido()    FUNCTION       CREATE FUNCTION public.postgruppovalido() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
    recupera_partecipazioni CURSOR FOR
        SELECT P.tag_gruppo
        FROM Partecipanti_al_gruppo AS P
        WHERE P.email_membro = NEW.email_creatore_post;
        
    gruppo Partecipanti_al_gruppo.tag_gruppo%TYPE;
    valid INTEGER;
BEGIN
    valid := 0;
    OPEN recupera_partecipazioni;
    
    LOOP
        FETCH recupera_partecipazioni INTO gruppo;
        EXIT WHEN valid = 1 OR NOT FOUND; 
        
        IF (gruppo = NEW.tag_gruppo) THEN
            valid := 1;
        END IF;
    END LOOP;
    
    CLOSE recupera_partecipazioni; 

    IF (valid <> 1) THEN
        RAISE EXCEPTION 'L''utente non può pubblicare in questo gruppo';
    END IF;
    
    RETURN NEW;
END;
$$;
 )   DROP FUNCTION public.postgruppovalido();
       public          postgres    false            �            1255    16734    primopartecipante()    FUNCTION     �   CREATE FUNCTION public.primopartecipante() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN

	INSERT INTO Partecipanti_al_gruppo 
		VALUES(NEW.email_creatore, NEW.tag);
return new;

END;	
$$;
 *   DROP FUNCTION public.primopartecipante();
       public          postgres    false            �            1255    16736    richiestagruppovalida()    FUNCTION     �  CREATE FUNCTION public.richiestagruppovalida() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
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
$$;
 .   DROP FUNCTION public.richiestagruppovalida();
       public          postgres    false            �            1255    16738    utenteingruppovalida()    FUNCTION     �  CREATE FUNCTION public.utenteingruppovalida() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
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
$$;
 -   DROP FUNCTION public.utenteingruppovalida();
       public          postgres    false            �            1259    16672    commento    TABLE     �   CREATE TABLE public.commento (
    id_commento integer NOT NULL,
    commento character varying(300) NOT NULL,
    email_utente character varying(60),
    id_post integer,
    data_ora_commento timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);
    DROP TABLE public.commento;
       public         heap    postgres    false            �            1259    16671    commento_id_commento_seq    SEQUENCE     �   CREATE SEQUENCE public.commento_id_commento_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public.commento_id_commento_seq;
       public          postgres    false    225            f           0    0    commento_id_commento_seq    SEQUENCE OWNED BY     U   ALTER SEQUENCE public.commento_id_commento_seq OWNED BY public.commento.id_commento;
          public          postgres    false    224            �            1259    16599    gruppo    TABLE     �   CREATE TABLE public.gruppo (
    tag character varying(20) NOT NULL,
    tema character varying(20) NOT NULL,
    email_creatore character varying(60) NOT NULL
);
    DROP TABLE public.gruppo;
       public         heap    postgres    false            �            1259    16689    mi_piace    TABLE     �   CREATE TABLE public.mi_piace (
    id_mi_piace integer NOT NULL,
    email_utente character varying(60),
    id_post integer,
    data_ora_mi_piace timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);
    DROP TABLE public.mi_piace;
       public         heap    postgres    false            �            1259    16688    mi_piace_id_mi_piace_seq    SEQUENCE     �   CREATE SEQUENCE public.mi_piace_id_mi_piace_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public.mi_piace_id_mi_piace_seq;
       public          postgres    false    227            g           0    0    mi_piace_id_mi_piace_seq    SEQUENCE OWNED BY     U   ALTER SEQUENCE public.mi_piace_id_mi_piace_seq OWNED BY public.mi_piace.id_mi_piace;
          public          postgres    false    226            �            1259    16645    notifica    TABLE     �   CREATE TABLE public.notifica (
    id_notifica integer NOT NULL,
    id_post_pubblicato integer,
    data_ora_notifica timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);
    DROP TABLE public.notifica;
       public         heap    postgres    false            �            1259    16644    notifica_id_notifica_seq    SEQUENCE     �   CREATE SEQUENCE public.notifica_id_notifica_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public.notifica_id_notifica_seq;
       public          postgres    false    222            h           0    0    notifica_id_notifica_seq    SEQUENCE OWNED BY     U   ALTER SEQUENCE public.notifica_id_notifica_seq OWNED BY public.notifica.id_notifica;
          public          postgres    false    221            �            1259    16609    partecipanti_al_gruppo    TABLE     }   CREATE TABLE public.partecipanti_al_gruppo (
    email_membro character varying(60),
    tag_gruppo character varying(20)
);
 *   DROP TABLE public.partecipanti_al_gruppo;
       public         heap    postgres    false            �            1259    16628    post    TABLE     \  CREATE TABLE public.post (
    id_post integer NOT NULL,
    didascalia character varying(300) NOT NULL,
    percorso_file character varying(100),
    tipo_file public.tipo_post NOT NULL,
    email_creatore_post character varying(60),
    tag_gruppo character varying(20),
    data_ora_post timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);
    DROP TABLE public.post;
       public         heap    postgres    false    874            �            1259    16627    post_id_post_seq    SEQUENCE     �   CREATE SEQUENCE public.post_id_post_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.post_id_post_seq;
       public          postgres    false    220            i           0    0    post_id_post_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.post_id_post_seq OWNED BY public.post.id_post;
          public          postgres    false    219            �            1259    16656 	   raggiunge    TABLE     c   CREATE TABLE public.raggiunge (
    email_utente character varying(60),
    id_notifica integer
);
    DROP TABLE public.raggiunge;
       public         heap    postgres    false            �            1259    16707 	   richiesta    TABLE     �   CREATE TABLE public.richiesta (
    accettato boolean,
    email_utente character varying(60),
    tag_gruppo character varying(20),
    data_ora_richiesta timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);
    DROP TABLE public.richiesta;
       public         heap    postgres    false            �            1259    16584    seguiti    TABLE     t   CREATE TABLE public.seguiti (
    email_utente_1 character varying(60),
    email_utente_2 character varying(60)
);
    DROP TABLE public.seguiti;
       public         heap    postgres    false            �            1259    16577    utente    TABLE     Y  CREATE TABLE public.utente (
    username character varying(20) NOT NULL,
    email character varying(60) NOT NULL,
    password character varying(60) NOT NULL,
    img_profilo character varying(100),
    descrizione character varying(300),
    CONSTRAINT check_email_valida CHECK (((email)::text ~ '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$'::text)),
    CONSTRAINT check_password_valida CHECK ((((password)::text ~ '[0-9]'::text) AND ((password)::text ~ '[A-Z]'::text) AND ((password)::text ~ '[a-z]'::text) AND ((password)::text ~ '[!?@#%&$]'::text) AND (length((password)::text) >= 8)))
);
    DROP TABLE public.utente;
       public         heap    postgres    false            �           2604    16675    commento id_commento    DEFAULT     |   ALTER TABLE ONLY public.commento ALTER COLUMN id_commento SET DEFAULT nextval('public.commento_id_commento_seq'::regclass);
 C   ALTER TABLE public.commento ALTER COLUMN id_commento DROP DEFAULT;
       public          postgres    false    225    224    225            �           2604    16692    mi_piace id_mi_piace    DEFAULT     |   ALTER TABLE ONLY public.mi_piace ALTER COLUMN id_mi_piace SET DEFAULT nextval('public.mi_piace_id_mi_piace_seq'::regclass);
 C   ALTER TABLE public.mi_piace ALTER COLUMN id_mi_piace DROP DEFAULT;
       public          postgres    false    227    226    227            �           2604    16648    notifica id_notifica    DEFAULT     |   ALTER TABLE ONLY public.notifica ALTER COLUMN id_notifica SET DEFAULT nextval('public.notifica_id_notifica_seq'::regclass);
 C   ALTER TABLE public.notifica ALTER COLUMN id_notifica DROP DEFAULT;
       public          postgres    false    222    221    222            �           2604    16631    post id_post    DEFAULT     l   ALTER TABLE ONLY public.post ALTER COLUMN id_post SET DEFAULT nextval('public.post_id_post_seq'::regclass);
 ;   ALTER TABLE public.post ALTER COLUMN id_post DROP DEFAULT;
       public          postgres    false    220    219    220            \          0    16672    commento 
   TABLE DATA           c   COPY public.commento (id_commento, commento, email_utente, id_post, data_ora_commento) FROM stdin;
    public          postgres    false    225   ��       T          0    16599    gruppo 
   TABLE DATA           ;   COPY public.gruppo (tag, tema, email_creatore) FROM stdin;
    public          postgres    false    217   -�       ^          0    16689    mi_piace 
   TABLE DATA           Y   COPY public.mi_piace (id_mi_piace, email_utente, id_post, data_ora_mi_piace) FROM stdin;
    public          postgres    false    227   ��       Y          0    16645    notifica 
   TABLE DATA           V   COPY public.notifica (id_notifica, id_post_pubblicato, data_ora_notifica) FROM stdin;
    public          postgres    false    222   j�       U          0    16609    partecipanti_al_gruppo 
   TABLE DATA           J   COPY public.partecipanti_al_gruppo (email_membro, tag_gruppo) FROM stdin;
    public          postgres    false    218   ރ       W          0    16628    post 
   TABLE DATA           }   COPY public.post (id_post, didascalia, percorso_file, tipo_file, email_creatore_post, tag_gruppo, data_ora_post) FROM stdin;
    public          postgres    false    220   k�       Z          0    16656 	   raggiunge 
   TABLE DATA           >   COPY public.raggiunge (email_utente, id_notifica) FROM stdin;
    public          postgres    false    223   ��       _          0    16707 	   richiesta 
   TABLE DATA           \   COPY public.richiesta (accettato, email_utente, tag_gruppo, data_ora_richiesta) FROM stdin;
    public          postgres    false    228   #�       S          0    16584    seguiti 
   TABLE DATA           A   COPY public.seguiti (email_utente_1, email_utente_2) FROM stdin;
    public          postgres    false    216   �       R          0    16577    utente 
   TABLE DATA           U   COPY public.utente (username, email, password, img_profilo, descrizione) FROM stdin;
    public          postgres    false    215   l�       j           0    0    commento_id_commento_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.commento_id_commento_seq', 6, true);
          public          postgres    false    224            k           0    0    mi_piace_id_mi_piace_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.mi_piace_id_mi_piace_seq', 11, true);
          public          postgres    false    226            l           0    0    notifica_id_notifica_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.notifica_id_notifica_seq', 6, true);
          public          postgres    false    221            m           0    0    post_id_post_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.post_id_post_seq', 7, true);
          public          postgres    false    219            �           2606    16677    commento commento_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.commento
    ADD CONSTRAINT commento_pkey PRIMARY KEY (id_commento);
 @   ALTER TABLE ONLY public.commento DROP CONSTRAINT commento_pkey;
       public            postgres    false    225            �           2606    16603    gruppo gruppo_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY public.gruppo
    ADD CONSTRAINT gruppo_pkey PRIMARY KEY (tag);
 <   ALTER TABLE ONLY public.gruppo DROP CONSTRAINT gruppo_pkey;
       public            postgres    false    217            �           2606    16694    mi_piace mi_piace_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.mi_piace
    ADD CONSTRAINT mi_piace_pkey PRIMARY KEY (id_mi_piace);
 @   ALTER TABLE ONLY public.mi_piace DROP CONSTRAINT mi_piace_pkey;
       public            postgres    false    227            �           2606    16696    mi_piace mi_piace_unica 
   CONSTRAINT     c   ALTER TABLE ONLY public.mi_piace
    ADD CONSTRAINT mi_piace_unica UNIQUE (email_utente, id_post);
 A   ALTER TABLE ONLY public.mi_piace DROP CONSTRAINT mi_piace_unica;
       public            postgres    false    227    227            �           2606    16743    notifica notifica_per_post 
   CONSTRAINT     p   ALTER TABLE ONLY public.notifica
    ADD CONSTRAINT notifica_per_post UNIQUE (id_notifica, id_post_pubblicato);
 D   ALTER TABLE ONLY public.notifica DROP CONSTRAINT notifica_per_post;
       public            postgres    false    222    222            �           2606    16650    notifica notifica_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.notifica
    ADD CONSTRAINT notifica_pkey PRIMARY KEY (id_notifica);
 @   ALTER TABLE ONLY public.notifica DROP CONSTRAINT notifica_pkey;
       public            postgres    false    222            �           2606    16660    raggiunge notifica_unica 
   CONSTRAINT     h   ALTER TABLE ONLY public.raggiunge
    ADD CONSTRAINT notifica_unica UNIQUE (email_utente, id_notifica);
 B   ALTER TABLE ONLY public.raggiunge DROP CONSTRAINT notifica_unica;
       public            postgres    false    223    223            �           2606    16745 *   partecipanti_al_gruppo partecipa_una_volta 
   CONSTRAINT     y   ALTER TABLE ONLY public.partecipanti_al_gruppo
    ADD CONSTRAINT partecipa_una_volta UNIQUE (email_membro, tag_gruppo);
 T   ALTER TABLE ONLY public.partecipanti_al_gruppo DROP CONSTRAINT partecipa_una_volta;
       public            postgres    false    218    218            �           2606    16633    post post_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY public.post
    ADD CONSTRAINT post_pkey PRIMARY KEY (id_post);
 8   ALTER TABLE ONLY public.post DROP CONSTRAINT post_pkey;
       public            postgres    false    220            �           2606    16761    seguiti segui_unico 
   CONSTRAINT     h   ALTER TABLE ONLY public.seguiti
    ADD CONSTRAINT segui_unico UNIQUE (email_utente_1, email_utente_2);
 =   ALTER TABLE ONLY public.seguiti DROP CONSTRAINT segui_unico;
       public            postgres    false    216    216            �           2606    16583    utente utente_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY public.utente
    ADD CONSTRAINT utente_pkey PRIMARY KEY (email);
 <   ALTER TABLE ONLY public.utente DROP CONSTRAINT utente_pkey;
       public            postgres    false    215            �           2620    16723    richiesta accettanuovomembro    TRIGGER     ~   CREATE TRIGGER accettanuovomembro AFTER UPDATE ON public.richiesta FOR EACH ROW EXECUTE FUNCTION public.accettanuovomembro();
 5   DROP TRIGGER accettanuovomembro ON public.richiesta;
       public          postgres    false    230    228            �           2620    16759    commento commentogruppovalido    TRIGGER     �   CREATE TRIGGER commentogruppovalido BEFORE INSERT ON public.commento FOR EACH ROW EXECUTE FUNCTION public.commentogruppovalido();
 6   DROP TRIGGER commentogruppovalido ON public.commento;
       public          postgres    false    225    250            �           2620    16721    post filepresenteinpost    TRIGGER     z   CREATE TRIGGER filepresenteinpost BEFORE INSERT ON public.post FOR EACH ROW EXECUTE FUNCTION public.filepresenteinpost();
 0   DROP TRIGGER filepresenteinpost ON public.post;
       public          postgres    false    229    220            �           2620    16725    post filevalido    TRIGGER     j   CREATE TRIGGER filevalido BEFORE INSERT ON public.post FOR EACH ROW EXECUTE FUNCTION public.filevalido();
 (   DROP TRIGGER filevalido ON public.post;
       public          postgres    false    220    231            �           2620    16727    utente imgprofilovalido    TRIGGER     x   CREATE TRIGGER imgprofilovalido BEFORE INSERT ON public.utente FOR EACH ROW EXECUTE FUNCTION public.imgprofilovalido();
 0   DROP TRIGGER imgprofilovalido ON public.utente;
       public          postgres    false    215    232            �           2620    16757    mi_piace mipiacegruppovalido    TRIGGER     �   CREATE TRIGGER mipiacegruppovalido BEFORE INSERT ON public.mi_piace FOR EACH ROW EXECUTE FUNCTION public.mipiacegruppovalido();
 5   DROP TRIGGER mipiacegruppovalido ON public.mi_piace;
       public          postgres    false    227    251            �           2620    16729     notifica notificaraggiungeutenti    TRIGGER     �   CREATE TRIGGER notificaraggiungeutenti AFTER INSERT ON public.notifica FOR EACH ROW EXECUTE FUNCTION public.notificaraggiungeutenti();
 9   DROP TRIGGER notificaraggiungeutenti ON public.notifica;
       public          postgres    false    222    233            �           2620    16731    post nuovanotifica    TRIGGER     o   CREATE TRIGGER nuovanotifica AFTER INSERT ON public.post FOR EACH ROW EXECUTE FUNCTION public.nuovanotifica();
 +   DROP TRIGGER nuovanotifica ON public.post;
       public          postgres    false    234    220            �           2620    16733    post postgruppovalido    TRIGGER     v   CREATE TRIGGER postgruppovalido BEFORE INSERT ON public.post FOR EACH ROW EXECUTE FUNCTION public.postgruppovalido();
 .   DROP TRIGGER postgruppovalido ON public.post;
       public          postgres    false    235    220            �           2620    16735    gruppo primopartecipante    TRIGGER     y   CREATE TRIGGER primopartecipante AFTER INSERT ON public.gruppo FOR EACH ROW EXECUTE FUNCTION public.primopartecipante();
 1   DROP TRIGGER primopartecipante ON public.gruppo;
       public          postgres    false    236    217            �           2620    16737    richiesta richiestagruppovalida    TRIGGER     �   CREATE TRIGGER richiestagruppovalida BEFORE INSERT ON public.richiesta FOR EACH ROW EXECUTE FUNCTION public.richiestagruppovalida();
 8   DROP TRIGGER richiestagruppovalida ON public.richiesta;
       public          postgres    false    237    228            �           2620    16739    richiesta utenteingruppovalida    TRIGGER     �   CREATE TRIGGER utenteingruppovalida BEFORE INSERT ON public.richiesta FOR EACH ROW EXECUTE FUNCTION public.utenteingruppovalida();
 7   DROP TRIGGER utenteingruppovalida ON public.richiesta;
       public          postgres    false    228    238            �           2606    16604    gruppo fk_creatore    FK CONSTRAINT     |   ALTER TABLE ONLY public.gruppo
    ADD CONSTRAINT fk_creatore FOREIGN KEY (email_creatore) REFERENCES public.utente(email);
 <   ALTER TABLE ONLY public.gruppo DROP CONSTRAINT fk_creatore;
       public          postgres    false    217    4754    215            �           2606    16612    partecipanti_al_gruppo fk_email    FK CONSTRAINT     �   ALTER TABLE ONLY public.partecipanti_al_gruppo
    ADD CONSTRAINT fk_email FOREIGN KEY (email_membro) REFERENCES public.utente(email) ON DELETE CASCADE;
 I   ALTER TABLE ONLY public.partecipanti_al_gruppo DROP CONSTRAINT fk_email;
       public          postgres    false    218    4754    215            �           2606    16661    raggiunge fk_email    FK CONSTRAINT     z   ALTER TABLE ONLY public.raggiunge
    ADD CONSTRAINT fk_email FOREIGN KEY (email_utente) REFERENCES public.utente(email);
 <   ALTER TABLE ONLY public.raggiunge DROP CONSTRAINT fk_email;
       public          postgres    false    215    223    4754            �           2606    16678    commento fk_email    FK CONSTRAINT     y   ALTER TABLE ONLY public.commento
    ADD CONSTRAINT fk_email FOREIGN KEY (email_utente) REFERENCES public.utente(email);
 ;   ALTER TABLE ONLY public.commento DROP CONSTRAINT fk_email;
       public          postgres    false    225    4754    215            �           2606    16697    mi_piace fk_email    FK CONSTRAINT     y   ALTER TABLE ONLY public.mi_piace
    ADD CONSTRAINT fk_email FOREIGN KEY (email_utente) REFERENCES public.utente(email);
 ;   ALTER TABLE ONLY public.mi_piace DROP CONSTRAINT fk_email;
       public          postgres    false    215    227    4754            �           2606    16639    post fk_email_creatore_post    FK CONSTRAINT     �   ALTER TABLE ONLY public.post
    ADD CONSTRAINT fk_email_creatore_post FOREIGN KEY (email_creatore_post) REFERENCES public.utente(email);
 E   ALTER TABLE ONLY public.post DROP CONSTRAINT fk_email_creatore_post;
       public          postgres    false    215    220    4754            �           2606    16666    raggiunge fk_id_notifica    FK CONSTRAINT     �   ALTER TABLE ONLY public.raggiunge
    ADD CONSTRAINT fk_id_notifica FOREIGN KEY (id_notifica) REFERENCES public.notifica(id_notifica);
 B   ALTER TABLE ONLY public.raggiunge DROP CONSTRAINT fk_id_notifica;
       public          postgres    false    4766    223    222            �           2606    16651    notifica fk_post    FK CONSTRAINT     ~   ALTER TABLE ONLY public.notifica
    ADD CONSTRAINT fk_post FOREIGN KEY (id_post_pubblicato) REFERENCES public.post(id_post);
 :   ALTER TABLE ONLY public.notifica DROP CONSTRAINT fk_post;
       public          postgres    false    220    4762    222            �           2606    16683    commento fk_post    FK CONSTRAINT     s   ALTER TABLE ONLY public.commento
    ADD CONSTRAINT fk_post FOREIGN KEY (id_post) REFERENCES public.post(id_post);
 :   ALTER TABLE ONLY public.commento DROP CONSTRAINT fk_post;
       public          postgres    false    4762    220    225            �           2606    16702    mi_piace fk_post    FK CONSTRAINT     s   ALTER TABLE ONLY public.mi_piace
    ADD CONSTRAINT fk_post FOREIGN KEY (id_post) REFERENCES public.post(id_post);
 :   ALTER TABLE ONLY public.mi_piace DROP CONSTRAINT fk_post;
       public          postgres    false    227    220    4762            �           2606    16617    partecipanti_al_gruppo fk_tag    FK CONSTRAINT     �   ALTER TABLE ONLY public.partecipanti_al_gruppo
    ADD CONSTRAINT fk_tag FOREIGN KEY (tag_gruppo) REFERENCES public.gruppo(tag) ON DELETE CASCADE;
 G   ALTER TABLE ONLY public.partecipanti_al_gruppo DROP CONSTRAINT fk_tag;
       public          postgres    false    218    4758    217            �           2606    16634    post fk_tag    FK CONSTRAINT     o   ALTER TABLE ONLY public.post
    ADD CONSTRAINT fk_tag FOREIGN KEY (tag_gruppo) REFERENCES public.gruppo(tag);
 5   ALTER TABLE ONLY public.post DROP CONSTRAINT fk_tag;
       public          postgres    false    217    220    4758            �           2606    16710    richiesta fk_tag    FK CONSTRAINT     t   ALTER TABLE ONLY public.richiesta
    ADD CONSTRAINT fk_tag FOREIGN KEY (tag_gruppo) REFERENCES public.gruppo(tag);
 :   ALTER TABLE ONLY public.richiesta DROP CONSTRAINT fk_tag;
       public          postgres    false    228    4758    217            �           2606    16715    richiesta fk_utente    FK CONSTRAINT     {   ALTER TABLE ONLY public.richiesta
    ADD CONSTRAINT fk_utente FOREIGN KEY (email_utente) REFERENCES public.utente(email);
 =   ALTER TABLE ONLY public.richiesta DROP CONSTRAINT fk_utente;
       public          postgres    false    228    215    4754            �           2606    16589    seguiti fk_utente_1    FK CONSTRAINT     �   ALTER TABLE ONLY public.seguiti
    ADD CONSTRAINT fk_utente_1 FOREIGN KEY (email_utente_1) REFERENCES public.utente(email) ON DELETE CASCADE;
 =   ALTER TABLE ONLY public.seguiti DROP CONSTRAINT fk_utente_1;
       public          postgres    false    4754    215    216            �           2606    16594    seguiti fk_utente_2    FK CONSTRAINT     �   ALTER TABLE ONLY public.seguiti
    ADD CONSTRAINT fk_utente_2 FOREIGN KEY (email_utente_2) REFERENCES public.utente(email) ON DELETE CASCADE;
 =   ALTER TABLE ONLY public.seguiti DROP CONSTRAINT fk_utente_2;
       public          postgres    false    216    215    4754            \   �   x�}�A� ����)�@	
+=�[Wn�J"	������ƍq���{c*���U�S�cn\�.�8�8 E�S�i/�����6���f�k��|In-Ս���ș{���k������OC� �HYt�=/����fѓ��-�$"~ p�>�      T   k   x�s���M�t��y�y����IE@�!5713G/��+,3%5�=�
΂��M,)I�G(OM
IM����O��D�@��e�$�"�$��$�p�XPs3s������� �;1      ^   �   x�}�;
B1��zfn�03�d�T� ��l�D\� �q�j���s��}�C[�S��D����f�
YQr�Q,�ªNS;�����IIF�������������0��8^�ڿi�B��(A1��5����|&�G���2{�L�>��]�{x+>92Vd�U�_����<�"� �fZt      Y   d   x�eλ�0��pf�����q�yF�7Y##(�V�V��xM�44���=j��\����V��pP��&!Z���z�/[�1P�(��E}��-h�t~���"r      U   }   x�NL*��KtH�M����,�t���M��M,)I�G��e���'e�2KsR2�I!��y�9���ÒR��*��刬�0����B�D��F�/3s�|^����XT�埖���Z�M*F��� �m�      W   *  x����j1�u�yCn�8�j)��^��n�c���2��B߾Q�Ru!�dsN�w����&�H��!�3�r!��Ѣ��!��n�v�y��<�%X4���4ܸ`�S�h		&Ԉ�ׄ�F�F)jSBb�G\���hΞ��B�uWZ�a��FKV��` �֑��rAS֟S�ֻ��w�xumL>��O�qS	ʴ��5�{fR��ѣ�٥��),]z��V�2!�X_��Pz)��<�!|�0�F�Ҳ�ؔ����G����v[��]<#�W��^$~�O�����0]I<�����      Z   n   x�e�=
�0��F�?�z�� 8���B�V(]��kl�<ޗ$`k�,t#��0���rA�0�LBǌfF1�-D*�A�����S©�����^1��h̰��U�# |/Ji�      _   �   x���MN�0�u|�^����wEW��*�n�� Ki�����c��@9��y�^����!��s*����rΝF-[�[��%R /Z�U}w�j��b�v NkgT�y/�ؗ�<ݦ�0[�Z7���7�!��.0�״;쁅���y���|�az�,�D$��d��� �Ι�:��d@�`u�_ʵ��D1�����kDZm-�����)���5Czo�f�6�#���j+@)���K      S   =   x�NL*��KtH�M����,��M,)I͇�����9��%�R��*�a�%�H�ѥc���� �h-:      R   0  x�mRMo�0='���C\z !�	*�'.n��GWi@b�~.�(Ҹ���{�qRbȣ*�3�f��t2����V-	�9F�sk�}X�qHp�
j�={i�m@R+���k���h�kw��Y�Oh�ΓސE5��b��S��G�^�5�@��Eۊ���0��U��Y~c,#�eh�6�a �,^8��`��UrCI�v��]% r�4Rh�>\7u:nHF@hdS������McbDUﯣ�b�P;��{��;\ߟ@�(��K��Dw"��j=/�,�|na�Q,ׅ�gZ����     