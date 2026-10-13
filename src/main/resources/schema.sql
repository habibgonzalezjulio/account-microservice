CREATE TABLE IF NOT EXISTS accounts (
    id           VARCHAR(36)  NOT NULL PRIMARY KEY,
    user_id      VARCHAR(36)  NOT NULL,
    owner_name   VARCHAR(100) NOT NULL,
    owner_email  VARCHAR(150) NOT NULL,
    type         VARCHAR(20)  NOT NULL
                 CONSTRAINT chk_accounts_type CHECK (type IN ('SAVINGS', 'CHECKING')),
    status       VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE'
                 CONSTRAINT chk_accounts_status CHECK (status IN ('ACTIVE', 'INACTIVE', 'BLOCKED')),
    created_at   TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    -- el usuario solo puede tener una cuenta por tipo
    CONSTRAINT uq_accounts_user_type UNIQUE (user_id, type)
);

CREATE INDEX IF NOT EXISTS idx_accounts_user_id ON accounts (user_id);