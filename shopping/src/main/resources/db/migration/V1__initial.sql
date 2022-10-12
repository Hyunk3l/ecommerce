
CREATE TABLE IF NOT EXISTS shopping_cart(
    id UUID PRIMARY KEY,
    user_id UUID UNIQUE NOT NULL,
    data JSONB NOT NULL
);

CREATE TABLE IF NOT EXISTS outbox(
    id UUID NOT NULL,
    type TEXT NOT NULL,
    event JSONB NOT NULL
);

CREATE TABLE IF NOT EXISTS product(
    id UUID unique NOT NULL,
    title TEXT NOT NULL,
    price NUMERIC(12, 6) NOT NULL
);
