CREATE TABLE coupon_policy (
                               coupon_policy_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               coupon_policy_name VARCHAR(255) NOT NULL,
                               coupon_policy_discount_value DECIMAL(19, 2),
                               coupon_policy_rate DECIMAL(19, 2),
                               coupon_policy_min_order_amount DECIMAL(19, 2) NOT NULL,
                               coupon_policy_max_amount DECIMAL(19, 2) NOT NULL,
                               coupon_policy_discount_type BOOLEAN,
                               coupon_policy_created_at TIMESTAMP,
                               coupon_policy_updated_at TIMESTAMP
);

CREATE TABLE coupon (
                        coupon_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        coupon_policy_id BIGINT,
                        coupon_name VARCHAR(255) NOT NULL,
                        coupon_code VARCHAR(255) NOT NULL,
                        coupon_expired_at TIMESTAMP NOT NULL,
                        coupon_created_at TIMESTAMP,
                        FOREIGN KEY (coupon_policy_id) REFERENCES coupon_policy (coupon_policy_id)
);

CREATE TABLE coupon_policy_book (
                                    coupon_policy_book_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    book_id BIGINT NOT NULL,
                                    book_name VARCHAR(255),
                                    coupon_policy_id BIGINT NOT NULL,
                                    FOREIGN KEY (coupon_policy_id) REFERENCES coupon_policy (coupon_policy_id)
);

CREATE TABLE coupon_policy_category (
                                        coupon_policy_category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        category_id BIGINT NOT NULL,
                                        category_name VARCHAR(255),
                                        coupon_policy_id BIGINT NOT NULL,
                                        FOREIGN KEY (coupon_policy_id) REFERENCES coupon_policy (coupon_policy_id)
);