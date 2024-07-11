use be6_yes255_coupon;

#----------------------------------------------------------------------------------------------------------------------------------------------------

select * from coupon;

describe coupon;
INSERT INTO coupon (coupon_id, coupon_expired_at, coupon_name, coupon_policy_id)
VALUES (2, '2025-06-27', 'Best coupon3', 40);

delete from coupon where coupon_id >= 3;
#----------------------------------------------------------------------------------------------------------------------------------------------------

select * from coupon_policy;
delete from coupon_policy where coupon_policy_id = 3;

describe coupon_policy;

INSERT INTO coupon_policy (
    coupon_policy_name,
    coupon_policy_discount_value,
    coupon_policy_created_at,
    coupon_policy_updated_at,
    coupon_policy_rate,
    coupon_policy_min_order_amount,
    coupon_policy_max_amount,
    coupon_policy_discount_type
) VALUES (
             'Policy4',
             50.00,
             '2024-06-20',
             '2024-06-21',
             0.10,
             100.00,
             500.00,
             false
         );


#----------------------------------------------------------------------------------------------------------------------------------------------------

select * from coupon_policy_book;

delete from coupon_policy_book where coupon_policy_book_id >= 1;

INSERT INTO coupon_policy_book (coupon_policy_book_id, book_id, coupon_policy_id)
VALUES (1, 1, 3);

delete from coupon_policy_book where coupon_policy_book_id = 1;

#----------------------------------------------------------------------------------------------------------------------------------------------------

select * from coupon_policy_category;

INSERT INTO coupon_policy_category (coupon_policy_category_id, category_id, coupon_policy_id)
VALUES (1, 1, 4);

delete from coupon_policy_category where coupon_policy_category_id >= 1;

#----------------------------------------------------------------------------------------------------------------------------------------------------

SHOW INDEX FROM coupon_policy_book;
SHOW INDEX FROM coupon_policy_category;