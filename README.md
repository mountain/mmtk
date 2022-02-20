# A toolkit of metamath based on mmj2

This is an experimental toolkit of metamath based on mmj2.
It had already offer several basic functions by wrapping mmj2.

## Concept

This toolkit is designed to facility metamath developing,
and in the center of the design is the concept of a workspace.
git-managed folder or IDE project are examples of similar thing.

A workspace in mmtk is related with one project, and it is
a folder with a special subfolder structure and has a mmtk.yaml file.

The mmtk.yaml file is a configuration of the project.

The subfolders are just follow the convention
- database: contains mm database and 
- macros
- params
- proofs

## Install

build first and install, then use anywhere
```bash
bin/build
bin/install
```

## Usage

```bash
mmtk init myworkspace
cd myworkspace
mmtk pa
```

