# mmj2e of metamath

This is a new experimental mmj2 revision.

- support higher Java versions
- the build system is also updated to catch up with modern Java development.

So far it was only tested on Mac OS.

## Usage

After you change your DefaultParams.txt accordingly, you can issue commands below to build the tool and then run it.

```bash
bin/lein uberjar
java -jar target/mmj2e.jar DefaultParams.txt
```

