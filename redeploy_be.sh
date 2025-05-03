#!/bin/bash

IMAGE="myn199x/my-lazada-app:latest"
CONTAINER_NAME="backend"

echo "📥 Pulling latest image..."
docker pull $IMAGE

echo "🛑 Stopping old container..."
docker stop $CONTAINER_NAME || true
docker rm $CONTAINER_NAME || true

echo "🚀 Starting new container..."
docker run -d --name $CONTAINER_NAME -p 8081:8081 $IMAGE

echo "✅ Done: $CONTAINER_NAME is running with latest image"
