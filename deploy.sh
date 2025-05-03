#!/bin/bash
# 1. chạy maven clean install bằng tay


# Thoát nếu có lỗi
set -e

# Cấu hình tên image
IMAGE_NAME="my-lazada-app"
TAG="latest"
DOCKER_HUB_USER="myn199x"



# 2. Docker build
echo "🐳 Building Docker image..."
docker build -t $IMAGE_NAME:$TAG .

# 3. Docker tag
echo "🏷️ Tagging image..."
docker tag $IMAGE_NAME:$TAG $DOCKER_HUB_USER/$IMAGE_NAME:$TAG

# 4. Docker push
echo "📤 Pushing image to Docker Hub..."
docker push $DOCKER_HUB_USER/$IMAGE_NAME:$TAG

echo "✅ DONE: Image available at https://hub.docker.com/r/$DOCKER_HUB_USER/$IMAGE_NAME"


# run command local để build và push latest image lên docker hub:
# ./deploy.sh

# server tạo script redeploy.sh với nội dung như trong file redeploy_be.sh và chạy lệnh ./redeploy_be.sh

