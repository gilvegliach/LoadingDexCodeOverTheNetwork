Dynamic DEX code loading over the network
=========================================
Shows an example of dex dynamic class loading. Two dex files are downloaded from
a server, loaded into the VM and classes from the dex files executed.

Explanations in the blog post: 
http://gilvegliach.it/?id=12


Building and running
--------------------
Set `dx` command variable in `dexs/build.gradle` to its full path:

    def dxCommand = '<SDK_DIR>/build-tools/current/dx'

Build with:

    ./gradlew build

Install app and run server with

    ./gradlew installDebug bootRun 

Launch app on the device and have fun :)
