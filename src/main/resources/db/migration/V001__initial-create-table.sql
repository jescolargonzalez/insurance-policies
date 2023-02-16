CREATE TABLE `decissions` (
                              `id` int(11) NOT NULL AUTO_INCREMENT,
                              `create_time` date DEFAULT NULL,
                              `deleted` bit(1) DEFAULT NULL,
                              `name` varchar(255) DEFAULT NULL,
                              `update_time` date DEFAULT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `policies_types` (
                                  `id` int(11) NOT NULL AUTO_INCREMENT,
                                  `create_time` date DEFAULT NULL,
                                  `deleted` bit(1) DEFAULT NULL,
                                  `description` varchar(255) DEFAULT NULL,
                                  `name` varchar(255) DEFAULT NULL,
                                  `update_time` date DEFAULT NULL,
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `policies` (
                            `id` int(11) NOT NULL AUTO_INCREMENT,
                            `benefit_dni` varchar(255) DEFAULT NULL,
                            `create_time` date DEFAULT NULL,
                            `deleted` bit(1) DEFAULT NULL,
                            `user_id` int(11) DEFAULT NULL,
                            `update_time` date DEFAULT NULL,
                            `type_id` int(11) DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            KEY `FKbjmag8fos48lt04idicpdfbn3` (`type_id`),
                            CONSTRAINT `FKbjmag8fos48lt04idicpdfbn3` FOREIGN KEY (`type_id`) REFERENCES `policies_types` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `parts` (
                         `id` int(11) NOT NULL AUTO_INCREMENT,
                         `add_info` varchar(255) DEFAULT NULL,
                         `affected_dni` varchar(255) DEFAULT NULL,
                         `create_time` date DEFAULT NULL,
                         `deleted` bit(1) DEFAULT NULL,
                         `pay` bit(1) DEFAULT NULL,
                         `update_time` date DEFAULT NULL,
                         `policy_id` int(11) DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         KEY `FKrpcxt1t8jls9i4hmpvuo0l73u` (`policy_id`),
                         CONSTRAINT `FKrpcxt1t8jls9i4hmpvuo0l73u` FOREIGN KEY (`policy_id`) REFERENCES `policies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `peritages` (
                             `id` int(11) NOT NULL AUTO_INCREMENT,
                             `create_time` date DEFAULT NULL,
                             `deleted` bit(1) DEFAULT NULL,
                             `information` varchar(255) DEFAULT NULL,
                             `update_time` date DEFAULT NULL,
                             `decission_id` int(11) DEFAULT NULL,
                             `part_id` int(11) DEFAULT NULL,
                             PRIMARY KEY (`id`),
                             KEY `FKpgd7ey29sxrpqxcp89vvdnf5n` (`decission_id`),
                             KEY `FKlfppdg7xxbofwv6nlk0v6r5q6` (`part_id`),
                             CONSTRAINT `FKlfppdg7xxbofwv6nlk0v6r5q6` FOREIGN KEY (`part_id`) REFERENCES `parts` (`id`),
                             CONSTRAINT `FKpgd7ey29sxrpqxcp89vvdnf5n` FOREIGN KEY (`decission_id`) REFERENCES `decissions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
