PGDMP                      |            postgres    16.1    16.0 F    J           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            K           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            L           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            M           1262    5    postgres    DATABASE     {   CREATE DATABASE postgres WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Italian_Italy.1252';
    DROP DATABASE postgres;
                postgres    false            N           0    0    DATABASE postgres    COMMENT     N   COMMENT ON DATABASE postgres IS 'default administrative connection database';
                   postgres    false    4941                        3079    16384 	   adminpack 	   EXTENSION     A   CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;
    DROP EXTENSION adminpack;
                   false            O           0    0    EXTENSION adminpack    COMMENT     M   COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';
                        false    2            w           1247    16701 	   tipo_post    TYPE     T   CREATE TYPE public.tipo_post AS ENUM (
    'post_semplice',
    'post_con_media'
);
    DROP TYPE public.tipo_post;
       public          postgres    false            �            1255    16733    accettanuovomembro()    FUNCTION     ?  CREATE FUNCTION public.accettanuovomembro() RETURNS trigger
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
       public          postgres    false            �            1255    16705    assegnazionedatapost()    FUNCTION     5  CREATE FUNCTION public.assegnazionedatapost() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
DECLARE
	current_date DATE;
	current_time TIME;
	
BEGIN

	SELECT SYSDATE INTO current_date
  		FROM dual;
	SELECT SYSDATE INTO current_time
 		FROM dual;

	NEW.data = current_date;
	NEW.ora = current_time;

END;
$$;
 -   DROP FUNCTION public.assegnazionedatapost();
       public          postgres    false            �            1255    16698    filepresenteinpost()    FUNCTION       CREATE FUNCTION public.filepresenteinpost() RETURNS trigger
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
       public          postgres    false            �            1255    16726    primopartecipante()    FUNCTION     �   CREATE FUNCTION public.primopartecipante() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN

	INSERT INTO Partecipanti_al_gruppo 
		VALUES(NEW.email_creatore, NEW.tag);
return new;
END;	
$$;
 *   DROP FUNCTION public.primopartecipante();
       public          postgres    false            �            1255    16731    richiestagruppovalida()    FUNCTION     �  CREATE FUNCTION public.richiestagruppovalida() RETURNS trigger
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
       public          postgres    false            �            1259    16562    amicizia    TABLE     u   CREATE TABLE public.amicizia (
    email_utente_1 character varying(60),
    email_utente_2 character varying(60)
);
    DROP TABLE public.amicizia;
       public         heap    postgres    false            �            1259    16655    commento    TABLE     �   CREATE TABLE public.commento (
    commento character varying(300) NOT NULL,
    email_utente character varying(60) NOT NULL,
    id_post integer NOT NULL,
    data_ora_commento timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);
    DROP TABLE public.commento;
       public         heap    postgres    false            �            1259    16577    gruppo    TABLE     �   CREATE TABLE public.gruppo (
    tag character varying(20) NOT NULL,
    tema character varying(20) NOT NULL,
    email_creatore character varying(60) NOT NULL
);
    DROP TABLE public.gruppo;
       public         heap    postgres    false            �            1259    16670    mi_piace    TABLE     �   CREATE TABLE public.mi_piace (
    email_utente character varying(60),
    id_post integer,
    data_ora_mi_piace timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);
    DROP TABLE public.mi_piace;
       public         heap    postgres    false            �            1259    16629    notifica    TABLE     �   CREATE TABLE public.notifica (
    id_notifica integer NOT NULL,
    id_post_pubblicato integer,
    data_ora_notifica timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);
    DROP TABLE public.notifica;
       public         heap    postgres    false            �            1259    16628    notifica_id_notifica_seq    SEQUENCE     �   CREATE SEQUENCE public.notifica_id_notifica_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 /   DROP SEQUENCE public.notifica_id_notifica_seq;
       public          postgres    false    223            P           0    0    notifica_id_notifica_seq    SEQUENCE OWNED BY     U   ALTER SEQUENCE public.notifica_id_notifica_seq OWNED BY public.notifica.id_notifica;
          public          postgres    false    222            �            1259    16587    partecipanti_al_gruppo    TABLE     }   CREATE TABLE public.partecipanti_al_gruppo (
    email_membro character varying(60),
    tag_gruppo character varying(20)
);
 *   DROP TABLE public.partecipanti_al_gruppo;
       public         heap    postgres    false            �            1259    16601    post    TABLE     J  CREATE TABLE public.post (
    id_post integer NOT NULL,
    didascalia character varying(300),
    percorso_file character varying(100),
    email_creatore_post character varying(60),
    tag_gruppo character varying(20),
    tipo_file public.tipo_post,
    data_ora_post timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);
    DROP TABLE public.post;
       public         heap    postgres    false    887            �            1259    16600    post_id_post_seq    SEQUENCE     �   CREATE SEQUENCE public.post_id_post_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE public.post_id_post_seq;
       public          postgres    false    221            Q           0    0    post_id_post_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE public.post_id_post_seq OWNED BY public.post.id_post;
          public          postgres    false    220            �            1259    16640 	   raggiunge    TABLE     c   CREATE TABLE public.raggiunge (
    email_utente character varying(60),
    id_notifica integer
);
    DROP TABLE public.raggiunge;
       public         heap    postgres    false            �            1259    16685 	   richiesta    TABLE     �   CREATE TABLE public.richiesta (
    email_utente character varying(60),
    tag_gruppo character varying(20),
    data_ora_richiesta timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    accettato boolean
);
    DROP TABLE public.richiesta;
       public         heap    postgres    false            �            1259    16555    utente    TABLE     Y  CREATE TABLE public.utente (
    username character varying(20) NOT NULL,
    email character varying(60) NOT NULL,
    password character varying(60) NOT NULL,
    img_profilo character varying(100),
    descrizione character varying(300),
    CONSTRAINT check_email_valida CHECK (((email)::text ~ '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$'::text)),
    CONSTRAINT check_password_valida CHECK ((((password)::text ~ '[0-9]'::text) AND ((password)::text ~ '[A-Z]'::text) AND ((password)::text ~ '[a-z]'::text) AND ((password)::text ~ '[!?@#%&$]'::text) AND (length((password)::text) >= 8)))
);
    DROP TABLE public.utente;
       public         heap    postgres    false            �           2604    16632    notifica id_notifica    DEFAULT     |   ALTER TABLE ONLY public.notifica ALTER COLUMN id_notifica SET DEFAULT nextval('public.notifica_id_notifica_seq'::regclass);
 C   ALTER TABLE public.notifica ALTER COLUMN id_notifica DROP DEFAULT;
       public          postgres    false    223    222    223            ~           2604    16604    post id_post    DEFAULT     l   ALTER TABLE ONLY public.post ALTER COLUMN id_post SET DEFAULT nextval('public.post_id_post_seq'::regclass);
 ;   ALTER TABLE public.post ALTER COLUMN id_post DROP DEFAULT;
       public          postgres    false    220    221    221            =          0    16562    amicizia 
   TABLE DATA           B   COPY public.amicizia (email_utente_1, email_utente_2) FROM stdin;
    public          postgres    false    217   �X       E          0    16655    commento 
   TABLE DATA           V   COPY public.commento (commento, email_utente, id_post, data_ora_commento) FROM stdin;
    public          postgres    false    225   �X       >          0    16577    gruppo 
   TABLE DATA           ;   COPY public.gruppo (tag, tema, email_creatore) FROM stdin;
    public          postgres    false    218   Y       F          0    16670    mi_piace 
   TABLE DATA           L   COPY public.mi_piace (email_utente, id_post, data_ora_mi_piace) FROM stdin;
    public          postgres    false    226   `Y       C          0    16629    notifica 
   TABLE DATA           V   COPY public.notifica (id_notifica, id_post_pubblicato, data_ora_notifica) FROM stdin;
    public          postgres    false    223   }Y       ?          0    16587    partecipanti_al_gruppo 
   TABLE DATA           J   COPY public.partecipanti_al_gruppo (email_membro, tag_gruppo) FROM stdin;
    public          postgres    false    219   �Y       A          0    16601    post 
   TABLE DATA           }   COPY public.post (id_post, didascalia, percorso_file, email_creatore_post, tag_gruppo, tipo_file, data_ora_post) FROM stdin;
    public          postgres    false    221   �Y       D          0    16640 	   raggiunge 
   TABLE DATA           >   COPY public.raggiunge (email_utente, id_notifica) FROM stdin;
    public          postgres    false    224   �Z       G          0    16685 	   richiesta 
   TABLE DATA           \   COPY public.richiesta (email_utente, tag_gruppo, data_ora_richiesta, accettato) FROM stdin;
    public          postgres    false    227   �Z       <          0    16555    utente 
   TABLE DATA           U   COPY public.utente (username, email, password, img_profilo, descrizione) FROM stdin;
    public          postgres    false    216   [       R           0    0    notifica_id_notifica_seq    SEQUENCE SET     G   SELECT pg_catalog.setval('public.notifica_id_notifica_seq', 1, false);
          public          postgres    false    222            S           0    0    post_id_post_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.post_id_post_seq', 12, true);
          public          postgres    false    220            �           2606    16566    amicizia amicizia_unica 
   CONSTRAINT     l   ALTER TABLE ONLY public.amicizia
    ADD CONSTRAINT amicizia_unica UNIQUE (email_utente_1, email_utente_2);
 A   ALTER TABLE ONLY public.amicizia DROP CONSTRAINT amicizia_unica;
       public            postgres    false    217    217            �           2606    16581    gruppo gruppo_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY public.gruppo
    ADD CONSTRAINT gruppo_pkey PRIMARY KEY (tag);
 <   ALTER TABLE ONLY public.gruppo DROP CONSTRAINT gruppo_pkey;
       public            postgres    false    218            �           2606    16674    mi_piace mi_piace_unica 
   CONSTRAINT     c   ALTER TABLE ONLY public.mi_piace
    ADD CONSTRAINT mi_piace_unica UNIQUE (email_utente, id_post);
 A   ALTER TABLE ONLY public.mi_piace DROP CONSTRAINT mi_piace_unica;
       public            postgres    false    226    226            �           2606    16723    notifica notifica_per_post 
   CONSTRAINT     p   ALTER TABLE ONLY public.notifica
    ADD CONSTRAINT notifica_per_post UNIQUE (id_notifica, id_post_pubblicato);
 D   ALTER TABLE ONLY public.notifica DROP CONSTRAINT notifica_per_post;
       public            postgres    false    223    223            �           2606    16634    notifica notifica_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.notifica
    ADD CONSTRAINT notifica_pkey PRIMARY KEY (id_notifica);
 @   ALTER TABLE ONLY public.notifica DROP CONSTRAINT notifica_pkey;
       public            postgres    false    223            �           2606    16644    raggiunge notifica_unica 
   CONSTRAINT     h   ALTER TABLE ONLY public.raggiunge
    ADD CONSTRAINT notifica_unica UNIQUE (email_utente, id_notifica);
 B   ALTER TABLE ONLY public.raggiunge DROP CONSTRAINT notifica_unica;
       public            postgres    false    224    224            �           2606    16725 *   partecipanti_al_gruppo partecipa_una_volta 
   CONSTRAINT     y   ALTER TABLE ONLY public.partecipanti_al_gruppo
    ADD CONSTRAINT partecipa_una_volta UNIQUE (email_membro, tag_gruppo);
 T   ALTER TABLE ONLY public.partecipanti_al_gruppo DROP CONSTRAINT partecipa_una_volta;
       public            postgres    false    219    219            �           2606    16609    post post_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY public.post
    ADD CONSTRAINT post_pkey PRIMARY KEY (id_post);
 8   ALTER TABLE ONLY public.post DROP CONSTRAINT post_pkey;
       public            postgres    false    221            �           2606    16561    utente utente_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY public.utente
    ADD CONSTRAINT utente_pkey PRIMARY KEY (email);
 <   ALTER TABLE ONLY public.utente DROP CONSTRAINT utente_pkey;
       public            postgres    false    216            �           2620    16736    richiesta accettanuovomembro    TRIGGER     �   CREATE TRIGGER accettanuovomembro AFTER UPDATE OF accettato ON public.richiesta FOR EACH ROW EXECUTE FUNCTION public.accettanuovomembro();
 5   DROP TRIGGER accettanuovomembro ON public.richiesta;
       public          postgres    false    227    227    236            �           2620    16699    post filepresenteinpost    TRIGGER     z   CREATE TRIGGER filepresenteinpost BEFORE INSERT ON public.post FOR EACH ROW EXECUTE FUNCTION public.filepresenteinpost();
 0   DROP TRIGGER filepresenteinpost ON public.post;
       public          postgres    false    228    221            �           2620    16727    gruppo primopartecipante    TRIGGER     y   CREATE TRIGGER primopartecipante AFTER INSERT ON public.gruppo FOR EACH ROW EXECUTE FUNCTION public.primopartecipante();
 1   DROP TRIGGER primopartecipante ON public.gruppo;
       public          postgres    false    218    230            �           2620    16732    richiesta richiestagruppovalida    TRIGGER     �   CREATE TRIGGER richiestagruppovalida BEFORE INSERT ON public.richiesta FOR EACH ROW EXECUTE FUNCTION public.richiestagruppovalida();
 8   DROP TRIGGER richiestagruppovalida ON public.richiesta;
       public          postgres    false    231    227            �           2606    16582    gruppo fk_creatore    FK CONSTRAINT     |   ALTER TABLE ONLY public.gruppo
    ADD CONSTRAINT fk_creatore FOREIGN KEY (email_creatore) REFERENCES public.utente(email);
 <   ALTER TABLE ONLY public.gruppo DROP CONSTRAINT fk_creatore;
       public          postgres    false    4744    216    218            �           2606    16590    partecipanti_al_gruppo fk_email    FK CONSTRAINT     �   ALTER TABLE ONLY public.partecipanti_al_gruppo
    ADD CONSTRAINT fk_email FOREIGN KEY (email_membro) REFERENCES public.utente(email) ON DELETE CASCADE;
 I   ALTER TABLE ONLY public.partecipanti_al_gruppo DROP CONSTRAINT fk_email;
       public          postgres    false    216    219    4744            �           2606    16645    raggiunge fk_email    FK CONSTRAINT     z   ALTER TABLE ONLY public.raggiunge
    ADD CONSTRAINT fk_email FOREIGN KEY (email_utente) REFERENCES public.utente(email);
 <   ALTER TABLE ONLY public.raggiunge DROP CONSTRAINT fk_email;
       public          postgres    false    224    4744    216            �           2606    16660    commento fk_email    FK CONSTRAINT     y   ALTER TABLE ONLY public.commento
    ADD CONSTRAINT fk_email FOREIGN KEY (email_utente) REFERENCES public.utente(email);
 ;   ALTER TABLE ONLY public.commento DROP CONSTRAINT fk_email;
       public          postgres    false    4744    225    216            �           2606    16675    mi_piace fk_email    FK CONSTRAINT     y   ALTER TABLE ONLY public.mi_piace
    ADD CONSTRAINT fk_email FOREIGN KEY (email_utente) REFERENCES public.utente(email);
 ;   ALTER TABLE ONLY public.mi_piace DROP CONSTRAINT fk_email;
       public          postgres    false    4744    226    216            �           2606    16615    post fk_email_creatore_post    FK CONSTRAINT     �   ALTER TABLE ONLY public.post
    ADD CONSTRAINT fk_email_creatore_post FOREIGN KEY (email_creatore_post) REFERENCES public.utente(email);
 E   ALTER TABLE ONLY public.post DROP CONSTRAINT fk_email_creatore_post;
       public          postgres    false    216    4744    221            �           2606    16650    raggiunge fk_id_notifica    FK CONSTRAINT     �   ALTER TABLE ONLY public.raggiunge
    ADD CONSTRAINT fk_id_notifica FOREIGN KEY (id_notifica) REFERENCES public.notifica(id_notifica);
 B   ALTER TABLE ONLY public.raggiunge DROP CONSTRAINT fk_id_notifica;
       public          postgres    false    4756    223    224            �           2606    16635    notifica fk_post    FK CONSTRAINT     ~   ALTER TABLE ONLY public.notifica
    ADD CONSTRAINT fk_post FOREIGN KEY (id_post_pubblicato) REFERENCES public.post(id_post);
 :   ALTER TABLE ONLY public.notifica DROP CONSTRAINT fk_post;
       public          postgres    false    223    4752    221            �           2606    16665    commento fk_post    FK CONSTRAINT     s   ALTER TABLE ONLY public.commento
    ADD CONSTRAINT fk_post FOREIGN KEY (id_post) REFERENCES public.post(id_post);
 :   ALTER TABLE ONLY public.commento DROP CONSTRAINT fk_post;
       public          postgres    false    225    4752    221            �           2606    16680    mi_piace fk_post    FK CONSTRAINT     s   ALTER TABLE ONLY public.mi_piace
    ADD CONSTRAINT fk_post FOREIGN KEY (id_post) REFERENCES public.post(id_post);
 :   ALTER TABLE ONLY public.mi_piace DROP CONSTRAINT fk_post;
       public          postgres    false    4752    226    221            �           2606    16595    partecipanti_al_gruppo fk_tag    FK CONSTRAINT     �   ALTER TABLE ONLY public.partecipanti_al_gruppo
    ADD CONSTRAINT fk_tag FOREIGN KEY (tag_gruppo) REFERENCES public.gruppo(tag) ON DELETE CASCADE;
 G   ALTER TABLE ONLY public.partecipanti_al_gruppo DROP CONSTRAINT fk_tag;
       public          postgres    false    4748    219    218            �           2606    16610    post fk_tag    FK CONSTRAINT     o   ALTER TABLE ONLY public.post
    ADD CONSTRAINT fk_tag FOREIGN KEY (tag_gruppo) REFERENCES public.gruppo(tag);
 5   ALTER TABLE ONLY public.post DROP CONSTRAINT fk_tag;
       public          postgres    false    221    4748    218            �           2606    16688    richiesta fk_tag    FK CONSTRAINT     t   ALTER TABLE ONLY public.richiesta
    ADD CONSTRAINT fk_tag FOREIGN KEY (tag_gruppo) REFERENCES public.gruppo(tag);
 :   ALTER TABLE ONLY public.richiesta DROP CONSTRAINT fk_tag;
       public          postgres    false    4748    218    227            �           2606    16693    richiesta fk_utente    FK CONSTRAINT     {   ALTER TABLE ONLY public.richiesta
    ADD CONSTRAINT fk_utente FOREIGN KEY (email_utente) REFERENCES public.utente(email);
 =   ALTER TABLE ONLY public.richiesta DROP CONSTRAINT fk_utente;
       public          postgres    false    4744    227    216            �           2606    16567    amicizia fk_utente_1    FK CONSTRAINT     �   ALTER TABLE ONLY public.amicizia
    ADD CONSTRAINT fk_utente_1 FOREIGN KEY (email_utente_1) REFERENCES public.utente(email) ON DELETE CASCADE;
 >   ALTER TABLE ONLY public.amicizia DROP CONSTRAINT fk_utente_1;
       public          postgres    false    4744    217    216            �           2606    16572    amicizia fk_utente_2    FK CONSTRAINT     �   ALTER TABLE ONLY public.amicizia
    ADD CONSTRAINT fk_utente_2 FOREIGN KEY (email_utente_2) REFERENCES public.utente(email) ON DELETE CASCADE;
 >   ALTER TABLE ONLY public.amicizia DROP CONSTRAINT fk_utente_2;
       public          postgres    false    216    4744    217            =      x������ � �      E      x������ � �      >   8   x�s���M�t��y�y��ŉI����9z�%\ɉ9ə��*� ��U>F��� �p      F      x������ � �      C      x������ � �      ?   (   x�+NLrH�M����,�LN�I�����,J������� �'�      A   �   x���A
�0�ur�\@����^�2���&$��/v�T(u;�������� R�u� ���M#�p�m%%v��~UR5�TE%E%���.u�t��&yƅ�~�.�r���>�k�'T���9��<2�9�dw�U��Ņ���Tf�X �p�-=���ɾ7      D      x������ � �      G   ^   x����	�  г��b�T=u�^D<�P�?m'h���"���f���ZF�K1�����fL昉0�D.)�����S���~����ǧ� 7I)�      <   `   x��M,)I���S����9z�%�>�y�%�99�����1~�E�e�\ŉI�@�P��W���hb�Pdĕ�Y�4D"�q�3���q��qqq �)�     