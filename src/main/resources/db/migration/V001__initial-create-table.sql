/* ------------- */
/* Create tables */
/* ------------- */

CREATE TABLE `decissions` (
                              `id` int(11) NOT NULL AUTO_INCREMENT,
                              `name` varchar(255) NOT NULL,
                              `create_time` timestamp NOT NULL DEFAULT current_timestamp(),
                              `update_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                              `deleted` tinyint(4) NOT NULL DEFAULT 0,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO decissions (name) VALUES('no siniestro');
INSERT INTO decissions (name) VALUES('en tramite');
INSERT INTO decissions (name) VALUES('siniestro');

CREATE TABLE `policies_types` (
                                  `id` int(11) NOT NULL AUTO_INCREMENT,
                                  `name` varchar(255) NOT NULL,
                                  `descr` varchar(255) NOT NULL,
                                  `create_time` timestamp NOT NULL DEFAULT current_timestamp(),
                                  `update_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                                  `deleted` tinyint(4) NOT NULL DEFAULT 0,
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `policies` (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `benefit_dni` varchar(255) NOT NULL,
                            `user_id` int(11) NOT NULL,
                            `type_id` int(11) NOT NULL,
                            `vehicle_id` int(11) NOT NULL,
                            `create_time` timestamp NOT NULL DEFAULT current_timestamp(),
                            `update_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                            `deleted` tinyint(4) NOT NULL DEFAULT 0,
                            PRIMARY KEY (`id`),
                            KEY `FKbjmag8fos48lt04idicpdfbn3` (`type_id`),
                            CONSTRAINT `FKbjmag8fos48lt04idicpdfbn3` FOREIGN KEY (`type_id`) REFERENCES `policies_types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `parts` (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `policy_id` int(11) NOT NULL,
                         `affected_dni` varchar(10) NOT NULL,
                         `add_info` varchar(255) NOT NULL,
                         `pay` tinyint(4) DEFAULT NULL,
                         `create_time` timestamp NOT NULL DEFAULT current_timestamp(),
                         `update_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                         `deleted` tinyint(4) NOT NULL DEFAULT 0,
                         PRIMARY KEY (`id`),
                         KEY `FKrpcxt1t8jls9i4hmpvuo0l73u` (`policy_id`),
                         CONSTRAINT `FKrpcxt1t8jls9i4hmpvuo0l73u` FOREIGN KEY (`policy_id`) REFERENCES `policies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `peritages` (
                             `id` int(11) NOT NULL AUTO_INCREMENT,
                             `part_id` int(11) NOT NULL,
                             `decission_id` int(11) NOT NULL,
                             `information` varchar(255) NOT NULL,
                             `create_time` timestamp NOT NULL DEFAULT current_timestamp(),
                             `update_time` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                             `deleted` tinyint(4) NOT NULL DEFAULT 0,
                             PRIMARY KEY (`id`),
                             KEY `FKpgd7ey29sxrpqxcp89vvdnf5n` (`decission_id`),
                             KEY `FKlfppdg7xxbofwv6nlk0v6r5q6` (`part_id`),
                             CONSTRAINT `FKlfppdg7xxbofwv6nlk0v6r5q6` FOREIGN KEY (`part_id`) REFERENCES `parts` (`id`),
                             CONSTRAINT `FKpgd7ey29sxrpqxcp89vvdnf5n` FOREIGN KEY (`decission_id`) REFERENCES `decissions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO policies_types (name, descr) VALUES ('fullrisk', 'Seguro a todo riesgo bla bla bla');
INSERT INTO policies_types (name, descr) VALUES ('thirds', 'Seguro para terceros y bla bla bla');