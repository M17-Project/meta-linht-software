SUMMARY = "A satellite orbit prediction library"
DESCRIPTION = "libpredict is a C library for predicting satellite orbits based on TLEs (Two-Line Elements). \
This is a fork of the original la1k/libpredict library with additional features and improvements."
HOMEPAGE = "https://github.com/sp5wwp/libpredict"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263"

SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"

SRC_URI = "git://github.com/sp5wwp/libpredict.git;protocol=https;branch=master"

S = "${WORKDIR}/git"

inherit cmake pkgconfig

DEPENDS = "cmake-native"

# CMake configuration
EXTRA_OECMAKE = " \
    -DCMAKE_BUILD_TYPE=Release \
"

# Package configuration
PACKAGES = "${PN} ${PN}-dev ${PN}-staticdev ${PN}-dbg"

FILES:${PN} = " \
    ${libdir}/libpredict.so.* \
"

FILES:${PN}-dev = " \
    ${includedir}/predict/*.h \
    ${libdir}/libpredict.so \
    ${libdir}/pkgconfig/predict.pc \
    ${libdir}/cmake/libpredict/*.cmake \
"

FILES:${PN}-staticdev = " \
    ${libdir}/libpredict.a \
"

FILES:${PN}-dbg = " \
    ${libdir}/.debug \
    ${prefix}/src/debug \
"

BBCLASSEXTEND = "native nativesdk"
