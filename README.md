Dynamic DEX code loading over the network
=========================================
Shows an example of dex dynamic class loading. Two dex files are downloaded from
a server, loaded into the VM and classes from the dex files executed.

Explanations in the blog post: 
http://gilvegliach.it/?id=12


Building and running
--------------------
Prerequisites:

1. `local.properties` in the project root with `sdk.dir` set
   (just import the project in Android Studio)
2. Set the path for the `dx` command in the variable `dxCommand` in
  `dexs/build.gradle`, i.e. modify the line:

```
    def dxCommand = '<SDK_DIR>/build-tools/current/dx'
```

Build with:

    ./gradlew build

Install app and run server with

    ./gradlew installDebug bootRun 

Launch app on the device and have fun :)
