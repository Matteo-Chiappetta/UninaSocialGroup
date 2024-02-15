PGDMP  .    -                |            postgres    16.2    16.2 Q    U           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            V           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            W           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            X           1262    5    postgres    DATABASE     {   CREATE DATABASE postgres WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Italian_Italy.1252';
    DROP DATABASE postgres;
                postgres    false            Y           0    0    DATABASE postgres    COMMENT     N   COMMENT ON DATABASE postgres IS 'default administrative connection database';
                   postgres    false    4952                        3079    16384 	   adminpack 	   EXTENSION     A   CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;
    DROP EXTENSION adminpack;
                   false            Z           0    0    EXTENSION adminpack    COMMENT     M   COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';
                        false    2            ^           1247    16399 	   tipo_post    TYPE     T   CREATE TYPE public.tipo_post AS ENUM (
    'post_semplice',
    'post_con_media'
);
    DROP TYPE public.tipo_post;
       public          postgres    false            �            1255    16403    accettanuovomembro()    FUNCTION     ?  CREATE FUNCTION public.accettanuovomembro() RETURNS trigger
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
       public          postgres    false            �            1255    16405    filepresenteinpost()    FUNCTION       CREATE FUNCTION public.filepresenteinpost() RETURNS trigger
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
       public          postgres    false            �            1255    16561    filevalido()    FUNCTION     .  CREATE FUNCTION public.filevalido() RETURNS trigger
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
       public          postgres    false            �            1255    16563    imgprofilovalido()    FUNCTION     M  CREATE FUNCTION public.imgprofilovalido() RETURNS trigger
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
       public          postgres    false            �            1255    16574    notificaraggiungeutenti()    FUNCTION     ~  CREATE FUNCTION public.notificaraggiungeutenti() RETURNS trigger
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
       public          postgres    false            �            1255    16572    nuovanotifica()    FUNCTION     �   CREATE FUNCTION public.nuovanotifica() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
BEGIN
	insert into notifica(id_post_pubblicato) values (NEW.id_post);
RETURN NEW;
END;
$$;
 &   DROP FUNCTION public.nuovanotifica();
       public          postgres    false            �            1255    16566    postgruppovalido()    FUNCTION       CREATE FUNCTION public.postgruppovalido() RETURNS trigger
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
       public          postgres    false            �            1255    16406    primopartecipante()    FUNCTION     �   CREATE FUNCTION public.primopartecipante() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN

	INSERT INTO Partecipanti_al_gruppo 
		VALUES(NEW.email_creatore, NEW.tag);
return new;
END;	
$$;
 *   DROP FUNCTION public.primopartecipante();
       public          postgres    false            �            1255    16407    richiestagruppovalida()    FUNCTION     �  CREATE FUNCTION public.richiestagruppovalida() RETURNS trigger
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
       public          postgres    false            �            1255    16570    utenteingruppovalida()    FUNCTION     �  CREATE FUNCTION public.utenteingruppovalida() RETURNS trigger
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
       public          postgres    false            �            1259    16408    amicizia    TABLE     u   CREATE TABLE public.amicizia (
    email_utente_1 character varying(60),
    email_utente_2 character varying(60)
);
    DROP TABLE public.amicizia;
       public         heap    postgres    false            �            1259    16411    commento    TABLE     �   CREATE TABLE public.commento (
    commento character varying(300) NOT NULL,
    email_utente character varying(60) NOT NULL,
    id_post integer NOT NULL,
    data_ora_commento timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);
    DROP TABLE public.commento;
       public         heap    postgres    false            �            1259    16415    gruppo    TABLE     �   CREATE TABLE public.gruppo (
    tag character varying(20) NOT NULL,
    tema character varying(20) NOT NULL,
    email_creatore character varying(60) NOT NULL
);
    DROP TABLE public.gruppo;
       public         heap    postgres    false            �            1259    16418    mi_piace    TABLE     �   CREATE TABLE public.mi_piace (
    email_utente character varying(60),
    id_post integer,
    data_ora_mi_piace timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);
    DROP TABLE public.mi_piace;
       public         heap    postgres    false            �            1259    16422    notifica    TABLE     �   CREATE TABLE public.notifica (
    id_notifica integer NOT NULL,
    id_post_pubblicato integer,
    data_ora_notifica timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);
    DROP TABLE public.notifica;
       public         heap    postgres    false            �            1259    16426    notifica_id_notifica_seq    SEQUENCE     �   CREATE SEQUENCE public.notifica_id_notifica_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public.notifica_id_notifica_seq;
       public          postgres    false    220            [           0    0    notifica_id_notifica_seq    SEQUENCE OWNED BY     U   ALTER SEQUENCE public.notifica_id_notifica_seq OWNED BY public.notifica.id_notifica;
          public          postgres    false    221            �            1259    16427    partecipanti_al_gruppo    TABLE     }   CREATE TABLE public.partecipanti_al_gruppo (
    email_membro character varying(60),
    tag_gruppo character varying(20)
);
 *   DROP TABLE public.partecipanti_al_gruppo;
       public         heap    postgres    false            �            1259    16430    post    TABLE     S  CREATE TABLE public.post (
    id_post integer NOT NULL,
    percorso_file character varying(100),
    email_creatore_post character varying(60),
    tag_gruppo character varying(20),
    tipo_file public.tipo_post,
    data_ora_post timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    didascalia character varying(300) NOT NULL
);
    DROP TABLE public.post;
       public         heap    postgres    false    862            �            1259    16434    post_id_post_seq    SEQUENCE     �   CREATE SEQUENCE public.post_id_post_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.post_id_post_seq;
       public          postgres    false    223            \           0    0    post_id_post_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.post_id_post_seq OWNED BY public.post.id_post;
          public          postgres    false    224            �            1259    16435 	   raggiunge    TABLE     c   CREATE TABLE public.raggiunge (
    email_utente character varying(60),
    id_notifica integer
);
    DROP TABLE public.raggiunge;
       public         heap    postgres    false            �            1259    16438 	   richiesta    TABLE     �   CREATE TABLE public.richiesta (
    email_utente character varying(60),
    tag_gruppo character varying(20),
    data_ora_richiesta timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    accettato boolean
);
    DROP TABLE public.richiesta;
       public         heap    postgres    false            �            1259    16442    utente    TABLE     Y  CREATE TABLE public.utente (
    username character varying(20) NOT NULL,
    email character varying(60) NOT NULL,
    password character varying(60) NOT NULL,
    img_profilo character varying(100),
    descrizione character varying(300),
    CONSTRAINT check_email_valida CHECK (((email)::text ~ '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$'::text)),
    CONSTRAINT check_password_valida CHECK ((((password)::text ~ '[0-9]'::text) AND ((password)::text ~ '[A-Z]'::text) AND ((password)::text ~ '[a-z]'::text) AND ((password)::text ~ '[!?@#%&$]'::text) AND (length((password)::text) >= 8)))
);
    DROP TABLE public.utente;
       public         heap    postgres    false            �           2604    16449    notifica id_notifica    DEFAULT     |   ALTER TABLE ONLY public.notifica ALTER COLUMN id_notifica SET DEFAULT nextval('public.notifica_id_notifica_seq'::regclass);
 C   ALTER TABLE public.notifica ALTER COLUMN id_notifica DROP DEFAULT;
       public          postgres    false    221    220            �           2604    16450    post id_post    DEFAULT     l   ALTER TABLE ONLY public.post ALTER COLUMN id_post SET DEFAULT nextval('public.post_id_post_seq'::regclass);
 ;   ALTER TABLE public.post ALTER COLUMN id_post DROP DEFAULT;
       public          postgres    false    224    223            G          0    16408    amicizia 
   TABLE DATA           B   COPY public.amicizia (email_utente_1, email_utente_2) FROM stdin;
    public          postgres    false    216   zo       H          0    16411    commento 
   TABLE DATA           V   COPY public.commento (commento, email_utente, id_post, data_ora_commento) FROM stdin;
    public          postgres    false    217   �o       I          0    16415    gruppo 
   TABLE DATA           ;   COPY public.gruppo (tag, tema, email_creatore) FROM stdin;
    public          postgres    false    218   �o       J          0    16418    mi_piace 
   TABLE DATA           L   COPY public.mi_piace (email_utente, id_post, data_ora_mi_piace) FROM stdin;
    public          postgres    false    219   �o       K          0    16422    notifica 
   TABLE DATA           V   COPY public.notifica (id_notifica, id_post_pubblicato, data_ora_notifica) FROM stdin;
    public          postgres    false    220   p       M          0    16427    partecipanti_al_gruppo 
   TABLE DATA           J   COPY public.partecipanti_al_gruppo (email_membro, tag_gruppo) FROM stdin;
    public          postgres    false    222   dp       N          0    16430    post 
   TABLE DATA           }   COPY public.post (id_post, percorso_file, email_creatore_post, tag_gruppo, tipo_file, data_ora_post, didascalia) FROM stdin;
    public          postgres    false    223   �p       P          0    16435 	   raggiunge 
   TABLE DATA           >   COPY public.raggiunge (email_utente, id_notifica) FROM stdin;
    public          postgres    false    225   �q       Q          0    16438 	   richiesta 
   TABLE DATA           \   COPY public.richiesta (email_utente, tag_gruppo, data_ora_richiesta, accettato) FROM stdin;
    public          postgres    false    226   r       R          0    16442    utente 
   TABLE DATA           U   COPY public.utente (username, email, password, img_profilo, descrizione) FROM stdin;
    public          postgres    false    227   �r       ]           0    0    notifica_id_notifica_seq    SEQUENCE SET     F   SELECT pg_catalog.setval('public.notifica_id_notifica_seq', 3, true);
          public          postgres    false    221            ^           0    0    post_id_post_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.post_id_post_seq', 31, true);
          public          postgres    false    224            �           2606    16452    amicizia amicizia_unica 
   CONSTRAINT     l   ALTER TABLE ONLY public.amicizia
    ADD CONSTRAINT amicizia_unica UNIQUE (email_utente_1, email_utente_2);
 A   ALTER TABLE ONLY public.amicizia DROP CONSTRAINT amicizia_unica;
       public            postgres    false    216    216            �           2606    16454    gruppo gruppo_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY public.gruppo
    ADD CONSTRAINT gruppo_pkey PRIMARY KEY (tag);
 <   ALTER TABLE ONLY public.gruppo DROP CONSTRAINT gruppo_pkey;
       public            postgres    false    218            �           2606    16456    mi_piace mi_piace_unica 
   CONSTRAINT     c   ALTER TABLE ONLY public.mi_piace
    ADD CONSTRAINT mi_piace_unica UNIQUE (email_utente, id_post);
 A   ALTER TABLE ONLY public.mi_piace DROP CONSTRAINT mi_piace_unica;
       public            postgres    false    219    219            �           2606    16458    notifica notifica_per_post 
   CONSTRAINT     p   ALTER TABLE ONLY public.notifica
    ADD CONSTRAINT notifica_per_post UNIQUE (id_notifica, id_post_pubblicato);
 D   ALTER TABLE ONLY public.notifica DROP CONSTRAINT notifica_per_post;
       public            postgres    false    220    220            �           2606    16460    notifica notifica_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.notifica
    ADD CONSTRAINT notifica_pkey PRIMARY KEY (id_notifica);
 @   ALTER TABLE ONLY public.notifica DROP CONSTRAINT notifica_pkey;
       public            postgres    false    220            �           2606    16462    raggiunge notifica_unica 
   CONSTRAINT     h   ALTER TABLE ONLY public.raggiunge
    ADD CONSTRAINT notifica_unica UNIQUE (email_utente, id_notifica);
 B   ALTER TABLE ONLY public.raggiunge DROP CONSTRAINT notifica_unica;
       public            postgres    false    225    225            �           2606    16464 *   partecipanti_al_gruppo partecipa_una_volta 
   CONSTRAINT     y   ALTER TABLE ONLY public.partecipanti_al_gruppo
    ADD CONSTRAINT partecipa_una_volta UNIQUE (email_membro, tag_gruppo);
 T   ALTER TABLE ONLY public.partecipanti_al_gruppo DROP CONSTRAINT partecipa_una_volta;
       public            postgres    false    222    222            �           2606    16466    post post_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY public.post
    ADD CONSTRAINT post_pkey PRIMARY KEY (id_post);
 8   ALTER TABLE ONLY public.post DROP CONSTRAINT post_pkey;
       public            postgres    false    223            �           2606    16468    utente utente_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY public.utente
    ADD CONSTRAINT utente_pkey PRIMARY KEY (email);
 <   ALTER TABLE ONLY public.utente DROP CONSTRAINT utente_pkey;
       public            postgres    false    227            �           2620    16469    richiesta accettanuovomembro    TRIGGER     �   CREATE TRIGGER accettanuovomembro AFTER UPDATE OF accettato ON public.richiesta FOR EACH ROW EXECUTE FUNCTION public.accettanuovomembro();
 5   DROP TRIGGER accettanuovomembro ON public.richiesta;
       public          postgres    false    228    226    226            �           2620    16470    post filepresenteinpost    TRIGGER     z   CREATE TRIGGER filepresenteinpost BEFORE INSERT ON public.post FOR EACH ROW EXECUTE FUNCTION public.filepresenteinpost();
 0   DROP TRIGGER filepresenteinpost ON public.post;
       public          postgres    false    229    223            �           2620    16562    post filevalido    TRIGGER     j   CREATE TRIGGER filevalido BEFORE INSERT ON public.post FOR EACH ROW EXECUTE FUNCTION public.filevalido();
 (   DROP TRIGGER filevalido ON public.post;
       public          postgres    false    223    245            �           2620    16564    utente imgprofilovalido    TRIGGER     x   CREATE TRIGGER imgprofilovalido BEFORE INSERT ON public.utente FOR EACH ROW EXECUTE FUNCTION public.imgprofilovalido();
 0   DROP TRIGGER imgprofilovalido ON public.utente;
       public          postgres    false    244    227            �           2620    16575     notifica notificaraggiungeutenti    TRIGGER     �   CREATE TRIGGER notificaraggiungeutenti AFTER INSERT ON public.notifica FOR EACH ROW EXECUTE FUNCTION public.notificaraggiungeutenti();
 9   DROP TRIGGER notificaraggiungeutenti ON public.notifica;
       public          postgres    false    248    220            �           2620    16573    post nuovanotifica    TRIGGER     o   CREATE TRIGGER nuovanotifica AFTER INSERT ON public.post FOR EACH ROW EXECUTE FUNCTION public.nuovanotifica();
 +   DROP TRIGGER nuovanotifica ON public.post;
       public          postgres    false    247    223            �           2620    16567    post postgruppovalido    TRIGGER     v   CREATE TRIGGER postgruppovalido BEFORE INSERT ON public.post FOR EACH ROW EXECUTE FUNCTION public.postgruppovalido();
 .   DROP TRIGGER postgruppovalido ON public.post;
       public          postgres    false    243    223            �           2620    16471    gruppo primopartecipante    TRIGGER     y   CREATE TRIGGER primopartecipante AFTER INSERT ON public.gruppo FOR EACH ROW EXECUTE FUNCTION public.primopartecipante();
 1   DROP TRIGGER primopartecipante ON public.gruppo;
       public          postgres    false    230    218            �           2620    16472    richiesta richiestagruppovalida    TRIGGER     �   CREATE TRIGGER richiestagruppovalida BEFORE INSERT ON public.richiesta FOR EACH ROW EXECUTE FUNCTION public.richiestagruppovalida();
 8   DROP TRIGGER richiestagruppovalida ON public.richiesta;
       public          postgres    false    231    226            �           2620    16571    richiesta utenteingruppovalida    TRIGGER     �   CREATE TRIGGER utenteingruppovalida BEFORE INSERT ON public.richiesta FOR EACH ROW EXECUTE FUNCTION public.utenteingruppovalida();
 7   DROP TRIGGER utenteingruppovalida ON public.richiesta;
       public          postgres    false    246    226            �           2606    16473    gruppo fk_creatore    FK CONSTRAINT     |   ALTER TABLE ONLY public.gruppo
    ADD CONSTRAINT fk_creatore FOREIGN KEY (email_creatore) REFERENCES public.utente(email);
 <   ALTER TABLE ONLY public.gruppo DROP CONSTRAINT fk_creatore;
       public          postgres    false    218    227    4765            �           2606    16478    partecipanti_al_gruppo fk_email    FK CONSTRAINT     �   ALTER TABLE ONLY public.partecipanti_al_gruppo
    ADD CONSTRAINT fk_email FOREIGN KEY (email_membro) REFERENCES public.utente(email) ON DELETE CASCADE;
 I   ALTER TABLE ONLY public.partecipanti_al_gruppo DROP CONSTRAINT fk_email;
       public          postgres    false    227    4765    222            �           2606    16483    raggiunge fk_email    FK CONSTRAINT     z   ALTER TABLE ONLY public.raggiunge
    ADD CONSTRAINT fk_email FOREIGN KEY (email_utente) REFERENCES public.utente(email);
 <   ALTER TABLE ONLY public.raggiunge DROP CONSTRAINT fk_email;
       public          postgres    false    227    4765    225            �           2606    16488    commento fk_email    FK CONSTRAINT     y   ALTER TABLE ONLY public.commento
    ADD CONSTRAINT fk_email FOREIGN KEY (email_utente) REFERENCES public.utente(email);
 ;   ALTER TABLE ONLY public.commento DROP CONSTRAINT fk_email;
       public          postgres    false    227    4765    217            �           2606    16493    mi_piace fk_email    FK CONSTRAINT     y   ALTER TABLE ONLY public.mi_piace
    ADD CONSTRAINT fk_email FOREIGN KEY (email_utente) REFERENCES public.utente(email);
 ;   ALTER TABLE ONLY public.mi_piace DROP CONSTRAINT fk_email;
       public          postgres    false    219    227    4765            �           2606    16498    post fk_email_creatore_post    FK CONSTRAINT     �   ALTER TABLE ONLY public.post
    ADD CONSTRAINT fk_email_creatore_post FOREIGN KEY (email_creatore_post) REFERENCES public.utente(email);
 E   ALTER TABLE ONLY public.post DROP CONSTRAINT fk_email_creatore_post;
       public          postgres    false    4765    227    223            �           2606    16503    raggiunge fk_id_notifica    FK CONSTRAINT     �   ALTER TABLE ONLY public.raggiunge
    ADD CONSTRAINT fk_id_notifica FOREIGN KEY (id_notifica) REFERENCES public.notifica(id_notifica);
 B   ALTER TABLE ONLY public.raggiunge DROP CONSTRAINT fk_id_notifica;
       public          postgres    false    225    220    4757            �           2606    16508    notifica fk_post    FK CONSTRAINT     ~   ALTER TABLE ONLY public.notifica
    ADD CONSTRAINT fk_post FOREIGN KEY (id_post_pubblicato) REFERENCES public.post(id_post);
 :   ALTER TABLE ONLY public.notifica DROP CONSTRAINT fk_post;
       public          postgres    false    223    220    4761            �           2606    16513    commento fk_post    FK CONSTRAINT     s   ALTER TABLE ONLY public.commento
    ADD CONSTRAINT fk_post FOREIGN KEY (id_post) REFERENCES public.post(id_post);
 :   ALTER TABLE ONLY public.commento DROP CONSTRAINT fk_post;
       public          postgres    false    4761    217    223            �           2606    16518    mi_piace fk_post    FK CONSTRAINT     s   ALTER TABLE ONLY public.mi_piace
    ADD CONSTRAINT fk_post FOREIGN KEY (id_post) REFERENCES public.post(id_post);
 :   ALTER TABLE ONLY public.mi_piace DROP CONSTRAINT fk_post;
       public          postgres    false    219    4761    223            �           2606    16523    partecipanti_al_gruppo fk_tag    FK CONSTRAINT     �   ALTER TABLE ONLY public.partecipanti_al_gruppo
    ADD CONSTRAINT fk_tag FOREIGN KEY (tag_gruppo) REFERENCES public.gruppo(tag) ON DELETE CASCADE;
 G   ALTER TABLE ONLY public.partecipanti_al_gruppo DROP CONSTRAINT fk_tag;
       public          postgres    false    222    4751    218            �           2606    16528    post fk_tag    FK CONSTRAINT     o   ALTER TABLE ONLY public.post
    ADD CONSTRAINT fk_tag FOREIGN KEY (tag_gruppo) REFERENCES public.gruppo(tag);
 5   ALTER TABLE ONLY public.post DROP CONSTRAINT fk_tag;
       public          postgres    false    223    4751    218            �           2606    16533    richiesta fk_tag    FK CONSTRAINT     t   ALTER TABLE ONLY public.richiesta
    ADD CONSTRAINT fk_tag FOREIGN KEY (tag_gruppo) REFERENCES public.gruppo(tag);
 :   ALTER TABLE ONLY public.richiesta DROP CONSTRAINT fk_tag;
       public          postgres    false    4751    218    226            �           2606    16538    richiesta fk_utente    FK CONSTRAINT     {   ALTER TABLE ONLY public.richiesta
    ADD CONSTRAINT fk_utente FOREIGN KEY (email_utente) REFERENCES public.utente(email);
 =   ALTER TABLE ONLY public.richiesta DROP CONSTRAINT fk_utente;
       public          postgres    false    226    227    4765            �           2606    16543    amicizia fk_utente_1    FK CONSTRAINT     �   ALTER TABLE ONLY public.amicizia
    ADD CONSTRAINT fk_utente_1 FOREIGN KEY (email_utente_1) REFERENCES public.utente(email) ON DELETE CASCADE;
 >   ALTER TABLE ONLY public.amicizia DROP CONSTRAINT fk_utente_1;
       public          postgres    false    216    227    4765            �           2606    16548    amicizia fk_utente_2    FK CONSTRAINT     �   ALTER TABLE ONLY public.amicizia
    ADD CONSTRAINT fk_utente_2 FOREIGN KEY (email_utente_2) REFERENCES public.utente(email) ON DELETE CASCADE;
 >   ALTER TABLE ONLY public.amicizia DROP CONSTRAINT fk_utente_2;
       public          postgres    false    216    4765    227            G      x������ � �      H      x������ � �      I   8   x�s���M�t��y�y��ŉI����9z�%\ɉ9ə��*� ��U>F��� �p      J      x������ � �      K   ;   x�]ɻ�0�M����,��������sG�'O'{nW�$Ȕ��V8w)�� >��F      M   :   x�+NLrH�M����,�LN�I�����,J��-FV�藙��U�Y���j�.���� ��      N     x����J�0�s�}���gsr��e}��%�X"mS����-˶BN���f89���^B�bC�D��؆�_d(�J`V�4��Q����\҇��aL�P{�}z�Rڧq������M�">�?�ώOݥ��݄ �%�_�w��i͑%(��
A+�%�ũH?�G���g���5R1�p��o�PSÙ�pw.kfP[�(J	y�+#��m
�5T�U�ͬh?��qmE���UK�P[�'U�4�_w_NC��0�]��[�������X�3�|��gZ�'C�J_      P   #   x�+NLrH�M����,�4���,J�G����� �/+      Q   y   x���1�  �^��2�&��~�/��%P�
��m����7ʜ���(�A���W=!�:\��3"����v�<�!
&:���!�=��$��Gg��_���Ѯ#����
���Z�Kp7u      R   �   x�e�=�0��>\ ��U�ԩ���Y�
.�oHA�`�Y���� n;㈭b�;��k��k��1��� Ma����55�8��1�����B=�-����B�xM&��\��p��w�.��=U�*�7ۀZ!�h�NG     