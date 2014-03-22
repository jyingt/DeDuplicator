# DeDuplicator

## Description
Design and implement an efficient data storage locker that utilizes deduplication. Your locker should be able to receive files and store them (for later retrieval) with a minimum storage by storing some common data blocks only once. For example, a first file containing "This is an example" might store all characters, but a second file containing "This is an example too" might store a pointer to the first file followed by " too".

## Requirements
* Ability to store ten 10MB ASCII files, any two of which differ in at most 5 character edits(1), using at most 20MB of storage.
* Ability to retrieve all files stored in the locker in any order and at any time.
* Your program should not require any live state (i.e., it should be possible to stop and restart the program, even on a different computer, with the storage locker contents in order to reproduce the stored files).
* Command-line User Interface that allows users to insert files into the locker and displays current storage usage. Format should be: `store -file [file] -locker [locker location]`.

## Features
* Ability to store arbitrary binary files.
* Develop a Graphical User Interface that storage progress (and file allocation) in real time.
* Develop networked access to your locker.
* Ability to store directories of files as one entity.
* Develop an Android client for your locker.
* Implement file deletion from your locker.

## Front End
* Java Swing GUI
* Android (?)

## Back End
* File compression
* File similarity checking
* File dissimilarity locator

## Contributors
* Yuteng Pan (sunrisebay)
* Hansen Zhang  (zhswatson)
* Chengxi Yang (vermouth1026)
* John Ying (tienchii)
