CREATE TABLE "tours" (
                         "id" int PRIMARY KEY,
                         "name" varchar(50),
                         "description" varchar(1000),
                         "from" varchar(100),
                         "to" varchar(100),
                         "transport_type" varchar(50)
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
