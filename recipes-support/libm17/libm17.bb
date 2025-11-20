DESCRIPTION = "LibM17 from Git"
HOMEPAGE = "https://github.com/M17-Project/libm17.git"
LICENSE = "GPL-2.0"

SRCREV = "${AUTOREV}"
SRC_URI = "git://github.com/M17-Project/libm17.git;protocol=https;branch=master"

LIC_FILES_CHKSUM = "file://LICENSE;md5=b234ee4d69f5fce4486a80fdaf4a4263"

S = "${WORKDIR}/git"

inherit cmake
