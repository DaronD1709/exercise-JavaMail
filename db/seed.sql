-- Dữ liệu mẫu (tuỳ chọn). Chạy SAU khi app đã chạy ít nhất 1 lần:
-- lúc đó Hibernate mới tự tạo bảng "User" từ @Entity (hbm2ddl.auto=update).
--
--   createdb -h localhost -U darond email_list_jpa_db       (chỉ lần đầu, tạo database rỗng)
--   psql -h localhost -U darond -d email_list_jpa_db -f db/seed.sql

TRUNCATE TABLE "User" RESTART IDENTITY;

INSERT INTO "User" (email, firstName, lastName) VALUES
    ('jsmith@gmail.com',     'John',   'Smith'),
    ('andi@murach.com',      'Andrea', 'Steelman'),
    ('joelmurach@yahoo.com', 'Joel',   'Murach'),
    ('mike@murach.com',      'Mike',   'Murach');
