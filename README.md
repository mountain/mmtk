# A toolkit of metamath based on mmj2

This is an experimental toolkit of metamath based on mmj2.
It had already offer several basic functions by wrapping mmj2.

Unfortunately, we can not provide binary release due to license issues.
But we provide an easy way to build and install the toolkit, 
please just follow the install section. 

## Concept of workspace

This toolkit is designed to facilitate metamath developing,
and in the center of the design is the concept of a workspace.
git-managed folder or IDE project are examples of similar thing.

A workspace in mmtk is related with one project, and it is
a folder with a special subfolder structure and has a mmtk.yaml file.

The mmtk.yaml file is a configuration of the project.

The subfolders are just follow the convention
- database: mm database, mpeuni.zip file and others
- macros: the js marcos
- params: the RunParms files
- proofs: the proofs by users

## Install
Below instructions are on Linux or MacOS.

Prior to a installation, we assume you had already installed the JDK and git,
latest JDK LTS version is better, other versions may work also, but lack of enough test so far. 

Build first and install, then use anywhere on your computer.
```bash
git clone https://github.com/mountain/mmtk.git
cd mmtk
bin/build
bin/install
```

## Usage

Similar to git, the first step is `init` of a project
```bash
mmtk init myworkspace
```

The default database is set.mm, on case you want to switch to other database
```bash
mmtk init myworkspace -d https://github.com/metamath/set.mm/blob/develop/iset.mm
```

then enter the workspace, start the proof assistant

```bash
cd myworkspace
mmtk pa
```

or you can download the latest version of Metamath Proof Explorer for future usage when you are offline.

```bash
mmtk mpe update
mmtk mpe start
mmtk mpe stop
```

You also can find a way to execute any RunParms files by
```bash
mmtk batch params/RunParms.txt
```





