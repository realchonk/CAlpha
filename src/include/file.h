#include<version.h>
importlib<file>

const FILE_READ = 0
const FILE_WRITE = 1
const FILE_APPEND = 2

nativefunc fopen(String, int) from file
nativefunc fclose(File) from file
nativefunc fcloseall() from file
nativefunc fflush(File) from file
nativefunc fflushall() from file
nativefunc fdelete(File) from file

nativefunc fread(File) from file
nativefunc fwrite(File, String) from file
nativefunc fappend(File, String) from file
nativefunc fputc(File, char) from file
nativefunc fputi(File, int) from file