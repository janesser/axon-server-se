ALTER TABLE PROCESSOR_LOAD_BALANCING
    DROP PRIMARY KEY;

ALTER TABLE PROCESSOR_LOAD_BALANCING
    add TOKEN_STORE_IDENTIFIER VARCHAR(255) default '' not null;

ALTER TABLE PROCESSOR_LOAD_BALANCING
    ADD PRIMARY KEY (NAME, CONTEXT, TOKEN_STORE_IDENTIFIER);

ALTER TABLE RAFT_PROCESSOR_LOAD_BALANCING
    DROP PRIMARY KEY;

ALTER TABLE RAFT_PROCESSOR_LOAD_BALANCING
    add TOKEN_STORE_IDENTIFIER VARCHAR(255) default '' not null;

ALTER TABLE RAFT_PROCESSOR_LOAD_BALANCING
    ADD PRIMARY KEY (NAME, CONTEXT, TOKEN_STORE_IDENTIFIER);