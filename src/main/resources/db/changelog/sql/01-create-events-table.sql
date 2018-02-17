CREATE TABLE public.events (
  event_id      TEXT,
  event_version INT,
  timestamp     TIMESTAMP,
  aggreggate_id TEXT,
  payload_type  TEXT,
  payload       TEXT
);