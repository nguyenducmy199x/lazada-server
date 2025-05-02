# Deploy

# B1 :
# docker build -t my-lazada-app:latest .
# docker tag my-lazada-app myn199x/my-lazada-app:latest
# docker push myn199x/my-lazada-app:latest

# tạo ssl key 
# sudo keytool -genkeypair -alias myalias -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore /root/keystore.p12 -storepass adminlazada -keypass adminlazada -validity 3650 -dname "CN=Nguyen Duc My, OU=dev team, O=myn, L=Hanoi, ST=Hanoi, C=VN"

# B2 :
# tạo network docker network create my-network

# B3 :
# chạy docker backend 
# docker run -d --name backend --network=my-network  -p 8081:8081 -v /root/keystore.p12:/app/keystore.p12 myn199x/my-lazada-app:latest