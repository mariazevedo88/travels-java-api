CREATE TABLE rate (
  rate_key VARCHAR(255) NOT NULL,
  remaining BIGINT,
  remaining_quota BIGINT,
  reset BIGINT,
  expiration TIMESTAMP,
  PRIMARY KEY(rate_key)
);