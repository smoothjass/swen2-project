DROP TABLE IF EXISTS logs;
DROP TABLE IF EXISTS tours;
DROP TABLE IF EXISTS transport_types;

CREATE TABLE "tours" (
                       "tour_id" serial PRIMARY KEY,
                       "name" varchar(50),
                       "description" varchar(1000),
                       "point_a" varchar(100),
                       "point_b" varchar(100),
                       "transport_type_id" int,
                       "distance" float,
                       "duration" int
);

CREATE TABLE "logs" (
                      "log_id" serial PRIMARY KEY,
                      "fk_tours_id" int,
                      "starting_time" timestamp,
                      "comment" varchar(1000),
                      "difficulty" smallint,
                      "total_time" interval,
                      "rating" smallint,
                      "picture_path" varchar(150)
);

CREATE TABLE "transport_types" (
                                "transport_type_id" int PRIMARY KEY,
                                "type" varchar(50)
);

ALTER TABLE "logs" ADD FOREIGN KEY ("fk_tours_id") REFERENCES "tours" ("tour_id");
ALTER TABLE "tours" ADD FOREIGN KEY ("transport_type_id") REFERENCES "transport_types" ("transport_type_id");

INSERT INTO "transport_types" ("transport_type_id", "type") VALUES (1, 'bike');
INSERT INTO "transport_types" ("transport_type_id", "type") VALUES (2, 'car');
INSERT INTO "transport_types" ("transport_type_id", "type") VALUES (3, 'pedestrian');