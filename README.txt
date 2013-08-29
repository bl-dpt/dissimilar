Dissimilar 0.5-SNAPSHOT
=======================
A Java program for calculating PSNR and SSIM.  By default a jar for the GUI is built.
 
Requirements:
* JDK7 with JavaFX 
* Apache-commons imaging 1.0-SNAPSHOT needs to be packaged and installed to your Maven repository (snapshot 
repository now added to pom)
* lots of RAM!
* To load JP2 files, OpenJPEG binaries need to be installed or stored in the resources folder (opj_decompress or j2k_to_image).  

Caveats:
* Whilst the code has seen some very light testing, it should not yet be trusted in a production environment
* To get JP2 loading working some editing of code is required 

Usage:
* The GUI expects a CSV file without headers, with two image files per line: e.g. "fileone, filetwo,"