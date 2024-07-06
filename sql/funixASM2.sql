DROP DATABASE IF EXISTS Asm2;
CREATE DATABASE Asm2;
USE Asm2;
#password apache

-- CREATE TABLE USER
DROP TABLE IF EXISTS `USER`;
CREATE TABLE `USER`(
                       ID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
                       ADDRESS VARCHAR(255),
                       `DESCRIPTION` VARCHAR(255),
                       EMAIL VARCHAR(255) NOT NULL UNIQUE,
                       FULL_NAME VARCHAR(50) NOT NULL,
                       IMAGE VARCHAR(50),
                       `PASSWORD` VARCHAR(255) NOT NULL,
                       PHONE_NUMBER VARCHAR(50),
                       `STATUS` INT,
                       ROLE_ID INT UNSIGNED,
                       CV_ID INT UNSIGNED
);

-- CREATE TABLE CV
DROP TABLE IF EXISTS CV;
CREATE TABLE CV(
                   ID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
                   FILE_NAME VARCHAR(255) NOT NULL,
                   USER_ID INT UNSIGNED,
                   FOREIGN KEY (USER_ID) REFERENCES `USER`(ID)
);

-- CREATE TABLE COMPANY
DROP TABLE IF EXISTS COMPANY;
CREATE TABLE COMPANY(
                        ID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
                        ADDRESS VARCHAR(255) NOT NULL,
                        `DESCRIPTION` VARCHAR(255),
                        EMAIL VARCHAR(255) NOT NULL UNIQUE,
                        LOGO VARCHAR(255),
                        NAME_COMPANY VARCHAR(255),
                        PHONE_NUMBER VARCHAR(255),
                        `STATUS` INT,
                        USER_ID INT UNSIGNED UNIQUE,
                        FOREIGN KEY (USER_ID) REFERENCES `USER`(ID)
);

-- CREATE TABLE CATEGORY
DROP TABLE IF EXISTS CATEGORY;
CREATE TABLE CATEGORY(
                         ID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
                         NAME_CATEGORY VARCHAR(255),
                         NUMBER_CHOOSE INT
);

-- CREATE TABLE RECRUITMENT
DROP TABLE IF EXISTS RECRUITMENT;
CREATE TABLE RECRUITMENT(
                            ID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
                            ADDRESS VARCHAR(255) NOT NULL,
                            CREATED_AT VARCHAR(255),
                            `DESCRIPTION` VARCHAR(255),
                            EXPERIENCE VARCHAR(255),
                            QUANTITY INT,
                            `RANK` VARCHAR(255),
                            SALARY VARCHAR(255),
                            `STATUS` INT,
                            TITLE VARCHAR(255),
                            `TYPE` VARCHAR(255),
                            `VIEW` INT,
                            CATEGORY_ID INT UNSIGNED,
                            COMPANY_ID INT UNSIGNED,
                            DEADLINE VARCHAR(255),
                            FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID),
                            FOREIGN KEY (CATEGORY_ID) REFERENCES CATEGORY(ID)
);

-- CREATE TABLE SAVE_JOB
DROP TABLE IF EXISTS SAVE_JOB;
CREATE TABLE SAVE_JOB(
                         ID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
                         RECRUITMENT_ID INT UNSIGNED,
                         USER_ID INT UNSIGNED,
                         FOREIGN KEY (USER_ID) REFERENCES `USER`(ID),
                         FOREIGN KEY (RECRUITMENT_ID) REFERENCES RECRUITMENT(ID)
);

-- CREATE TABLE ROLE
DROP TABLE IF EXISTS `ROLE`;
CREATE TABLE `ROLE`(
                       ID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
                       ROLE_NAME VARCHAR(50) NOT NULL UNIQUE
);

-- CREATE TABLE FOLLOW_COMPANY
DROP TABLE IF EXISTS FOLLOW_COMPANY;
CREATE TABLE FOLLOW_COMPANY(
                               ID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
                               COMPANY_ID INT UNSIGNED,
                               USER_ID INT UNSIGNED,
                               FOREIGN KEY (USER_ID) REFERENCES `USER`(ID),
                               FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID)
);

-- CREATE TABLE APPLY_POST
DROP TABLE IF EXISTS APPLY_POST;
CREATE TABLE APPLY_POST(
                           ID INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
                           CREATED_AT VARCHAR(255),
                           RECRUITMENT_ID INT UNSIGNED,
                           USER_ID INT UNSIGNED,
                           NAME_CV VARCHAR(255),
                           `STATUS` INT,
                           `TEXT` VARCHAR(255),
                           FOREIGN KEY (USER_ID) REFERENCES `USER`(ID),
                           FOREIGN KEY (RECRUITMENT_ID) REFERENCES RECRUITMENT(ID)
);

ALTER TABLE `USER`
    ADD FOREIGN KEY (ROLE_ID) REFERENCES `ROLE`(ID);

SET GLOBAL FOREIGN_KEY_CHECKS=0;

INSERT INTO `ROLE` (ROLE_NAME)
VALUES ('Manager'), ('User');
#  user   password= apache
#password apache
#password apache

INSERT INTO `USER` (ADDRESS, `DESCRIPTION`, EMAIL, FULL_NAME, IMAGE, `PASSWORD`, PHONE_NUMBER, `STATUS`, ROLE_ID)
VALUES
    ('123 Main St, Hồ Chí Minh', 'A passionate photographer', 'alex.nguyen@photography.com', 'Alex Nguyễn', '15122023230124.jpg', '$2a$12$pl06R0YXxdhez3CEQh/TFei5O8oTa2g3j0eKc.aDqjmHZjobj/b7a', '0912345678', 1, 1),
    ('456 Ocean Dr, Đà Nẵng', 'A food blogger', 'mia.phan@foodies.com', 'Mia Phan', '15122023224047.jpg', '$2a$12$pl06R0YXxdhez3CEQh/TFei5O8oTa2g3j0eKc.aDqjmHZjobj/b7a', '0923456789', 1, 2),
    ('789 Forest Ln, Nha Trang', 'A travel enthusiast', 'ethan.tran@travel.com', 'Ethan Trần', '10122023214100.jpg', '$2a$12$pl06R0YXxdhez3CEQh/TFei5O8oTa2g3j0eKc.aDqjmHZjobj/b7a', '0934567890', 1, 1),
    ('101 Lake View, Vũng Tàu', 'A tech geek', 'sophia.le@techguru.com', 'Sophia Lê', '10122023134523.jpg', '$2a$12$pl06R0YXxdhez3CEQh/TFei5O8oTa2g3j0eKc.aDqjmHZjobj/b7a', '0945678901', 1, 2),
    ('202 Mountain Rd, Hà Nội', 'A fitness trainer', 'lucas.hoang@fitnesspro.com', 'Lucas Hoàng', '10122023132538.jpg', '$2a$12$pl06R0YXxdhez3CEQh/TFei5O8oTa2g3j0eKc.aDqjmHZjobj/b7a', '0956789012', 1, 1),
    ('303 City Square, Hải Phòng', 'A fashion designer', 'isabella.vo@fashionhub.com', 'Isabella Võ', '05072024154249.jpeg', '$2a$12$pl06R0YXxdhez3CEQh/TFei5O8oTa2g3j0eKc.aDqjmHZjobj/b7a', '0967890123', 1, 2),
    ('404 River Blvd, Cần Thơ', 'A software developer', 'noah.pham@devhouse.com', 'Noah Phạm', '05072024154804.jpeg', '$2a$12$pl06R0YXxdhez3CEQh/TFei5O8oTa2g3j0eKc.aDqjmHZjobj/b7a', '0978901234', 1, 1),
    ('505 Valley Rd, Huế', 'A marketing strategist', 'amelia.tran@marketwise.com', 'Amelia Trần', '04072024222113.jpg', '$2a$12$pl06R0YXxdhez3CEQh/TFei5O8oTa2g3j0eKc.aDqjmHZjobj/b7a', '0989012345', 1, 2),
    ('606 Sunset Blvd, Đà Lạt', 'A graphic designer', 'liam.nguyen@designpro.com', 'Liam Nguyễn', '04072024213427.jpg', '$2a$12$pl06R0YXxdhez3CEQh/TFei5O8oTa2g3j0eKc.aDqjmHZjobj/b7a', '0990123456', 1, 1),
    ('707 Sunrise Dr, Bình Dương', 'A freelance writer', 'emma.phan@writershub.com', 'Emma Phan', 'image_7.jpg', '$2a$12$pl06R0YXxdhez3CEQh/TFei5O8oTa2g3j0eKc.aDqjmHZjobj/b7a', '0910112233', 1, 2),
    ('808 Maple St, Đồng Nai', 'A business analyst', 'james.vo@analystics.com', 'James Võ', 'person_4.jpg', '$2a$12$pl06R0YXxdhez3CEQh/TFei5O8oTa2g3j0eKc.aDqjmHZjobj/b7a', '0921223344', 1, 1),
    ('909 Elm St, Quảng Ninh', 'A product manager', 'olivia.le@producthub.com', 'Olivia Lê', 'person_3.jpg', '$2a$12$pl06R0YXxdhez3CEQh/TFei5O8oTa2g3j0eKc.aDqjmHZjobj/b7a', '0932334455', 1, 2),
    ('1010 Pine Rd, Thanh Hóa', 'A data scientist', 'benjamin.tran@datainsights.com', 'Benjamin Trần', 'anh_dai_dien.jpg', '$2a$12$pl06R0YXxdhez3CEQh/TFei5O8oTa2g3j0eKc.aDqjmHZjobj/b7a', '0943445566', 1, 1),
    ('1111 Birch Ave, Nghệ An', 'A medical researcher', 'ava.nguyen@medresearch.com', 'Ava Nguyễn', 'anh_dai_dien.jpg', '$2a$12$pl06R0YXxdhez3CEQh/TFei5O8oTa2g3j0eKc.aDqjmHZjobj/b7a', '0954556677', 1, 2),
    ('1212 Cedar Blvd, Quảng Nam', 'A digital marketer', 'henry.pham@digitalwave.com', 'Henry Phạm', 'anh_dai_dien.jpg', '$2a$12$pl06R0YXxdhez3CEQh/TFei5O8oTa2g3j0eKc.aDqjmHZjobj/b7a', '0965667788', 1, 1);

INSERT INTO CV (FILE_NAME, USER_ID)
VALUES
    ('212603.pdf', 1),
    ('adssadsa.pdf', 2),
    ('apo.pdf', 5),
    ('Dublin-Resume-Template-Modern.pdf', 6),
    ('lolwqe.pdf', 10),
    ('Madrid-Resume-Template-Modern.pdf', 9),
    ('Moscow-Creative-Resume-Template.pdf', 4),
    ('padawan.pdf', 7),
    ('popp.pdf', 8),
    ('tran ngoc hien.pdf', 3);

ALTER TABLE `USER`
    ADD FOREIGN KEY (CV_ID) REFERENCES CV(ID);

INSERT INTO COMPANY (
    ADDRESS, `DESCRIPTION`, EMAIL, LOGO, NAME_COMPANY, PHONE_NUMBER, `STATUS`, USER_ID
)
VALUES
    ('Long Biên, Hà Nội', 'Kinh nghiệm Java > 2 năm', 'info01@gmail.com', '04072024201656MISA Corp.jpg', 'MISA Corp', '02435234001', 1, 1),
    ('Đống Đa, Hà Nội', 'Kinh nghiệm 2 năm', 'info02@gmail.com', 'company-1.jpg', 'Công ty Cổ phần Rikkeisoft', '02435234002', 1, 2),
    ('Thanh Xuân, Hà Nội', 'Kinh nghiệm 2 năm', 'info03@gmail.com', 'fptsoftware.jpg', 'Công ty Cổ phần FPT', '02435234003', 1, 3),
    ('Cầu Giấy, Hà Nội', 'Kinh nghiệm 2 năm', 'info04@gmail.com', 'vnpt.png', 'Công ty Cổ phần VNPT', '02435234004', 1, 4),
    ('Tây Hồ, Hà Nội', 'Kinh nghiệm 2 năm', 'info05@gmail.com', 'viettel.jpg', 'Công ty Cổ phần Viettel', '02435234005', 1, 5),
    ('Ba Đình, Hà Nội', 'Kinh nghiệm 2 năm', 'info06@gmail.com', 'evn.png', 'Công ty điện lực Việt Nam', '02435234006', 1, 6),
    ('Hai Bà Trưng, Hà Nội', 'Kinh nghiệm 2 năm', 'info07@gmail.com', 'tonhoasen.jpg', 'Công ty Cổ phần Tôn hoa xen', '02435234007', 1, 7),
    ('Hoàn Kiếm, Hà Nội', 'Kinh nghiệm 2 năm', 'info08@gmail.com', 'truonghai.jpg', 'Công ty Cổ phần truongHai', '02435234008', 1, 8),
    ('Từ Liêm, Hà Nội', 'Kinh nghiệm 2 năm', 'info09@gmail.com', 'petrolimex.jpg', 'Công ty Cổ phần petrolimex', '02435234009', 1, 9),
    ('Thanh Trì, Hà Nội', 'Kinh nghiệm 2 năm', 'info10@gmail.com', 'hoaphat.jpg', 'Công ty Cổ phần Hoa Phat', '02435234010', 1, 10);

INSERT INTO CATEGORY (NAME_CATEGORY, NUMBER_CHOOSE)
VALUES
    ('Java', 1),
    ('JavaScript', 2),
    ('Python', 3),
    ('Rust', 4),
    ('C++', 5),
    ('React', 6),
    ('Node.js', 7),
    ('PHP', 8),
    ('Ruby', 9),
    ('C#', 10);
INSERT INTO RECRUITMENT (
    ADDRESS, CREATED_AT, `DESCRIPTION`, EXPERIENCE, QUANTITY, `RANK`, SALARY, `STATUS`, TITLE, `TYPE`, `VIEW`, CATEGORY_ID, COMPANY_ID, DEADLINE
)
VALUES
    ('Quận 1, Hồ Chí Minh', '2023-01-05', 'Kỹ năng giao tiếp mạnh mẽ và kinh nghiệm phát triển phần mềm 2 năm.', 'Senior', 5, 'A', '2000$', 1, 'Senior Java Developer', 'Full-time', 100, 1, 1, '2024-07-31'),
    ('Quận 2, Hồ Chí Minh', '2023-01-10', 'Kỹ năng lập trình JavaScript tốt và kinh nghiệm làm việc 3 năm.', 'Senior', 3, 'A', '3000$', 1, 'Senior JavaScript Developer', 'Full-time', 150, 2, 2, '2024-07-31'),
    ('Quận 3, Hồ Chí Minh', '2023-01-15', 'Kinh nghiệm làm việc với Python và khả năng giải quyết vấn đề.', 'Junior', 7, 'B', '1500$', 1, 'Junior Python Developer', 'Part-time', 200, 3, 3, '2024-07-31'),
    ('Quận 4, Hồ Chí Minh', '2023-01-20', 'Kỹ năng lãnh đạo và kinh nghiệm phát triển ứng dụng React 4 năm.', 'Lead', 2, 'A', '4000$', 1, 'Lead React Developer', 'Full-time', 50, 4, 4, '2024-07-31'),
    ('Quận 5, Hồ Chí Minh', '2023-01-25', 'Kỹ năng lập trình Node.js và kinh nghiệm phát triển phần mềm 2 năm.', 'Mid', 4, 'B', '2500$', 1, 'Mid-level Node.js Developer', 'Full-time', 75, 5, 5, '2024-07-31'),
    ('Quận 6, Hồ Chí Minh', '2023-02-01', 'Kinh nghiệm phát triển PHP và khả năng làm việc nhóm tốt.', 'Senior', 6, 'A', '2000$', 1, 'Senior PHP Developer', 'Full-time', 90, 6, 6, '2024-07-31'),
    ('Quận 7, Hồ Chí Minh', '2023-02-05', 'Kỹ năng lập trình Ruby và kinh nghiệm phát triển web 3 năm.', 'Senior', 8, 'A', '3000$', 1, 'Senior Ruby Developer', 'Full-time', 120, 7, 7, '2024-07-31'),
    ('Quận 8, Hồ Chí Minh', '2023-02-10', 'Kỹ năng lập trình C# cơ bản và kinh nghiệm phát triển phần mềm 1 năm.', 'Junior', 10, 'B', '1500$', 1, 'Junior C# Developer', 'Part-time', 180, 8, 8, '2024-07-31'),
    ('Quận 9, Hồ Chí Minh', '2023-02-15', 'Kinh nghiệm phát triển HTML và khả năng làm việc độc lập.', 'Mid', 4, 'B', '2500$', 1, 'Mid-level HTML Developer', 'Full-time', 110, 9, 9, '2024-07-31'),
    ('Quận 10, Hồ Chí Minh', '2023-02-20', 'Kinh nghiệm phát triển CSS và kỹ năng thiết kế giao diện tốt.', 'Lead', 2, 'A', '4000$', 1, 'Lead CSS Developer', 'Full-time', 60, 10, 10, '2024-07-31');

INSERT INTO SAVE_JOB (RECRUITMENT_ID, USER_ID)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5),
    (6, 6),
    (7, 7),
    (8, 8),
    (9, 9),
    (10, 10);

INSERT INTO FOLLOW_COMPANY (COMPANY_ID, USER_ID)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5),
    (6, 6),
    (7, 7),
    (8, 8),
    (9, 9),
    (10, 10);

INSERT INTO APPLY_POST (CREATED_AT, RECRUITMENT_ID, USER_ID, NAME_CV, `STATUS`, `TEXT`)
VALUES
    ('2022-11-05', 1, 1, '212603.pdf', 1, 'Application for Senior Java Developer'),
    ('2022-11-06', 2, 2, 'adssadsa.pdf', 1, 'Application for Senior JavaScript Developer'),
    ('2022-11-07', 3, 3, 'tran ngoc hien.pdf', 1, 'Application for Junior Python Developer'),
    ('2022-11-08', 4, 4, 'Moscow-Creative-Resume-Template.pdf', 1, 'Application for Lead React Developer'),
    ('2022-11-09', 5, 5, 'apo.pdf', 1, 'Application for Mid-level Node.js Developer'),
    ('2022-11-10', 6, 6, 'Dublin-Resume-Template-Modern.pdf', 1, 'Application for Senior PHP Developer'),
    ('2022-11-11', 7, 7, 'padawan.pdf', 1, 'Application for Senior Ruby Developer'),
    ('2022-11-12', 8, 8, 'popp.pdf', 1, 'Application for Junior C# Developer'),
    ('2022-11-13', 9, 9, 'Madrid-Resume-Template-Modern.pdf', 1, 'Application for Mid-level HTML Developer'),
    ('2022-11-14', 10, 10, 'lolwqe.pdf', 1, 'Application for Lead CSS Developer');

SET GLOBAL FOREIGN_KEY_CHECKS=1;
