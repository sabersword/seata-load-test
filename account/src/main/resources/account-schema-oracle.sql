
CREATE TABLE account_tbl
(
    id      NUMBER(11) NOT NULL ,
    id_default     NUMBER(11) DEFAULT 0 NOT NULL ,
    user_id  VARCHAR2(255) DEFAULT NULL,
    freeze_money NUMBER(11) DEFAULT 0,
    money   NUMBER(11)      DEFAULT 0,
    PRIMARY KEY (id, id_default),
    CONSTRAINT user_id_index UNIQUE (user_id)
    );

-- for AT mode you must to init this sql for you business database. the seata server not need it.
CREATE TABLE undo_log
(
    id            NUMBER(19)    NOT NULL,
    branch_id     NUMBER(19)    NOT NULL,
    xid           VARCHAR2(100) NOT NULL,
    context       VARCHAR2(128) NOT NULL,
    rollback_info BLOB          NOT NULL,
    log_status    NUMBER(10)    NOT NULL,
    log_created   TIMESTAMP(0)  NOT NULL,
    log_modified  TIMESTAMP(0)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT ux_undo_log UNIQUE (xid, branch_id)
);
-- Generate ID using sequence and trigger
CREATE SEQUENCE UNDO_LOG_SEQ START WITH 1 INCREMENT BY 1;

INSERT INTO account_tbl (id,user_id, money) VALUES (1, 'user1', 1000000);
INSERT INTO account_tbl (id,user_id, money) VALUES (2,'user2', 1000000);
INSERT INTO account_tbl (id,user_id, money) VALUES (3,'user3', 1000000);
INSERT INTO account_tbl (id,user_id, money) VALUES (4,'user4', 1000000);
INSERT INTO account_tbl (id,user_id, money) VALUES (5,'user5', 1000000);
INSERT INTO account_tbl (id,user_id, money) VALUES (6,'user6', 1000000);
INSERT INTO account_tbl (id,user_id, money) VALUES (7,'user7', 1000000);
INSERT INTO account_tbl (id,user_id, money) VALUES (8,'user8', 1000000);
INSERT INTO account_tbl (id,user_id, money) VALUES (9,'user9', 1000000);
INSERT INTO account_tbl (id,user_id, money) VALUES (10,'user10', 1000000);
