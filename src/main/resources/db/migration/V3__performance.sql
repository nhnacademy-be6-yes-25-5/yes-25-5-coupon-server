-- CouponCreationUtil
-- 쿠폰 저장 실행 계획 확인
EXPLAIN INSERT INTO coupon (coupon_name, coupon_code, coupon_expired_at, coupon_created_at, coupon_policy_id) VALUES ('name', 'code', NOW() + INTERVAL 1 MONTH, NOW(), 1);

-- CouponPolicyBookServiceImpl
-- 모든 쿠폰 정책 도서 조회 실행 계획 확인
-- 쿠폰 정책 저장 실행 계획 확인
EXPLAIN INSERT INTO coupon_policy (coupon_policy_name, coupon_policy_discount_value, coupon_policy_rate, coupon_policy_min_order_amount, coupon_policy_max_amount, coupon_policy_discount_type) VALUES ('name', 10.0, 0.1, 100.0, 1000.0, 'type');

-- 쿠폰 정책 도서 저장 실행 계획 확인
EXPLAIN INSERT INTO coupon_policy_book (coupon_policy_id, book_id, book_name) VALUES (1, 1, 'book name');

-- CouponPolicyCategoryServiceImpl
-- 모든 쿠폰 정책 카테고리 조회 실행 계획 확인
EXPLAIN SELECT * FROM coupon_policy_category;

-- 쿠폰 정책 저장 실행 계획 확인
EXPLAIN INSERT INTO coupon_policy (coupon_policy_name, coupon_policy_discount_value, coupon_policy_rate, coupon_policy_min_order_amount, coupon_policy_max_amount, coupon_policy_discount_type) VALUES ('name', 10.0, 0.1, 100.0, 1000.0, 'type');

-- 쿠폰 정책 카테고리 저장 실행 계획 확인
EXPLAIN INSERT INTO coupon_policy_category (coupon_policy_id, category_id, category_name) VALUES (1, 1, 'category name');

-- CouponPolicyServiceImpl
-- 모든 쿠폰 정책 조회 실행 계획 확인
EXPLAIN SELECT * FROM coupon_policy;

-- 쿠폰 정책 저장 실행 계획 확인
EXPLAIN INSERT INTO coupon_policy (coupon_policy_name, coupon_policy_discount_value, coupon_policy_rate, coupon_policy_min_order_amount, coupon_policy_max_amount, coupon_policy_discount_type) VALUES ('name', 10.0, 0.1, 100.0, 1000.0, 'type');

-- CouponServiceImpl
-- 쿠폰 저장 실행 계획 확인
EXPLAIN INSERT INTO coupon (coupon_name, coupon_code, coupon_expired_at, coupon_created_at, coupon_policy_id) VALUES ('name', 'code', NOW() + INTERVAL 1 MONTH, NOW(), 1);

-- 도서 ID에 따른 쿠폰 정책 조회 실행 계획 확인
EXPLAIN SELECT * FROM coupon_policy_book WHERE book_id = 1;

-- 카테고리 ID 목록에 따른 쿠폰 정책 조회 실행 계획 확인
EXPLAIN SELECT * FROM coupon_policy_category WHERE category_id IN (1, 2, 3);

-- 쿠폰 정책 목록에 따른 쿠폰 조회 실행 계획 확인
EXPLAIN SELECT * FROM coupon WHERE coupon_policy_id IN (SELECT coupon_policy_id FROM coupon_policy WHERE coupon_policy_id IN (1, 2, 3));

-- 쿠폰 ID에 따른 쿠폰 조회 실행 계획 확인
EXPLAIN SELECT * FROM coupon WHERE coupon_id = 1;

-- 만료 날짜 이전 쿠폰 삭제 실행 계획 확인
EXPLAIN DELETE FROM coupon WHERE coupon_expired_at < NOW();

-- 쿠폰 ID 목록에 따른 쿠폰 조회 실행 계획 확인
EXPLAIN SELECT * FROM coupon WHERE coupon_id IN (1, 2, 3);

-- 슬로우 쿼리 로그 활성화
SET GLOBAL slow_query_log = 'ON';
SET GLOBAL long_query_time = 2;  -- 2초 이상 걸리는 쿼리를 기록

-- MySQL 성능 튜닝을 위한 시스템 설정 조정
SET GLOBAL innodb_buffer_pool_size = 4G;
SET GLOBAL transaction_isolation = 'READ-COMMITTED';

-- 슬로우 쿼리 로그에 잘 기록되는지 보기위한 쿼리문
SELECT SLEEP(3);