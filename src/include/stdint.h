#include<version.h>
// Sizes of all numbers
importlib <stdint>

// byte
const BYTE_MIN = -128B
const BYTE_MAX = 127B
// char/unsigned short
const CHAR_MIN = 0C
const CHAR_MAX = 65535C
// short
const SHORT_MIN = -32768S
const SHORT_MAX = 32767S
// int
const INT_MIN = -2147483648I
const INT_MAX = 2147483647I
// long
const LONG_MIN = -9223372036854775808L
const LONG_MAX = 9223372036854775807L

nativefunc tob(Number) from stdint
nativefunc tos(Number) from stdint
nativefunc toi(Number) from stdint
nativefunc tol(Number) from stdint
nativefunc tof(Number) from stdint
nativefunc tod(Number) from stdint
nativefunc toa(Number) from stdint