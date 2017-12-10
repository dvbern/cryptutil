# HOWTO: create reference keys and signatures

## Private/Public Keys

### Create private key
openssl genrsa -out mykey.pem 4096
Java needs the certificate in PKCS#8 format, so conver it:
openssl pkcs8 -topk8 -inform PEM -in mykey.pem -out mykey-pkcs8.pem -nocrypt


### Extract certificate/public key from private key
openssl rsa -in meykey.pem -pubout > mykey.pub


##Reference signatures created with:
openssl dgst -sha256 -sign mykey.pem -out foo.txt.sha256 foo.txt 
openssl dgst -sha256 -verify mykey.pub -signature foo.txt.sha256 foo.txt
=> Verified OK

Source:
https://stackoverflow.com/questions/5140425/openssl-command-line-to-verify-the-signature


