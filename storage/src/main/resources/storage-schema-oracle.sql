
CREATE TABLE storage_tbl
(
    id             NUMBER(11) NOT NULL,
    commodity_code VARCHAR2(255) DEFAULT NULL,
    freeze_count          NUMBER(11)      DEFAULT 0,
    count          NUMBER(11)      DEFAULT 0,
    PRIMARY KEY (id),
    CONSTRAINT commodity_code_index UNIQUE (commodity_code)
    ) ;

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

INSERT INTO storage_tbl (id,commodity_code, count) VALUES (1,'commodity1', 1000000);
INSERT INTO storage_tbl (id,commodity_code, count) VALUES (2,'commodity2', 1000000);
INSERT INTO storage_tbl (id,commodity_code, count) VALUES (3,'commodity3', 1000000);
INSERT INTO storage_tbl (id,commodity_code, count) VALUES (4,'commodity4', 1000000);
INSERT INTO storage_tbl (id,commodity_code, count) VALUES (5,'commodity5', 1000000);
INSERT INTO storage_tbl (id,commodity_code, count) VALUES (6,'commodity6', 1000000);
INSERT INTO storage_tbl (id,commodity_code, count) VALUES (7,'commodity7', 1000000);
INSERT INTO storage_tbl (id,commodity_code, count) VALUES (8,'commodity8', 1000000);
INSERT INTO storage_tbl (id,commodity_code, count) VALUES (9,'commodity9', 1000000);
INSERT INTO storage_tbl (id,commodity_code, count) VALUES (10,'commodity10', 1000000);