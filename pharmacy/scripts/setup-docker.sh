#!/bin/bash
set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
COMPOSE_FILE="$PROJECT_ROOT/docker/docker-compose.yml"

if docker compose version >/dev/null 2>&1; then
	COMPOSE_CMD="docker compose"
else
	if command -v docker-compose >/dev/null 2>&1; then
		COMPOSE_CMD="docker-compose"
	else
		echo "Error: Docker Compose not found. Install Docker Desktop or docker-compose."
		exit 1
	fi
fi

echo "Starting Docker containers using compose file: $COMPOSE_FILE ..."

echo "Bringing down any existing stack (if running)..."
$COMPOSE_CMD -f "$COMPOSE_FILE" down --remove-orphans || true

echo "Launching fresh stack..."
$COMPOSE_CMD -f "$COMPOSE_FILE" up -d --build

echo "Checking Docker containers status..."
$COMPOSE_CMD -f "$COMPOSE_FILE" ps

echo "Docker containers are up and running."
