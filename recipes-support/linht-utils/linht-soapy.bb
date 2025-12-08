SUMMARY = "SoapySDR driver for LinHT (SX1255 over ZMQ)"
HOMEPAGE = "https://github.com/M17-Project/LinHT-utils"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d41d8cd98f00b204e9800998ecf8427e"

SRC_URI = "git://github.com/M17-Project/LinHT-utils.git;protocol=https;branch=main"
SRCREV = "${AUTOREV}"

# CMakeLists.txt is in tests/soapy_linht
S = "${WORKDIR}/git/tests/soapy_linht"

inherit cmake pkgconfig

DEPENDS = "soapysdr zeromq libsx1255"
RDEPENDS:${PN} = "soapysdr zeromq libsx1255"

EXTRA_OECMAKE += "-DCMAKE_BUILD_TYPE=Release"

FILES:${PN} += " \
    ${libdir}/SoapySDR/modules*/*.so \
"
