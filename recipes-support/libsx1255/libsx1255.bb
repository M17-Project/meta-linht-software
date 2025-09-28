SUMMARY = "C library for controlling the SX1255 radio transceiver chip"
LICENSE = "GPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1ebbd3e34237af26da5dc08a4e440464"

DEPENDS = "libgpiod"

SRC_URI = "git://github.com/M17-Project/libsx1255.git;protocol=https;branch=main"
SRCREV = "${AUTOREV}"
PV = "1.0+git${SRCPV}"

S = "${WORKDIR}/git"

do_compile() {
    # Build it ourselves - just one source file
    ${CC} ${CFLAGS} ${LDFLAGS} -fPIC -shared -o libsx1255.so sx1255.c -lgpiod -lm
    ${CC} ${CFLAGS} -c sx1255.c -o sx1255.o
    ${AR} rcs libsx1255.a sx1255.o
}

do_install() {
    install -d ${D}${libdir}
    install -d ${D}${includedir}
    
    install -m 644 sx1255.h ${D}${includedir}/
    install -m 644 libsx1255.a ${D}${libdir}/
    install -m 755 libsx1255.so ${D}${libdir}/
}

FILES:${PN} = "${libdir}/libsx1255.so"
FILES:${PN}-dev = "${includedir}/* ${libdir}/libsx1255.a"

INSANE_SKIP:${PN} = "dev-so"
