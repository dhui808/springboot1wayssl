# Spring Boot RestTemplate with Outbound SSL
## Server keystore
Generate self-signed PKCS12 cert, password 123456 \
(Need to provide localhost as a subject alternative name when creating the certificate)
keytool -genkeypair -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore globekeystore.p12 -dname  \CN=*.globe.com,OU=Information Services,O=Globe Inc.,C=CA" -ext "SAN:c=DNS:localhost,IP:127.0.0.1"  -validity 365 -alias globekey -storepass 123456 -keypass 123456 \
keytool -v -list -keystore globekeystore.p12 -storepass 123456

## Client keystore
keytool -genkeypair -keyalg RSA -keysize 2048 -storetype JKS -keystore abcdkeystore.jks -dname "CN=*.abcd.com,OU=Consulting,O=ABC Co.,C=CA" -ext "SAN:c=DNS:localhost,IP:127.0.0.1"  -validity 365 -alias abcdkey -storepass 123456 -keypass 123456

## Export server certificate
keytool -export -alias globekey  -keystore globekeystore.p12 -storepass 123456 -rfc -file public.cert

## Import server certificate into client truststore:
keytool -import -alias globekey -file public.cert -storetype JKS -keystore abcdtruststore.jks -storepass 123456 \
keytool -v -list -keystore abcdtruststore.jks -storepass 123456

## Build Server and Client applications
mvn clean package

## Start Server and Client spplications

## Browser URL
https://localjost:8080/

