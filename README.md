# Spring Boot RestTemplate with Outbound SSL
## Server keystore
Generate self-signed PKCS12 cert, password 123456  
(Need to provide localhost as a subject alternative name when creating the certificate)  

keytool -genkeypair -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore globekeystore.p12 -dname  \CN=*.globe.com,OU=Information Services,O=Globe Inc.,C=CA" -ext "SAN:c=DNS:localhost,IP:127.0.0.1"  -validity 365 -alias globekey -storepass 123456 -keypass 123456  

keytool -v -list -keystore globekeystore.p12 -storepass 123456

## Client keystore
keytool -genkeypair -keyalg RSA -keysize 2048 -storetype JKS -keystore abcdkeystore.jks -dname "CN=*.abcd.com,OU=Consulting,O=ABC Co.,C=CA" -ext "SAN:c=DNS:localhost,IP:127.0.0.1"  -validity 365 -alias abcdkey -storepass 123456 -keypass 123456

## Export server certificate
keytool -export -alias globekey  -keystore globekeystore.p12 -storepass 123456 -rfc -file public.cert

## Import server certificate into client truststore:
keytool -import -alias globekey -file public.cert -storetype JKS -keystore abcdtruststore.jks -storepass 123456  

keytool -v -list -keystore abcdtruststore.jks -storepass 123456

## Build Server and Client applications
mvn clean package

## Start Server and Client spplications

## Browser URL
https://localjost:8080/

## Two-way ssl (application-2way.yml)
Add below properties to application.yml  

    trust-store: classpath:globetruststore.p12  
    trust-store-password: 123456  
    trust-store-type: PKCS12  
    client-auth: need  

## Export client cert and import it to server truststore
keytool -export -alias abcdkey  -keystore abcdkeystore.jks -storepass 123456 -rfc -file client.cert  

keytool -import -alias abcdkey -file client.cert -storetype PKCS12 -keystore globetruststore.p12 -storepass 123456
