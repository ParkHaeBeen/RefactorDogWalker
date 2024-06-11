use dogWalker;
CREATE TABLE `users` (
                         `id`	BIGINT AUTO_INCREMENT PRIMARY KEY,
                         `email`	varchar(100) UNIQUE NOT NULL,
                         `phone_number`	varchar(20)	NOT NULL,
                         `location` GEOMETRY NOT NULL ,
                         `role`	varchar(10)	NOT NULL,
                         `name`	varchar(100)	NOT NULL,
                         `created_at`	DATE	NOT NULL,
                         `updated_at`	DATE	NOT NULL
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

CREATE TABLE `walker_service_price` (
                                        `walker_price_id`	BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        `walker_id`	bigint	NOT NULL,
                                        `walker_service_unit`	int	NOT NULL,
                                        `walker_service_fee`	int	NOT NULL,
                                        FOREIGN KEY (`walker_id`) REFERENCES `users` (`id`)

);

CREATE TABLE `walker_schedule_temporary` (
                                             `walker_sc_temp_id`	BIGINT AUTO_INCREMENT PRIMARY KEY,
                                             `walker_Id`	bigint	NOT NULL,
                                             `unavailable_date`	DATE	NOT NULL,
                                             FOREIGN KEY (`walker_id`) REFERENCES `users` (`id`)

);

CREATE TABLE `walker_schedule_perm` (
                                   `walker_sc_id`	BIGINT AUTO_INCREMENT PRIMARY KEY,
                                   `walker_id`	bigint	NOT NULL,
                                   `unavailable_day`	varchar(4) NOT NULL,
                                   `unavailable_time_start`	int	NOT NULL,
                                   `unavailable_time_end`	int	NOT NULL,
                                   FOREIGN KEY (`walker_id`) REFERENCES `users` (`id`)

);

CREATE TABLE `walker_reserve_service` (
                                          `walker_reserve_service_id`	bigint AUTO_INCREMENT PRIMARY KEY,
                                          `walker_id`	bigint	NOT NULL,
                                          `customer_id`	bigint	NOT NULL,
                                          `created_at`	DATE NOT NULL,
                                          `walker_service_date`	DATE NOT NULL,
                                          `walker_service_time_unit` int NOT NULL,
                                          `walker_service_status`	varchar(255)	NOT NULL,
                                          `updated_at`	DATE NOT NULL,
                                          `walker_reserve_service_price` int NOT NULL,
                                          UNIQUE KEY unique_walker_datetime (walker_id, walker_service_date),
                                          FOREIGN KEY (`walker_id`) REFERENCES `users` (`id`),
                                          FOREIGN KEY (`customer_id`) REFERENCES `users` (`id`)
);

CREATE TABLE `walker_service_route` (
                                        `walker_service_route_id`	bigint	AUTO_INCREMENT PRIMARY KEY ,
                                        `walker_reserve_service_id`	bigint	NOT NULL,
                                        walker_route geometry not null,
                                        `created_at`	DATE	NULL,
                                        FOREIGN KEY (`walker_reserve_service_id`) REFERENCES `walker_reserve_service` (`walker_reserve_service_id`)

);


CREATE TABLE `pay_history` (
                               `pay_history_id`	bigint  AUTO_INCREMENT	PRIMARY KEY,
                               `user_id`	bigint	NOT NULL,
                               `walker_reserve_service_id`	bigint	NOT NULL,
                               `pay_price`	int NOT NULL,
                               `pay_status`	varchar(255) NOT NULL,
                               `created_at`	DATE NOT NULL,
                               `updated_at`	DATE NOT NULL,
                               `pay_method`	varchar(50) NOT NULL,
                               FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
                               FOREIGN KEY (`walker_reserve_service_id`) REFERENCES `walker_reserve_service` (`walker_reserve_service_id`)

);

CREATE TABLE `walker_adjust` (
                                 `walker_adjust_id`	bigint	 AUTO_INCREMENT	PRIMARY KEY,
                                 `user_id`	bigint	NOT NULL,
                                 `walker_adjust_date`	DATE  NOT NULL,
                                 `walker_ttlprice`	bigint  NOT NULL,
                                 `walker_adjust_status`	varchar(255)  NOT NULL,
                                 `walker_adjust_period_start` DATE NOT NULL,
                                 `walker_adjust_period_end` DATE NOT NULL,
                                 `created_at`	DATE	NOT NULL,
                                 `updated_at`	DATE	NOT NULL,
                                 FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)

);

CREATE TABLE `walker_adjust_detail` (
                                        `walker_adjust_detail_id`	bigint	 AUTO_INCREMENT	PRIMARY KEY,
                                        `pay_history_id` bigint NOT NULL,
                                        `walker_adjust_id`	bigint	NOT NULL,
                                        `walker_adjust_price`	bigint NOT 	NULL,
                                        `walker_adjust_status`	varchar(255) NOT NULL,
                                        FOREIGN KEY (`walker_adjust_id`) REFERENCES `walker_adjust` (`walker_adjust_id`),
                                        FOREIGN KEY (`pay_history_id`) REFERENCES  `pay_history` (`pay_history_id`)
);
