# New mmj2 of metamath

This is a new experimental mmj2 revision for higher version of Java, and the build system is also updated to catch up with modern Java development.

## Usage

After you change your DefaultParams.txt accordingly, you can issue commands below to build the tool and then run it.

```bash
bin/lein uberjar
java -jar target/nmmj2.jar DefaultParams.txt
```

