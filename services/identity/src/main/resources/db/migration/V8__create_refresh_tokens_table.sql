CREATE TABLE refresh_tokens (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users(id),
    session_id UUID NOT NULL REFERENCES user_sessions(id),
    token_hash VARCHAR(255) NOT NULL UNIQUE,
    token_family UUID NOT NULL,
    parent_token_id UUID REFERENCES refresh_tokens(id),
    expires_at TIMESTAMPTZ NOT NULL,
    revoked_at TIMESTAMPTZ,
    replaced_by_token_id UUID REFERENCES refresh_tokens(id),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_ip INET
);
