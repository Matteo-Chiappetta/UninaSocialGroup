--
-- PostgreSQL database cluster dump
--

-- Started on 2024-02-08 12:50:17

SET default_transaction_read_only = off;

SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;

--
-- Roles
--

CREATE ROLE postgres;
ALTER ROLE postgres WITH SUPERUSER INHERIT CREATEROLE CREATEDB LOGIN REPLICATION BYPASSRLS PASSWORD 'SCRAM-SHA-256$4096:FBGoRJYp0pMiaheVCJLTKg==$mrjExoXV466dnBIVYDY2knohspSfoPgXi8XUV901P88=:73xyDVTS+QZRaVY902G+iaaVGGsBtwjSyhiSCn+ITsI=';

--
-- User Configurations
--








--
-- Databases
--

--
-- Database "template1" dump
--

\connect template1

--
-- PostgreSQL database dump
--

-- Dumped from database version 16.1
-- Dumped by pg_dump version 16.0

-- Started on 2024-02-08 12:50:17

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

-- Completed on 2024-02-08 12:50:18

--
-- PostgreSQL database dump complete
--

--
-- Database "postgres" dump
--

\connect postgres

--
-- PostgreSQL database dump
--

-- Dumped from database version 16.1
-- Dumped by pg_dump version 16.0

-- Started on 2024-02-08 12:50:18

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 2 (class 3079 OID 16384)
-- Name: adminpack; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;


--
-- TOC entry 4922 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION adminpack; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';


--
-- TOC entry 228 (class 1255 OID 16698)
-- Name: filepresenteinpost(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.filepresenteinpost() RETURNS trigger
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


ALTER FUNCTION public.filepresenteinpost() OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 217 (class 1259 OID 16562)
-- Name: amicizia; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.amicizia (
    email_utente_1 character varying(60),
    email_utente_2 character varying(60)
);


ALTER TABLE public.amicizia OWNER TO postgres;

--
-- TOC entry 225 (class 1259 OID 16655)
-- Name: commento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.commento (
    data_commento date NOT NULL,
    ora_commento time without time zone NOT NULL,
    commento character varying(300) NOT NULL,
    email_utente character varying(60) NOT NULL,
    id_post integer NOT NULL
);


ALTER TABLE public.commento OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16577)
-- Name: gruppo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.gruppo (
    tag character varying(20) NOT NULL,
    tema character varying(20) NOT NULL,
    email_creatore character varying(60) NOT NULL
);


ALTER TABLE public.gruppo OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 16670)
-- Name: mi_piace; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.mi_piace (
    data_commento date NOT NULL,
    ora_commento time without time zone NOT NULL,
    email_utente character varying(60),
    id_post integer
);


ALTER TABLE public.mi_piace OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 16629)
-- Name: notifica; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.notifica (
    id_notifica integer NOT NULL,
    data_notifica date,
    ora_notifica time without time zone,
    id_post_pubblicato integer
);


ALTER TABLE public.notifica OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 16628)
-- Name: notifica_id_notifica_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.notifica_id_notifica_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.notifica_id_notifica_seq OWNER TO postgres;

--
-- TOC entry 4923 (class 0 OID 0)
-- Dependencies: 222
-- Name: notifica_id_notifica_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.notifica_id_notifica_seq OWNED BY public.notifica.id_notifica;


--
-- TOC entry 219 (class 1259 OID 16587)
-- Name: partecipanti_al_gruppo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.partecipanti_al_gruppo (
    email_membro character varying(60),
    tag_gruppo character varying(20)
);


ALTER TABLE public.partecipanti_al_gruppo OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16601)
-- Name: post; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.post (
    id_post integer NOT NULL,
    data date,
    ora time without time zone,
    didascalia character varying(300),
    percorso_file character varying(100),
    email_creatore_post character varying(60),
    tag_gruppo character varying(20),
    tipo_file character varying(20)
);


ALTER TABLE public.post OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16600)
-- Name: post_id_post_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.post_id_post_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.post_id_post_seq OWNER TO postgres;

--
-- TOC entry 4924 (class 0 OID 0)
-- Dependencies: 220
-- Name: post_id_post_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.post_id_post_seq OWNED BY public.post.id_post;


--
-- TOC entry 224 (class 1259 OID 16640)
-- Name: raggiunge; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.raggiunge (
    email_utente character varying(60),
    id_notifica integer
);


ALTER TABLE public.raggiunge OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 16685)
-- Name: richiesta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.richiesta (
    accettato boolean,
    data_richiesta date,
    ora_richiesta time without time zone,
    email_utente character varying(60),
    tag_gruppo character varying(20)
);


ALTER TABLE public.richiesta OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16555)
-- Name: utente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.utente (
    username character varying(20) NOT NULL,
    email character varying(60) NOT NULL,
    password character varying(60) NOT NULL,
    img_profilo character varying(100),
    descrizione character varying(300)
);


ALTER TABLE public.utente OWNER TO postgres;

--
-- TOC entry 4728 (class 2604 OID 16632)
-- Name: notifica id_notifica; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notifica ALTER COLUMN id_notifica SET DEFAULT nextval('public.notifica_id_notifica_seq'::regclass);


--
-- TOC entry 4727 (class 2604 OID 16604)
-- Name: post id_post; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.post ALTER COLUMN id_post SET DEFAULT nextval('public.post_id_post_seq'::regclass);


--
-- TOC entry 4906 (class 0 OID 16562)
-- Dependencies: 217
-- Data for Name: amicizia; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.amicizia (email_utente_1, email_utente_2) FROM stdin;
\.


--
-- TOC entry 4914 (class 0 OID 16655)
-- Dependencies: 225
-- Data for Name: commento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.commento (data_commento, ora_commento, commento, email_utente, id_post) FROM stdin;
\.


--
-- TOC entry 4907 (class 0 OID 16577)
-- Dependencies: 218
-- Data for Name: gruppo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.gruppo (tag, tema, email_creatore) FROM stdin;
ANime	ANimeUnina	sab@email.it
\.


--
-- TOC entry 4915 (class 0 OID 16670)
-- Dependencies: 226
-- Data for Name: mi_piace; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.mi_piace (data_commento, ora_commento, email_utente, id_post) FROM stdin;
\.


--
-- TOC entry 4912 (class 0 OID 16629)
-- Dependencies: 223
-- Data for Name: notifica; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.notifica (id_notifica, data_notifica, ora_notifica, id_post_pubblicato) FROM stdin;
\.


--
-- TOC entry 4908 (class 0 OID 16587)
-- Dependencies: 219
-- Data for Name: partecipanti_al_gruppo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.partecipanti_al_gruppo (email_membro, tag_gruppo) FROM stdin;
\.


--
-- TOC entry 4910 (class 0 OID 16601)
-- Dependencies: 221
-- Data for Name: post; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.post (id_post, data, ora, didascalia, percorso_file, email_creatore_post, tag_gruppo, tipo_file) FROM stdin;
3	2024-02-06	\N	prova trigger post	\N	sab@email.it	ANime	\N
4	2024-02-06	\N	prova trigger post	percorso	sab@email.it	ANime	\N
5	2024-02-06	\N	prova trigger post	percorso	sab@email.it	ANime	\N
6	2024-02-06	\N	prova trigger post	percorso	sab@email.it	ANime	post_con_media
7	2024-02-06	\N	prova trigger post	\N	sab@email.it	ANime	post_semplice
\.


--
-- TOC entry 4913 (class 0 OID 16640)
-- Dependencies: 224
-- Data for Name: raggiunge; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.raggiunge (email_utente, id_notifica) FROM stdin;
\.


--
-- TOC entry 4916 (class 0 OID 16685)
-- Dependencies: 227
-- Data for Name: richiesta; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.richiesta (accettato, data_richiesta, ora_richiesta, email_utente, tag_gruppo) FROM stdin;
\.


--
-- TOC entry 4905 (class 0 OID 16555)
-- Dependencies: 216
-- Data for Name: utente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.utente (username, email, password, img_profilo, descrizione) FROM stdin;
matteo	matteo@email.it	ciaociao	\N	prova
sab	sab@email.it	ciaociao	\N	prova2
\.


--
-- TOC entry 4925 (class 0 OID 0)
-- Dependencies: 222
-- Name: notifica_id_notifica_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.notifica_id_notifica_seq', 1, false);


--
-- TOC entry 4926 (class 0 OID 0)
-- Dependencies: 220
-- Name: post_id_post_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.post_id_post_seq', 7, true);


--
-- TOC entry 4732 (class 2606 OID 16566)
-- Name: amicizia amicizia_unica; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.amicizia
    ADD CONSTRAINT amicizia_unica UNIQUE (email_utente_1, email_utente_2);


--
-- TOC entry 4742 (class 2606 OID 16659)
-- Name: commento commento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commento
    ADD CONSTRAINT commento_pkey PRIMARY KEY (id_post, email_utente, data_commento, ora_commento);


--
-- TOC entry 4734 (class 2606 OID 16581)
-- Name: gruppo gruppo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gruppo
    ADD CONSTRAINT gruppo_pkey PRIMARY KEY (tag);


--
-- TOC entry 4744 (class 2606 OID 16674)
-- Name: mi_piace mi_piace_unica; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mi_piace
    ADD CONSTRAINT mi_piace_unica UNIQUE (email_utente, id_post);


--
-- TOC entry 4738 (class 2606 OID 16634)
-- Name: notifica notifica_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notifica
    ADD CONSTRAINT notifica_pkey PRIMARY KEY (id_notifica);


--
-- TOC entry 4740 (class 2606 OID 16644)
-- Name: raggiunge notifica_unica; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.raggiunge
    ADD CONSTRAINT notifica_unica UNIQUE (email_utente, id_notifica);


--
-- TOC entry 4736 (class 2606 OID 16609)
-- Name: post post_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.post
    ADD CONSTRAINT post_pkey PRIMARY KEY (id_post);


--
-- TOC entry 4730 (class 2606 OID 16561)
-- Name: utente utente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utente
    ADD CONSTRAINT utente_pkey PRIMARY KEY (email);


--
-- TOC entry 4761 (class 2620 OID 16699)
-- Name: post filepresenteinpost; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER filepresenteinpost BEFORE INSERT ON public.post FOR EACH ROW EXECUTE FUNCTION public.filepresenteinpost();


--
-- TOC entry 4747 (class 2606 OID 16582)
-- Name: gruppo fk_creatore; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.gruppo
    ADD CONSTRAINT fk_creatore FOREIGN KEY (email_creatore) REFERENCES public.utente(email);


--
-- TOC entry 4748 (class 2606 OID 16590)
-- Name: partecipanti_al_gruppo fk_email; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.partecipanti_al_gruppo
    ADD CONSTRAINT fk_email FOREIGN KEY (email_membro) REFERENCES public.utente(email) ON DELETE CASCADE;


--
-- TOC entry 4753 (class 2606 OID 16645)
-- Name: raggiunge fk_email; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.raggiunge
    ADD CONSTRAINT fk_email FOREIGN KEY (email_utente) REFERENCES public.utente(email);


--
-- TOC entry 4755 (class 2606 OID 16660)
-- Name: commento fk_email; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commento
    ADD CONSTRAINT fk_email FOREIGN KEY (email_utente) REFERENCES public.utente(email);


--
-- TOC entry 4757 (class 2606 OID 16675)
-- Name: mi_piace fk_email; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mi_piace
    ADD CONSTRAINT fk_email FOREIGN KEY (email_utente) REFERENCES public.utente(email);


--
-- TOC entry 4750 (class 2606 OID 16615)
-- Name: post fk_email_creatore_post; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.post
    ADD CONSTRAINT fk_email_creatore_post FOREIGN KEY (email_creatore_post) REFERENCES public.utente(email);


--
-- TOC entry 4754 (class 2606 OID 16650)
-- Name: raggiunge fk_id_notifica; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.raggiunge
    ADD CONSTRAINT fk_id_notifica FOREIGN KEY (id_notifica) REFERENCES public.notifica(id_notifica);


--
-- TOC entry 4752 (class 2606 OID 16635)
-- Name: notifica fk_post; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.notifica
    ADD CONSTRAINT fk_post FOREIGN KEY (id_post_pubblicato) REFERENCES public.post(id_post);


--
-- TOC entry 4756 (class 2606 OID 16665)
-- Name: commento fk_post; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commento
    ADD CONSTRAINT fk_post FOREIGN KEY (id_post) REFERENCES public.post(id_post);


--
-- TOC entry 4758 (class 2606 OID 16680)
-- Name: mi_piace fk_post; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.mi_piace
    ADD CONSTRAINT fk_post FOREIGN KEY (id_post) REFERENCES public.post(id_post);


--
-- TOC entry 4749 (class 2606 OID 16595)
-- Name: partecipanti_al_gruppo fk_tag; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.partecipanti_al_gruppo
    ADD CONSTRAINT fk_tag FOREIGN KEY (tag_gruppo) REFERENCES public.gruppo(tag) ON DELETE CASCADE;


--
-- TOC entry 4751 (class 2606 OID 16610)
-- Name: post fk_tag; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.post
    ADD CONSTRAINT fk_tag FOREIGN KEY (tag_gruppo) REFERENCES public.gruppo(tag);


--
-- TOC entry 4759 (class 2606 OID 16688)
-- Name: richiesta fk_tag; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.richiesta
    ADD CONSTRAINT fk_tag FOREIGN KEY (tag_gruppo) REFERENCES public.gruppo(tag);


--
-- TOC entry 4760 (class 2606 OID 16693)
-- Name: richiesta fk_utente; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.richiesta
    ADD CONSTRAINT fk_utente FOREIGN KEY (email_utente) REFERENCES public.utente(email);


--
-- TOC entry 4745 (class 2606 OID 16567)
-- Name: amicizia fk_utente_1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.amicizia
    ADD CONSTRAINT fk_utente_1 FOREIGN KEY (email_utente_1) REFERENCES public.utente(email) ON DELETE CASCADE;


--
-- TOC entry 4746 (class 2606 OID 16572)
-- Name: amicizia fk_utente_2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.amicizia
    ADD CONSTRAINT fk_utente_2 FOREIGN KEY (email_utente_2) REFERENCES public.utente(email) ON DELETE CASCADE;


-- Completed on 2024-02-08 12:50:18

--
-- PostgreSQL database dump complete
--

-- Completed on 2024-02-08 12:50:18

--
-- PostgreSQL database cluster dump complete
--

