SUMMARY = "Simple and easy-to-use library to enjoy videogames programming"
HOMEPAGE = "https://www.raylib.com"
LICENSE = "Zlib"
LIC_FILES_CHKSUM = "file://LICENSE;md5=12bd98761b1577144a8ce71fd2470fb7"

DEPENDS = "libdrm mesa linux-libc-headers"

# Use git with AUTOREV to avoid checksum issues
SRCREV_raylib = "${AUTOREV}"
SRCREV_raygui = "${AUTOREV}"

SRC_URI = "git://github.com/raysan5/raylib.git;protocol=https;branch=master;name=raylib;destsuffix=raylib \
           git://github.com/raysan5/raygui.git;protocol=https;branch=master;name=raygui;destsuffix=raygui"

S = "${WORKDIR}/raylib"

inherit pkgconfig

# Disable network check warnings for AUTOREV
SRCREV_FORMAT = "raylib_raygui"

do_compile() {
    cd ${S}/src
    
    # Fix the hardcoded include path in Makefile
    sed -i 's|-I/usr/include/libdrm|-I${STAGING_INCDIR}/libdrm -I${STAGING_INCDIR}/drm|g' Makefile
    
    oe_runmake PLATFORM=PLATFORM_DRM RAYLIB_LIBTYPE=SHARED \
               CC="${CC}" \
               AR="${AR}" \
               RANLIB="${RANLIB}" \
               LDFLAGS="${LDFLAGS} -L${STAGING_LIBDIR}"
}

do_install() {
    install -d ${D}${libdir}
    install -d ${D}${includedir}
    
    # Install raylib library
    install -m 0644 ${S}/src/libraylib.so.5.5.0 ${D}${libdir}/
    ln -sf libraylib.so.5.5.0 ${D}${libdir}/libraylib.so.5
    ln -sf libraylib.so.5 ${D}${libdir}/libraylib.so
    
    # Install raylib headers
    install -m 0644 ${S}/src/raylib.h ${D}${includedir}/
    install -m 0644 ${S}/src/raymath.h ${D}${includedir}/
    install -m 0644 ${S}/src/rlgl.h ${D}${includedir}/
    install -m 0644 ${S}/src/rcamera.h ${D}${includedir}/
    
    # Install raygui header from separate git repo
    install -m 0644 ${WORKDIR}/raygui/src/raygui.h ${D}${includedir}/
}

# Create separate packages for raygui
PACKAGES += "${PN}-raygui"

FILES:${PN} += "${libdir}/libraylib.so.*"
FILES:${PN}-dev += " \
    ${includedir}/raylib.h \
    ${includedir}/raymath.h \
    ${includedir}/rlgl.h \
    ${includedir}/rcamera.h \
    ${libdir}/libraylib.so \
"
FILES:${PN}-raygui = "${includedir}/raygui.h"

RDEPENDS:${PN}-raygui = "${PN}"
