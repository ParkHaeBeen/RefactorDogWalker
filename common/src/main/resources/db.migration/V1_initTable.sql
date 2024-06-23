use dogWalker;
CREATE TABLE `users` (
                         `id`	BIGINT AUTO_INCREMENT PRIMARY KEY,
                         `email`	varchar(100) UNIQUE NOT NULL,
                         `phone_number`	varchar(20)	NOT NULL,
                         `location` POINT NOT NULL SRID 4326,
                         `role`	varchar(10)	NOT NULL,
                         `name`	varchar(100)	NOT NULL,
                         `created_at`	TIMESTAMP	NOT NULL,
                         `updated_at`	TIMESTAMP	NOT NULL
);

CREATE TABLE `customer_dog` (
                                     `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     `user_id` BIGINT NOT NULL,
                                     `img_url` VARCHAR(100) NOT NULL,
                                     `birth_at` DATE NOT NULL,
                                     `name` VARCHAR(10) NOT NULL,
                                     `type` VARCHAR(20) NOT NULL,
                                     `description` VARCHAR(500) NOT NULL,
                                     FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

CREATE TABLE `walker_price` (
                                        `id`	BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        `user_id`	bigint	NOT NULL,
                                        `time_unit`	int	NOT NULL,
                                        `price`	int	NOT NULL,
                                        FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                                        UNIQUE key id_time (`id`, `time_unit`)

);

CREATE TABLE `walker_schedule_temp` (
                                             `id`	BIGINT AUTO_INCREMENT PRIMARY KEY,
                                             `user_id`	bigint	NOT NULL,
                                             `unavail_at`	DATE	NOT NULL,
                                             FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                                             UNIQUE key id_unavailAt (`id`, `unavail_at`)
);

CREATE TABLE `walker_schedule_perm` (
                                   `id`	BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   `user_id`	bigint	NOT NULL,
                                   `unavail_day`	varchar(4) NOT NULL,
                                   `unavail_time_start`	int	NOT NULL,
                                   `unavail_time_end`	int	NOT NULL,
                                   FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                                   UNIQUE key id_unavailDay (`id`, `unavail_day`, `unavail_time_start` , `unavail_time_end`)

);

CREATE TABLE `walker_reserve` (
                                          `id`	bigint AUTO_INCREMENT PRIMARY KEY,
                                          `walker_id`	bigint	NOT NULL,
                                          `customer_id`	bigint	NOT NULL,
                                          `created_at`	TIMESTAMP NOT NULL,
                                          `date`	TIMESTAMP NOT NULL,
                                          `time_unit` int NOT NULL,
                                          `status`	varchar(255)	NOT NULL,
                                          `updated_at`	TIMESTAMP NOT NULL,
                                          `price` int NOT NULL,
                                          UNIQUE KEY unique_walker_datetime (walker_id, date),
                                          FOREIGN KEY (`walker_id`) REFERENCES `users` (`id`),
                                          FOREIGN KEY (`customer_id`) REFERENCES `users` (`id`)
);

CREATE TABLE `walker_service_route` (
                                        `id`	bigint	AUTO_INCREMENT PRIMARY KEY ,
                                        `walker_reserve_id`	bigint	NOT NULL,
                                        route geometry not null,
                                        `created_at`	DATE	NULL,
                                        FOREIGN KEY (`walker_reserve_id`) REFERENCES `walker_reserve` (`id`)

);


CREATE TABLE `pay_history` (
                               `id`	bigint  AUTO_INCREMENT	PRIMARY KEY,
                               `user_id`	bigint	NOT NULL,
                               `walker_reserve_id`	bigint	NOT NULL,
                               `price`	int NOT NULL,
                               `status`	varchar(255) NOT NULL,
                               `created_at`	TIMESTAMP NOT NULL,
                               `updated_at`	TIMESTAMP NOT NULL,
                               `method`	varchar(50) NOT NULL,
                               FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                               FOREIGN KEY (`walker_reserve_id`) REFERENCES `walker_reserve` (`id`)

);

CREATE TABLE `walker_adjust` (
                                 `id`	bigint	 AUTO_INCREMENT	PRIMARY KEY,
                                 `user_id`	bigint	NOT NULL,
                                 `price`	bigint  NOT NULL,
                                 `status`	varchar(255)  NOT NULL,
                                 `period_start` DATE NOT NULL,
                                 `period_end` DATE NOT NULL,
                                 `created_at`	TIMESTAMP	NOT NULL,
                                 `updated_at`	TIMESTAMP	NOT NULL,
                                 FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)

);

CREATE TABLE `walker_adjust_detail` (
                                        `id`	bigint	 AUTO_INCREMENT	PRIMARY KEY,
                                        `pay_history_id` bigint unique NOT NULL,
                                        `walker_adjust_id`	bigint	NOT NULL,
                                        `price`	bigint NOT 	NULL,
                                        `status`	varchar(255) NOT NULL,
                                        FOREIGN KEY (`walker_adjust_id`) REFERENCES `walker_adjust` (`id`),
                                        FOREIGN KEY (`pay_history_id`) REFERENCES  `pay_history` (`id`)
);
