CREATE TABLE order_tbl
(
    id             NUMBER(11) NOT NULL,
    user_id        VARCHAR2(255) DEFAULT NULL,
    commodity_code VARCHAR2(255) DEFAULT NULL,
    count          NUMBER(11)      DEFAULT 0,
    money          NUMBER(11)      DEFAULT 0,
    status          VARCHAR(16)     DEFAULT 'PRESERVED',
    PRIMARY KEY (id)
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


