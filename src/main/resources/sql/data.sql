
call next value for hibernate_sequence;
INSERT INTO CATEGORY (CATEGORY_ID, IS_DELETE, NAME, PARENT_ID, CREATED_AT, UPDATED_AT) VALUES (1, 'N', '의류', null, now(), now());

call next value for hibernate_sequence;
INSERT INTO CATEGORY (CATEGORY_ID, IS_DELETE, NAME, PARENT_ID, CREATED_AT, UPDATED_AT) VALUES (2, 'N', '상의', 1, now(), now());

call next value for hibernate_sequence;
INSERT INTO CATEGORY (CATEGORY_ID, IS_DELETE, NAME, PARENT_ID, CREATED_AT, UPDATED_AT) VALUES (3, 'N', '하의', 1, now(), now());

call next value for hibernate_sequence;
INSERT INTO CATEGORY (CATEGORY_ID, IS_DELETE, NAME, PARENT_ID, CREATED_AT, UPDATED_AT) VALUES (4, 'N', '속옷', 3, now(), now());

call next value for hibernate_sequence;
INSERT INTO CATEGORY (CATEGORY_ID, IS_DELETE, NAME, PARENT_ID, CREATED_AT, UPDATED_AT) VALUES (5, 'N', '양말', 3, now(), now());

call next value for hibernate_sequence;
INSERT INTO CATEGORY (CATEGORY_ID, IS_DELETE, NAME, PARENT_ID, CREATED_AT, UPDATED_AT) VALUES (6, 'N', '아우터', 2, now(), now());

call next value for hibernate_sequence;
INSERT INTO CATEGORY (CATEGORY_ID, IS_DELETE, NAME, PARENT_ID, CREATED_AT, UPDATED_AT) VALUES (7, 'N', '얇은', 6, now(), now());

call next value for hibernate_sequence;
INSERT INTO CATEGORY (CATEGORY_ID, IS_DELETE, NAME, PARENT_ID, CREATED_AT, UPDATED_AT) VALUES (8, 'N', '두꺼운', 6, now(), now());

