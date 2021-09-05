CREATE TABLE actual_weather(
                               id SERIAL PRIMARY KEY,
                               city_id BIGINT,
                               degrees_celsius NUMERIC,
                               wind_speed_mps INTEGER
);

CREATE TABLE cities(
                       id SERIAL,
                       name TEXT,
                       city_desc TEXT
);

CREATE TABLE roles(
                      id SERIAL,
                      name TEXT,
                      display_name TEXT
);

CREATE TABLE subscriptions(
                              id SERIAL PRIMARY KEY,
                              city_id BIGINT,
                              user_id BIGINT,
                              subscription_time TIMESTAMP WITH TIME ZONE DEFAULT now(),
                              active BOOLEAN DEFAULT TRUE
);

CREATE TABLE users(
                      id SERIAL PRIMARY KEY,
                      login TEXT,
                      password CHARACTER VARYING,
                      fullname TEXT,
                      role_id BIGINT,
                      create_time TIMESTAMP WITH TIME ZONE DEFAULT now()
);

INSERT INTO roles(name, display_name) VALUES('admin', 'Role admin');
INSERT INTO roles(name, display_name) VALUES('user', 'Role admin');

--password 2783200
INSERT INTO users(login, password, fullname, role_id) VALUES('admin@email.com', '$2a$12$Si3MmTDAdNCjGx2bqUNKy.F7mupuaq4SL58TMt.UikCuUvlj393ce', 'Test Admin', 1);
--password 2783200
INSERT INTO users(login, password, fullname, role_id) VALUES('user@email.com', '$2a$12$Si3MmTDAdNCjGx2bqUNKy.F7mupuaq4SL58TMt.UikCuUvlj393ce', 'Test User', 2);