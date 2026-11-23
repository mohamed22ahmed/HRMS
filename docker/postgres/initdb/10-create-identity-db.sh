#!/usr/bin/env bash
set -euo pipefail

if [[ -z "${IDENTITY_DB:-}" ]]; then
  echo "IDENTITY_DB must be set to create the identity database" >&2
  exit 1
fi

if [[ "$IDENTITY_DB" == "$POSTGRES_DB" ]]; then
  echo "IDENTITY_DB and POSTGRES_DB must have different values" >&2
  exit 1
fi

psql \
  --set=ON_ERROR_STOP=1 \
  --username "$POSTGRES_USER" \
  --dbname "$POSTGRES_DB" \
  --set=identity_db="$IDENTITY_DB" \
  --command='CREATE DATABASE :"identity_db";'
