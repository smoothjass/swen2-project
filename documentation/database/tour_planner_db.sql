CREATE TABLE "tours" (
                       "id" int PRIMARY KEY,
                       "name" varchar(50),
                       "description" varchar(1000),
                       "from" varchar(100),
                       "to" varchar(100),
                       "fk_transport_type" int,
                       "distance" float,
                       "time" interval
);

CREATE TABLE "logs" (
                      "id" int PRIMARY KEY,
                      "fk_tours_id" int,
                      "starting_time" timestamp,
                      "comment" varchar(1000),
                      "difficulty" smallint,
                      "total_time" interval,
                      "rating" smallint,
                      "picture_path" varchar(150)
);

CREATE TABLE "transport_type" (
                                "id" int PRIMARY KEY,
                                "type" varchar(50)
);

ALTER TABLE "logs" ADD FOREIGN KEY ("fk_tours_id") REFERENCES "tours" ("id");
ALTER TABLE "tours" ADD FOREIGN KEY ("fk_transport_type") REFERENCES "transport_type" ("id");

INSERT INTO transport_type (type) VALUES ('pedestrian');
INSERT INTO transport_type (type) VALUES ('bicycle');
INSERT INTO transport_type (type) VALUES ('car');