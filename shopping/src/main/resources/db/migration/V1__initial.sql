
CREATE TABLE IF NOT EXISTS shopping_cart(
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    data JSONB NOT NULL
);
