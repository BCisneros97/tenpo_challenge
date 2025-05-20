INSERT INTO clients (id, name, created_at, updated_at) VALUES (1, 'Pedro Lopez', now(), now()) ON CONFLICT (id) DO NOTHING ;
INSERT INTO clients (id, name, created_at, updated_at) VALUES (2, 'Maria Garcia', now(), now()) ON CONFLICT (id) DO NOTHING ;
INSERT INTO clients (id, name, created_at, updated_at) VALUES (3, 'Juan Martinez', now(), now()) ON CONFLICT (id) DO NOTHING ;
INSERT INTO clients (id, name, created_at, updated_at) VALUES (4, 'Ana Rodriguez', now(), now()) ON CONFLICT (id) DO NOTHING ;
INSERT INTO clients (id, name, created_at, updated_at) VALUES (5, 'Luis Hernandez', now(), now()) ON CONFLICT (id) DO NOTHING ;
INSERT INTO clients (id, name, created_at, updated_at) VALUES (6, 'Laura Gonzalez', now(), now()) ON CONFLICT (id) DO NOTHING ;
INSERT INTO clients (id, name, created_at, updated_at) VALUES (7, 'Carlos Perez', now(), now()) ON CONFLICT (id) DO NOTHING ;
INSERT INTO clients (id, name, created_at, updated_at) VALUES (8, 'Sofia Sanchez', now(), now()) ON CONFLICT (id) DO NOTHING ;
INSERT INTO clients (id, name, created_at, updated_at) VALUES (9, 'Javier Ramirez', now(), now()) ON CONFLICT (id) DO NOTHING ;
INSERT INTO clients (id, name, created_at, updated_at) VALUES (10, 'Isabel Torres', now(), now()) ON CONFLICT (id) DO NOTHING ;

INSERT INTO public.transactions
(amount, client_id, deleted, id, created_at, transaction_date, updated_at, business_sector, client_name)
VALUES(4000, 10, false, 1, '2025-05-17 19:53:33.361', '2025-05-17 20:20:00.000', '2025-05-17 21:30:53.894', 'Ahorros', 'Isabel Torres') ON CONFLICT (id) DO NOTHING ;
INSERT INTO public.transactions
(amount, client_id, deleted, id, created_at, transaction_date, updated_at, business_sector, client_name)
VALUES(4000, 6, false, 2, '2025-05-17 19:58:36.976', '2025-05-17 11:20:00.000', '2025-05-17 19:58:36.976', 'Ahorros', 'Laura Gonzalez') ON CONFLICT (id) DO NOTHING ;
INSERT INTO public.transactions
(amount, client_id, deleted, id, created_at, transaction_date, updated_at, business_sector, client_name)
VALUES(5000, 6, false, 3, '2025-05-17 20:44:53.005', '2025-05-17 11:20:00.000', '2025-05-17 20:44:53.005', 'Ahorros', 'Laura Gonzalez') ON CONFLICT (id) DO NOTHING ;
INSERT INTO public.transactions
(amount, client_id, deleted, id, created_at, transaction_date, updated_at, business_sector, client_name)
VALUES(5000, 6, false, 4, '2025-05-17 21:46:08.507', '2025-05-17 11:20:00.000', '2025-05-17 21:46:08.507', 'Ahorros', 'Laura Gonzalez') ON CONFLICT (id) DO NOTHING ;
INSERT INTO public.transactions
(amount, client_id, deleted, id, created_at, transaction_date, updated_at, business_sector, client_name)
VALUES(1000, 2, false, 5, '2025-05-19 22:51:09.329', '2025-05-19 22:50:00.000', '2025-05-19 22:51:09.329', 'ahorros', 'Maria Garcia') ON CONFLICT (id) DO NOTHING ;
INSERT INTO public.transactions
(amount, client_id, deleted, id, created_at, transaction_date, updated_at, business_sector, client_name)
VALUES(1000, 10, false, 6, '2025-05-19 22:57:43.640', '2025-05-19 22:55:00.000', '2025-05-19 22:57:43.640', 'Compras', 'Isabel Torres') ON CONFLICT (id) DO NOTHING ;
INSERT INTO public.transactions
(amount, client_id, deleted, id, created_at, transaction_date, updated_at, business_sector, client_name)
VALUES(2000, 7, false, 7, '2025-05-19 23:07:11.011', '2025-05-19 23:07:00.000', '2025-05-19 23:07:11.011', 'ventas', 'Carlos Perez') ON CONFLICT (id) DO NOTHING ;
INSERT INTO public.transactions
(amount, client_id, deleted, id, created_at, transaction_date, updated_at, business_sector, client_name)
VALUES(2000, 5, false, 8, '2025-05-20 00:29:52.284', '2025-05-20 00:50:00.000', '2025-05-20 00:50:48.051', 'ahorros2', 'Luis Hernandez') ON CONFLICT (id) DO NOTHING ;
