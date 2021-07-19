# How to use CryptoBucks's Android SDK 
 
## Getting your Merchant's API Key
 
To get your `API_KEY` you will need to register on [our website](https://aliantpayments.com/payment-gateway) 
 
## Using the SDK 
 
Our SDK is very simple to use, you only need to: 
- Declare a variable 
- Add the context 
- Add your `API_KEY `
 
**That's it!!!** Our SDK will handle everything else. 
 
### Example: 
```
val sdk = cryptoBucksSdk(this,'YOUR_API_KEY') 
 
// Call sdk.launch() whenever you whant to start the payment's flow
sdk.launch()  
``` 
 
## Project Configuration 
 
### On your Manifest Configuration 
 
``` 
<uses-permission android:name="android.permission.INTERNET"/> 
``` 
 
### On your build.gradle (Module) 
 
``` 
packagingOptions  {
    exclude 'META-INF/LICENSE'
    exclude 'META-INF/LICENSE-FIREBASE.txt'
    exclude 'META-INF/NOTICE'
    exclude 'lib/x86_64/darwin/libscrypt.dylib'
    exclude 'lib/x86_64/freebsd/libscrypt.so'
    exclude 'lib/x86_64/linux/libscrypt.so'   
    exclude 'META-INF/DEPENDENCIES'    
} 
``` 
 
### Implementation 
 
`implementation 'com.github.Cryptobucksapp:Cryptobucksapp: 1.0.0` 
 
### On your build.gradle (APP) 
 
Add the **jitpack.io** dependency 
 
``` 
allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter() // Warning: this repository is going to shut down soon
        maven { url "https://jitpack.io" }
    }
}

``` 
 
**NOTE: CryptoBucks's SDK needs API 21 in order to work** 

