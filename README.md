# mmj2e of metamath

This is a new experimental mmj2 revision.

- support higher Java versions
- the build system is also updated to catch up with modern Java development
- generate installer by jpackage

So far it was only tested on Mac OS.

## Usage

generate an installer by jpackage

```bash
bin/build
```

Or you just want to build it and run on jvm

```bash
bin/lein uberjar
java -jar target/mmj2e-standalone.jar
```


